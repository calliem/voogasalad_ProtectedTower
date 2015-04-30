package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.IntArray2DToImageConverter.src.IntArray2DToImageConverter;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.NoImageFoundException;
import authoringEnvironment.Variables;


public abstract class UpdatableDisplay extends VBox {

    private List<GameObject> myObjects;
    private HBox currentRow;
    private VBox objectsDisplay;
    private double thumbnailSizeMultiplier;
    private int numObjsPerRow;
    private StackPane selectedView;
    private Controller myController;
    private String myPartType;
    private static final int SPACING = 15;

    public UpdatableDisplay (List<GameObject> list,
                             int rowSize,
                             double thumbnailMultiplier) {
        myObjects = list;
        displayValues();
        numObjsPerRow = rowSize;
        thumbnailSizeMultiplier = thumbnailMultiplier;
        selectedView = null;
    }

    public UpdatableDisplay (Controller c,
                             String partType,
                             int rowSize,
                             double thumbnailMultiplier) {
        thumbnailSizeMultiplier = thumbnailMultiplier;
        ObservableList<String> keys = c.getKeysForPartType(partType);
        myObjects = new ArrayList<GameObject>();
        myController = c;
        myPartType = partType;
        numObjsPerRow = rowSize;

        populateObjects(keys);
        displayValues();
    }

    public void populateObjects (ObservableList<String> keys) {
        for (String key : keys) {
            Map<String, Object> partParameters = myController.getPartCopy(key);
            Image image = null;
            try {
                image = myController.getImageForKey(key);
                // test image below:
            }
            catch (NoImageFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // image = new Image("images/white_square.png");

            ImageView thumbnail = setThumbnailSize(new ImageView(image));
            String name = (String) partParameters.get(InstanceManager.NAME_KEY);
            GameObject displayObject = new GameObject(key, name, thumbnail);
            myObjects.add(displayObject);
            System.out.println("displayobj " + displayObject);
        }
        System.out.println("myobjects " + myObjects);
    }

    private ImageView setThumbnailSize (ImageView thumbnail) {
        if (thumbnailSizeMultiplier == 0) {
            thumbnail.setVisible(false);
        }
        thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
                              thumbnailSizeMultiplier);
        thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
                               thumbnailSizeMultiplier);
        return thumbnail;
    }

    private void displayValues () {
        System.out.println("displayValues");
        ScrollPane container = new ScrollPane();

        container.setHbarPolicy(ScrollBarPolicy.NEVER);
        container.setPrefHeight(AuthoringEnvironment.getEnvironmentHeight() *
                                Variables.THUMBNAIL_SIZE_MULTIPLIER * 4);

        objectsDisplay = new VBox(SPACING);
        setCurrentRow();

        if (myObjects.isEmpty()) {
            Text isEmpty = new Text("This list is empty.");
            // TODO: set text in center of scrollpane
            objectsDisplay.getChildren().add(isEmpty);
        }
        for (GameObject object : myObjects) {
            if (currentRow.getChildren().size() == numObjsPerRow) {
                objectsDisplay.getChildren().add(currentRow);
                setCurrentRow();
            }

            StackPane objectView = new StackPane();

            ImageView thumbnail = object.getUniqueThumbnail(); // may give rectangle or imageview
            // TODO; write if statement: if has thumbnail then get it, if not then get the image and
            // resize it
            setThumbnailSize(thumbnail);
            /*
             * thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
             * Variables.THUMBNAIL_SIZE_MULTIPLIER);
             * thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
             * Variables.THUMBNAIL_SIZE_MULTIPLIER);
             */
            if (objectView == selectedView) { // TODO: this doesn't work since we're making a new
                                              // objectview each time. have to check something else
                selectObject(objectView);
            }

            Text mapName = new Text(object.getName());
            mapName.setTranslateY(AuthoringEnvironment.getEnvironmentHeight() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER / 1.8);

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

    private void selectObject (StackPane objectDisplay) {
        objectDisplay.setOpacity(0.2);
    }

    private void deselectObject (StackPane objectDisplay) {

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

    public void updateDisplay () {
        if (!getChildren().isEmpty()) {
            getChildren().clear();
        }
        ObservableList<String> keys = myController.getKeysForPartType(myPartType);
        myObjects.clear();
        populateObjects(keys);
        displayValues();
    }

    protected void objectClicked (GameObject object, StackPane objectView) {
        if (selectedView != null) {
            selectedView.setOpacity(1);
            // deselectObject(selectedView);
        }
        // objectView.setOpacity(1);

        selectObject(objectView);
        selectedView = objectView;
    }

    public void setSelectedView (StackPane view) {
        selectedView = view;
    }

}
