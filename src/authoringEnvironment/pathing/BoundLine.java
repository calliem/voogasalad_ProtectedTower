package authoringEnvironment.pathing;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;


/**
 * Dashed line that binds to the start and end points
 * 
 * @author Callie Mao, jewelsea (StackOverflow)
 *
 */

public class BoundLine extends Line {

    private static final int STROKE_WIDTH = 2;

    public BoundLine (DoubleProperty startX,
                      DoubleProperty startY,
                      DoubleProperty endX,
                      DoubleProperty endY) {
        startXProperty().bind(startX);
        startYProperty().bind(startY);
        endXProperty().bind(endX);
        endYProperty().bind(endY);
        setStrokeWidth(STROKE_WIDTH);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
    }
}
