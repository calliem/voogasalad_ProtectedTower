package authoringEnvironment.map;

import imageselectorTEMP.GraphicFileChooser;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.MapUpdatableDisplay;
import authoringEnvironment.Sidebar;
import authoringEnvironment.UpdatableDisplay;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;
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

    private static final double PADDING = AuthoringEnvironment.getEnvironmentWidth() / 128; // maybe
                                                                                            // set
                                                                                            // the
                                                                                            // spacing
                                                                                            // dynamically
                                                                                            // instead

    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
    private static final Color DEFAULT_TILE_DISPLAY_COLOR = Color.TRANSPARENT;
    private static final double DEFAULT_TILE_DISPLAY_SIZE = AuthoringEnvironment
            .getEnvironmentWidth() / 32;
    // TODO: is importing the main environment bad design? is this an added
    // dependency?
    private static final double TEXT_FIELD_WIDTH = AuthoringEnvironment
            .getEnvironmentWidth() / 32;
    private int myLives;
    // private MapWorkspace getMapWorkspace();
    private static final int DEFAULT_LIVES = 20; // TODO: how to get this number
                                                 // from Johnny
    private Color myActiveColor;

    private TextField tileRowDisplay;
    private TextField tileColDisplay;
    private TextField tileSizeDisplay;
    private VBox mapSettings;
    private VBox pathSettings;
    private VBox tileSettings;
    private TextField mapNameTextField;
    private Controller myController;
    private UpdatableDisplay mapDisplay;

    public MapSidebar (ResourceBundle resources, ObservableList<GameObject> maps,
                       MapWorkspace mapWorkspace, Controller c) {
        super(resources, maps, mapWorkspace);
        System.out.println("mapsidebar initializer " + mapWorkspace);
        // getMapWorkspace() = mapWorkspace;
        myLives = DEFAULT_LIVES;
        /*
         * ObservableList<PathView> pathList =
         * FXCollections.observableArrayList();
         * getChildren().add(createListView(pathList, 300));
         */
        myController = c;
        createMapSettings();
    }

    /*
     * public void setMapNameTextField(String s){
     * mapNameTextField.setText(s);
     * }
     */

    public void changeMap (TileMap map) {
        getMapWorkspace().updateWithNewMap(map, myActiveColor);
        mapNameTextField.setText(map.getName());
        tileRowDisplay.setText(Integer.toString(map.getNumRows()));
        tileColDisplay.setText(Integer.toString(map.getNumCols()));
        tileSizeDisplay.setText(Integer.toString(map.getTileSize()));
        // paths
    }

    protected void createMapSettings () {
        // mapSettings = createAccordionTitleText(getResources().getString("MapSettings"));
        tileSettings = createAccordionTitleText(getResources().getString("TileSettings"));
        pathSettings = createAccordionTitleText(getResources().getString("PathSettings"));

        // createGeneralSettings();

        selectTile();
        setTileSize();

    }

    /*
     * private void displayMaps () {
     * mapDisplay =
     * new MapUpdatableDisplay(super.getMaps(), UPDATABLEDISPLAY_ELEMENTS, getMapWorkspace()); //
     * test
     * mapSettings.getChildren().add(mapDisplay);
     * }
     */

    // Lots of duplication below
    /*
     * private void setLives () {
     * HBox selection = new HBox();
     * selection.setSpacing(PADDING);
     * Text lives = new Text(getResources().getString("Lives"));
     * TextField textField = new TextField(Integer.toString(myLives));
     * Button button = new Button(getResources().getString("Update"));
     * button.setOnMouseClicked(e -> System.out.println(textField.getText()));
     * textField.setPrefWidth(TEXT_FIELD_WIDTH);
     * selection.getChildren().addAll(lives, textField, button);
     * mapSettings.getChildren().add(selection);
     * }
     */

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

    private HBox setEditMapButtons () {
        HBox mapButtons = new HBox();
        mapButtons.setSpacing(20);
        Button createMapButton = new Button(getResources().getString("CreateMap"));
        createMapButton.setOnMouseClicked(e -> createMap());
        Button saveMapButton = new Button(getResources().getString("SaveMap"));
        saveMapButton.setStyle("-fx-background-color: #6e8b3d; -fx-text-fill: white;");
        saveMapButton.setOnMouseClicked(e -> saveMap(getMapWorkspace().getActiveMap()));
        Button deleteMapButton = new Button(getResources().getString("DeleteMap"));
        deleteMapButton.setStyle("-fx-background-color: #cd3333; -fx-text-fill: white;");
        deleteMapButton.setOnMouseClicked(e -> removeMap());
        mapButtons.getChildren().addAll(createMapButton, saveMapButton, deleteMapButton);
        return mapButtons;
    }

    private void removeMap () {
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
            getMapWorkspace().getChildren().add(newMap.getRoot());
            tileRowDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getNumRows()));
            tileColDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getNumCols()));
            tileSizeDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getTileSize()));
            getMapWorkspace().getActiveMap().setActiveColor(myActiveColor);
            // TODO: setTileSize();
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
        System.out.println("snapImage " + snapImage);
        ImageView snapView = new ImageView();
        snapView.setImage(snapImage);
        System.out.println("snapview " + snapView);
        activeMap.setThumbnail(snapView);
        System.out.println("thumbnail " + activeMap.getThumbnail());

        if (!super.getMaps().contains(activeMap)) {
            super.getMaps().add(activeMap);
        }
        else {
            int existingIndex = super.getMaps().indexOf(activeMap);
            super.getMaps().remove(activeMap);
            super.getMaps().add(existingIndex, activeMap);
        }
        System.out.println("Maps after save: " + super.getMaps());

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

        System.out.println("your file has been saved");

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
     */
    private void saveToXML () {
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
        // TODO: remove duplicated code
        container.setVgap(5);
        container.setHgap(20);

        container.add(setEditMapButtons(), 0, 0, 2, 1);

        GraphicFileChooser fileChooser = setImage();
        container.add(fileChooser, 0, 1, 2, 1);

        Text name = new Text(getResources().getString("Name"));
        // Setting name = new StringSetting("label", "hi");
        container.add(name, 0, 2);
        mapNameTextField = new TextField();
        container.add(mapNameTextField, 1, 2);

        Text mapDimensions = new Text(getResources().getString("MapDimensions"));
        container.add(mapDimensions, 0, 3);

        HBox mapDimensionsInput = new HBox();
        mapDimensionsInput.setSpacing(4); // TODO remove magic values
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
}
