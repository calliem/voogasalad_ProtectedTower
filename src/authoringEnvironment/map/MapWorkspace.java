package authoringEnvironment.map;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.pathing.Curve;


public class MapWorkspace extends StackPane {

    private TileMap myActiveMap;
    private PathView myActivePath; 
    private Color myActiveColor;
    
    private static final int DEFAULT_MAP_ROWS =
            (int) (AuthoringEnvironment.getEnvironmentWidth() * .8 / 50);
    private static final int DEFAULT_MAP_COLS =
            (int) (AuthoringEnvironment.getEnvironmentHeight() * .9 / 25); // getHeight();
    private static final int DEFAULT_TILE_SIZE = 30; // based on height since monitor height < width
                                                     // and that is usually the limiting factor

    public static final double MAP_WIDTH_MULTIPLIER = .75;
    public static final double MAP_HEIGHT_PERCENT = 100;

    // TODO: fix all of these constants so there are no more replicates

    public Color getActiveColor(){
        return myActiveColor;
    }
    
    public void setActiveColor(Color color){
        myActiveMap.setActiveColor(color);
        myActiveColor = color;
    }
    
    public MapWorkspace () { // Dimension2D pass this in?
        super();
        Rectangle background =
                new Rectangle(AuthoringEnvironment.getEnvironmentWidth() * MAP_WIDTH_MULTIPLIER,
                              0.9 * AuthoringEnvironment.getEnvironmentHeight(),
                              Color.web("2A2A29"));
        getChildren().add(background);
        createDefaultMap();

    }

    public TileMap createDefaultMap () {
        TileMap defaultMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
        updateWithNewMap(defaultMap);
        return defaultMap;
    }

    public TileMap getActiveMap () {
        return myActiveMap;
    }

    public void removeMap () {
        remove(myActiveMap.getRoot());
    }
    
    public void removePath() {
        remove(myActivePath.getRoot());
    }
    
    public void remove(Node node) {
        if (node == null)
            return;
        if (getChildren().contains(node)) {
            getChildren().remove(node);
            node = null; //this prob wil lnot work 
        }
    }
    
    
    //public PathView

    public void updateWithNewMap (GameObject object) {
        // TODO: reattach event handlers if none
        // TODO: doing this is not actually that good because javaFX does not allow you to have
        // multiple nodes, so you will require an update method which is doign exactly what you did
        // before. Maybe reconstructing the object may be best here.
        // updateWithInteractiveNewMap;
        // updateWithNonInteractiveNewMap();

        if (myActiveMap != null && myActiveMap.getRoot() != null) {
            getChildren().remove(myActiveMap.getRoot());
        }
        myActiveMap = (TileMap) object;
        // if (getChildren().contains(myActiveMap.getRoot())){
        // System.out.println("active map already exists");

        // scaler....

        // TODO:
        /*
         * ScaleTransition scale =
         * Scaler.scaleOverlay(0.0, 1.0, myActiveMap.getRoot());
         * scale.setOnFinished( (e) -> {
         */
        getChildren().add(myActiveMap.getRoot());
        // });

        myActiveMap.setActiveColor(myActiveColor);
    }
    
  /*  public void displayMessage(String message){
        
    }*/

}
