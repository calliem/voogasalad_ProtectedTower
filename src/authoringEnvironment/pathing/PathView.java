package authoringEnvironment.pathing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.Coordinate;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.util.Screenshot;


/**
 * PathView displays the path consisting of anchors, curves (bound lines), and adds them to the
 * parent
 * 
 * @author Callie Mao
 *
 */
public class PathView extends GameObject {
    private static final double CONTROL_POINT_LOCATION_MULTIPLIER = 0.2;
    private static final int DEFAULT_STROKE_WIDTH = 4;
    private static final double MAP_OPACITY_ACTIVATED = 0.2;
    private static final String NUMBER_ANCHOR_POINTS = "\nNumber of anchor points: ";
    private TileMap myParent;
    private int numPoints;
    private Anchor mostRecentPoint;
    private List<Anchor> myAnchors;
    private Group myRoot;

    public PathView (TileMap parent) {
        myAnchors = new ArrayList<Anchor>();
        myParent = parent;

        numPoints = 0;
        myRoot = new Group();
        Rectangle pathModeOverlay =
                new Rectangle(parent.getWidth(), parent.getHeight());
        pathModeOverlay.setOpacity(MAP_OPACITY_ACTIVATED);
        StackPane.setAlignment(pathModeOverlay, Pos.CENTER);
        myRoot.getChildren().add(pathModeOverlay);
        myParent.getRoot().getChildren().addAll(myRoot);
    }

    public int getNumPoints () {
        return numPoints;
    }

    public boolean areAnchorsSelected () {
        for (Anchor anchor : myAnchors) {
            if (anchor.isSelected())
                return true;
        }
        return false;
    }

    public void addAnchor (double startX, double startY) {
        DoubleProperty startXProperty = new SimpleDoubleProperty(startX);
        DoubleProperty startYProperty = new SimpleDoubleProperty(startY);

        Anchor anchor =
                new Anchor(Color.PALEGREEN, startXProperty, startYProperty,
                           myParent.getWidth(), myParent.getHeight());

        myParent.getRoot().getChildren().add(anchor);

        if (numPoints > 0) {

            createCurve(mostRecentPoint, anchor);
        }

        Text num = new Text(Integer.toString(numPoints));
        num.xProperty().bind(anchor.centerXProperty());
        num.yProperty().bind(anchor.centerYProperty());
        myRoot.getChildren().add(num);

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

        curve.startXProperty().bind(start.centerXProperty());
        curve.endXProperty().bind(end.centerXProperty());

        curve.startYProperty().bind(start.centerYProperty());
        curve.endYProperty().bind(end.centerYProperty());

        myAnchors.add(control1);

        myAnchors.add(control2);

        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        myRoot.getChildren().add(path);
    }

    private CubicCurve createStartingCurve (double startX, double startY, double endX, double endY) {
        CubicCurve curve = new CubicCurve();

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
        curve.setFill(Color.TRANSPARENT);
        return curve;
    }

    public Map<String, Object> save () {
        List<Coordinate> anchorCoordinates = new ArrayList<Coordinate>();
        for (Anchor anchor : myAnchors) {
            anchorCoordinates.add(anchor.getCoordinates());
        }

        ImageView image = Screenshot.snap(myParent);
        setImageView(image);

        Map<String, Object> settings = new HashMap<String, Object>();
        settings.put(InstanceManager.NAME_KEY, getName());
        settings.put(Variables.PARAMETER_CURVES_COORDINATES, anchorCoordinates);
        settings.put(InstanceManager.PART_TYPE_KEY, InstanceManager.PATH_PARTNAME);
        return settings;
    }

    protected String getToolTipInfo () {
        String info = Variables.EMPTY_STRING;
        info += Variables.NAME_HEADER + getName();
        info += NUMBER_ANCHOR_POINTS + numPoints;
        return info;
    }

    @Override
    public Group getRoot () {
        return myRoot;
    }

    @Override
    public double getWidth () {
        return myParent.getWidth();
    }

    @Override
    public double getHeight () {
        return myParent.getHeight();
    }

}
