package authoringEnvironment.pathing;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import authoringEnvironment.pathing.BoundLine;


public class PathCreator {
    public PathCreator (Group parent) {
        CubicCurve curve = createStartingCurve();

        Line controlLine1 =
                new BoundLine(curve.controlX1Property(), curve.controlY1Property(),
                              curve.startXProperty(), curve.startYProperty());
        Line controlLine2 =
                new BoundLine(curve.controlX2Property(), curve.controlY2Property(),
                              curve.endXProperty(), curve.endYProperty());

        Anchor2 start =
                new Anchor2(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty());
        Anchor2 control1 =
                new Anchor2(Color.GOLD, curve.controlX1Property(), curve.controlY1Property());
        Anchor2 control2 =
                new Anchor2(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property());
        Anchor2 end = new Anchor2(Color.TOMATO, curve.endXProperty(), curve.endYProperty());

        AnchorPane anchor = new AnchorPane();

        Group path = new Group(controlLine1, controlLine2, curve, start, control1,
                               control2, end);
        parent.getChildren().add(path);
    }

    private CubicCurve createStartingCurve () {
        CubicCurve curve = new CubicCurve();
        double startX = 100;
        double startY = 100;
        double endX = 300;
        double endY = 300;
        double lineLength = Math.sqrt();
        //double xDiff = endX-startX;
        
        try{
        double slope = (endY-startY)/(endX-startX);
        }
        catch(ArithmeticException e){
            slope = Integer.MAX_VALUE;
        }
        curve.setStartX(startX);
        curve.setStartY(startY);
        curve.setControlX1(startX-);
        curve.setControlY1(startY*.5);
        curve.setControlX2(startX*2.5);
        curve.setControlY2(startY*1.5);
        curve.setEndX(endX);
        curve.setEndY(endY);
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        return curve;
    }
}
