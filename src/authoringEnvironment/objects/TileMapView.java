package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class TileMapView extends GameObject{
    
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


    public TileMapView(Controller c, String key){ //TODO: maybe move this into the gameobject super class
        myRoot = new Group();
        //myRoot.setOnDragDetected(e -> myRoot.startFullDrag());
        //myMapRows = mapRows;
        //myMapCols = mapCols;
        //myTileSize = tileSize;
        //myGridLines = new Group();
        //myActiveColor = DEFAULT_TILE_COLOR;
       // imgFilePath = DEFAULT_BACKGROUND_PATH;
        //imgFilePath = null;
        
        
        myController = c;
        
        Map<String, Object> params = myController.getPartCopy(key);
        params.get(InstanceManager.NAME_KEY);
        params.get(Variables.PARAMETER_TILESIZE);
        
        String filePath = (String) params.get(Variables.PARAMETER_BACKGROUND_FILEPATH);
        myBackground = new ImageView(new Image(filePath));
        
        int[][] thumbnailArray = (int[][]) params.get(Variables.PARAMETER_THUMBNAIL);
        Image thumbnail = IntArray2DToImageConverter.convert2DIntArrayToImage(thumbnailArray, 1);
        setThumbnail(new ImageView(thumbnail));

        String[][] tileArray = params.get(TILE_KEY_ARRAY);
        myTiles = new Tile[tileArray[0].length][tileArray.length];
        for (int i = 0; i < tileArray[0].length; i++){
            for (int j = 0; j < tileArray.length; j++){
                myTiles[i][j] = new TileView(myController, tileArray[i][j]);  //TODO: make tileview
            }
    }
        
        
        
        //tilewhatever view: int mapRows, int mapCols, int tileSize
        if (tile is null)
            then make an empty tile
        setThumbnail(myBackground);
        setImageDimensions(myBackground);
        myRoot.getChildren().add(myBackground);
        // TODO: sethover x, y coordinate, tile size, etc.

        createMap();
        createGridLines();
        changeTileSize(myTileSize);
    }
    }

    
}
