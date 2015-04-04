package player;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * This class lets a user choose between showing the authoring environment to create a game or the
 * player to play a game.
 * 
 * @author Greg McKeon
 *
 */
public class Main extends Application {

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        primaryStage.setTitle("Protected Tower()");
        Rectangle2D bounds = setScreenBounds(primaryStage);
        Group launchRoot = new Group();
        primaryStage.show();
        setMainNodes(launchRoot.getChildren(), bounds);
        Scene launchChooser = new Scene(launchRoot);
        primaryStage.setScene(launchChooser);

    }

    // TODO: move button names and constants to resource bundle, add setOnClick(), move constants to
    // a bundle,
    // make buttons look nicer by making vboxes, adding graphics etc.
    private void setMainNodes (ObservableList<Node> children, Rectangle2D bounds) {
        Button makeGame = new Button("Make a Game");
        makeGame.setTranslateX(bounds.getWidth() / 2);
        makeGame.setTranslateY(bounds.getHeight() / 2 - 50);
        Button playGame = new Button("Play a Game");
        playGame.setTranslateX(bounds.getWidth() / 2);
        playGame.setTranslateY(bounds.getHeight() / 2 + 50);
        children.addAll(makeGame, playGame);
    }

    private Rectangle2D setScreenBounds (Stage primaryStage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        return bounds;
    }
}
