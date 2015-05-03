package highscore;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * 
 * A test application that demonstrates the features of the High Score Utility.
 * 
 * @author Greg McKeon
 *
 */

public class HighScoreTester extends Application {

    @Override
    public void start (Stage primaryStage) throws Exception {
        final String player = "player";
        List<String> nameList = new ArrayList<>();
        List<Double> valList = new ArrayList<>();
        nameList.add("firstval");
        nameList.add("secondval");
        valList.add(new Double(50));
        valList.add(new Double(150));

        HighScoreController controller = HighScoreController.getController();
        controller.setValue(player, 0, "cat5", 4);
        // player is now empty
        controller.clearInstance(player, 0);
        // Deletes everything in the High Score folder
        controller.deleteAllHighScores();

        // Clears specific High Score file, if it exists.
        controller.deleteGameHighScores("game");

        // Setting a value twice overrides the first value
        controller.setValue(player, 0, "Some category", 400);
        controller.setValue(player, 0, "Number of Towers", 6);
        controller.setValue(player, 0, "Number of Towers", 20);

        // This will not be displayed with the player gameNames
        controller.setValue("game", 1, "Another category", 10);

        // This method adds many values at once
        controller.setValues(player, 2, nameList, valList);

        // We must create a value to increment it, categories are case sensitive
        controller.setValue(player, 1, "A final Category", 5);
        controller.incrementValue(player, 1, "A final Category", 5);

        // Note that saving a game clears the instance
        // we could alternatively use this line if we knew none of the player's names
        // controller.saveGameScoreData(player);

        controller.saveInstanceScoreData(player, 0);
        controller.saveInstanceScoreData(player, 1, "Bob");
        controller.saveInstanceScoreData(player, 2, null);

        // If we wanted to simplify and save the game data too, we could use
        controller.saveAllScoreData();

        // Note that this displays all High scores under the gameName player
        controller.displayHighScores(player, "A final Category", 500, 600);
    }

    public static void main (String[] args) throws HighScoreException {
        launch(args);
    }

}
