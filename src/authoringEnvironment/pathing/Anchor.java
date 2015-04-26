package authoringEnvironment.pathing;

import authoringEnvironment.objects.Coordinate;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;


/**
 * This class codes for a draggable anchor point that is displayed to the user visually as a circle.
 * 
 * @author Callie Mao, jewelsea (StackOverflow)
 *
 */

public class Anchor extends Circle {
    private static final int RADIUS = 10;

    public Anchor (Color color,
                   DoubleProperty x,
                   DoubleProperty y,
                   int parentWidth,
                   int parentHeight) {
        super(x.get(), y.get(), RADIUS);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);

        x.bind(centerXProperty());
        y.bind(centerYProperty());
        enableDrag(parentWidth, parentHeight);
    }

    public Coordinate getCoordinates () {
        return new Coordinate(getCenterX(), getCenterY());
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag (int parentWidth, int parentHeight) {
        final Delta dragDelta = new Delta();
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                getScene().setCursor(Cursor.HAND);
                // TODO: store coordinates and then get them from the superclass's methods
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 + RADIUS && newX < parentWidth - RADIUS) {
                    setCenterX(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y;
                if (newY > 0 + RADIUS && newY < parentHeight - RADIUS) {
                    setCenterY(newY);
                }
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                }
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            }
        });
    }

    // records relative x and y co-ordinates.
    private class Delta {
        double x, y;
    }
}
