/*package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import voogasalad.util.IntArray2DToImageConverter.src.IntArray2DToImageConverter;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


public class TileMapView extends GameObject {

    private Tile[][] myTiles;
    private int myTileSize;
    private ImageView myBackground;
    private Color myActiveColor;
    private String imgFilePath;

    private static final String DEFAULT_BACKGROUND_PATH = "images/white_square.png";
    private static final String TILE_KEY_ARRAY = "TileArrayKeys";
    private static final int LINE_START_COORDINATE = 0;

    private HashMap<String, Integer> myTags; // maps a string to the number of elements with that
                                             // tag

    // allowing both width and height gives greater flexibility in map creation
    private int myMapRows;
    private int myMapCols;
    private Group myRoot; // need to include this instead of extending Group for additional
                          // extendibility and so that GameObject can be extended

    private Group myGridLines;
    private Controller myController;

    public TileMapView (Controller c, String key) { // TODO: maybe move this into the gameobject
                                                    // super class
        myController = c;

        Map<String, Object> params = myController.getPartCopy(key);
        params.get(InstanceManager.NAME_KEY);
        params.get(Variables.PARAMETER_TILESIZE);

        String filePath = (String) params.get(Variables.PARAMETER_BACKGROUND_FILEPATH);
        myBackground = new ImageView(new Image(filePath));

        int[][] thumbnailArray = (int[][]) params.get(Variables.PARAMETER_THUMBNAIL);
        Image thumbnail = IntArray2DToImageConverter.convert2DIntArrayToImage(thumbnailArray, 1);
        setThumbnail(new ImageView(thumbnail));
        // setFilePath()

        createMap(params);
        changeTileSize(myTileSize);
        // createGridLines();
    }

    private void createMap (Map<String, Object> params) {
        String[][] tileArray = (String[][]) params.get(TILE_KEY_ARRAY);
        myTiles = new Tile[tileArray[0].length][tileArray.length];
        for (int i = 0; i < tileArray[0].length; i++) {
            for (int j = 0; j < tileArray.length; j++) {
                myTiles[i][j] = new Tile(myController, tileArray[i][j]); 
            }
        }
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
    }

    private void setImageDimensions (ImageView image) {
        image.setFitWidth(myMapCols * myTileSize);
        image.setFitHeight(myMapRows * myTileSize);
    }

    private void updateGridLines () {
        removeGridLines();
        createGridLines();
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

}
*/