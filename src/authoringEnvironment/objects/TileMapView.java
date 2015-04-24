package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
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


    public TileMapView(int mapRows, int mapCols, int tileSize){
        myRoot = new Group();
        //myRoot.setOnDragDetected(e -> myRoot.startFullDrag());
        //myMapRows = mapRows;
        //myMapCols = mapCols;
        //myTileSize = tileSize;
        //myGridLines = new Group();
        //myActiveColor = DEFAULT_TILE_COLOR;
       // imgFilePath = DEFAULT_BACKGROUND_PATH;
        //imgFilePath = null;
        myBackground = new ImageView(new Image(DEFAULT_BACKGROUND_PATH));
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
