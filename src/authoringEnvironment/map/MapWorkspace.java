package authoringEnvironment.map;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
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
    private Rectangle myMapOverlay;

    private static final double MAP_OPACITY_ACTIVATED = 0.2;
    private static final int DEFAULT_MAP_ROWS =
            (int) (AuthoringEnvironment.getEnvironmentWidth() * .8 / 50);
    private static final int DEFAULT_MAP_COLS =
            (int) (AuthoringEnvironment.getEnvironmentHeight() * .9 / 25); // getHeight();
    private static final int DEFAULT_TILE_SIZE = 30; // based on height since monitor height < width
                                                     // and that is usually the limiting factor

    public static final double WORKSPACE_WIDTH_MULTIPLIER = .75;
    public static final double WORKSPACE_HEIGHT_MULTIPLIER = .89;

    // TODO: fix all of these constants so there are no more replicates

    
    public MapWorkspace () { 
        super();
        Rectangle background =
                new Rectangle(AuthoringEnvironment.getEnvironmentWidth() * WORKSPACE_WIDTH_MULTIPLIER,
                              WORKSPACE_HEIGHT_MULTIPLIER * AuthoringEnvironment.getEnvironmentHeight(),
                              Color.web("2A2A29"));
        getChildren().add(background);
        createDefaultMap();
        myMapOverlay =
                new Rectangle(myActiveMap.getWidth(), myActiveMap.getHeight());
        myMapOverlay.setOpacity(MAP_OPACITY_ACTIVATED);

    }

    public TileMap createDefaultMap () {
        TileMap defaultMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
        updateWithNewMap(defaultMap);
        return defaultMap;
    }

    public TileMap getActiveMap () {
        return myActiveMap;
    }

    public PathView getActivePath () {
        return myActivePath;
    }

    public void remove (Node node) {
        if (node == null)
            return;
        if (getChildren().contains(node)) {
            getChildren().remove(node);
            node = null; // this prob wil lnot work
        }
    }

    public void updateWithNewMap (GameObject object) {
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
    
    //TODO: duplicated
    public void updateWithNewPath (GameObject object){
        if (myActivePath != null && myActivePath.getRoot() != null) {
            getChildren().remove(myActivePath.getRoot());
        }
        myActivePath = (PathView) object;
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

    }
    
    public void createNewPath(){
        myActivePath = new PathView(myActiveMap);
        myActiveMap.getRoot().setOnMousePressed(e -> setAnchorPoint(myActivePath, e));
    }
    

    private void setAnchorPoint (PathView path, MouseEvent e) {
        if (!path.areAnchorsSelected())
            path.addAnchor(e.getX(), e.getY());
    }
    //    TODO:

    /*
     * public void displayMessage(String message){
     * 
     * }
     */
    
    protected void displayMessage(String text, Color color){
        Text saved = new Text(text);
        saved.setFill(color);
        saved.setFont(new Font(20));
        StackPane.setAlignment(saved, Pos.BOTTOM_CENTER);
        //saved.setVisible(false);
        getChildren().add(saved);
        
        //saved.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.play();
        pause.setOnFinished(e ->getChildren().remove(saved));
    }
    
    public void activatePathMode () {
        myActiveMap.removeTileListeners();
        myActiveMap.getRoot().getChildren().add(myMapOverlay);
    }


    public void deactivatePathMode () {
        myActiveMap.attachTileListeners();
        // getMapWorkspace().getActiveMap().getRoot().setOpacity(MAP_OPACITY_DEACTIVATED);
        myActiveMap.getRoot().getChildren().remove(myMapOverlay);
        // getMapWorkspace().getActiveMap().getRoot().removeEventFilter(activePath);
    }

    public Color getActiveColor () {
        return myActiveColor;
    }

    public void setActiveColor (Color color) {
        myActiveMap.setActiveColor(color);
        myActiveColor = color;
    }

}
