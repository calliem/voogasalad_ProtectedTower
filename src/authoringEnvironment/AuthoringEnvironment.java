

package authoringEnvironment;

import java.util.ResourceBundle;
import protectedtower.Main;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
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

/**
 * Sets up the main environment where the MenuPane, TabPane, and editor classes are displayed. When switching tabs, each tab's editor is automatically updated to the controller
 * @author Callie Mao
 * @author Johnny Kumpf, populateTabBar() methods
 * @author Kevin He
 */

public class AuthoringEnvironment {
    private static Dimension2D myDimensions;
    private Stage myStage; //is this necessary
    private Scene myScene;
    private TabPane myTabPane;
    private GridPane myGridPane;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "main_environment_english");
    private Tab myCurrentTab;
    
    public AuthoringEnvironment(Stage s){
        myStage = s;
    }

    public Scene initScene(Dimension2D dimensions) {
        myDimensions = dimensions;
        
        myGridPane = new GridPane();
        myGridPane.setGridLinesVisible(true);
        createEnvironment(myGridPane);

        createTabs();

        myScene = new Scene(myGridPane, myDimensions.getWidth(), myDimensions.getHeight());
        return myScene;
    }

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


    /**
     * Populates the tab bar with 1 tab for every non-abstract class in editors package
     */
    private void createTabs(){
        ProjectReader.populateTabBar(this, myDimensions, myResources, myStage);

        //Tab selectedTab = myTabPane.getSelectionModel().getSelectedItem();
        for (Tab tab : myTabPane.getTabs()){
            tab.setOnSelectionChanged(e -> update(tab)); //is this updating the old tab?
        }
        myCurrentTab = myTabPane.getSelectionModel().getSelectedItem();
    }

    private void update(Tab selectedTab){
        if (myCurrentTab != selectedTab){
            Editor editor = (Editor) myCurrentTab.getContent();
            Controller.updateEditor(myCurrentTab.getText(), editor); //update old tab in the controller

            myCurrentTab = selectedTab;
            Editor editor2 = (Editor) myCurrentTab.getContent();
            editor2.update();
        }
    }

    protected void addTab(Editor newEditor, String tabName, boolean main) {
        Tab tab = new Tab();
        tab.setText(tabName);
        tab.setContent(newEditor);
        if (main){
            tab.setStyle("-fx-base: #3c3c3c;");
            System.out.println(tabName + "main = true, property set");
        }
        tab.setClosable(false);
        myTabPane.getTabs().add(tab); 
    }

    private MenuBar configureTopMenu() {
        Menu file = configureFileMenu();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file);
        return menuBar;
    }
    
    private Menu configureFileMenu() {
        Menu file = new Menu(myResources.getString("File"));
        MenuItem quit = new MenuItem(myResources.getString("Quit"));
        quit.setOnAction(e -> Platform.exit());
        MenuItem mainMenu = new MenuItem(myResources.getString("Menu"));
        mainMenu.setOnAction(e -> returnToMenu());
        
        file.getItems().addAll(mainMenu, quit);
        return file;
    }
    
    private void returnToMenu(){
        myStage.setScene(Main.getScenes()[0]);
        myStage.show();
    }
}