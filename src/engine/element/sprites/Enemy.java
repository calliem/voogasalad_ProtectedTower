package engine.element.sprites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import util.pathsearch.graph.PathCell;
import util.pathsearch.pathalgorithms.NoPathExistsException;
import util.pathsearch.pathalgorithms.ObstacleFunction;
import util.pathsearch.wrappers.GridWrapper;
import annotations.parameter;
import authoringEnvironment.objects.Coordinate;
import engine.InsufficientParametersException;


/**
 * This class represents a sprite that moves across the screen and shoots/is shot at by towers. The
 * Enemy object is the primary one which can damage a player's health as it move through the map.
 * Some may also be able to attack towers.
 * 
 * @author Qian Wang
 * @author Sean Scott
 * @author Greg McKeon
 *
 */

public class Enemy extends GameSprite {

    private static final int MOVE_DELAY = 1000;

    @parameter(settable = true, playerDisplay = true, defaultValue = "false")
    private Boolean CanHurtPlayer;

    private Path myPath;
    private double myPathLength;
    private List<GridCell> myGridPath;
    private static final double MOVE_DURATION = 1000;

    public Enemy () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        CanHurtPlayer = (Boolean) parameters.get("CanHurtPlayer");
    }

    public GridCell getGoal () {
        return myGridPath.get(myGridPath.size() - 1);
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide (GameElement element) {
        // TODO write collide methods
        // super.decreaseHealth(sprite.getDamage());
    }

    @Override
    public Double getSpeed () {
        return super.getSpeed() * .1;
    }

    /**
     * Adds a poison modifier to the enemy so it loses health for a set duration
     * 
     * @param damage the amount of damage the enemy should lose
     * @param duration the amount of time damage should be lost
     */
    public void poison (double damage, double duration) {
        Timer timer = new Timer();
        TimerTask poison = new TimerTask() {
            @Override
            public void run () {
                decreaseHealth(damage);
            }
        };
        timer.schedule(poison, MOVE_DELAY, (long) (MOVE_DURATION * duration));
    }

    protected void decreaseHealth (Double amount) {
        super.decreaseHealth(amount);
    }

    @Override
    public void move () {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(MOVE_DURATION * myPathLength / getSpeed()));
        pathTransition.setPath(myPath);
        pathTransition.setNode(super.getImageView());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
    }

    /**
     * Takes a predefined path using Bezier curves
     * 
     * @param curveCoords
     */
    public void bezierPath (List<Coordinate> curveCoords) {
        Path path = new Path();
        MoveTo initial = new MoveTo();
        initial.setX(curveCoords.get(0).getX());
        initial.setY(curveCoords.get(0).getY());
        path.getElements().add(initial);
        for (int i = 1; i < curveCoords.size() - 2; i += 3) {
            path.getElements().add(new CubicCurveTo(curveCoords.get(i).getX(),
                                                    curveCoords.get(i).getY(),
                                                    curveCoords.get(i + 1).getX(),
                                                    curveCoords.get(i + 1).getY(),
                                                    curveCoords.get(i + 2).getX(),
                                                    curveCoords.get(i + 2).getY()));
        }
        myPath = path;
        myPathLength = (curveCoords.size() - 1) / 3;
        move();
    }

    /**
     * Uses pathfinding to find a new optimal path
     * 
     * @param grid
     * @throws NoPathExistsException
     * @throws InsufficientParametersException
     */
    public void planPath (GridCell[][] grid,
                          int startRow,
                          int startCol,
                          int goalRow,
                          int goalCol) throws NoPathExistsException {
        GridWrapper wrap = new GridWrapper();
        wrap.initializeGraph(grid, new ObstacleFunction() {
            @Override
            public boolean isObstacle (Object o) {
                GridCell cell = (GridCell) o;
                return false;
            }
        });
        List<PathCell> coordPath = wrap.shortestPath(startRow, startCol, goalRow, goalCol);
        List<GridCell> gridPath = new ArrayList<>();
        for (PathCell coord : coordPath) {
            gridPath.add(grid[coord.getRow()][coord.getCol()]);
        }
        myGridPath = gridPath;
        Path path = new Path();
        for (GridCell cell : myGridPath) {
            path.getElements().add(new MoveTo(cell.getCenterX(), cell.getCenterY()));
        }
        myPath = path;
        myPathLength = myGridPath.size();
        move();
    }

    @Override
    public Map<Object, List<String>> update () {
        move();
        Map<Object, List<String>> spawnMap = new HashMap<Object, List<String>>();
        if (this.getHealth() == 0) {
            spawnMap.put(this.getLocation(), this.getNextSprites());
        }
        return spawnMap;
    }

}
