

package authoringEnvironment;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.editors.LevelEditor;

/**
 * Sets up the main environment where the MenuPane, TabPane, and editor classes are displayed
 * @author Callie Mao
 * @author Johnny Kumpf, populateTabBar() methods
 */

public class MainEnvironment {

	private static Dimension2D myDimensions;
	private Stage myStage; //is this necessary
	private TabPane myTabPane;
	private GridPane myGridPane;
	private static final boolean MAIN_TAB = true;
	private static final boolean SPRITE_TAB = false;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "main_environment_english");

	//private SceneSetter mySceneSetter = new SceneSetter();

	public MainEnvironment(Stage s) {
		System.out.println(DEFAULT_RESOURCE_PACKAGE + "main_environment_english");
		initScreen();
		initStage(s);
	//	s.setTitle(myResources.getString("MainStageTitle"));
		//TODO: How to add to the mainenvironment resources without the parser freaking out?
				//MainStageTitle=protected Tower()

		myGridPane = new GridPane();
		myGridPane.setGridLinesVisible(true);
		createEnvironment(myGridPane);

		createTabs();

		setupScene(myStage, myGridPane, myDimensions.getWidth(), myDimensions.getHeight());
	}

	/**
	 * Populates the tab bar with 1 tab for every non-abstract class in editors package
	 */
	

	public static double getEnvironmentWidth(){
		return myDimensions.getWidth();
	}

	public static double getEnvironmentHeight(){
		return myDimensions.getHeight();
	}

	private void createEnvironment(GridPane grid) {
		//TODO: hardcoded numbers should be removed
		//TODO: remove 'x's' from tabs
		//gridPane.setStyle("-fx-background-color: #C0C0C0;");

		ColumnConstraints col0 = new ColumnConstraints();
		col0.setPercentWidth(100);

		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(4);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(96);
		grid.getRowConstraints().add(row0);
		grid.getRowConstraints().add(row1);
		grid.getColumnConstraints().add(col0);

		grid.add(configureTopMenu(),0,0);
		myTabPane = new TabPane();

		
		grid.add(myTabPane,0,1);
	}
	
	private void createTabs(){
		ProjectReader.populateTabBar(this, myDimensions, myResources, myStage);
		
		//Tab selectedTab = myTabPane.getSelectionModel().getSelectedItem();
		for (Tab tab : myTabPane.getTabs()){
			System.out.println("loop " + tab.getText());
			tab.setOnSelectionChanged(e -> update(tab)); //is this updating the old tab?
		}
	}
	
	private void update(Tab selectedTab){
		Editor editor = (Editor) selectedTab.getContent();
		editor.update();
		System.out.println("changed selection! " + selectedTab.getText());
		Controller.updateEditor(selectedTab.getText(), editor); //this is dependent on the tab's name not changing, which may not be optimal design
		//why does this printout twice????
				//TODO: alternatively: use reflection to update the tab. unnecessary use of reflection....but might not be able to do anything else because of javafx limitations. that's also bad becasue of dependencies...
				//can i get the index of the tab and then match it to the one in the properties file? that's bad because of dependencies...brainstorm more ways
		
		//below is just to allow for testing of the LevelEditor right now:
	}


	protected void addTab(Editor newEditor, String tabName, boolean main) {
		Tab tab = new Tab();
		tab.setText(tabName);
		tab.setContent(newEditor);
		//TODO: instead of calling configureUI, create a new editor each time, have UI stuff written in the constructor, and have each editor extend a node

		if (main){
			tab.setStyle("-fx-base: #3c3c3c;");
			System.out.println(tabName + "main = true, property set");
		}
		tab.setClosable(false);
		myTabPane.getTabs().add(tab); 
	}

	private void initScreen() {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		myDimensions = new Dimension2D(bounds.getWidth(), bounds.getHeight());
	}

	private void initStage(Stage s) {
		myStage = s;      
		myStage.setResizable(false);
	}

	private MenuBar configureTopMenu() {
		Menu file = configureFileMenu();
		Menu info = configureInfoMenu();
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file, info);
		return menuBar;
	}

	private Menu configureInfoMenu() {
		Menu info = new Menu(myResources.getString("Info"));
		MenuItem help = new MenuItem(myResources.getString("Help"));
		info.getItems().addAll(help);
		help.setOnAction(e -> displayPage("/src/resources/help.html"));
		return info;
	}

	private Menu configureFileMenu() {
		Menu file = new Menu(myResources.getString("File"));
		MenuItem exit = new MenuItem(myResources.getString("Exit"));
		exit.setOnAction(e -> Platform.exit());
		file.getItems().addAll(exit);
		return file;
	}

	private void displayPage(String loc) {
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load("file://" + System.getProperty("user.dir") + loc);
		Stage stage = new Stage();
		setupScene(stage, browser, 1000, 750);
	}

	public void setupScene(Stage stage, Parent root, double width, double height) {
		Scene scene = new Scene(root, width, height);
		myStage.setTitle(myResources.getString("Title"));
		myStage.setScene(scene);
		myStage.show();
	}
}