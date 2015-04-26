package authoringEnvironment.pathing;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import authoringEnvironment.objects.Coordinate;


/**
 * Class containing all coordinates for the properties of a single Bezier curve path. This includes
 * its start coordinate, end coordinate, and the coordinates of the two control points.
 * 
 * @author Callie Mao
 *
 */

public class Curve extends Group {

    private CurveCoordinates myCoordinates;

    /*
     * public Curve (Coordinate start, Coordinate end, Coordinate ctrl1, Coordinate ctrl2) {
     * startCoordinate = start;
     * endCoordinate = end;
     * control1Coordinate = ctrl1;
     * control2Coordinate = ctrl2;
     * }
     */
    
    public void createCurve (Anchor start, Anchor end) {
        CubicCurve curve =
                createStartingCurve(start.getCenterX(), start.getCenterY(), end.getCenterX(),
                                    end.getCenterY());

        Line controlLine1 =
                new BoundLine(curve.controlX1Property(), curve.controlY1Property(),
                              curve.startXProperty(), curve.startYProperty());
        Line controlLine2 =
                new BoundLine(curve.controlX2Property(), curve.controlY2Property(),
                              curve.endXProperty(), curve.endYProperty());
        Anchor control1 =
                new Anchor(Color.GOLD, curve.controlX1Property(), curve.controlY1Property(),
                           myParent.getWidth(), myParent.getHeight());
        Anchor control2 =
                new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property(),
                           myParent.getWidth(), myParent.getHeight());

        Coordinate startCoordinates = start.getCoordinates();
        Coordinate endCoordinates = end.getCoordinates();
        Coordinate ctrl1Coordinates = control1.getCoordinates();
        Coordinate ctrl2Coordinates = control2.getCoordinates();

        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        myParent.getRoot().getChildren().add(path);
    }

    public Curve (double startX, double startY, double endX, double endY) {
        CubicCurve curve = createStartingCurve(startX, startY, endX, endY);

        Line controlLine1 =
                new BoundLine(curve.controlX1Property(), curve.controlY1Property(),
                              curve.startXProperty(), curve.startYProperty());
        Line controlLine2 =
                new BoundLine(curve.controlX2Property(), curve.controlY2Property(),
                              curve.endXProperty(), curve.endYProperty());

        Anchor start =
                new Anchor(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty(),
                           myParent.getWidth(), myParent.getHeight());
        Anchor control1 =
                new Anchor(Color.GOLD, curve.controlX1Property(), curve.controlY1Property(),
                           myParent.getWidth(), myParent.getHeight());
        Anchor control2 =
                new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property(),
                           myParent.getWidth(), myParent.getHeight());
        Anchor end =
                new Anchor(Color.TOMATO, curve.endXProperty(), curve.endYProperty(),
                           myParent.getWidth(), myParent.getHeight());

        myCoordinates =
                new CurveCoordinates(start.getCoordinates(), end.getCoordinates(),
                                     control1.getCoordinates(), control2.getCoordinates());

        getChildren().addAll(controlLine1, controlLine2, curve, start, control1,
                             control2, end);
    }

    public CurveCoordinates getCurveCoordinates () {
        return myCoordinates;
    }
}
