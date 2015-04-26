package authoringEnvironment.map;

import imageselectorTEMP.GraphicFileChooser;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.MapUpdatableDisplay;
import authoringEnvironment.objects.Sidebar;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.objects.UpdatableDisplay;
import authoringEnvironment.pathing.Anchor;
import authoringEnvironment.pathing.Anchor;
import authoringEnvironment.pathing.PathCreator;
import authoringEnvironment.util.Scaler;


/**
 * Right sidebar on the map editor containing display properties that allows a
 * user to interactively set grid size, tile size, specific tiles / tile colors,
 * paths, and other visual elements of the map.
 * 
 * @author Callie Mao
 *
 */

// TODO: abstract further
public class MapSidebar extends Sidebar { // add a gridpane later on. but a
                                          // gridpane is hard to edit to set
                                          // things in between other things...

    // TODO: display sidebar with a gridpane not an HBox to keep everything
    // aligned and beautiful

    private static final double PADDING = AuthoringEnvironment.getEnvironmentWidth() / 128;

    private static final int MAP_OPACITY_DEACTIVATED = 1;
    private static final double MAP_OPACITY_ACTIVATED = 0.9;
    private static final Color DEFAULT_TILE_DISPLAY_COLOR = Color.TRANSPARENT;
    private static final double DEFAULT_TILE_DISPLAY_SIZE = AuthoringEnvironment
            .getEnvironmentWidth() / 32;
    // TODO: is importing the main environment bad design? is this an added
    // dependency?
    private static final double TEXT_FIELD_WIDTH = AuthoringEnvironment.getEnvironmentWidth() / 32;
    // TODO: how to get this number
    // from Johnny
    private static final int DEFAULT_LIVES = 20;

    private int myLives;
    // private MapWorkspace getMapWorkspace();
    private Color myActiveColor;

    private TextField tileRowDisplay;
    private TextField tileColDisplay;
    private TextField tileSizeDisplay;
    private VBox pathSettings;
    private VBox tileSettings;
    private GraphicFileChooser fileChooser;
    private TextField mapNameTextField;
    private Controller myController;
    private UpdatableDisplay mapDisplay;
    private static final int INPUT_HBOX_SPACING = 4;
    private static final int VGAP_PADDING = 5;
    private static final int HGAP_PADDING = 20;

    public MapSidebar (ResourceBundle resources, ObservableList<GameObject> maps,
                       MapWorkspace mapWorkspace, Controller c) {
        super(resources, maps, mapWorkspace);
        myLives = DEFAULT_LIVES;
        /*
         * ObservableList<PathView> pathList =
         * FXCollections.observableArrayList();
         * getChildren().add(createListView(pathList, 300));
         */
        myController = c;
        createMapSettings();
    }

    public void changeMap (TileMap map) {
        getMapWorkspace().updateWithNewMap(map, myActiveColor);
        mapNameTextField.setText(map.getName());
        tileRowDisplay.setText(Integer.toString(map.getNumRows()));
        tileColDisplay.setText(Integer.toString(map.getNumCols()));
        tileSizeDisplay.setText(Integer.toString(map.getTileSize()));
        String detailedFilePath = map.getImgFilePath();
        if (detailedFilePath != null) {
            String displayFilePath =
                    detailedFilePath.substring(detailedFilePath.lastIndexOf("/") + 1);
            fileChooser.getFileDisplay().setText(displayFilePath);
        }
        else {
            // TODO magic value. this is also used in the graphic file chooser
            fileChooser.getFileDisplay().setText(getResources().getString("SetBackground"));
        }

        // paths
    }

    protected void createMapSettings () {
        // mapSettings = createAccordionTitleText(getResources().getString("MapSettings"));
        tileSettings = createAccordionTitleText(getResources().getString("TileSettings"));
        pathSettings = createAccordionTitleText(getResources().getString("PathSettings"));

        // createGeneralSettings();

        selectTile();
        setTileSize();
        setPaths();

    }

