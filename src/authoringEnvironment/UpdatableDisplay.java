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


public class UpdatableDisplay extends VBox {

    private List<GameObject> myObjects;
    private HBox currentRow;
    private int numObjsPerRow;

    private static final int HBOX_SPACING = 10;

    public UpdatableDisplay (List<GameObject> list, int rowSize) {
        myObjects = list;
        displayValues();
        numObjsPerRow = rowSize;
    }

    private void displayValues () {
        ScrollPane container = new ScrollPane();

        VBox objectsDisplay = new VBox(20);
        objectsDisplay.setTranslateY(10);
        currentRow = new HBox(HBOX_SPACING);

        if (myObjects.isEmpty()) {
            Text isEmpty = new Text("This list is empty.");
            objectsDisplay.getChildren().add(isEmpty);
        }
        for (GameObject object : myObjects) {
            if (currentRow.getChildren().size() == numObjsPerRow) {
                HBox newRow = new HBox(HBOX_SPACING);
                newRow.setAlignment(Pos.TOP_CENTER);
                objectsDisplay.getChildren().add(currentRow);
                currentRow = newRow;
                objectsDisplay.getChildren().add(newRow);
            }

            StackPane objectView = new StackPane();
            
            Rectangle objectBackground = new Rectangle(50, 50, Color.WHITE); //TODO: remove hard coded stuff
            
//            Node thumbnail = object.getThumbnail(); // may give rectangle or imageview
//            Text nameDisplay = new Text(object.getName());
//            nameDisplay.setFont(new Font(10));
//            nameDisplay.setTextAlignment(TextAlignment.CENTER);
//            nameDisplay.setWrappingWidth(90);
//TODO: set on hover
  //          objectView.getChildren().addAll(objectBackground, thumbnail, nameDisplay);
        

            Text mapName = new Text();
            ImageView image = new ImageView();
            currentRow.getChildren().addAll(mapName, image);
        }
        if (!objectsDisplay.getChildren().contains(currentRow)){
            objectsDisplay.getChildren().add(currentRow);
        }

        container.setContent(objectsDisplay);
        getChildren().add(container);
    }

    public void updateDisplay (List<GameObject> updatedObjects) {
        if (!getChildren().isEmpty()) {
            getChildren().clear();
        }
        myObjects = updatedObjects;
        displayValues();
    }
 }
