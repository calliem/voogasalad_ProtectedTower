package engine.element.sprites;

import java.util.ArrayList;
import java.util.List;
import annotations.parameter;
import javafx.animation.PathTransition;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import util.pathsearch.graph.PathCell;
import util.pathsearch.pathalgorithms.NoPathExistsException;
import util.pathsearch.pathalgorithms.ObstacleFunction;
import util.pathsearch.wrappers.GridWrapper;
import engine.InsufficientParametersException;


/**
 * This class represents a sprite that moves across the screen and shoots/is shot at by towers. The
 * Enemy object is the primary one which can damage a player's health as it move through the map.
 * Some may also be able to attack towers.
 * 
 * @author Qian Wang
 *
 */

public class Enemy extends GameSprite {

    @parameter(settable = false, playerDisplay = true, defaultValue = "false")
    private Boolean CanHurtPlayer;

    private List<GridCell> myPath;
    private static final double MOVE_DURATION = 1000;

    public Enemy () {
        super();
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide (Sprite sprite) {
        // TODO write collide methods
        // super.decreaseHealth(sprite.getDamage());
    }

    @Override
    public void move () {
        Path path = new Path();
        for (GridCell cell : myPath) {
            path.getElements().add(new MoveTo(cell.getCenterX(), cell.getCenterY()));
        }
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(MOVE_DURATION * (myPath.size()) /
                                                   super.getSpeed()));
        pathTransition.setPath(path);
        pathTransition.setNode(super.getImageView());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
    }

    /**
     * Uses pathfinding to find a new optimal path
     * 
     * @param grid
     * @throws NoPathExistsException
     * @throws InsufficientParametersException
     */
    public void updatePath (GridCell[][] grid,
                            String type,
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
        myPath = gridPath;
    }

    @Override
    public void update (int counter) {
    }

}