    private void setTileSize () {
        HBox selection = new HBox();
        selection.setSpacing(PADDING);
        Text lives = new Text(getResources().getString("TileSize"));
        tileSizeDisplay = new TextField(Integer.toString(getMapWorkspace().getActiveMap()
                .getTileSize()));
        Button button = new Button(getResources().getString("Update"));
        button.setOnMouseClicked(e -> getMapWorkspace().getActiveMap()
                .changeTileSize(
                                Integer.parseInt(tileSizeDisplay.getText())));
        tileSizeDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
        Text px = new Text(getResources().getString("PxSuffix"));
        selection.getChildren().addAll(lives, tileSizeDisplay, px, button);
        tileSettings.getChildren().add(selection);

    }

    private void selectTile () {
        HBox selectTile = new HBox();
        selectTile.setSpacing(30); // remove hardcoding

        VBox selection = new VBox();
        Text selectTileColor = new Text(getResources().getString("TileColor")); // TODO:
                                                                                // fix
        ColorPicker picker = new ColorPicker();
        selection.getChildren().addAll(selectTileColor, picker);

        Rectangle rectangleDisplay =
                new Rectangle(DEFAULT_TILE_DISPLAY_SIZE,
                              DEFAULT_TILE_DISPLAY_SIZE, DEFAULT_TILE_DISPLAY_COLOR);
        selectTile.getChildren().addAll(selection, rectangleDisplay);

        tileSettings.getChildren().add(selectTile);
        picker.setOnAction(e -> changeActiveTileColor(picker.getValue(), rectangleDisplay));
    }

    private void changeActiveTileColor (Color color, Rectangle display) {
        myActiveColor = color;
        display.setFill(color);
        getMapWorkspace().getActiveMap().setActiveColor(color);
    }

    private GraphicFileChooser setImage () {
        HBox selection = new HBox();
        selection.setSpacing(PADDING);

        // TODO: this is duplicated from Kevin's class
        GraphicFileChooser imgSelector = new GraphicFileChooser("Select background");
        imgSelector.addExtensionFilter("gif");
        imgSelector.addExtensionFilter("png");
        imgSelector.addExtensionFilter("jpg");

        StringProperty imgFile = imgSelector.getSelectedFileNameProperty();
        imgFile.addListener( (obs, oldValue, newValue) -> {
            // System.out.println(newValue);
            getMapWorkspace().getActiveMap().setBackground(newValue);
        });
        return imgSelector;
    }

    private HBox setEditButtons (Button createButton, Button saveButton, Button deleteButton) {
        HBox mapButtons = new HBox();
        mapButtons.setSpacing(20);

        saveButton.setStyle("-fx-background-color: #6e8b3d; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #cd3333; -fx-text-fill: white;");
        mapButtons.getChildren().addAll(createButton, saveButton, deleteButton);
        return mapButtons;
    }

    private void removeMap () {
        // TODO: move this to map workspace
        ScaleTransition scale =
                Scaler.scaleOverlay(1.0, 0.0, getMapWorkspace().getActiveMap().getRoot());
        scale.setOnFinished( (e) -> {
            if (super.getMaps().contains(getMapWorkspace().getActiveMap())) {
                super.getMaps().remove(getMapWorkspace().getActiveMap());
                mapDisplay.updateDisplay(super.getMaps());
            }
            getMapWorkspace().removeMap();
        });

    }

