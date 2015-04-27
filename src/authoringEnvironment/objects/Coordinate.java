package authoringEnvironment.objects;

/**
 * Data structure that stores x- and y-coordinates on a Cartesian plane.
 * 
 * @author Callie Mao
 *
 */
public class Coordinate {

    private double myX;
    private double myY;

    public Coordinate (double x, double y) {
        myX = x;
        myY = y;
    }

    public double getX () {
        return myX;
    }

    public double getY () {
        return myY;
    }
}
