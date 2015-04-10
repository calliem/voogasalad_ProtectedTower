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
    @Override
    public void start(Stage stage) throws Exception {
        MainEnvironment myView = new MainEnvironment(stage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }	
}