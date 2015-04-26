package authoringEnvironment.pathing;

import authoringEnvironment.objects.Coordinate;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;


public class Anchor extends Circle {
    //private Coordinate dragDelta;

    public Anchor (Color color, DoubleProperty x, DoubleProperty y) {
        super(x.get(), y.get(), 10);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);

        //x.bind(centerXProperty());
        //y.bind(centerYProperty());
        enableDrag();

    }

    private void enableDrag () {
        // final Coordinate dragDelta; // = new Coordinate(0,0);
     /*   setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                // System.out.println("pressed");
                // double x = getCenterX() - mouseEvent.getX();
                // double y = getCenterY() - mouseEvent.getY();
                // dragDelta = new Coordinate(x, y);
                // getScene().setCursor(Cursor.MOVE);
            }
        });*/
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseDragged(e -> handleDragInput(e)
        /*
         * new EventHandler<MouseEvent>() {
         * 
         * @Override
         * public void handle (MouseEvent mouseEvent) {
         * System.out.println("dragged");
         * double newX = mouseEvent.getX() +
         * dragDelta.getX();
         * //if (newX > 0 && newX < getScene().getWidth())
         * {
         * setTranslateX(newX);
         * //}
         * double newY = mouseEvent.getY() +
         * dragDelta.getY();
         * //if (newY > 0 && newY <
         * getScene().getHeight()) {
         * setTranslateY(newY);
         * //}
         * }
         * }
         */);
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

    private void handleDragInput (MouseEvent e) {
        double currentX = e.getX();
        double currentY = e.getY();
      //  if (currentX > 0 && currentX < getScene().getWidth()){ // TODO: make getscenegetwidth actually be the width of the map
        System.out.println("X: " + currentX + "Y: " + currentY);
        //System.out.println(currentY);
            setTranslateX(currentX);
       // }
        //if (currentY > 0 && currentY < getScene().getHeight()) { // TODO: see above TODO
            setTranslateY(e.getY());
        //}
    }
}
