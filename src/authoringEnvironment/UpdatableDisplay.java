package authoringEnvironment;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.SpriteView;


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

        objectsDisplay = new VBox(SPACING);
        //objectsDisplay.setTranslateY(10);
        setCurrentRow();
        //currentRow = new HBox(HBOX_SPACING);

        if (myObjects.isEmpty()) {
            Text isEmpty = new Text("This list is empty.");
            objectsDisplay.getChildren().add(isEmpty);
        }
        for (GameObject object : myObjects) {
            if (currentRow.getChildren().size() == numObjsPerRow) {
                objectsDisplay.getChildren().add(currentRow);
                setCurrentRow();
                //currentRow = newRow;
                //objectsDisplay.getChildren().add(newRow);
            }

            StackPane objectView = new StackPane();

    //        Rectangle objectBackground = new Rectangle(45, 45, Color.WHITE); // TODO: remove hard
                                                                             // coded stuff

             Node thumbnail = object.getThumbnail(); // may give rectangle or imageview
            // Text nameDisplay = new Text(object.getName());
            // nameDisplay.setFont(new Font(10));
            // nameDisplay.setTextAlignment(TextAlignment.CENTER);
            // nameDisplay.setWrappingWidth(90);
            // TODO: set on hover
            // objectView.getChildren().addAll(objectBackground, thumbnail, nameDisplay);

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
