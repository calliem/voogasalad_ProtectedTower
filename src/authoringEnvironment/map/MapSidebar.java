package authoringEnvironment.map;

import imageselectorTEMP.GraphicFileChooser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.util.Scaler;
import authoringEnvironment.Sidebar;
import authoringEnvironment.UpdatableDisplay;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;
import javafx.animation.ScaleTransition;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


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
    private MapWorkspace myMapWorkspace;
    private static final int DEFAULT_LIVES = 20; // TODO: how to get this number
                                                 // from Johnny
    private Color myActiveColor;

    private TextField tileRowDisplay;
    private TextField tileColDisplay;
    private TextField tileSizeDisplay;
    private VBox mapSettings;
    private VBox pathSettings;
    private TextField mapNameTextField;
    private Controller myController;
    private UpdatableDisplay mapDisplay;

    public static final String MAP_PART_NAME = "GameMap"; // TODO: get this perhaps from the
                                                          // property file?
    public static final String TILEMAP_KEY = "TileMap";

    public MapSidebar (ResourceBundle resources, List<GameObject> maps,
                       MapWorkspace mapWorkspace, Controller c) { 
        super(resources, maps, mapWorkspace);
        myMapWorkspace = mapWorkspace;
        myLives = DEFAULT_LIVES;
        /*
         * ObservableList<PathView> pathList =
         * FXCollections.observableArrayList();
         * getChildren().add(createListView(pathList, 300));
         */
        myController = c;
        createMapSettings();
    }

    protected void createMapSettings () {
        // TODO: make a main tab to display the stuff here
        // createTitleText(getResources().getString("GameSettings"));
        // setLives();
        mapSettings = createTitleText(getResources().getString("MapSettings"));
        pathSettings = createTitleText(getResources().getString("PathSettings"));
        // createTitleText(getResources().getString("SetTiles"));
        selectTile();
        createGeneralSettings();

        displayMaps();
    }

    private void displayMaps () {
        mapDisplay = new UpdatableDisplay(getMaps(), 3); // test
        mapSettings.getChildren().add(mapDisplay);
    }

    // Lots of duplication below
    private void setLives () {
        HBox selection = new HBox();
        selection.setSpacing(PADDING);
        Text lives = new Text(getResources().getString("Lives"));
        TextField textField = new TextField(Integer.toString(myLives));

        Button button = new Button(getResources().getString("Update"));
        button.setOnMouseClicked(e -> System.out.println(textField.getText())); // TODO:
                                                                                // add
                                                                                // to
                                                                                // properties
                                                                                // file
                                                                                // to
                                                                                // be
                                                                                // saved

        textField.setPrefWidth(TEXT_FIELD_WIDTH);
        selection.getChildren().addAll(lives, textField, button);
        mapSettings.getChildren().add(selection);
    }

    private void setTileSize () {
        HBox selection = new HBox();
        selection.setSpacing(PADDING);
        Text lives = new Text(getResources().getString("TileSize"));
        tileSizeDisplay = new TextField(Integer.toString(myMapWorkspace.getActiveMap()
                .getTileSize()));
        Button button = new Button(getResources().getString("Update"));
        button.setOnMouseClicked(e -> myMapWorkspace.getActiveMap()
                .changeTileSize(
                                Integer.parseInt(tileSizeDisplay.getText())));
        tileSizeDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
        Text px = new Text(getResources().getString("PxSuffix"));
        selection.getChildren().addAll(lives, tileSizeDisplay, px, button);
        mapSettings.getChildren().add(selection);

    }

    private void selectTile () {
        HBox selectTile = new HBox();
        selectTile.setSpacing(30); // remove hardcoding

        VBox selection = new VBox();
        Text selectTileColor = new Text(getResources().getString("SelectTileColor")); // TODO:
                                                                                      // fix
        ColorPicker picker = new ColorPicker();
        selection.getChildren().addAll(selectTileColor, picker);

        Rectangle rectangleDisplay =
                new Rectangle(DEFAULT_TILE_DISPLAY_SIZE,
                              DEFAULT_TILE_DISPLAY_SIZE, DEFAULT_TILE_DISPLAY_COLOR); // remove
                                                                                      // hardcoded
                                                                                      // including
                                                                                      // the
                                                                                      // color;

        selectTile.getChildren().addAll(selection, rectangleDisplay);

        mapSettings.getChildren().add(selectTile);
        picker.setOnAction(e -> changeActiveTileColor(picker.getValue(), rectangleDisplay));
    }

    private void changeActiveTileColor (Color color, Rectangle display) {
        myActiveColor = color;
        display.setFill(color);
        myMapWorkspace.getActiveMap().setActiveColor(color);
    }

    // TODO: remove duplicated code
    private void createGeneralSettings () {
        HBox selection = new HBox();
        selection.setSpacing(PADDING);
        setImage();

        HBox nameHBox = new HBox();
        Text name = new Text(getResources().getString("Name")); // Name
        mapNameTextField = new TextField();
        nameHBox.getChildren().addAll(name, mapNameTextField);

        HBox textFields = new HBox();
        Text mapDimensions = new Text(getResources().getString("MapDimensions"));
        tileRowDisplay = new TextField(Integer.toString(myMapWorkspace.getActiveMap()
                .getNumRows()));
        tileRowDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
        Text xSeparator = new Text(getResources().getString("DimensionXSeparation"));
        tileColDisplay = new TextField(Integer.toString(myMapWorkspace.getActiveMap()
                .getNumCols()));
        textFields.getChildren().addAll(mapDimensions, tileRowDisplay, xSeparator, tileColDisplay);

        tileColDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
        Button setGridDimButton = new Button(getResources().getString("Update"));
        setGridDimButton.setOnMouseClicked(e -> updateMapDim(tileRowDisplay.getText(),
                                                             tileColDisplay.getText()));
        selection.getChildren().addAll(nameHBox);

        setTileSize();

        mapSettings.getChildren().addAll(selection, textFields, setGridDimButton);

        setEditMapButtons();

    }

    private void setImage () {
        HBox selection = new HBox();
        selection.setSpacing(PADDING);

        // Text text = new Text("Select Background Image");
        // getResources().getString("Select Background Image")

        // TODO: this is duplicated from Kevin's class
        GraphicFileChooser imgSelector = new GraphicFileChooser("Select background");
        imgSelector.addExtensionFilter("gif");
        imgSelector.addExtensionFilter("png");
        imgSelector.addExtensionFilter("jpg");

        StringProperty imgFile = imgSelector.getSelectedFileNameProperty();
        imgFile.addListener( (obs, oldValue, newValue) -> {
            System.out.println(newValue);
            myMapWorkspace.getActiveMap().setBackground(newValue);
        });

        mapSettings.getChildren().add(imgSelector);
    }

    // TODO: find a way to make this work
    /*
     * private Button updateTextField(String s, int boxWidth){ HBox selection =
     * new HBox(); selection.setSpacing(10); Text lives = new
     * Text(getResources().getString(s)); TextField textField = new TextField();
     * Button button = new Button(getResources().getString("Update"));
     * textField.setPrefWidth(boxWidth); selection.getChildren().addAll(lives,
     * textField, button); getChildren().add(selection);
     * 
     * }
     */

    // TODO: path view

    private void setEditMapButtons () {
        HBox mapButtons = new HBox();
        mapButtons.setSpacing(20);
        Button createMapButton = new Button(getResources().getString("CreateMap"));
        createMapButton.setOnMouseClicked(e -> createMap());
        Button saveMapButton = new Button(getResources().getString("SaveMap"));
        saveMapButton.setStyle("-fx-background-color: #6e8b3d; -fx-text-fill: white;");
        saveMapButton.setOnMouseClicked(e -> saveMap(myMapWorkspace.getActiveMap()));
        Button deleteMapButton = new Button(getResources().getString("DeleteMap"));
        deleteMapButton.setStyle("-fx-background-color: #cd3333; -fx-text-fill: white;");
        deleteMapButton.setOnMouseClicked(e -> removeMap());
        mapButtons.getChildren().addAll(createMapButton, saveMapButton, deleteMapButton);
        mapSettings.getChildren().add(mapButtons);
    }

    private void removeMap () {
        System.out.println("removing map from map sidebar");
        System.out.println("Maps before remove: " + getMaps());
        System.out.println("activemap" + myMapWorkspace.getActiveMap().getRoot());

        ScaleTransition scale =
                Scaler.scaleOverlay(1.0, 0.0, myMapWorkspace.getActiveMap().getRoot());
        scale.setOnFinished( (e) -> {
            if (getMaps().contains(myMapWorkspace.getActiveMap())) {
                getMaps().remove(myMapWorkspace.getActiveMap());
                mapDisplay.updateDisplay(getMaps());
            }
            getMapWorkspace().removeMap();
        });

        // PauseTransition wait = new PauseTransition(Duration.millis(200));
        // wait.setOnFinished((e) -> getMapWorkspace().removeMap());

        // ScaleTransition scale = myScaler.scaleEditScreen(1.0, 0.0, overlay);
        // scale.setOnFinished((e) -> {
        // myContent.getChildren().remove(overlay);
        // overlayActive = false;
        // });

        // wait.play();

        System.out.println("Maps after remove: " + getMaps());

    }

    protected void createMap () {
        getMapWorkspace().removeMap();
        TileMap newMap = getMapWorkspace().createDefaultMap();
        ScaleTransition scale = Scaler.scaleOverlay(0.0, 1.0, newMap.getRoot());
        scale.setOnFinished( (e) -> {
            // getMaps().remove(myMapWorkspace.getActiveMap());
            getMapWorkspace().getChildren().add(newMap.getRoot());
            tileRowDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getNumRows()));
            tileColDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getNumCols()));
            tileSizeDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
                    .getTileSize()));
            myMapWorkspace.getActiveMap().setActiveColor(myActiveColor);
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
        if (!getMaps().contains(activeMap)) {
            getMaps().add(activeMap);
            System.out.println("Maps after save: " + getMaps());
        }
        else {
            getMaps().remove(activeMap);
            getMaps().add(activeMap);
            System.out.println("Maps after save: " + getMaps());
        }

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

        mapDisplay.updateDisplay(getMaps());

    }

    private void updateMapDim (String numRows, String numCols) {
        myMapWorkspace.getActiveMap().setMapDimensions(Integer.parseInt(numRows),
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
         * for (Node map : getMaps()) {
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
         * // Con: myMapWorkspace.getActiveMap(). instead of myActiveMap introduces a
         * // dependency on my workspace. if something is changed there without this
         * // class knowing, the code is easy to break
         */

        for (GameObject map : getMaps()) {
            Map<String, Object> mapSettings = map.saveToXML();
            myController.addPartToGame(MAP_PART_NAME, mapSettings);

        }
    }
}
