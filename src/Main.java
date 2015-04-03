import authoringEnvironment.MainEnvironment;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Creates the view and runs the game engine
 * 
 * @author Callie
 *
 */

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		MainEnvironment myView = new MainEnvironment(stage);
	}

<<<<<<< HEAD
	public static void main(String[] args) {
		launch(args);
	}
=======
    public static void main(String[] args) {
    	
        launch(args);
    }	
>>>>>>> df2f93acfe7e9c4087013fb6a34dbf5006539293
}