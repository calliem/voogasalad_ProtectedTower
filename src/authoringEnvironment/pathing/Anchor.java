package authoringEnvironment.pathing;

import authoringEnvironment.objects.Coordinate;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;


/**
 * This class codes for a draggable anchor point that is displayed to the user visually as a circle.
 * 
 * @author Callie Mao, jewelsea (StackOverflow)
 *
 */

public class Anchor extends Circle {
    public static final int RADIUS = 10;
    private boolean isPressed;

    public Anchor (Color color,
                   DoubleProperty x,
                   DoubleProperty y,
                   double parentWidth,
                   double parentHeight) {
        super(x.get(), y.get(), RADIUS);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
        isPressed = false;
        // TODO: change back to false and then fix the true registers below
        x.bind(centerXProperty());
        y.bind(centerYProperty());
        enableDrag(parentWidth, parentHeight);
    }

    public Coordinate getCoordinates () {
        // TODO: make sure to normalize this to the size of teh group/stackpane that it is on
        return new Coordinate(getCenterX(), getCenterY());
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag (double parentWidth, double parentHeight) {
        final Delta dragDelta = new Delta();
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.

                isPressed = true;
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);

            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {

                getScene().setCursor(Cursor.HAND);
                // PauseTransition pause = new PauseTransition(Duration.millis(10000));
                // pause.play();
                // pause.setOnFinished(ae -> isPressed = false);
                isPressed = false;
                // TODO: problem is that isPressed will register as "false" before the listener in
                // MapSidebar's setOnMouseClicked/Pressed will finish. Thus it will not know that
                // this was pressed

            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {

                isPressed = true;
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

    public boolean isSelected () {

        return isPressed;
    }

    // records relative x and y coordinates.
    private class Delta {
        double x, y;
    }

}
