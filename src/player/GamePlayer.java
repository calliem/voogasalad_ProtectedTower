package player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.event.DirectEvent;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import engine.GameController;
import engine.InsufficientParametersException;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;

/*
 * This class represents the Player that will be used to play created games.  This class instantiates a view, and passes it to
 * the GameLoop which then runs the game.
 */
public class GamePlayer extends Application{
	private GameController game;
	private Stage playerStage;
	private Group engineRoot = new Group();
	private Scene mainScene;
	private VBox sideBar;
	private GridPane towerGrid;
	private double screenWidth = 0;
	private double screenHeight = 0;
	public GamePlayer() throws InsufficientParametersException {
		game = new GameController();
	}
	// find and open .game file
	public void loadGame(){
		
		try{
			engineRoot.getChildren().clear();
			File gameFile = getGameFile();
			playerStage.setTitle(gameFile.getName().split("\\.")[0]);
			ObservableList<Tower> availableTowers = FXCollections.observableArrayList(new ArrayList<>());
			availableTowers.addListener(new ListChangeListener<Tower>(){
				@Override
				public void onChanged(Change change) {
				while(change.next()){
				addToSideBar(change.getAddedSubList());
				deleteFromSideBar(change.getRemoved());
				}
				}
			});
			//game.loadGame(gameFile.getParent(), engineRoot, screenWidth*3/4, screenHeight, availableTowers);
			ImageView test = new ImageView(".\\images\\addtower.png");
			ImageView test2 = new ImageView(".\\images\\addtower.png");
			ImageView test3 = new ImageView(".\\images\\addtower.png");
			ImageView test4 = new ImageView(".\\images\\addtower.png");
			ImageView test5 = new ImageView(".\\images\\addtower.png");
			availableTowers.add(new Tower(test));
			availableTowers.add(new Tower(test2));
			availableTowers.add(new Tower(test3));
			availableTowers.add(new Tower(test4));
			availableTowers.add(new Tower(test5));
		}
		catch(NullPointerException e){
		}
		
	
	}
	private void addToSideBar(List<Sprite> addedSubList) {
		if(addedSubList.size()!=0){
		for(Sprite s:addedSubList){
			int currentSize = towerGrid.getChildren().size();
			double oldWidth = s.getImageView().getBoundsInLocal().getWidth();
			ImageView myView = s.getImageView();
			myView.setOnDragDetected(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent event) {
				      Dragboard db = myView.startDragAndDrop(TransferMode.ANY);
				        
				        /* Put a string on a dragboard */
				        ClipboardContent content = new ClipboardContent();
				        content.putImage(myView.getImage());
				        db.setContent(content);
				        
				        event.consume();
				        System.out.println("started drag");
				    }
				
			});
			myView.setFitWidth(towerGrid.getColumnConstraints().get(currentSize%2).getPrefWidth());
			towerGrid.add(myView, currentSize%2, currentSize/2);
			GridPane.setHgrow(myView, Priority.NEVER);
		}	
		}
	}
	private void deleteFromSideBar(List<Sprite> deletedSubList) {
		while(!deletedSubList.isEmpty()){
				Sprite toDeleteSprite = deletedSubList.remove(0);
				ImageView toDelete = deletedSubList.remove(0).getImageView();
				ArrayList<Node> currentList= new ArrayList<>(towerGrid.getChildren());
					for(Node node:currentList){
						if(toDelete.equals(node)){
							towerGrid.getChildren().remove(toDeleteSprite);
						}
			}
		}
	}
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		playerStage = primaryStage;
		setScreenBounds(primaryStage);
		Group root = new Group();
		Group menu = new Group();
		sideBar = makeSideBar();
		Pane mainArea = makeMainPane();
		mainScene = new Scene(root);
		root.getChildren().addAll(mainArea,sideBar,menu);
		menu.getChildren().add(setUpMenu());
		playerStage.setScene(mainScene);
		playerStage.show();
	}
	
	private MenuBar setUpMenu() throws InsufficientParametersException {
		MenuBar myMenu = new MenuBar();
		Menu file = new Menu("File");
		MenuItem openGame = new MenuItem("Open Game");
		MenuItem switchEnv = new MenuItem("Switch to Authoring Env.");
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(openGame,switchEnv,exit);
		myMenu.getMenus().add(file);
		openGame.setOnAction(e->loadGame());
		exit.setOnAction(e->System.exit(0));
		return myMenu;
	}
	private File getGameFile() {
		FileChooser fileOpener = new FileChooser();
		fileOpener.setTitle("Select a Game");
		fileOpener.setInitialDirectory(new File("."));
		fileOpener.getExtensionFilters().add(new ExtensionFilter("Game Files","*.game"));
		return fileOpener.showOpenDialog(playerStage);
	}
	private Rectangle2D setScreenBounds (Stage primaryStage) {
	        Screen screen = Screen.getPrimary();
	        Rectangle2D bounds = screen.getVisualBounds();
	        screenWidth =bounds.getWidth();
	        screenHeight = bounds.getHeight();
	        primaryStage.setX(bounds.getMinX());
	        primaryStage.setY(bounds.getMinY());
	        primaryStage.setWidth(screenWidth);
	        primaryStage.setHeight(screenHeight);
	        return bounds;
	}
	private VBox makeSideBar(){
		sideBar = new VBox();
		// TODO: prop file
		sideBar.setPrefWidth(screenWidth*1/4);
		sideBar.setPrefHeight(screenHeight);
		// TODO: prop
		sideBar.setTranslateX(screenWidth*3/4);
		// TODO: prop
		sideBar.setStyle("-fx-border-style: solid outside; -fx-border-size: 0 0 0 2; -fx-border-color: transparent transparent transparent black; -fx-padding: 0; -fx-background-color: #00fb10");
		//makeGameVarBox();
		//makeSpriteInfoBox();
		ScrollPane towerDisplay = makeTurretBox();
		sideBar.getChildren().add(towerDisplay);
		VBox.setMargin(towerDisplay, new Insets(0));
		return sideBar;
	}
	
	private ScrollPane makeTurretBox() {
		// TODO Auto-generated method stub
		ScrollPane towerDisplay = new ScrollPane();
		towerDisplay.setPrefWidth(screenWidth/4);
		towerDisplay.setPrefHeight(screenHeight/2);
		towerDisplay.setTranslateY(screenHeight/2);
		towerDisplay.setStyle("-fx-background-color:transparent");
		towerDisplay.vbarPolicyProperty().set(ScrollBarPolicy.AS_NEEDED);
		towerDisplay.hbarPolicyProperty().set(ScrollBarPolicy.AS_NEEDED);
		towerDisplay.setContent(makeGridPane(towerDisplay));
		return towerDisplay;
	}
	private GridPane makeGridPane(ScrollPane gridHolder){
		towerGrid = new GridPane();
		 ColumnConstraints col1 = new ColumnConstraints();
	     col1.setMaxWidth(gridHolder.getPrefWidth()/2);
	     ColumnConstraints col2 = new ColumnConstraints();
	     col2.setMaxWidth(gridHolder.getPrefWidth()/2);
	     towerGrid.getColumnConstraints().addAll(col1,col2);
	     towerGrid.setHgap(50);
		return towerGrid;
	}
	private Pane makeMainPane() {
		Pane mainArea = new Pane(engineRoot);
		// TODO: property file this
		mainArea.setPrefWidth(screenWidth - screenWidth/4);
		mainArea.setPrefHeight(screenHeight);
		mainArea.setStyle("-fx-background-color: #00dbc1");
		mainArea.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (event.getGestureSource() != mainArea &&
		                 event.getDragboard().hasImage()) {
		             mainArea.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		         }
		                
		         event.consume();
		    }
		});
		mainArea.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (event.getGestureSource() != mainArea &&
		                 event.getDragboard().hasImage()) {
		             mainArea.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		         }
		                
		         event.consume();
		    }
		});
		mainArea.setOnDragDropped(new EventHandler<DragEvent>(){
			 public void handle(DragEvent event) {
	                /* data dropped */
	                System.out.println("onDragDropped");
	                /* if there is a string data on dragboard, read it and use it */
	                Dragboard db = event.getDragboard();
	                boolean success = false;
	                if (db.hasImage()) {
	                    mainArea.getChildren().add(new ImageView(db.getImage()));
	                    success = true;
	                }
	                /* let the source know whether the string was successfully 
	                 * transferred and used */
	                event.setDropCompleted(success);
	                
	                event.consume();
	            }
		});
		return mainArea;
	}

	
}
