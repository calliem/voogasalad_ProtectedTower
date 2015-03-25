
import authoring.environment.MainView;
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

	private MainEnvironment myView;

	@Override
	public void start(Stage stage) throws Exception {
		myView = new MainEnvironment(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}	
}