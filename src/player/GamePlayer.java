package player;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import engine.GameController;
import engine.InsufficientParametersException;
import engine.element.Game;
import engine.element.Level;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;


/**
 * This class represents the Player that will be used to play created games. This class instantiates
 * a view, and passes it to
 * the GameLoop which then runs the game.
 * 
 * @author Greg McKeon
 * @author Sean Scott
 */
public class GamePlayer extends Application{
	private final int LEVEL_INDEX = 0;
	private final int LIVES_INDEX = 1;
	private final int TOWER_GRID_PADDING = 20;
	private final int INFO_PADDING = 24;
	
    private Stage myPlayerStage;
    private Group myEngineRoot = new Group();
    private Scene myMainScene;
    private VBox mySidebar;
    private GridPane myTowerGrid = new GridPane();
    private double myScreenWidth = 0;
    private double myScreenHeight = 0;
    private Pane myMainArea;
    private ScrollPane myTowerDisplay;
    private GameController myGameController;
    private VBox myInfoBox;
    private LevelObserver myLevelObserver = new LevelObserver();
    private GameObserver myGameObserver = new GameObserver();

    // find and open .game file
    public void loadGame () {

        myEngineRoot.getChildren().clear();
        myTowerGrid.getChildren().clear();
        File gameFile = getGameFile();
        myPlayerStage.setTitle(gameFile.getName().split("\\.")[0]);
        ObservableList<Tower> availableTowers =
                FXCollections.observableArrayList(new ArrayList<>());
        availableTowers.addListener(new ListChangeListener<Tower>() {
            @Override
            public void onChanged (Change change) {
                makeTowerGrid(change.getList());
            }
        });
        ObservableList<Sprite> displayList = FXCollections.observableArrayList(new ArrayList<>());
        try {
            myGameController =
                    new GameController(gameFile.getParent(), displayList);
        }
        catch (InsufficientParametersException e) {
            return;
        }
        displayList.addListener((ListChangeListener<Sprite>) change-> {
                while (change.next()) {
                    for (Object obj : change.getAddedSubList()) {
                        Sprite placeSprite = (Sprite) obj;
                        ImageView myView = placeSprite.getImageView();
                        myView.setOnMouseClicked( m-> updateInfoBox(placeSprite));
                        myMainArea.getChildren().add(myView);
                    }
                    for (Object obj : change.getRemoved()) {
                        Node placeSprite = (Node) obj;
                        myMainArea.getChildren().remove(placeSprite);
                    }
                }
        });
        // game.loadGame(gameFile.getParent(), engineRoot, screenWidth*3/4, screenHeight,
        // availableTowers);
        Image myImage = new Image(".\\images\\liltower.jpg");
        // ImageView test3 = new ImageView(myImage);
        // ImageView test4 = new ImageView(myImage);
        // ImageView test5 = new ImageView(myImage);
        // test5.setTranslateX(300);
        // test5.setTranslateY(300);
        // availableTowers.add(new Tower(test2));
        // availableTowers.add(new Tower(test3));
        // availableTowers.add(new Tower(test4));
        // displayList.add(new Tower(test5));
        //myGameController.startGame(60);
    }

