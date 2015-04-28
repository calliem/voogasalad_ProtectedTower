package authoringEnvironment.map;

<<<<<<< HEAD
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
=======
>>>>>>> parent of 50d57e5... magic number fix
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;
<<<<<<< HEAD
import authoringEnvironment.pathing.Anchor;
=======
import authoringEnvironment.pathing.Curve;
>>>>>>> parent of 50d57e5... magic number fix


/**
 * Houses the stackpane that holds the currently active tilemap, paths, messages, etc. that may be
 * displayed on it. Updates the currently active objects accordingly based upon changes called from
 * other classes.
 * 
 * @author callie
 *
 */
public class MapWorkspace extends StackPane {

    private TileMap myActiveMap;
    private Curve myActivePath; 
    private static final int DEFAULT_MAP_ROWS =
            (int) (AuthoringEnvironment.getEnvironmentWidth() * .8 / 50);
    private static final int DEFAULT_MAP_COLS =
            (int) (AuthoringEnvironment.getEnvironmentHeight() * .9 / 25); // getHeight();
    private static final int DEFAULT_TILE_SIZE = 30; // based on height since monitor height < width
                                                     // and that is usually the limiting factor
<<<<<<< HEAD
    private static final double WORKSPACE_WIDTH_MULTIPLIER = .75;
    private static final double WORKSPACE_HEIGHT_MULTIPLIER = .89;
    private static final double MESSAGE_DISPLAY_DURATION = 1000;
    private static final int MESSAGE_FONT_SIZE = 20;

    private TileMap myActiveMap;
    private PathView myActivePath;
    private Color myActiveColor;
    private Rectangle pathModeOverlay;
=======

    public static final double MAP_WIDTH_MULTIPLIER = .75;
    public static final double MAP_HEIGHT_PERCENT = 100;
>>>>>>> parent of 50d57e5... magic number fix

    // TODO: fix all of these constants so there are no more replicates

    public MapWorkspace () { // Dimension2D pass this in?
        super();
        Rectangle background =
                new Rectangle(AuthoringEnvironment.getEnvironmentWidth() * MAP_WIDTH_MULTIPLIER,
                              0.9 * AuthoringEnvironment.getEnvironmentHeight(),
                              Color.web("2A2A29"));
        getChildren().add(background);
<<<<<<< HEAD
        StackPane.setAlignment(background, Pos.CENTER);

        createDefaultMap();
        pathModeOverlay =
                //new Rectangle(myActiveMap.getWidth()+2*Anchor.RADIUS, myActiveMap.getHeight()+2*Anchor.RADIUS);
                new Rectangle(myActiveMap.getWidth(), myActiveMap.getHeight());
        pathModeOverlay.setOpacity(MAP_OPACITY_ACTIVATED);
        StackPane.setAlignment(pathModeOverlay, Pos.CENTER);

        
=======
        createDefaultMap(Variables.DEFAULT_TILE_COLOR);
>>>>>>> parent of 50d57e5... magic number fix

    }

    public TileMap createDefaultMap (Color currentActiveColor) {
        TileMap defaultMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
        updateWithNewMap(defaultMap, currentActiveColor);
        return defaultMap;
    }

    public TileMap getActiveMap () {
        return myActiveMap;
    }

    public void removeMap () {
        if (myActiveMap == null)
            return;
        if (getChildren().contains(myActiveMap.getRoot())) {
            System.out.println("remove active map");
            getChildren().remove(myActiveMap.getRoot());
            myActiveMap = null;
        }
    }

<<<<<<< HEAD
    private void update (GameObject object) {
=======
    public void updateWithNewMap (GameObject object, Color currentActiveColor) {
        // TODO: reattach event handlers if none
        // TODO: doing this is not actually that good because javaFX does not allow you to have
        // multiple nodes, so you will require an update method which is doign exactly what you did
        // before. Maybe reconstructing the object may be best here.
        // updateWithInteractiveNewMap;
        // updateWithNonInteractiveNewMap();

>>>>>>> parent of 50d57e5... magic number fix
        if (myActiveMap != null && myActiveMap.getRoot() != null) {
            getChildren().remove(myActiveMap.getRoot());
        }
        StackPane.setAlignment(object.getRoot(), Pos.CENTER);
        getChildren().add(object.getRoot());
    }

    public void updateWithNewMap (GameObject object) {
        update(object);

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
        // });

<<<<<<< HEAD
        myActiveMap.setActiveColor(myActiveColor);

    }

    // TODO: duplicated
    public void updateWithNewPath (GameObject object) {
        update(object);

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
        // });

    }

    public void createNewPath () {
        myActivePath = new PathView(myActiveMap);
        myActiveMap.getRoot().setOnMousePressed(e -> setAnchorPoint(myActivePath, e));
    }

    private void setAnchorPoint (PathView path, MouseEvent e) {
        if (!path.areAnchorsSelected())
            path.addAnchor(e.getX(), e.getY());
    }


    protected void displayMessage (String text, Color color) {
        Text saved = new Text(text);
        saved.setFill(color);
        saved.setFont(new Font(MESSAGE_FONT_SIZE));
        StackPane.setAlignment(saved, Pos.BOTTOM_CENTER);
        getChildren().add(saved);
        PauseTransition pause = new PauseTransition(Duration.millis(MESSAGE_DISPLAY_DURATION));
        pause.play();
        pause.setOnFinished(e -> getChildren().remove(saved));
    }

    public void activatePathMode () {
        myActiveMap.removeTileListeners();
        
        myActiveMap.getRoot().getChildren().add(pathModeOverlay);
    }

    public void deactivatePathMode () {
        myActiveMap.attachTileListeners();
        myActiveMap.getRoot().getChildren().remove(pathModeOverlay);
    }

    public Color getActiveColor () {
        return myActiveColor;
    }

    public void setActiveColor (Color color) {
        myActiveMap.setActiveColor(color);
        myActiveColor = color;
=======
        myActiveMap.setActiveColor(currentActiveColor);
>>>>>>> parent of 50d57e5... magic number fix
    }
    
  /*  public void displayMessage(String message){
        
    }*/

}
