package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


/**
 * Holds a 2D array of tiles, attaches listeners, and draws gridlines to display on the map
 * workspace
 * 
 * @author Callie Mao
 *
 */
public class TileMap extends GameObject {

    private StringProperty[][] myTileKeys;
    private Rectangle[][] myTileDisplay;
    private int myTileSize;
    private ImageView myBackground;
    private String myActiveTile;
    private String myBackgroundFilePath;
    private ObservableList<GameObject> myPaths;

    private Controller myController;
    private static final String EMPTY_KEY = "";
    private static final Color EMPTY_COLOR = Color.TRANSPARENT;
    private static final String DEFAULT_BACKGROUND_PATH = "images/white_square.png";
    private static final String TILE_KEY_ARRAY = "TileArrayKeys";
    private static final int LINE_START_COORDINATE = 0;
    private static final String TILE_SIZE = "\nTile Size: ";

    private int myMapRows;
    private int myMapCols;
    private Group myRoot; // used this instead of extending Group for additional
                          // extendibility and so that GameObject can be extended

    private Group myGridLines;

    private static final String NUMBER_OF_TILES = "\nNumber of Tiles: ";
    private static final String X_SEPARATOR = " x ";

    public TileMap (Controller controller, int mapRows, int mapCols, int tileSize) {
        myController = controller;
        myRoot = new Group();
        myRoot.setOnDragDetected(e -> myRoot.startFullDrag());
        myMapRows = mapRows;
        myMapCols = mapCols;
        myTileSize = tileSize;
        myGridLines = new Group();
        myPaths = FXCollections.observableArrayList();
        myActiveTile = EMPTY_KEY;
        myPaths = FXCollections.observableArrayList();
        myBackground = new ImageView(new Image(DEFAULT_BACKGROUND_PATH));
        myBackgroundFilePath = DEFAULT_BACKGROUND_PATH;
        setImageView(myBackground);
        setImageDimensions(myBackground);
        myRoot.getChildren().add(myBackground);
        createMap(myMapRows, myMapCols);
        createGridLines();
        changeTileSize(myTileSize);
    }

    protected TileMap () {
    }

    public ObservableList<GameObject> getPaths () {
        return myPaths;
    }

    public TileMap (int mapRows, int mapCols, int tileSize) {
        myPaths = FXCollections.observableArrayList();
    }

    private void setImageDimensions (ImageView image) {
        image.setFitWidth(myMapCols * myTileSize);
        image.setFitHeight(myMapRows * myTileSize);
    }

    public double getWidth () {
        return myTileSize * myMapCols;
    }

    public double getHeight () {
        return myTileSize * myMapRows;
    }

    public void setBackground (String filepath) {
        System.out.println(filepath);
        myBackgroundFilePath = filepath;
        myRoot.getChildren().remove(myBackground);
        Image image = new Image(filepath);
        myBackgroundFilePath = filepath;
        myBackground = new ImageView(image);
        setImageDimensions(myBackground);
        myRoot.getChildren().add(0, myBackground);
        setImageView(myBackground);
    }

    public void attachTileListeners () {
        for (int i = 0; i < myTileKeys.length; i++) {
            for (int j = 0; j < myTileKeys[0].length; j++) {
                attachTileListener(i, j);
            }
        }
    }

    private void attachTileListener (int i, int j) {
        myTileDisplay[i][j].setOnMouseClicked(e -> tileClicked(i, j));

        // this method is used instead of tileClicked to allow for easier "coloring" of large groups
        // of tiles
        myTileDisplay[i][j].setOnMouseDragEntered(e -> myTileKeys[i][j].setValue(myActiveTile));

        myTileDisplay[i][j].setOnMouseDragReleased(e -> {
        });
    }

    private void tileClicked (int i, int j) {
        if (myTileKeys[i][j].getValue() == myActiveTile) {
            myTileKeys[i][j].setValue(EMPTY_KEY);
        }
        else {
            myTileKeys[i][j].setValue(myActiveTile);
        }
    }

    public void changeTileSize (int tileSize) {
        myTileSize = tileSize;
        for (int i = 0; i < myTileDisplay.length; i++) {
            for (int j = 0; j < myTileDisplay[0].length; j++) {
                setTileSize(myTileDisplay[i][j], i, j);
            }
        }
        updateGridLines();
        setImageDimensions(myBackground);
    }

    private void setTileSize (Rectangle tile, int rowIndex, int colIndex) {
        tile.setWidth(myTileSize);
        tile.setHeight(myTileSize);
        tile.setTranslateX(colIndex * myTileSize);
        tile.setTranslateY(rowIndex * myTileSize);
    }

    public void removeTileListeners () {
        for (int i = 0; i < myTileDisplay.length; i++) {
            for (int j = 0; j < myTileDisplay[0].length; j++) {
                myTileDisplay[i][j].setOnMouseClicked(e -> {
                });
                myTileDisplay[i][j].setOnMouseDragEntered(e -> {
                });
            }
        }
    }

    public void setActiveTile (String key) {
        myActiveTile = key;
    }

