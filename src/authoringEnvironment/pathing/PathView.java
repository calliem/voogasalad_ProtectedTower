package authoringEnvironment.pathing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.Coordinate;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.pathing.BoundLine;


public class PathView {
    private static final double CONTROL_POINT_LOCATION_MULTIPLIER = 0.2;
    private static final int DEFAULT_STROKE_WIDTH = 4;
    private TileMap myParent;
    private List<Curve> myPaths;
    private String myName;
    private int numPoints;
    private Anchor mostRecentPoint;
    private List<Anchor> myAnchors; 

    public PathView (TileMap parent) {
        myAnchors = new ArrayList<Anchor>();
        myParent = parent;
        myPaths = new ArrayList<Curve>();
        numPoints = 0;
    }
    
    public int getNumPoints(){
        return numPoints;
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
        
        myAnchors.add(control1);
        myAnchors.add(control2);


       /* Coordinate startCoordinates = start.getCoordinates();
        Coordinate endCoordinates = end.getCoordinates();
        Coordinate ctrl1Coordinates = control1.getCoordinates();
        Coordinate ctrl2Coordinates = control2.getCoordinates();

        Curve pathView =
                new Curve(startCoordinates, endCoordinates, ctrl1Coordinates, ctrl2Coordinates);
        myPaths.add(pathView);*/

        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        myParent.getRoot().getChildren().add(path);
    }
    
    public boolean areAnchorsSelected(){
        for (Anchor anchor: myAnchors){
            System.out.print(anchor);
            System.out.println(" " + anchor.isSelected() + " " );
            if (anchor.isSelected()) 
                return true;
        }
        return false;
    }

    public void addAnchor (double startX, double startY) {
        DoubleProperty startXProperty = new SimpleDoubleProperty(startX);
        DoubleProperty startYProperty = new SimpleDoubleProperty(startY);
        
        System.out.println("ADDANCHOR");
        
        Anchor anchor =
                new Anchor(Color.PALEGREEN, startXProperty, startYProperty,
                           myParent.getWidth(), myParent.getHeight());
        
        myParent.getRoot().getChildren().add(anchor);
        
        
        if (numPoints > 0){
            //Anchor mostRecentAnchor = myAnchorsandControls.get(myAnchorsandControls.size()-1);
            createCurve(mostRecentPoint, anchor);
        }
        
        Text num = new Text(anchor.getCenterX(), anchor.getCenterY(), Integer.toString(numPoints));
        myParent.getRoot().getChildren().add(num);
        mostRecentPoint = anchor;
        myAnchors.add(anchor);
        
        numPoints++;
        
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
                           myParent.getWidth(), myParent.getHeight());
        Anchor control2 =
                new Anchor(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property(),
                           myParent.getWidth(), myParent.getHeight());

     /*   Coordinate startCoordinates = start.getCoordinates();
        Coordinate endCoordinates = end.getCoordinates();
        Coordinate ctrl1Coordinates = control1.getCoordinates();
        Coordinate ctrl2Coordinates = control2.getCoordinates();

        Curve pathView =
                new Curve(startCoordinates, endCoordinates, ctrl1Coordinates, ctrl2Coordinates);
        myPaths.add(pathView);*/

        myAnchors.add(control1);
        myAnchors.add(control2);
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
