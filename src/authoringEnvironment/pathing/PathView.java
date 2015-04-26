package authoringEnvironment.pathing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.Coordinate;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.pathing.BoundLine;


public class PathView {
    private static final double CONTROL_POINT_LOCATION_MULTIPLIER = 0.2;
    private static final int DEFAULT_STROKE_WIDTH = 4;
    private Pane myParent;
    private List<Curve> myPaths;
    private String myName;
    private int numAnchors;
    private Anchor mostRecentAnchor;
    
    private Group myRoot;
    
    private int boundingWidth; 
    private int boundingHeight;

    public PathView (Pane parent, int width, int height) {
        myParent = parent;
        myRoot = new Group();
        myPaths = new ArrayList<Curve>();
        numAnchors = 0;
        boundingWidth = width;
        boundingHeight = height;
        myParent.getChildren().add(myRoot);
        Rectangle myMapOverlay =
                new Rectangle(width, height);
        myMapOverlay.setOpacity(0.2);
        myRoot.getChildren().add(myMapOverlay);

    }
    
    public int getNumAnchors(){
        return numAnchors;
    }
    
    

    public void createCurve (double startX, double startY, double endX, double endY) {
        // TODO: remove this method
        CubicCurve curve = createStartingCurve(startX, startY, endX, endY);

        Line controlLine1 =
                new BoundLine(curve.controlX1Property(), curve.controlY1Property(),
                              curve.startXProperty(), curve.startYProperty());
        Line controlLine2 =
                new BoundLine(curve.controlX2Property(), curve.controlY2Property(),
                              curve.endXProperty(), curve.endYProperty());

        Anchor start =
                new Anchor(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty(),
                           boundingWidth, boundingHeight);
        Anchor control1 =
                new Anchor(Color.GOLD, curve.controlX1Property(), curve.controlY1Property(),
                           boundingWidth, boundingHeight);
        Anchor control2 =
                new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property(),
                           boundingWidth, boundingHeight);
        Anchor end =
                new Anchor(Color.TOMATO, curve.endXProperty(), curve.endYProperty(),
                           boundingWidth, boundingHeight);

       /* Coordinate startCoordinates = start.getCoordinates();
        Coordinate endCoordinates = end.getCoordinates();
        Coordinate ctrl1Coordinates = control1.getCoordinates();
        Coordinate ctrl2Coordinates = control2.getCoordinates();

        Curve pathView =
                new Curve(startCoordinates, endCoordinates, ctrl1Coordinates, ctrl2Coordinates);
        myPaths.add(pathView);*/

        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        myRoot.getChildren().add(path);
    }

    public void addAnchor (double startX, double startY) {
        DoubleProperty startXProperty = new SimpleDoubleProperty(startX);
        DoubleProperty startYProperty = new SimpleDoubleProperty(startY);
        
        Anchor anchor =
                new Anchor(Color.PALEGREEN, startXProperty, startYProperty,
                           boundingWidth, boundingHeight);
        myRoot.getChildren().add(anchor);
        
        if (numAnchors > 0)
            createCurve(mostRecentAnchor, anchor);
        
        mostRecentAnchor = anchor;
        numAnchors ++;
    }

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
                           boundingWidth, boundingHeight);
        Anchor control2 =
                new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property(),
                           boundingWidth, boundingHeight);

     /*   Coordinate startCoordinates = start.getCoordinates();
        Coordinate endCoordinates = end.getCoordinates();
        Coordinate ctrl1Coordinates = control1.getCoordinates();
        Coordinate ctrl2Coordinates = control2.getCoordinates();

        Curve pathView =
                new Curve(startCoordinates, endCoordinates, ctrl1Coordinates, ctrl2Coordinates);
        myPaths.add(pathView);*/

        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        myRoot.getChildren().add(path);
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
        curve.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        //curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        curve.setFill(Color.TRANSPARENT);
        return curve;
    }

    public void setName (String name) {
        myName = name;
    }

    public Map<String, Object> save () {
        // TODO: getName
        String name = "temporary"; // testing only TODO: remove
        Map<String, Object> settings = new HashMap<String, Object>();
        settings.put(InstanceManager.NAME_KEY, myName);
        settings.put(Variables.PARAMETER_CURVES, myPaths);
        return settings;
    }
}
