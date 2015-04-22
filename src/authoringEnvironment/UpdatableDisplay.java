package authoringEnvironment;

import java.awt.BorderLayout;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import authoringEnvironment.objects.GameObject;


public abstract class UpdatableDisplay extends VBox {

    private List<GameObject> myObjects;
    private HBox currentRow;
    private VBox objectsDisplay;
    private int numObjsPerRow;

    private static final int SPACING = 15;

    public UpdatableDisplay (List<GameObject> list, int rowSize) {
        myObjects = list;
        displayValues();
        numObjsPerRow = rowSize;
    }

    private void displayValues () {
        ScrollPane container = new ScrollPane();
        
        container.setHbarPolicy(ScrollBarPolicy.NEVER);
    //    container.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        container.setPrefHeight(AuthoringEnvironment.getEnvironmentHeight() *
                                       Variables.THUMBNAIL_SIZE_MULTIPLIER*4);
      //  contentScrollPane.setMaxWidth(CONTENT_WIDTH);

        objectsDisplay = new VBox(SPACING);
        //objectsDisplay.setTranslateY(10);
        setCurrentRow();
        //currentRow = new HBox(HBOX_SPACING);

        if (myObjects.isEmpty()) {
            Text isEmpty = new Text("This list is empty.");
            //TODO: set text in center of scrollpane
            objectsDisplay.getChildren().add(isEmpty);
            //add(isEmpty, BorderLayout.CENTER);
        }
        for (GameObject object : myObjects) {
            if (currentRow.getChildren().size() == numObjsPerRow) {
                objectsDisplay.getChildren().add(currentRow);
                setCurrentRow();
                //currentRow = newRow;
                //objectsDisplay.getChildren().add(newRow);
            }

            StackPane objectView = new StackPane();

           // Rectangle objectBackground = new Rectangle(45, 45, Color.WHITE); // TODO: remove hard
                                                                             // coded stuff

             ImageView thumbnail = object.getThumbnail(); // may give rectangle or imageview
             //TODO; write if statment: if has thumbnail then get it, if not then get the image and resize it
             thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
                                   Variables.THUMBNAIL_SIZE_MULTIPLIER);
             thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
                                   Variables.THUMBNAIL_SIZE_MULTIPLIER);
 

            Text mapName = new Text(object.getName());
            
            
            objectView.getChildren().addAll(thumbnail, mapName);
            currentRow.getChildren().add(objectView);
            objectView.setOnMouseClicked(e -> objectClicked(object));
        }
        
        if (!objectsDisplay.getChildren().contains(currentRow)) {
            objectsDisplay.getChildren().add(currentRow);
        }

        container.setContent(objectsDisplay);
        getChildren().add(container);
    }
    
    protected VBox getObjectsDisplay(){
        return objectsDisplay;
    }
    
    private void setCurrentRow(){
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
    
    /*public void updateDisplay (ObservableList<? extends GameObject> list) {
        if (!getChildren().isEmpty()) {
            getChildren().clear();
        }
        myObjects = (List<GameObject>) list;
        displayValues();
    }*/
    //TODO: duplicated above 2 methods code
    
    protected abstract void objectClicked(GameObject object);
}
