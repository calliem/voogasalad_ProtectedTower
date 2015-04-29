package main;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import authoringEnvironment.AuthoringEnvironment;

/**
 * Creates the view and runs the game engine
 * @author Callie Mao
 * @author Kevin He
 */

public class Main extends Application {
    private Stage myStage;
    private Dimension2D myDimensions;
    private static Scene[] scenes;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "main_environment_english");
    private static final String defaultSaveLocation = System.getProperty(
			"user.dir").concat("/data/TestingTesting123");
    
    @Override
    public void start(Stage stage) throws Exception {
        initScreen();
        myStage = stage;
        
        AuthoringEnvironment myView = new AuthoringEnvironment(stage, "ExampleGame", defaultSaveLocation);
        Scene authoring = myView.initScene(myDimensions);
        
        MainMenu myMainMenu = new MainMenu(stage);
        Scene menu = myMainMenu.initScene(myDimensions);
        
        scenes = new Scene[]{menu, authoring};
        
        setupStage(menu);
    }
    
    public static Scene[] getScenes(){
        return scenes;
    }
    
    private void setupStage(Scene scene){
        myStage.setResizable(false);
        myStage.setTitle(myResources.getString("Title"));
        myStage.setScene(scene);
        myStage.show();
    }
    
    private void initScreen() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        myDimensions = new Dimension2D(bounds.getWidth(), bounds.getHeight());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}