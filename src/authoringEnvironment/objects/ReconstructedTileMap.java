//package authoringEnvironment.objects;
//
//import java.util.Map;
//import util.IntArray2DToImageConverter.src.IntArray2DToImageConverter;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import authoringEnvironment.Controller;
//import authoringEnvironment.InstanceManager;
//import authoringEnvironment.Variables;
//
//
//public class ReconstructedTileMap extends TileMap {
//
//    private Controller myController;
//
//    public ReconstructedTileMap (Controller c, String key) {
//        // super class
//        myController = c;
//
//        Map<String, Object> params = myController.getPartCopy(key);
//        String name = (String) params.get(InstanceManager.NAME_KEY);
//        setName(name);
//
//        int tileSize = (int) params.get(Variables.PARAMETER_TILESIZE);
//        changeTileSize(tileSize);
//
//        String filePath = (String) params.get(Variables.PARAMETER_BACKGROUND);
//        setBackground(filePath);
//
//        int[][] thumbnailArray = (int[][]) params.get(Variables.PARAMETER_IMAGE);
//        Image thumbnail = IntArray2DToImageConverter.convert2DIntArrayToImage(thumbnailArray, 1);
//        setImageView(new ImageView(thumbnail));
//        // setFilePath()
//
//        createMap(params);
//        changeTileSize(getTileSize());
//        //createGridLines();
//    }
//
//    private void createMap (Map<String, Object> params) {
//        String[][] tileArray = (String[][]) params.get(TILE_KEY_ARRAY);
//        
//        
//        Tile[][] newMap = new Tile[tileArray[0].length][tileArray.length];
//        for (int i = 0; i < tileArray[0].length; i++) {
//            for (int j = 0; j < tileArray.length; j++) {
//                
//                newMap[i][j] =  (Tile) myController.getPartCopy(getKey());  // TODO: make tileview
//                
//                newMap[i][j].positionTile(getTileSize(), i, j);
//                getRoot().getChildren().add(newMap[i][j]);
//                //setupTooltip(newMap[i][j]);
//            }
//        }
//        setTiles(newMap);
//    }
//}
