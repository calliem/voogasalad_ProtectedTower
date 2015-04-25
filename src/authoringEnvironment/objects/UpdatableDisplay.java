package authoringEnvironment.objects;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Variables;


public abstract class UpdatableDisplay extends VBox {

    private List<GameObject> myObjects;
    private HBox currentRow;
    private VBox objectsDisplay;
    private int numObjsPerRow;
    
    private StackPane selectedView;
   // private GameObject selectedObject;

    private static final int SPACING = 15;

    public UpdatableDisplay (List<GameObject> list, int rowSize) {
        myObjects = list;
        displayValues();
        numObjsPerRow = rowSize;
        selectedView = null;
    }

    private void displayValues () {
        ScrollPane container = new ScrollPane();

        container.setHbarPolicy(ScrollBarPolicy.NEVER);
        container.setPrefHeight(AuthoringEnvironment.getEnvironmentHeight() *
                                Variables.THUMBNAIL_SIZE_MULTIPLIER * 4);

        objectsDisplay = new VBox(SPACING);
        // objectsDisplay.setTranslateY(10);
        setCurrentRow();
        // currentRow = new HBox(HBOX_SPACING);

        if (myObjects.isEmpty()) {
            Text isEmpty = new Text("This list is empty.");
            // TODO: set text in center of scrollpane
            objectsDisplay.getChildren().add(isEmpty);
            // add(isEmpty, BorderLayout.CENTER);
        }
        for (GameObject object : myObjects) {
            if (currentRow.getChildren().size() == numObjsPerRow) {
                objectsDisplay.getChildren().add(currentRow);
                setCurrentRow();
                // currentRow = newRow;
                // objectsDisplay.getChildren().add(newRow);
            }

            StackPane objectView = new StackPane();
            
            // Rectangle objectBackground = new Rectangle(45, 45, Color.WHITE); // TODO: remove hard
            // coded stuff
            
            ImageView thumbnail = object.getUniqueThumbnail(); // may give rectangle or imageview
            // TODO; write if statement: if has thumbnail then get it, if not then get the image and
            // resize it
            thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER);
            thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
                                   Variables.THUMBNAIL_SIZE_MULTIPLIER);
            if (objectView == selectedView){ //TODO: this doesn't work since we're making a new objectview each time. have to check something else
                System.out.println(objectView + "I'M THE SELECTED ONE");
                selectObject(objectView);
            }

            Text mapName = new Text(object.getName());
            mapName.setTranslateY(AuthoringEnvironment.getEnvironmentHeight() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER / 1.8);
            /*
             * mapName.setTranslateX(AuthoringEnvironment.getEnvironmentWidth() *
             * Variables.THUMBNAIL_SIZE_MULTIPLIER / 2);
             */
            // mapName.setFont(new Font(10));

            // TODO: set wrapping
            /*
             * mapName.setWrappingWidth(AuthoringEnvironment.getEnvironmentWidth() *
             * Variables.THUMBNAIL_SIZE_MULTIPLIER);
             */
            StackPane.setAlignment(mapName, Pos.CENTER);

            objectView.getChildren().addAll(thumbnail, mapName);
            currentRow.getChildren().add(objectView);
            objectView.setOnMouseClicked(e -> objectClicked(object, objectView));
            objectView.setOnMouseEntered(e -> selectObject(objectView));
            objectView.setOnMouseExited(e -> deselectObject(objectView));
        }

        if (!objectsDisplay.getChildren().contains(currentRow)) {
            objectsDisplay.getChildren().add(currentRow);
        }

        container.setContent(objectsDisplay);
        getChildren().add(container);
    }
    
    private void selectObject(StackPane objectDisplay){
        /*selectionRect = new Rectangle(AuthoringEnvironment.getEnvironmentWidth() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER, AuthoringEnvironment.getEnvironmentHeight() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER * 1.1, Color.RED);*/
        //selectionRect.setOpacity(0.2);
        //objectDisplay.getChildren().add(selectionRect);
        objectDisplay.setOpacity(0.2);
    }
    
    private void deselectObject(StackPane objectDisplay){
        //objectDisplay.getChildren().remove(selectionRect);  
        System.out.println(objectDisplay);
        System.out.println(selectedView);
        if (objectDisplay != selectedView)
            objectDisplay.setOpacity(1);
    }

    protected VBox getObjectsDisplay () {
        return objectsDisplay;
    }

    private void setCurrentRow () {
        currentRow = new HBox(SPACING);
        currentRow.setAlignment(Pos.TOP_CENTER);
    }

    public void updateDisplay (List<GameObject> list) {
        if (!getChildren().isEmpty()) {
            getChildren().clear();
        }
        myObjects = list;
        displayValues();
    }

    /*
     * public void updateDisplay (ObservableList<? extends GameObject> list) {
     * if (!getChildren().isEmpty()) {
     * getChildren().clear();
     * }
     * myObjects = (List<GameObject>) list;
     * displayValues();
     * }
     */
    // TODO: duplicated above 2 methods code

    protected void objectClicked (GameObject object, StackPane objectView){
        if (selectedView != null){
            selectedView.setOpacity(1);
            //deselectObject(selectedView);
        }
           // objectView.setOpacity(1);

        selectObject(objectView);
        selectedView = objectView;
    }
    
    public void setSelectedView(StackPane view){
        selectedView = view;
    }

}
