package protectedtower;

import authoringEnvironment.MainEnvironment;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Creates the view and runs the game engine
 * @author Callie Mao
 *
 */

public class Main extends Application {
    private static Stage environmentStage; 
    
    @Override
    public void start(Stage stage) throws Exception {
        environmentStage = stage;
        MainEnvironment myView = new MainEnvironment(stage);
    }

    public static Stage getStage(){
        return environmentStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }	
}