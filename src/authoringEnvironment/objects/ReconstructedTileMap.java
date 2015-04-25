package authoringEnvironment.objects;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


public class ReconstructedTileMap extends TileMap {

    private Controller myController;

    public ReconstructedTileMap (Controller c, String key) {
        // super class
        myController = c;

        Map<String, Object> params = myController.getPartCopy(key);
        params.get(InstanceManager.NAME_KEY);
        params.get(Variables.PARAMETER_TILESIZE);

        String filePath = (String) params.get(Variables.PARAMETER_BACKGROUND_FILEPATH);
        setBackground(new ImageView(new Image(filePath)));

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
