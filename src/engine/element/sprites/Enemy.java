package engine.element.sprites;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import pathsearch.graph.PathCell;
import pathsearch.pathalgorithms.NoPathExistsException;
import pathsearch.pathalgorithms.ObstacleFunction;
import pathsearch.wrappers.GridWrapper;
import engine.InsufficientParametersException;


/**
 * This class represents a sprite that moves across the screen and shoots/is shot at by towers. The
 * Enemy object is the primary one which can damage a player's health as it move through the map.
 * Some may also be able to attack towers.
 * 
 * @author Qian Wang
 *
 */
public class Enemy extends MoveableSprite {

	private List<GridCell> myPath;
	
    public Enemy () throws InsufficientParametersException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void collide (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void move () {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isTargetableBy (String type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCollidableWith (String type) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Uses pathfinding to find a new optimal path
     * @param grid
     * @throws NoPathExistsException 
     * @throws InsufficientParametersException 
     */
    public void updatePath(GridCell[][] grid, String type, int goalRow, int goalCol) throws NoPathExistsException, InsufficientParametersException{
    	GridWrapper wrap = new GridWrapper();
    	wrap.initializeGraph(grid, new ObstacleFunction() {
			@Override
			public boolean isObstacle(Object o) {
				GridCell cell = (GridCell)o;
				return cell.isObstacle(type);
			}
    	});
    	List<PathCell> coordPath = wrap.shortestPath((int)super.getLocationY(), (int)super.getLocationX(), goalRow, goalCol);
    	List<GridCell> gridPath = new ArrayList<>();
    	for (PathCell coord: coordPath){
    		GridCell cell = new GridCell();
    		cell.setLocation(coord.getCol(), coord.getRow());
    		gridPath.add(cell);
    	}
    	myPath = gridPath;
    }

}
