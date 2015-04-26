package authoringEnvironment.pathing;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import authoringEnvironment.objects.Coordinate;
import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.pathing.BoundLine;


public class PathCreator {
    private static final double CONTROL_POINT_LOCATION_MULTIPLIER = 0.2;
    private TileMap myParent;
    private List<PathView> myPaths;

    public PathCreator (TileMap parent) {
        myParent = parent;
    }

    public void createCurve (double startX, double startY, double endX, double endY) {
        CubicCurve curve = createStartingCurve(startX, startY, endX, endY);

        // int parentWidth = parent.
        // int parentHeight =

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
        
        Coordinate startCoordinates = start.getCoordinates();
        Coordinate endCoordinates = end.getCoordinates();
        Coordinate ctrl1Coordinates = control1.getCoordinates();
        Coordinate ctrl2Coordinates = control2.getCoordinates();
        
        PathView pathView = new PathView(startCoordinates, endCoordinates, ctrl1Coordinates, ctrl2Coordinates);
        myPaths.add(pathView);
        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        myParent.getRoot().getChildren().add(path);
    }

    private CubicCurve createStartingCurve (double startX, double startY, double endX, double endY) {
        CubicCurve curve = new CubicCurve();

        // double lineLength = Math.sqrt(Math.pow(startX-endX, 2) + Math.pow(startY-endY, 2));
        double xDiff = endX - startX;
        double yDiff = endY - startY;

        double controlX1 = startX + xDiff * CONTROL_POINT_LOCATION_MULTIPLIER;
        double controlY1 = startY + yDiff * CONTROL_POINT_LOCATION_MULTIPLIER;

        double controlX2 = endX - xDiff * CONTROL_POINT_LOCATION_MULTIPLIER;
        double controlY2 = endY - yDiff * CONTROL_POINT_LOCATION_MULTIPLIER;


        curve.setStartX(startX);
        curve.setStartY(startY);
        curve.setControlX1(controlX1);
        curve.setControlY1(controlY1);
        curve.setControlX2(controlX2);
        curve.setControlY2(controlY2);
        curve.setEndX(endX);
        curve.setEndY(endY);
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        return curve;
    }
    
    public void save(){
        //TODO: save 
    }
}
