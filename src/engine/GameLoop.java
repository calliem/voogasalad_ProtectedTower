package engine;

import java.util.ArrayList;
import com.thoughtworks.xstream.XStream;
import engine.sprites.GridCell;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;


/**
 * This class contains the main game loop which runs the game and updates the Scene. The constructor
 * is given an ObservableList of nodes in the scene, as well as a list of the towers, enemies and
 * map created by the authoring environment. A new version of this class must be created by the
 * player whenever a new game is loaded. The player calls this class when it loads a new game, and
 * this class then runs the game.
 */

public class GameLoop {
    public GameLoop () throws InsufficientParametersException {
        throw new InsufficientParametersException();
    }

    public GameLoop (ObservableList<Node> sceneContents, XStream inputStream)
        throws InsufficientParametersException {

    }

    public void parse (XStream inputStream) {

    }

    public ArrayList<Node> displayMap (Iterable<GridCell> map) {
        return null;
    }

    // Will handle hotkeys
    public void handleKeyInput (KeyEvent k) {

    }
}
