package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
        changeTileSize(getTileSize());
        // createGridLines();
    }

    private void createMap (Map<String, Object> params) {
        String[][] tileArray = (String[][]) params.get(TILE_KEY_ARRAY);
        myTiles = new Tile[tileArray[0].length][tileArray.length];
        for (int i = 0; i < tileArray[0].length; i++) {
            for (int j = 0; j < tileArray.length; j++) {
                myTiles[i][j] = new TileView(myController, tileArray[i][j]);  // TODO: make tileview
            }
        }
    }

}
