package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


/**
 * Holds a 2D array of tiles, attaches listeners, and draws gridlines to display on the map
 * workspace
 * 
 * @author Callie Mao
 *
 */

public class TileMap extends Group {
    
    private Tile[][] myTiles;
    private int myTileSize;
    private Color myActiveColor;
    private ImageView myBackground;
 //   private String myBackgroundPath;
    private int myKey; // XML key
    private String myName;
    private static final String DEFAULT_BACKGROUND_PATH = "images/white_square.png";
    private static final String TILESIZE_SETTING = "TileSize";
    private static final String BACKGROUND_SETTING = "Background";
    private static final String COORDINATES = "Coordinates";
    private static final String KEYS = "Keys";
    private static final String MAP_PART_NAME = "GameMap";

    private HashMap<String, Integer> myTags; // maps a string to the number of elements with that
                                             // tag

    // allowing both width and height gives greater flexibility in map creation
    private int myMapRows;
    private int myMapCols;

    private Group myGridLines;

    private static final Color DEFAULT_TILE_COLOR = Color.WHITE;

    // user specifies rectangle or square dimensions...allow this flexibility
    public TileMap (int mapRows, int mapCols, int tileSize) {
        setOnDragDetected(e -> startFullDrag());
        myMapRows = mapRows;
        myMapCols = mapCols;
        myTileSize = tileSize;
        myGridLines = new Group();
        myActiveColor = DEFAULT_TILE_COLOR;
  //      myBackgroundPath = DEFAULT_BACKGROUND_PATH; //JavaFX does not allow getting the path from the image and the filepath needs to be stored for loading the game with the proper images in the future
        myBackground = new ImageView(new Image(DEFAULT_BACKGROUND_PATH));
        
        setImageDimensions(myBackground);
        getChildren().add(myBackground);
        myKey = (Integer) null;
        // TODO: sethover x, y coordinate, tile size, etc.

        createMap();
        createGridLines();
    }

    private void setImageDimensions (ImageView image) {
        image.setFitWidth(myMapCols * myTileSize);
        image.setFitHeight(myMapRows * myTileSize);
    }

    public int addTag (int x, int y, String tag) {
        myTiles[x][y].addTag(tag);
        int numTags = myTags.get(tag);
        return ++numTags;
    }

    public int removeTag (int x, int y, String tag) {
        myTiles[x][y].removeTag(tag);
        int numTags = myTags.get(tag);
        return --numTags;
    }

    public void setBackground (String filepath) {
        getChildren().remove(myBackground);
        myBackground = new ImageView(new Image(filepath));
        setImageDimensions(myBackground);
        getChildren().add(0, myBackground);

    }

