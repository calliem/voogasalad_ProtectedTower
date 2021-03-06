package authoringEnvironment.map;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.Variables;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;
import authoringEnvironment.pathing.PathView;
import authoringEnvironment.util.Scaler;


/**
 * Houses the Stackpane that holds the currently active Tilemap, paths, messages, etc. that may be
 * displayed on it. Updates the currently active objects accordingly based upon changes called from
 * other classes.
 * 
 * @author Callie Mao
 *
 */
public class MapWorkspace extends StackPane {

    private static final Color MAP_BACKGROUND_COLOR = Color.web("2A2A29");

    private static final int DEFAULT_MAP_ROWS =
            (int) (AuthoringEnvironment.getEnvironmentWidth() * .8 / 50);
    private static final int DEFAULT_MAP_COLS =
            (int) (AuthoringEnvironment.getEnvironmentHeight() * .9 / 25);
    private static final int DEFAULT_TILE_SIZE = 30; // based on height since monitor height < width
                                                     // and that is usually the limiting factor

    private static final double MESSAGE_DISPLAY_DURATION = 1500;
    private static final int MESSAGE_FONT_SIZE = 20;
    private static final String EMPTY_KEY = "";

    private TileMap myActiveMap;
    private PathView myActivePath;
    private String myActiveTile;
    private ImageView myActiveImg;
    private Controller myController;

    public MapWorkspace (Controller controller) {
        super();
        myController = controller;
        Rectangle background =
                new Rectangle(AuthoringEnvironment.getEnvironmentWidth() *
                              Variables.WORKSPACE_WIDTH_MULTIPLIER,
                              Variables.WORKSPACE_HEIGHT_MULTIPLIER *
                                      AuthoringEnvironment.getEnvironmentHeight(),
                              MAP_BACKGROUND_COLOR);
        getChildren().add(background);
        StackPane.setAlignment(background, Pos.CENTER);

        createDefaultMap();

    }

    public TileMap createDefaultMap () {
        TileMap defaultMap =
                new TileMap(myController, DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
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
        }
    }

    private void update (GameObject oldObject, GameObject object) {
        if (oldObject != null) {
            remove(oldObject.getRoot());
        }
        StackPane.setAlignment(object.getRoot(), Pos.CENTER);
        if (!getChildren().contains(object.getRoot()))
            getChildren().add(object.getRoot());
    }

    public void updateWorkspaceWithImg (ImageView imgView) {
        if (myActiveImg != null)
            remove(imgView);
        getChildren().add(imgView);
        myActiveImg = imgView;
    }

    public void updateWithNewMap (GameObject object) {
        update(myActiveMap, object);
        myActiveMap = (TileMap) object;
        myActiveMap.setActiveTile(EMPTY_KEY);
    }

    public void updateWithNewPath (PathView object) {
        ScaleTransition scale =
                Scaler.scaleOverlay(0.0, 1.0, object.getRoot());
        scale.setOnFinished( (e) -> {
            update(myActivePath, object);
            myActivePath = object;
        });
    }

    public void createNewPath () {
        myActivePath = new PathView(myActiveMap);
        myActiveMap.getRoot().setOnMousePressed(e -> setAnchorPoint(myActivePath, e));
    }

    private void setAnchorPoint (PathView path, MouseEvent e) {
        if (!path.areAnchorsSelected())
            path.addAnchor(e.getX(), e.getY());
    }

    public void displayMessage (String text, Color color) {
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
    }

    public void deactivatePathMode () {
        myActiveMap.attachTileListeners();
        myActiveMap.getRoot().getChildren().remove(myActivePath);
    }

    public String getActiveTile () {
        return myActiveTile;
    }

    public void setActiveTile (String tileKey) {
        myActiveMap.setActiveTile(tileKey);
        myActiveTile = tileKey;
    }

    public void setActivePath (PathView path) {
        myActivePath = null;
    }

    public void setActiveMap (TileMap map) {
        myActiveMap = null;
    }

}
