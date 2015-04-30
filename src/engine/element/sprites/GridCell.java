package engine.element.sprites;

import java.util.Map;
import javafx.geometry.Point2D;


/**
 * This class represents a grid cell on the board for a tower defense game. The grid contains a type
 * which identifies it as a certain type, so the game knows what behaviors may occur on a specific
 * grid cell,
 * 
 * @author Qian Wang
 *
 */
public class GridCell extends GameElement {

    public GridCell () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
    }

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

    public boolean isObstacle (String type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCollide (GameElement element) {

    }

    @Override
    public void fixField (String fieldToModify, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setField (String fieldToModify, String value, Double duration) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeField (String fieldToModify, String value, Double duration) {
        // TODO Auto-generated method stub
        
    }

}
