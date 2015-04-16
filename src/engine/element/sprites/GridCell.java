package engine.element.sprites;

import java.util.List;
import javafx.geometry.Point2D;


/**
 * This class represents a grid cell on the board for a tower defense game. The grid contains a type
 * which identifies it as a certain type, so the game knows what behaviors may occur on a specific
 * grid cell,
 * 
 * @author Qian Wang
 *
 */
public class GridCell extends Sprite {

    private List<String> myTags;
    private Point2D centerLocation;

    /**
     * Sets the center of grid cell
     * 
     * @param location Point2D object representing (x, y) coordinates
     */
    public void setCenter (Point2D location) {
        centerLocation = location;
    }

    /**
     * Sets the center of grid cell
     * 
     * @param x double of x-coordinate
     * @param y double of y-coordinate
     */
    public void setCenter (double x, double y) {
        centerLocation = new Point2D(x, y);
    }

    /**
     * @return Point2D representing coordinate location of center of cell
     */
    public Point2D getCenter () {
        return new Point2D(centerLocation.getX(), centerLocation.getY());
    }

    public double getCenterX () {
        return centerLocation.getX();
    }

    public double getCenterY () {
        return centerLocation.getY();
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

    public boolean isObstacle (String type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void collide (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub
        
    }
}
