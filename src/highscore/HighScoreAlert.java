// This entire file is part of my masterpiece.
// Greg McKeon

package highscore;

import java.util.Properties;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * This class contains an alert that can be used for multiple purposes. In its current
 * implementation, it
 * is used to get the username for a specific game when the game creator saves the high scores.
 * 
 * @author Greg McKeon
 *
 */
public class HighScoreAlert {
    private Properties propFile;

    protected HighScoreAlert () {
        propFile = HighScoreController.loadResources();
    }

    /**
     * Method that displays an alert box to get the username for an associated game
     * 
     * @param game the game to get the username for
     */
    protected void getName (HighScoreHolder game) {
        Stage dialog = new Stage();
        VBox messageBox = new VBox();
        HBox inputBox = new HBox();
        Scene scene = new Scene(new Group(messageBox));
        dialog.setAlwaysOnTop(true);

        dialog.setHeight(Double.parseDouble(propFile.getProperty("alertHeight")));
        dialog.setWidth(Double.parseDouble(propFile.getProperty("alertWidth")));
        dialog.setTitle(propFile.getProperty("alertTitle") + " " + game.getTitle());
        dialog.initStyle(StageStyle.UTILITY);

        Button done = new Button(propFile.getProperty("doneText"));
        Text message = new Text(propFile.getProperty("namePrompt"));
        TextField name = new TextField(propFile.getProperty("defaultName"));

        done.setOnAction(doneAction -> dialog.close());
        name.setOnAction(nameAction -> dialog.close());

        message.setFont(new Font(Integer.parseInt(propFile.getProperty("fontSize"))));
        name.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(name, Priority.ALWAYS);
        inputBox.getChildren().addAll(name, done);
        messageBox.getChildren().addAll(message, inputBox);
        dialog.setOnHiding(hideSetter -> game.setUsername(name.getCharacters().toString()));
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
