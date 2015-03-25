
import authoring.environment.MainEnvironment;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main
 * Creates the view and then lets the program run
 * The view creates the parser, and they hold an instance of each other
 * @author Sid
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