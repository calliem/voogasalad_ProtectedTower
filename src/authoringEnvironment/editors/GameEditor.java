package authoringEnvironment.editors;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


/**
 * This displays the Main Game tab that prompts the user to enter information
 * regarding descriptions of their game and general features that will hold true
 * throughout. Updates here will be sent to the controller.
 * 
 * @author Callie Mao
 *
 */

public class GameEditor extends Editor {

    // private String myName;
    // private String myDescription;
    // private int myLives;
    
    // TODO: how to get this number
    // from Johnny
    private static final int DEFAULT_LIVES = 20;
    
    private TextField gameNameEntry;
    private TextArea gameDescriptionEntry;
    private TextField totalLivesEntry;

    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
    private static final ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE +
                                                                  "game_editor_english");

    public GameEditor (Controller controller, String name) {
        super(controller, name);
    }

    @Override
    protected Group configureUI () {

        // TODO: put everything into a StringSetting object
        Group visuals = new Group();
        GridPane framework = new GridPane();
        framework.setVgap(10);
        framework.setHgap(10);
        framework.setPadding(new Insets(10));

        // setConstraints(framework);
        Text gameName = new Text(myResources.getString("GameName"));
        gameNameEntry = new TextField();
        gameNameEntry.setPromptText(myResources.getString("EnterGameName"));
        Text gameDescription = new Text(myResources.getString("GameDescription"));
        gameDescriptionEntry = new TextArea();
        gameDescriptionEntry.setPromptText(myResources.getString("EnterGameDescription"));
        gameDescriptionEntry.setPrefHeight(300);

        Text totalLives = new Text(myResources.getString("TotalLives"));
        totalLivesEntry = new TextField();
        totalLivesEntry
                .setPromptText(myResources.getString("EnterLivesDescription"));

        framework.add(gameName, 0, 0);
        framework.add(gameNameEntry, 1, 0);
        framework.add(gameDescription, 0, 1);
        framework.add(gameDescriptionEntry, 1, 1);
        framework.add(totalLives, 0, 2);
        framework.add(totalLivesEntry, 1, 2);

        visuals.getChildren().add(framework);
        return visuals;
    }


    private void save () {

        Map<String, Object> settings = new HashMap<String, Object>();

        String name = gameNameEntry.getText();
        settings.put(InstanceManager.NAME_KEY, name);

        String description = gameDescriptionEntry.getText();
        settings.put(Variables.DESCRIPTION, name);

        int lives = Integer.parseInt(totalLivesEntry.getText());
        settings.put(Variables.LIVES, lives);

    }
}
