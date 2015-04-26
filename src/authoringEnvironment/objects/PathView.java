package authoringEnvironment.objects;

import java.util.ArrayList;
import authoringEnvironment.objects.Coordinate;


/**
 * Class containing all coordinates for the properties of a single Bezier curve path. This includes
 * its start coordinate, end coordinate, and the coordinates of the two control points.
 * 
 * @author Callie Mao
 *
 */

public class PathView {

    private Coordinate startCoordinate;
    private Coordinate endCoordinate;
    private Coordinate control1Coordinate;
    private Coordinate control2Coordinate;

    public PathView (Coordinate start, Coordinate end, Coordinate ctrl1, Coordinate ctrl2) {
        startCoordinate = start;
        endCoordinate = end;
        control1Coordinate = ctrl1;
        control2Coordinate = ctrl2;
    }

    public Coordinate getStartCoordinate () {
        return startCoordinate;
    }

    public Coordinate getEndCoordinate () {
        return endCoordinate;
    }

    public Coordinate getControl1Coordinate () {
        return control1Coordinate;
    }

    public Coordinate getControl2Coordinate () {
        return control2Coordinate;
    }

}