    /**
     * Creates a new TileMap through positioning of tiles, setting images, and default tile sizes.
     */
    private void createMap (int rows, int cols) {
        myTileKeys = new StringProperty[rows][cols];
        myTileDisplay = new Rectangle[rows][cols];
        for (int i = 0; i < myTileKeys.length; i++) {
            for (int j = 0; j < myTileKeys[0].length; j++) {
                myTileKeys[i][j] = new SimpleStringProperty(EMPTY_KEY);
                myTileDisplay[i][j] = new Rectangle(myTileSize, myTileSize, EMPTY_COLOR);
                myTileDisplay[i][j].setOpacity(0.6);

                int rowIndex = i;
                int colIndex = j;
                myTileKeys[i][j].addListener( (obs, oldValue, newValue) -> {
                    if (newValue != EMPTY_KEY) {
                        String key = myTileKeys[rowIndex][colIndex].getValue();
                        Color color =
                                (Color) myController.getPartCopy(key)
                                        .get(InstanceManager.COLOR_KEY);
                        myTileDisplay[rowIndex][colIndex].setFill(color);
                    }
                        else {
                            myTileDisplay[rowIndex][colIndex].setFill(EMPTY_COLOR);
                        }
                    });
                positionTile(myTileDisplay[i][j], i, j);
                myRoot.getChildren().add(myTileDisplay[i][j]);

                attachTileListener(i, j);
            }
        }
        setImageDimensions(myBackground);
        changeTileSize(myTileSize);
    }

    private void positionTile (Rectangle tile, int rowIndex, int colIndex) {
        tile.setTranslateX(colIndex * myTileSize);
        tile.setTranslateY(rowIndex * myTileSize);
    }

    /**
     * Sets dimensions of the map to the number of rows and columns specified in the parameter.
     * Directly sets the tile dimensions, while pixel adjustment is determined upon separate methods
     * changing tile size.
     * 
     * @param newMapRows integer representing the number of rows the new map dimensions should have
     * @param newMapCols integer representing the number of columns the new map dimensions should
     *        have
     */
    public void setMapDimensions (int newMapRows, int newMapCols) {
        clearTiles();
        createMap(newMapRows, newMapCols);

        myMapCols = newMapCols;
        myMapRows = newMapRows;
        setImageDimensions(myBackground);
        changeTileSize(myTileSize);
    }

    /**
     * Clears all tiles from the current active tile map by iterating through and removing all tiles
     * of the 2D array individually
     */
    private void clearTiles () {
        for (int i = 0; i < myMapRows; i++) {
            for (int j = 0; j < myMapCols; j++) {
                myRoot.getChildren().remove(myTileDisplay[i][j]);
            }
        }
        myRoot.getChildren().remove(myGridLines);
    }

    public int getNumRows () {
        return myTileKeys.length;
    }

    public int getNumCols () {
        return myTileKeys[0].length;
    }

    public int getTileSize () {
        return myTileSize;
    }

    private void createGridLines () {
        int mapWidth = myMapCols * myTileSize;
        int mapHeight = myMapRows * myTileSize;

        for (int i = 0; i < mapWidth; i += myTileSize) {
            Line verticalLine = new Line(i, LINE_START_COORDINATE, i, mapHeight);
            verticalLine.setStroke(Color.web("B2B2B2"));
            myGridLines.getChildren().add(verticalLine);
        }

        for (int i = 0; i < mapHeight; i += myTileSize) {
            Line horizontalLine = new Line(0, i, mapWidth, i);
            horizontalLine.setStroke(Color.web("B2B2B2"));
            myGridLines.getChildren().add(horizontalLine);
        }

        if (!myRoot.getChildren().contains(myGridLines))
            myRoot.getChildren().add(myGridLines);
    }

    private void removeGridLines () {
        myGridLines.getChildren().clear();
    }

    /**
     * Updates the length and location of gridlines (usually after the size of the tile or grid has
     * been adjusted
     */
    private void updateGridLines () {
        removeGridLines();
        createGridLines();
    }

    public Map<String, Object> save () {
        Map<String, Object> mapSettings = new HashMap<String, Object>();
        mapSettings.put(InstanceManager.NAME_KEY, getName());
        mapSettings.put(Variables.PARAMETER_TILESIZE, myTileSize);
        mapSettings.put(Variables.PARAMETER_BACKGROUND, myBackgroundFilePath);
        mapSettings.put(InstanceManager.PART_TYPE_KEY, InstanceManager.GAMEMAP_PARTNAME);

        mapSettings.put(TILE_KEY_ARRAY, myTileKeys);

        List<String> pathKeys = new ArrayList<String>();
        for (GameObject path : myPaths) {
            pathKeys.add(path.getKey());

        }
        mapSettings.put(Variables.PARAMETER_PATH_KEYS, pathKeys);
        return mapSettings;
    }

    public Group getRoot () {
        return myRoot;
    }

    public String getImgFilePath () {
        return myBackgroundFilePath;
    }

    @Override
    protected String getToolTipInfo () {
        String info = super.getToolTipInfo();
        info += NUMBER_OF_TILES + myMapRows + X_SEPARATOR + myMapCols;
        info += TILE_SIZE + myTileSize;
        return info;
    }
}