    // TODO:duplicated tile listeners being added/deleted?
    public void attachTileListeners () {
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                attachTileListener(myTiles[i][j]);
            }
        }
    }

    private void attachTileListener (Tile tile) {
        tile.setOnMousePressed(e -> {
            tile.setFill(myActiveColor);
            System.out.println("I have been clicked!" + tile.getFill().toString());
        });
        tile.setOnMouseDragEntered(e -> tile.setFill(myActiveColor)); // TODO: fix dragging errors
    }

    public void changeTileSize (int tileSize) {
        System.out.println(myMapCols * myTileSize);
        myTileSize = tileSize;
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                myTiles[i][j].setTileSize(tileSize);
            }
        }
        updateGridLines();
        System.out.println(myMapCols * myTileSize);
        setImageDimensions(myBackground);
    }

    public void removeTileListeners () {
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                myTiles[i][j].setOnMousePressed(e -> {
                });
                myTiles[i][j].setOnMouseDragEntered(e -> {
                });
            }
        }
    }

    public Tile getTile (int x, int y) {
        return myTiles[x][y];
    }

    public void setActiveColor (Color color) {
        myActiveColor = color;
    }

    private void createMap () {
        myTiles = new Tile[myMapRows][myMapCols];
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                myTiles[i][j] = new Tile();
                myTiles[i][j].positionTile(myTileSize, i, j);
                getChildren().add(myTiles[i][j]);
                attachTileListener(myTiles[i][j]);
            }
        }
        setImageDimensions(myBackground);
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
        Tile[][] newTiles = new Tile[newMapRows][newMapCols];

        // TODO make newmethod to avoid duplication since this is similar to createMap
        for (int i = 0; i < newMapRows; i++) {
            for (int j = 0; j < newMapCols; j++) {
                if (i >= myMapRows || j >= myMapCols) {
                    newTiles[i][j] = new Tile();
                    newTiles[i][j].positionTile(myTileSize, i, j);
                }
                else {
                    newTiles[i][j] = myTiles[i][j];
                }
                attachTileListener(newTiles[i][j]);
                getChildren().add(newTiles[i][j]);
            }
        }

        myMapCols = newMapCols;
        myMapRows = newMapRows;
        myTiles = newTiles;
        setImageDimensions(myBackground);
        updateGridLines();
    }

    /**
     * Clears all tiles from the current active tile map by iterating through and removing all tiles
     * of the 2D array individually
     */
    private void clearTiles () {
        for (int i = 0; i < myMapRows; i++) {
            for (int j = 0; j < myMapCols; j++) {
                getChildren().remove(myTiles[i][j]);
            }
        }
        getChildren().remove(myGridLines);
    }

    public int getNumRows () {
        return myTiles.length;
    }

    public int getNumCols () {
        return myTiles[0].length;
    }

    public int getTileSize () {
        return myTileSize;
    }

    private void createGridLines () {
        int mapWidth = myMapCols * myTileSize;
        int mapHeight = myMapRows * myTileSize;

        // TODO: make an error display if mapwidth or mapheight is greater than allowed or create a
        // scrollpane instead
        // vertical lines
        for (int i = 0; i < mapWidth; i += myTileSize) {
            Line verticalLine = new Line(i, 0, i, mapHeight);
            verticalLine.setStroke(Color.web("B2B2B2"));
            myGridLines.getChildren().add(verticalLine);
        }
        // horizontal lines
        for (int i = 0; i < mapHeight; i += myTileSize) {
            Line horizontalLine = new Line(0, i, mapWidth, i);
            horizontalLine.setStroke(Color.web("B2B2B2"));
            myGridLines.getChildren().add(horizontalLine);
        }

        if (!getChildren().contains(myGridLines))
            getChildren().add(myGridLines);
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

    public Tile[][] getTiles () {
        return myTiles;
    }

    public Map<String, Object> saveToXML () {
        /*
         * List<String> partFileKeys = new ArrayList<String>();
         * List<Color> colors = new ArrayList<Color>();
         * List<List<String>> tags = new ArrayList<List<String>>();
         * 
         * for (Tile tile: myTiles){
         * partFileNames.add(tile.getKey());
         * colors.add(tile.getColor());
         * tags.add(tile.getTags());
         * }
         * 
         * List<Object> data = new ArrayList<Object>();
         * // data.add(partFileNames);
         * data.add(colors);
         * data.add(tags);
         * myController.addPartToGame(TILE_PART_NAME, waveName,
         * ProjectReader.getParamsNoTypeOrName(WAVE), data);
         */

        Map<String, Object> mapSettings = new HashMap<String, Object>();
        mapSettings.put(InstanceManager.nameKey, myName);
        mapSettings.put(TILESIZE_SETTING, myTileSize);
        mapSettings.put(BACKGROUND_SETTING, myBackground);
        List<String> tileKeys = new ArrayList<String>();
        List<Coordinate> rowColCoordinates = new ArrayList<Coordinate>();

        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                tileKeys.add(myTiles[i][j].getKey());
                rowColCoordinates.add(new Coordinate(i, j));
            }
        }

        mapSettings.put(COORDINATES, rowColCoordinates);
        mapSettings.put(KEYS, tileKeys);
        return mapSettings;
    }
}
