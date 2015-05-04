// This entire file is part of my masterpiece.
// Qian Wang

package engine;

import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import engine.element.Game;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;


/**
 * This class contains the main game loop which runs the game and updates the
 * Scene. The constructor is given an ObservableList of nodes in the scene, as
 * well as a list of the towers, enemies and map created by the authoring
 * environment. A new version of this class must be created by the player
 * whenever a new game is loaded. The player calls this class when it loads a
 * new game, and this class then runs the game.
 * 
 * @author Qian Wang
 */

public class GameController {

    /**
     * Holds an instance of an entire game
     */
    private Game myGame;

    /**
     * Creates a new instance of a game represented by the XML files at a given file location.
     * 
     * @param filepath String to the main directory holding the game
     * @param nodes List<Sprite> reference to that used in the Player class to display the game
     * @param possibleTower List<Tower> reference that is used in Player class to display all towers
     *        to place
     * @throws InsufficientParametersException when filepath does not point to a well defined game
     *         file
     */
    public GameController (String filepath,
                           List<Sprite> nodes,
                           List<Tower> possibleTowers)
        throws InsufficientParametersException {
        myGame = GameLoader.loadGame(filepath, nodes, possibleTowers);
    }

    /**
     * Method called by player to start the animation of the game, essentially starting the play of
     * the game as well
     * 
     * @param frameRate int number of frames per second
     */
    public void startGame (int frameRate) {
        Timeline gameTimeline = new Timeline();
        KeyFrame game = new KeyFrame(Duration.millis(1000 / frameRate), e -> myGame.update());
        gameTimeline.getKeyFrames().add(game);
        gameTimeline.setCycleCount(Animation.INDEFINITE);
        gameTimeline.play();
    }

    /**
     * Called by the player to tell engine about keypresses
     * 
     * @param key KeyEvent object
     */
    public void handleKeyInput (KeyEvent key) {
        // currently not implemented, but would call a method in game
    }

    /**
     * Called by the player to place a game element at some location, such as placing a new tower
     * that the user has bought
     * 
     * @param id GUID of the game element
     * @param sceneX x-coordinate of placement
     * @param sceneY y-coordinate of placement
     */
    public void placeGameElement (String id, double sceneX, double sceneY) {
        // currently only places towers
        myGame.placeTower(id, sceneX, sceneY);
    }
}
