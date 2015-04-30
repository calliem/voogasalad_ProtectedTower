package authoringEnvironment.map;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.pathing.PathView;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.pathing.Anchor;


/**
 * Houses the stackpane that holds the currently active tilemap, paths, messages, etc. that may be
 * displayed on it. Updates the currently active objects accordingly based upon changes called from
 * other classes.
 * 
 * @author callie
 *
 */
public class MapWorkspace extends StackPane {

    private static final Color MAP_BACKGROUND_COLOR = Color.web("2A2A29");

    private static final double MAP_OPACITY_ACTIVATED = 0.2;
    private static final int DEFAULT_MAP_ROWS =
            (int) (AuthoringEnvironment.getEnvironmentWidth() * .8 / 50);
    private static final int DEFAULT_MAP_COLS =
            (int) (AuthoringEnvironment.getEnvironmentHeight() * .9 / 25); // getHeight();
    private static final int DEFAULT_TILE_SIZE = 30; // based on height since monitor height < width
                                                     // and that is usually the limiting factor
    private static final double WORKSPACE_WIDTH_MULTIPLIER = .75;
    private static final double WORKSPACE_HEIGHT_MULTIPLIER = .89;
    private static final double MESSAGE_DISPLAY_DURATION = 1500;
    private static final int MESSAGE_FONT_SIZE = 20;

    private TileMap myActiveMap;
    private PathView myActivePath;
    private Color myActiveColor;
    private Rectangle pathModeOverlay;

    // TODO: fix all of these constants so there are no more replicates

    public MapWorkspace () {
        super();
        Rectangle background =
                new Rectangle(AuthoringEnvironment.getEnvironmentWidth() *
                              WORKSPACE_WIDTH_MULTIPLIER,
                              WORKSPACE_HEIGHT_MULTIPLIER *
                                      AuthoringEnvironment.getEnvironmentHeight(),
                              MAP_BACKGROUND_COLOR);
        getChildren().add(background);
        StackPane.setAlignment(background, Pos.CENTER);

        createDefaultMap();
        pathModeOverlay =
                //new Rectangle(myActiveMap.getWidth()+2*Anchor.RADIUS, myActiveMap.getHeight()+2*Anchor.RADIUS);
                new Rectangle(myActiveMap.getWidth(), myActiveMap.getHeight());
        pathModeOverlay.setOpacity(MAP_OPACITY_ACTIVATED);
        StackPane.setAlignment(pathModeOverlay, Pos.CENTER);

        

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
        System.out.println("REMOVEEEE" + node);
        if (node == null)
            return;
        if (getChildren().contains(node)) {
            getChildren().remove(node);
        }
    }
   

    private void update (GameObject oldObject, GameObject object) {
        if (oldObject != null) {
            getChildren().remove(oldObject.getRoot());
        }
        StackPane.setAlignment(object.getRoot(), Pos.CENTER);
        getChildren().add(object.getRoot());
    }

    public void updateWithNewMap (GameObject object) {
        update(myActiveMap, object);
        myActiveMap = (TileMap) object;
        myActiveMap.setActiveColor(myActiveColor);

    }
    
    

    // TODO: duplicated
    public void updateWithNewPath (GameObject object) {
        update(myActivePath, object);
        myActivePath = (PathView) object;
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
        myActiveMap.getRoot().getChildren().remove(myActivePath);

    }

    public Color getActiveColor () {
        return myActiveColor;
    }
    
    public void setActivePath(PathView path){
        myActivePath = null;
    }
    
    public void setActiveMap(TileMap map){
        myActiveMap = null;
    }

    public void setActiveColor (Color color) {
        myActiveMap.setActiveColor(color);
        myActiveColor = color;
    }


}