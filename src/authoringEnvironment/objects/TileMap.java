package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import authoringEnvironment.InstanceManager;


/**
 * Holds a 2D array of tiles, attaches listeners, and draws gridlines to display on the map
 * workspace
 * 
 * @author Callie Mao
 *
 */

public class TileMap extends GameObject {

    private Tile[][] myTiles;
    private int myTileSize;
    private ImageView myBackground;
    private Color myActiveColor;

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
    private Group myRoot; // need to include this instead of extending Group for additional
                          // extendibility and so that GameObject can be extended

    private Group myGridLines;

    private static final Color DEFAULT_TILE_COLOR = Color.WHITE;

    // TODO: user specifies rectangle or square dimensions...allow this flexibility
    public TileMap (int mapRows, int mapCols, int tileSize) {
        myRoot = new Group();
        myRoot.setOnDragDetected(e -> myRoot.startFullDrag());
        myMapRows = mapRows;
        myMapCols = mapCols;
        myTileSize = tileSize;
        myGridLines = new Group();
        myActiveColor = DEFAULT_TILE_COLOR;
        myBackground = new ImageView(new Image(DEFAULT_BACKGROUND_PATH));
        setThumbnail(myBackground);
        setImageDimensions(myBackground);
        myRoot.getChildren().add(myBackground);
        // TODO: sethover x, y coordinate, tile size, etc.

        createMap();
        createGridLines();
        changeTileSize(myTileSize);
    }

    private void setImageDimensions (ImageView image) {
        image.setFitWidth(myMapCols * myTileSize);
        image.setFitHeight(myMapRows * myTileSize);
    }

    /*
     * public int addTag (int x, int y, String tag) {
     * myTiles[x][y].addTag(tag);
     * int numTags = myTags.get(tag);
     * return ++numTags;
     * }
     * 
     * public int removeTag (int x, int y, String tag) {
     * myTiles[x][y].removeTag(tag);
     * int numTags = myTags.get(tag);
     * return --numTags;
     * }
     */

    public void setBackground (String filepath) {
        myRoot.getChildren().remove(myBackground);
        myBackground = new ImageView(new Image(filepath));
        setImageDimensions(myBackground);
        myRoot.getChildren().add(0, myBackground);
        setThumbnail(myBackground);
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
        tile.setOnMouseClicked(e -> {
            tile.setFill(myActiveColor);
            // System.out.println("I have been clicked!" + tile.getFill().toString());
        });
        tile.setOnMouseDragEntered(e -> {
            tile.setFill(myActiveColor);
            // System.out.println("I have been dragged!" + tile.getFill().toString());
        });
        // System.out.print("tile listener added");
    }

    public void changeTileSize (int tileSize) {

        myTileSize = tileSize;
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                myTiles[i][j].setTileSize(tileSize, i, j);
            }
        }
        updateGridLines();
        setImageDimensions(myBackground);
        System.out.println("map rows " + myMapRows);
        System.out.println("map cols " + myMapCols);
        System.out.println("tile size " + myTileSize);
        System.out.println("map size: " + myMapRows * myTileSize + " x " + myMapCols * myTileSize);
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
                myRoot.getChildren().add(myTiles[i][j]);
                attachTileListener(myTiles[i][j]);
            }
        }
        setImageDimensions(myBackground);
        changeTileSize(myTileSize);

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
        System.out.println("hi");
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
                myRoot.getChildren().add(newTiles[i][j]);
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
                myRoot.getChildren().remove(myTiles[i][j]);
            }
        }
        myRoot.getChildren().remove(myGridLines);
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
        mapSettings.put(InstanceManager.nameKey, getName());
        mapSettings.put(TILESIZE_SETTING, myTileSize);
        mapSettings.put(BACKGROUND_SETTING, myBackground);
        List<String> tileKeys = new ArrayList<String>();
        List<Coordinate> rowColCoordinates = new ArrayList<Coordinate>();

        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                // tileKeys.add(myTiles[i][j]); //TODO: mytiles get key from controller
                rowColCoordinates.add(new Coordinate(i, j));
            }
        }

        mapSettings.put(COORDINATES, rowColCoordinates);
        mapSettings.put(KEYS, tileKeys);
        return mapSettings;
    }

    @Override
    public Node getThumbnail () {
        // TODO Auto-generated method stub
        return null;
    }

    public Group getRoot () {
        return myRoot;
    }
}