    protected void createMap () {
        getMapWorkspace().removeMap();
        TileMap newMap = getMapWorkspace().createDefaultMap(myActiveColor);
        ScaleTransition scale = Scaler.scaleOverlay(0.0, 1.0, newMap.getRoot());
        scale.setOnFinished( (e) -> {
            tileRowDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getNumRows()));
            tileColDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getNumCols()));
            tileSizeDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getTileSize()));
            getMapWorkspace().getActiveMap().setActiveColor(myActiveColor);
            // TODO: setTileSize();
            changeMap(getMapWorkspace().getActiveMap());
            mapDisplay.setSelectedView(null);
            mapDisplay.updateDisplay(super.getMaps());

        });
        // TODO: textField.setText to update it
    }

    /**
     * Saves the current active map into the map arraylist. The ArrayList is
     * ordered by most recently saved maps, which will contain the highest
     * index. Saving a currently existing map will update its index to the
     * highest possible one. This allows for easy searching and also for getting
     * the most recently used active map from other methods/classes.
     * 
     * @param activeMap
     */
    private void saveMap (TileMap activeMap) {
        activeMap.setName(mapNameTextField.getText());
        WritableImage snapImage = new WritableImage(activeMap.getWidth(), activeMap.getHeight()); // TODO
        snapImage = activeMap.getRoot().snapshot(new SnapshotParameters(), snapImage);
        ImageView snapView = new ImageView();
        snapView.setImage(snapImage);
        activeMap.setThumbnail(snapView);

        if (!super.getMaps().contains(activeMap)) {
            super.getMaps().add(activeMap);
        }
        else {
            int existingIndex = super.getMaps().indexOf(activeMap);
            super.getMaps().remove(activeMap);
            super.getMaps().add(existingIndex, activeMap);
        }
        displayWorkspaceMessage(getResources().getString("MapSaved"), Color.GREEN);

        // saves the map to a specific key
        // checks to see if the current map already exists
        /*
         * Map<String, Object> mapSettings = new HashMap<String, Object>();
         * if (mapSettings.containsValue(mapName.getText())) {
         * // display error
         * System.out.println("That map name already exists"); // TODO: utilize same visual display
         * // as spriteeditor
         * }
         * else {
         * mapSettings.put(InstanceManager.nameKey, mapName.getText());
         * mapSettings.put(TILEMAP_KEY, activeMap);
         * myController.addPartToGame(MAP_PART_NAME, mapSettings);
         * }
         * 
         * List<String> keys = myController.getKeysForPartType(MAP_PART_NAME);
         * for (String key : keys) {
         * Map<String, Object> part = myController.getPartCopy(key);
         * System.out.println("key" + key);
         * }
         * // part.get(InstanceManager.nameKey);
         * // part.get(MapEditor.TILE_MAP);
         */
        mapDisplay.updateDisplay(super.getMaps());

    }

    private void updateMapDim (String numRows, String numCols) {
        getMapWorkspace().getActiveMap().setMapDimensions(Integer.parseInt(numRows),
                                                          Integer.parseInt(numCols));
    }

    /**
     * Saves the properties of all the tilemaps stored within the editor into XML. Because storing
     * the entire TileMap object creates unreasonably large files, TileMaps are stored first by
     * recognizing each tile's key, storing the tile's key, and mapping that to the specific row and
     * column location within the map. Additional map parameters are saved accordingly (ie.
     * tileSize, etc.). This method is not as applicable in the TileMap since the TileMap name is
     * not updated dynamically as the user types it and needs to be retrieved from the textbox in
     * the editor. TODO: make the name update dynamically with a change listener
     * 
     * @throws MissingInformationException
     */
    private void saveToXML () throws MissingInformationException {
        /*
         * for (Node map : super.getMaps()) {
         * Map<String, Object> mapSettings = new HashMap<String, Object>();
         * mapSettings.put(InstanceManager.nameKey, mapName.getText());
         * mapSettings.put(TILEMAP_KEY, map);
         * myController.addPartToGame(MAP_PART_NAME, mapSettings);
         * }
         * 
         * }
         * 
         * 
         * 
         * // Con: getMapWorkspace().getActiveMap(). instead of myActiveMap introduces a
         * // dependency on my workspace. if something is changed there without this
         * // class knowing, the code is easy to break
         */

        for (GameObject map : super.getMaps()) {
            Map<String, Object> mapSettings = map.saveToXML();
            String key = myController.addPartToGame(Variables.PARTNAME_MAP, mapSettings);
            map.setKey(key);
        }
    }

    protected void setContent (GridPane container) {
        container.setVgap(VGAP_PADDING);
        container.setHgap(HGAP_PADDING);

        Button createMapButton = new Button(getResources().getString("CreateMap"));
        createMapButton.setOnMouseClicked(e -> createMap());

        Button saveMapButton = new Button(getResources().getString("SaveMap"));
        saveMapButton.setOnMouseClicked(e -> saveMap(getMapWorkspace().getActiveMap()));

        Button deleteMapButton = new Button(getResources().getString("DeleteMap"));
        deleteMapButton.setOnMouseClicked(e -> removeMap());

        HBox editMapbuttons = setEditButtons(createMapButton, saveMapButton, deleteMapButton);
        container.add(editMapbuttons, 0, 0, 2, 1);

        fileChooser = setImage();
        container.add(fileChooser, 0, 1, 2, 1);

        Text name = new Text(getResources().getString("Name"));
        // Setting name = new StringSetting("label", "hi");
        container.add(name, 0, 2);
        mapNameTextField = new TextField();
        container.add(mapNameTextField, 1, 2);

        Text mapDimensions = new Text(getResources().getString("MapDimensions"));
        container.add(mapDimensions, 0, 3);

        HBox mapDimensionsInput = new HBox();
        mapDimensionsInput.setSpacing(INPUT_HBOX_SPACING); // TODO remove magic values
        tileRowDisplay = new TextField(Integer.toString(getMapWorkspace().getActiveMap()
                .getNumRows()));
        tileRowDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
        Text xSeparator = new Text(getResources().getString("DimensionXSeparation"));
        tileColDisplay = new TextField(Integer.toString(getMapWorkspace().getActiveMap()
                .getNumCols()));
        tileColDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
        Button setGridDimButton = new Button(getResources().getString("Update"));
        setGridDimButton.setOnMouseClicked(e -> updateMapDim(tileRowDisplay.getText(),
                                                             tileColDisplay.getText()));

        mapDimensionsInput.getChildren().addAll(tileRowDisplay, xSeparator, tileColDisplay,
                                                setGridDimButton);
        container.add(mapDimensionsInput, 1, 3);

        // display maps
        mapDisplay =
                new MapUpdatableDisplay(super.getMaps(), UPDATABLEDISPLAY_ELEMENTS, this); // test
        container.add(mapDisplay, 0, 5, 2, 1);

        // mapSettings.getChildren().addAll(nameHBox, selection, textFields, setGridDimButton);
        // mapSettings.getChildren().add(container);

    }

    private void setPaths () {
        Button createMapButton = new Button(getResources().getString("CreatePath"));
        createMapButton.setOnMouseClicked(e -> createPath());

        Button saveMapButton = new Button(getResources().getString("SavePath"));
        saveMapButton.setOnMouseClicked(e -> savePath());

        Button deleteMapButton = new Button(getResources().getString("DeletePath"));
        deleteMapButton.setOnMouseClicked(e -> deletePath());

        HBox editMapbuttons = setEditButtons(createMapButton, saveMapButton, deleteMapButton);
        pathSettings.getChildren().add(editMapbuttons);
    }

    private void createPath () {
        activatePathMode();

    }

    private void savePath () {
        // getMapWorkspace().getActiveMap()
        deactivatePathMode();
        displayWorkspaceMessage(getResources().getString("PathSaved"), Color.GREEN); // TODO: make
                                                                                     // this a part
                                                                                     // of the
                                                                                     // workspace
                                                                                     // class so
                                                                                     // that the
                                                                                     // pathsettings
                                                                                     // class can
                                                                                     // access this
                                                                                     // too
    }

    private void deletePath () {
        /*
         * ScaleTransition scale =
         * Scaler.scaleOverlay(1.0, 0.0, getMapWorkspace().getActiveMap().getRoot());
         * scale.setOnFinished( (e) -> {
         * if (super.getMaps().contains(getMapWorkspace().getActiveMap())) {
         * super.getMaps().remove(getMapWorkspace().getActiveMap());
         * mapDisplay.updateDisplay(super.getMaps());
         * }
         * getMapWorkspace().removeMap();
         * });
         */

        deactivatePathMode();
    }

    private void activatePathMode () {
        getMapWorkspace().getActiveMap().removeTileListeners();
        getMapWorkspace().getActiveMap().getRoot().setOpacity(MAP_OPACITY_ACTIVATED);
        PathCreator creator = new PathCreator(getMapWorkspace().getActiveMap());
        double startX = 100;
        double startY = 100;
        double endX = 300;
        double endY = 300;
        creator.createCurve(startX, startY, endX, endY);
    }

 

   

    private void deactivatePathMode () {
        getMapWorkspace().getActiveMap().attachTileListeners();
        getMapWorkspace().getActiveMap().getRoot().setOpacity(MAP_OPACITY_DEACTIVATED);
    }
}
