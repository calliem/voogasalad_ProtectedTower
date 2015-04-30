package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


/**
 * Holds a 2D array of tiles, attaches listeners, and draws gridlines to display on the map
 * workspace
 * 
 * @author Callie Mao
 *
 */

//extends Group implements GameObject

public class TileMap extends GameObject {

    private Tile[][] newMap;
    private int myTileSize;
    private ImageView myBackground;
    private String myBackgroundFilePath;
    private Color myActiveColor;
    private String imgFilePath;


    private Map<String, Integer> myTags; // maps a string to the number of elements with that
                                             // tag

    // allowing both width and height gives greater flexibility in map creation
    private int myMapRows;
    private int myMapCols;
    private Group myRoot; // need to include this instead of extending Group for additional
                          // extendibility and so that GameObject can be extended

    private Group myGridLines;

    private static final String DEFAULT_BACKGROUND_PATH = "images/white_square.png";
    protected static final String TILE_KEY_ARRAY = "TileKeys";
    private static final int LINE_START_COORDINATE = 0;
    private static final Color DEFAULT_TILE_COLOR = Color.TRANSPARENT;

    // TODO: user specifies rectangle or square dimensions...allow this flexibility
    protected TileMap(){
        
    }
    
    public TileMap (int mapRows, int mapCols, int tileSize) {
        myRoot = new Group();
        myRoot.setOnDragDetected(e -> myRoot.startFullDrag());
        myMapRows = mapRows;
        myMapCols = mapCols;
        myTileSize = tileSize;
        myGridLines = new Group();
        myActiveColor = DEFAULT_TILE_COLOR;
       // imgFilePath = DEFAULT_BACKGROUND_PATH;
        imgFilePath = null;
        myBackground = new ImageView(new Image(DEFAULT_BACKGROUND_PATH));
        myBackgroundFilePath = DEFAULT_BACKGROUND_PATH;
        setImageView(myBackground);
        setImageDimensions(myBackground);
        myRoot.getChildren().add(myBackground);
        // TODO: sethover x, y coordinate, tile size, etc.

        createMap();
        createGridLines();
        changeTileSize(myTileSize);
    }
    
    public void setTiles(Tile[][] tiles){
        newMap = tiles;
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
        imgFilePath = filepath;
        myRoot.getChildren().remove(myBackground);
        Image image = new Image(filepath);
        myBackgroundFilePath = filepath;
        myBackground = new ImageView(image);
        setImageDimensions(myBackground);
        myRoot.getChildren().add(0, myBackground);
        setImageView(myBackground);
    }

    // TODO:duplicated tile listeners being added/deleted?
    public void attachTileListeners () {
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                attachTileListener(newMap[i][j]);
            }
        }
    }

    private void attachTileListener (Tile tile) {
        tile.setOnMouseClicked(e -> tileClicked(tile));
      //this method is used instead of tileClicked to allow for easier "coloring" of large groups of tiles
        tile.setOnMouseDragEntered(e -> tile.setFill(myActiveColor)); 
    }

    public void changeTileSize (int tileSize) {

        myTileSize = tileSize;
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                newMap[i][j].setTileSize(tileSize, i, j);
            }
        }
        updateGridLines();
        setImageDimensions(myBackground);
    }

    private void tileClicked (Tile tile) {
        if (tile.getColor() == myActiveColor) {
            tile.setFill(Color.TRANSPARENT);
        }
        else {
            tile.setFill(myActiveColor);
        }
    }

    public void removeTileListeners () {
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                newMap[i][j].setOnMouseClicked(e -> {
                });
                newMap[i][j].setOnMouseDragEntered(e -> {
                });
            }
        }
    }

    public Tile getTile (int x, int y) {
        return newMap[x][y];
    }

    public void setActiveColor (Color color) {
        myActiveColor = color;
    }

    /**
     * Creates a new TileMap through positioning of tiles, setting images, and default tile sizes.
     */
    private void createMap () {
        newMap = new Tile[myMapRows][myMapCols];
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                newMap[i][j] = new Tile();
                newMap[i][j].positionTile(myTileSize, i, j);
                myRoot.getChildren().add(newMap[i][j]);
                attachTileListener(newMap[i][j]);
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
        clearTiles();
        Tile[][] newTiles = new Tile[newMapRows][newMapCols];
        for (int i = 0; i < newMapRows; i++) {
            for (int j = 0; j < newMapCols; j++) {
                if (i >= myMapRows || j >= myMapCols) {
                    newTiles[i][j] = new Tile();
                    newTiles[i][j].positionTile(myTileSize, i, j);
                }
                else {
                    newTiles[i][j] = newMap[i][j];
                }
                attachTileListener(newTiles[i][j]); // TODO figure out why not working
                myRoot.getChildren().add(newTiles[i][j]);
            }
        }

        myMapCols = newMapCols;
        myMapRows = newMapRows;
        newMap = newTiles;
        setImageDimensions(myBackground);
        changeTileSize(myTileSize);
        updateGridLines();
    }

    /**
     * Clears all tiles from the current active tile map by iterating through and removing all tiles
     * of the 2D array individually
     */
    private void clearTiles () {
        for (int i = 0; i < myMapRows; i++) {
            for (int j = 0; j < myMapCols; j++) {
                myRoot.getChildren().remove(newMap[i][j]);
            }
        }
        myRoot.getChildren().remove(myGridLines);
    }

    public int getNumRows () {
        return newMap.length;
    }

    public int getNumCols () {
        return newMap[0].length;
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
            Line verticalLine = new Line(i, LINE_START_COORDINATE, i, mapHeight);
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
        return newMap;
    }

    public Map<String, Object> save () {
        Map<String, Object> mapSettings = super.save(); 
        mapSettings.put(Variables.PARAMETER_TILESIZE, myTileSize);
        mapSettings.put(Variables.PARAMETER_BACKGROUND, myBackgroundFilePath);
        mapSettings.put(InstanceManager.PART_TYPE_KEY, Variables.PARTNAME_MAP);

        String[][] tileKeyArray = new String[newMap.length][newMap[0].length];
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                tileKeyArray[i][j] = newMap[i][j].getKey();
            }
        }
        mapSettings.put(TILE_KEY_ARRAY, tileKeyArray);
        return mapSettings;
    }

    public Group getRoot () {
        return myRoot;
    }
    
    public String getImgFilePath(){
        return imgFilePath;
    }

    @Override
    protected String getToolTipInfo () {
        String info = super.getToolTipInfo();
        info += "\nNumber of Tiles: " + myMapRows + " x " + myMapCols;
        info += "\nTile Size: " + myTileSize;
        return info;
    }

}
