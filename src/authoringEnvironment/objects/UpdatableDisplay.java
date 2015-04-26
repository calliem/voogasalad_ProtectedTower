package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;
import authoringEnvironment.editors.Editor;


public class UpdatableDisplay extends VBox {

    private List<GameObject> myObjects;
    private HBox currentRow;
    private VBox objectsDisplay;
    private int numObjsPerRow;
    private Controller myController;

    private StackPane selectedView;
    private GameObject selectedObject;
    // private Consumer<UpdatableDisplay> myAction;
    // TODO: above stuff pretty much does the same thing

    private static final int SPACING = 15;

    // public UpdatableDisplay (Controller c, String part, int rowSize, Consumer<UpdatableDisplay>
    // action) {
    public UpdatableDisplay (Controller c, String part, int rowSize) {
        // myAction = action;
        myController = c;
        numObjsPerRow = rowSize;
        selectedView = null;
        selectedObject = null;
        List<String> keys = c.getKeysForPartType(part);
        myObjects = buildPreviewObjects(keys);
        displayValues();
    }

    private List<GameObject> buildPreviewObjects (List<String> keys) {
        List<GameObject> objects = new ArrayList<GameObject>();
        for (String key : keys) {
            Map<String, Object> settings = myController.getPartCopy(key);
            String name = (String) settings.get(InstanceManager.NAME_KEY);
            ImageView thumbnail = (ImageView) settings.get(Variables.PARAMETER_THUMBNAIL);

            // TODO: build the full object here maybe? and then attach listeners to it within the
            // each specific thing

            PreviewObject object = new PreviewObject(key, name, thumbnail);
            objects.add(object);
        }
        return objects;
    }

    private void displayValues () {
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
            // add(isEmpty, BorderLayout.CENTER);
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
            thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER);
            thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
                                   Variables.THUMBNAIL_SIZE_MULTIPLIER);
            if (object == selectedObject) {
                selectObject(objectView);
            }

            Text mapName = new Text(object.getName());
            mapName.setTranslateY(AuthoringEnvironment.getEnvironmentHeight() *
                                  Variables.THUMBNAIL_SIZE_MULTIPLIER / 1.8);
            StackPane.setAlignment(mapName, Pos.CENTER);

            objectView.getChildren().addAll(thumbnail, mapName);
            currentRow.getChildren().add(objectView);
            objectView.setOnMouseClicked(e -> objectClicked(object, objectView));
                                                            //, myAction));
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

    public void updateDisplay (List<String> list) {
        if (!getChildren().isEmpty()) {
            getChildren().clear();
        }
        myObjects = buildPreviewObjects(list);
        displayValues();
    }

    // protected void objectClicked (GameObject object, StackPane objectView,
    // Consumer<UpdatableDisplay> action) {
    protected void objectClicked (GameObject object, StackPane objectView) {
        if (selectedView != null) {
            selectedView.setOpacity(1);
        }
        selectObject(objectView);
        selectedView = objectView;

        // action.accept(this); //UpdatableDisplay that this is called on is not applicable
    }

    private static void test (Consumer<Integer> action) {
        action.accept(10);
    }

    private static void testMethod () {
        System.out.println("hi");
    }

    public void setSelectedView (StackPane view) {
        selectedView = view;
    }
}