    private TableView updateInfoBox (Sprite placeSprite) {
        return null;
        // TODO Auto-generated method stub
//        TableView<String> infoBox = new TableView<>();
//        
//        
//        TableColumn<String, String> name = new TableColumn<>();
//        name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().get));
//        TableColumn<String, String> value = new TableColumn<>();
//        name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>();
//        name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()
//                .getUsername()));
//        Class currentClass = placeSprite.getClass();
//        while(currentClass!=Object.class){
//            for(Field field:currentClass.getFields()){
//                if(field.getAnnotation(parameter.class)!=null&&field.getAnnotation(parameter.class).playerDisplay()){
//                    
//                }
//            }
//            // Make a new row
//            currentClass = currentClass.getSuperclass();
//        }
//        return null;
    }

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        myPlayerStage = primaryStage;
        setScreenBounds(primaryStage);
        myPlayerStage.setMaximized(true);
        Group root = new Group();
        Group menu = new Group();
        mySidebar = makeSideBar();
        myMainArea = makeMainPane();
        myMainScene = new Scene(root);
        root.getChildren().addAll(myMainArea, mySidebar, menu);
        menu.getChildren().add(setUpMenu());
        myPlayerStage.setScene(myMainScene);
        myPlayerStage.show();
    }

    private MenuBar setUpMenu () throws InsufficientParametersException {
        MenuBar myMenu = new MenuBar();
        Menu file = new Menu("File");
        MenuItem openGame = new MenuItem("Open Game");
        MenuItem switchEnv = new MenuItem("Switch to Authoring Env.");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().addAll(openGame, switchEnv, exit);
        myMenu.getMenus().add(file);
        openGame.setOnAction(e -> loadGame());
        exit.setOnAction(e -> System.exit(0));
        return myMenu;
    }

    private File getGameFile () {
        FileChooser fileOpener = new FileChooser();
        fileOpener.setTitle("Select a Game");
        fileOpener.setInitialDirectory(new File("."));
        fileOpener.getExtensionFilters().add(new ExtensionFilter("Game Files", "*.game"));
        return fileOpener.showOpenDialog(myPlayerStage);
    }

    private Rectangle2D setScreenBounds (Stage primaryStage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        myScreenWidth = bounds.getWidth();
        myScreenHeight = bounds.getHeight();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(myScreenWidth);
        primaryStage.setHeight(myScreenHeight);
        return bounds;
    }

    private VBox makeSideBar () {
        mySidebar = new VBox();
        // TODO: prop file
        mySidebar.setPrefWidth(myScreenWidth * 1 / 4);
        mySidebar.setPrefHeight(myScreenHeight);
        // TODO: prop
        mySidebar.setTranslateX(myScreenWidth * 3 / 4);
        // TODO: prop
        mySidebar.setStyle("-fx-border-style: solid outside; -fx-border-size: 0 0 0 2; -fx-border-color: transparent transparent transparent black; -fx-padding: 0; -fx-background-color: #00fb10");
        // makeGameVarBox();
        // makeSpriteInfoBox();
        mySidebar.getChildren().add(makeTowerScrollBox());
        VBox.setMargin(myTowerDisplay, new Insets(0));
        mySidebar.getChildren().add(makeInfoBox());
        VBox.setMargin(myInfoBox, new Insets(0));
        return mySidebar;
    }
    
    private VBox makeInfoBox() {
    	myInfoBox = new VBox();
    	myInfoBox.setPrefSize(myScreenWidth/4, myScreenHeight/4);
    	myInfoBox.setStyle("-fx-background-color:transparent");
    	myInfoBox.setTranslateY(-myScreenHeight/2);
    	myInfoBox.setAlignment(Pos.CENTER_LEFT);
    	Game game = myGameController.getGame();
    	game.addObserver(myGameObserver);
    	Level level = game.getActiveLevel();
    	level.addObserver(myLevelObserver);
    	Text levelText = new Text();
    	levelText.setFont(Font.font(INFO_PADDING));
    	levelText.setText("LEVEL: " + String.valueOf(game.getActiveLevelIndex()));
    	String lives = String.valueOf(level.getLives());
    	Text livesText = new Text();
    	livesText.setFont(Font.font(INFO_PADDING));
    	livesText.setText("LIVES: " + lives + " / " + lives);
    	myInfoBox.getChildren().add(LEVEL_INDEX, levelText);
    	myInfoBox.getChildren().add(LIVES_INDEX, livesText);
    	VBox.setMargin(livesText, new Insets(INFO_PADDING));
    	VBox.setMargin(levelText, new Insets(INFO_PADDING));
		return myInfoBox;
    }

    private ScrollPane makeTowerScrollBox () {
        myTowerDisplay = new ScrollPane();
        myTowerDisplay.setPrefWidth(myScreenWidth / 4);
        myTowerDisplay.setPrefHeight(myScreenHeight / 2);
        myTowerDisplay.setTranslateY(myScreenHeight / 2);
        myTowerDisplay.setStyle("-fx-background-color:transparent");
        myTowerDisplay.vbarPolicyProperty().set(ScrollBarPolicy.AS_NEEDED);
        myTowerDisplay.hbarPolicyProperty().set(ScrollBarPolicy.AS_NEEDED);
        return myTowerDisplay;
    }

    private void makeTowerGrid (ObservableList<Tower> towerList) {
        myTowerGrid = new GridPane();
        myTowerGrid.setPadding(new Insets(TOWER_GRID_PADDING));
        myTowerGrid.setAlignment(Pos.CENTER);
        double maxWidth = 0;
        for (Tower tower : towerList) {
            double towerWidth = tower.getImageView().getBoundsInParent().getWidth();
            if (towerWidth > maxWidth) {
                maxWidth = towerWidth;
            }
        }
        int numCols = (int) Math.floor((myTowerDisplay.getPrefWidth() - 2*TOWER_GRID_PADDING) / (maxWidth));
        if (numCols > towerList.size()) {
            numCols = towerList.size();
        }
        System.out.println(numCols);
        myTowerGrid.setHgap((myTowerDisplay.getPrefWidth() - 2*TOWER_GRID_PADDING - numCols * maxWidth) / (numCols - 1));
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints currCol = new ColumnConstraints();
            currCol.setMaxWidth(maxWidth);
            myTowerGrid.getColumnConstraints().add(currCol);
        }
        // if(numCols!=1){
        // }
        for (int i = 0; i < towerList.size(); i++) {
            ImageView myView = towerList.get(i).getImageView();
            myView.setOnDragDetected(new EventHandler<MouseEvent>() {

                @Override
                public void handle (MouseEvent event) {
                    Dragboard db = myView.startDragAndDrop(TransferMode.COPY);

                    /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    myView.setId("tower");
                    content.putImage(myView.getImage());
                    content.putString(myView.getId());
                    db.setContent(content);

                    event.consume();
                    System.out.println("started drag");
                }

            });
            myTowerGrid.add(myView, i % numCols, i / numCols);
        }
        myTowerDisplay.setContent(myTowerGrid);
    }

    private Pane makeMainPane () {
        Pane mainArea = new Pane(myEngineRoot);
        // TODO: property file this
        mainArea.setPrefWidth(myScreenWidth - myScreenWidth / 4);
        mainArea.setPrefHeight(myScreenHeight);
        mainArea.setStyle("-fx-background-color: #00dbc1");
        mainArea.setOnDragOver(event ->{
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
        });
        mainArea.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    // ImageView place = new ImageView(db.getImage());
                    System.out.println(db.getString());
                    myGameController.addPlaceable(db.getString(), event.getSceneX(),
                                                  event.getSceneY());
                    // place.setTranslateX(event.getSceneX() -
                    // Math.floor(db.getImage().getWidth() / 2));
                    // place.setTranslateY(event.getSceneY() -
                    // Math.floor(db.getImage().getHeight() / 2));
                    // TODO: tell engine about this
                    // game.addTower(place,);
                    // mainArea.getChildren().add(place);
                    success = true;
                }
                /*
                 * let the source know whether the string was successfully
                 * transferred and used
                 */
                event.setDropCompleted(success);
                event.consume();
        });
        return mainArea;
    }
    
    private void replaceStringInText(int index, Object arg){
    	Text text = (Text) myInfoBox.getChildren().get(index);
    	String[] spaceSplit = text.getText().split(" ");
		spaceSplit[1] = String.valueOf(arg);
		String newString = "";
		for (String piece: spaceSplit){
			newString += piece;
		}
		text.setText(newString);
    }
	
	private class LevelObserver implements Observer{

		@Override
		public void update(Observable o, Object arg) {
			replaceStringInText(LIVES_INDEX, arg);
		}
		
	}
	
	private class GameObserver implements Observer{

		@Override
		public void update(Observable o, Object arg) {
			replaceStringInText(LEVEL_INDEX, arg);			
			Game game = (Game) o;
			game.getActiveLevel().addObserver(myLevelObserver);
		}
		
	}

}
