package authoringEnvironment;

import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import protectedtower.Main;
import authoringEnvironment.editors.Editor;


/**
 * Sets up the main environment where the MenuPane, TabPane, and editor classes are displayed. When
 * switching tabs, each tab's editor is automatically updated to the controller
 * 
 * @author Callie Mao
 * @author Johnny Kumpf
 * @author Kevin He
 */

public class AuthoringEnvironment {
    private static final int MAIN_MENU_SCENE_INDEX = 0;
    private static Dimension2D myDimensions;
    private Stage myStage; // is this necessary
    private Scene myScene;
    private TabPane myTabPane;
    private GridPane myGridPane;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE +
            "main_environment_english");
    private Controller myController;
    
//    private static final int TAB_HEIGHT_PERCENT
 //   private static final int TAB_HEIGHT_PERCENT
    
    private Editor currentEditor;

    public AuthoringEnvironment (Stage s, String gameName, String rootDir) {
        myStage = s;
        InstanceManager myGame = GameCreator.createNewGame(gameName, rootDir);
        myController = new Controller(myGame);
    }

    public AuthoringEnvironment (Stage s, InstanceManager loadedGame) {
        myStage = s;
        myController = new Controller(loadedGame);
    }

    public Scene initScene (Dimension2D dimensions) {
        myDimensions = dimensions;

        myGridPane = new GridPane();
        myGridPane.setGridLinesVisible(true);
        createEnvironment(myGridPane);
        myScene = new Scene(myGridPane, myDimensions.getWidth(), myDimensions.getHeight());
        
        setupKeyPresses();
        return myScene;
    }

    /**
     * Just for Bojia
     */
    private void setupKeyPresses () {
        currentEditor = (Editor) myTabPane.getTabs().get(0);
        myTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            updateCurrentEditor((Editor) newTab);
        });
        
        myScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                if(currentEditor.isOverlayActive()){
                    currentEditor.hideOverlay();
                }
            }
        });
    }
    
    private void updateCurrentEditor(Editor current){
        currentEditor = current;
    }

    public static double getEnvironmentWidth () {
        return myDimensions.getWidth();
    }

    public static double getEnvironmentHeight () {
        return myDimensions.getHeight();
    }

    private void createEnvironment (GridPane grid) {
        // TODO: hardcoded numbers should be removed
        // TODO: remove 'x's' from tabs
        // gridPane.setStyle("-fx-background-color: #C0C0C0;");

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(100);

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(4);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(96);
        grid.getRowConstraints().add(row0);
        grid.getRowConstraints().add(row1);
        grid.getColumnConstraints().add(col0);
        grid.add(configureTopMenu(), 0, 0);
        myTabPane = new TabPane();
        List<Editor> editorsToAdd = ProjectReader.getOrderedEditorsList(myController);
        for (Editor e : editorsToAdd) {
            myTabPane.getTabs().add(e);
        }
        grid.add(myTabPane, 0, 1);
    }

    private MenuBar configureTopMenu () {
        Menu file = configureFileMenu();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file);
        return menuBar;
    }

    private Menu configureFileMenu () {
        Menu file = new Menu(myResources.getString("File"));
        MenuItem quit = new MenuItem(myResources.getString("Quit"));
        quit.setOnAction(e -> Platform.exit());
        MenuItem mainMenu = new MenuItem(myResources.getString("Menu"));
        mainMenu.setOnAction(e -> returnToMenu());
        MenuItem save = new MenuItem(myResources.getString("Save"));
        save.setOnAction(e -> saveGame());
        MenuItem load = new MenuItem(myResources.getString("Load"));
        load.setOnAction(e -> loadGame());
        
        file.getItems().addAll(save, load, mainMenu, quit);
        return file;
    }
    
   
    private void saveGame(){
        myController.saveGame();
    }
    
    private void loadGame(){
        
    }
    private void returnToMenu () {
        myStage.setScene(Main.getScenes()[MAIN_MENU_SCENE_INDEX]);
        myStage.show();
    }
    
    
    
    /** old code:
     * 
     * private void createTabs(){
                ProjectReader.populateTabBar(this, myDimensions, myResources, myStage);
                
                //Tab selectedTab = myTabPane.getSelectionModel().getSelectedItem();
                for (Tab tab : myTabPane.getTabs()){
                        System.out.println("loop " + tab.getText());
                        tab.setOnSelectionChanged(e -> update(tab)); //is this updating the old tab?
                }
                myCurrentTab = myTabPane.getSelectionModel().getSelectedItem();
        }
        
     * @param selectedTab
     */
    
    private void update(Tab selectedTab){
        System.out.println("UPDATETAB()---------");
        /*
        System.out.println("previousTab" + previousTab);
        System.out.println("selectedTab" + selectedTab);
        Editor editor = (Editor) selectedTab.getContent();
        System.out.println("changed selection to this selected tab: " + selectedTab.getText());
        
        //if (previousTab != newTab)
        editor.update();
        //update the previous tab, not the current tab below!
//      Controller.updateEditor(selectedTab.getText(), editor); //this is dependent on the tab's name not changing, which may not be optimal design
        //why does this printout twice????
                        //TODO: alternatively: use reflection to update the tab. unnecessary use of reflection....but might not be able to do anything else because of javafx limitations. that's also bad becasue of dependencies...
                        //can i get the index of the tab and then match it to the one in the properties file? that's bad because of dependencies...brainstorm more ways
        
        //below is just to allow for testing of the LevelEditor right now:*/
        
     /*   if (myCurrentTab != selectedTab){
                System.out.println("pls printout only once for tab " + myCurrentTab.getText());
                Editor editor = (Editor) myCurrentTab.getContent();
                //System.out.println("changed selection to this selected tab: " + selectedTab.getText());
//              editor.update(); //this should be editor.save
                Controller.updateEditor(myCurrentTab.getText(), editor); //update old tab in the controller
                
                myCurrentTab = selectedTab;
                Editor editor2 = (Editor) myCurrentTab.getContent();
                editor2.update();
        }*/
        
        /*System.out.println("before" + myCurrentTab.getText());
        myCurrentTab = selectedTab;
        System.out.println("after" + myCurrentTab.getText());
        if (myCurrentTab == selectedTab){
                System.out.println("Update" + myCurrentTab.getText());
                Editor editor = (Editor) myCurrentTab.getContent();
                editor.update();
        }*/
                
                
        
        //this should be editor.update
}
}
