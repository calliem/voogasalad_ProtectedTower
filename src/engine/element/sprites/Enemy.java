package engine.element.sprites;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import annotations.parameter;
import authoringEnvironment.pathing.CurveCoordinates;
import javafx.animation.PathTransition;
import javafx.scene.shape.CubicCurveTo;
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
 * @author Sean Scott
 * @author Greg McKeon
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
    public void onCollide (GameElement element) {
        // TODO write collide methods
        // super.decreaseHealth(sprite.getDamage());
    }
    
    public void poison (int damage, int duration){
    	Timer timer = new Timer();
    	TimerTask poison = new TimerTask(){
			@Override
			public void run() {
				decreaseHealth(damage);
			}
    	};
    	timer.schedule(poison, 1000, 1000*duration);
    }
    
    
    protected void decreaseHealth (Integer amount) {
        super.decreaseHealth(amount);
    }

    @Override
    public void move () {
    	Path path = pathPlanned();
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(MOVE_DURATION * (myPath.size()) / super.getSpeed()));
        pathTransition.setPath(path);
        pathTransition.setNode(super.getImageView());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
    }
    
    public Path pathBezier (List<CurveCoordinates> curves){
    	Path path = new Path();
        for (CurveCoordinates curve : curves) {
        	double Control1X = curve.getControl1Coordinate().getX();
        	double Control1Y = curve.getControl1Coordinate().getY();
        	double Control2X = curve.getControl2Coordinate().getX();
        	double Control2Y = curve.getControl2Coordinate().getY();
        	double EndX = curve.getEndCoordinate().getX();
        	double EndY = curve.getEndCoordinate().getY();
            path.getElements().add(new CubicCurveTo(Control1X, Control1Y, Control2X, Control2Y, EndX, EndY));
        }
        return path;
    }
    
    public Path pathPlanned(){
    	Path path = new Path();
        for (GridCell cell : myPath) {
            path.getElements().add(new MoveTo(cell.getCenterX(), cell.getCenterY()));
        }
        return path;
    }

    /**
     * Uses pathfinding to find a new optimal path
     * 
     * @param grid
     * @throws NoPathExistsException
     * @throws InsufficientParametersException
     */
    public void updatePath (GridCell[][] grid,
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
