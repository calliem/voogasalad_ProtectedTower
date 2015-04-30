package player;

import imageselector.util.ScaleImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import engine.GameController;
import engine.InsufficientParametersException;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;


/*
 * This class represents the Player that will be used to play created games. This class instantiates
 * a view, and passes it to
 * the GameLoop which then runs the game.
 */
public class GamePlayer extends Application {
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
    private Group background;

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
        displayList.addListener((ListChangeListener<Sprite>) change -> {
            while (change.next()) {
                for (Object obj : change.getAddedSubList()) {

                    Sprite placeSprite = (Sprite) obj;

                    ImageView myView = placeSprite.getImageView();
                    if (displayList.size() == 1) {
                          ScaleImage.scaleNoPreserve(myView, myMainArea.getWidth(), myMainArea.getHeight());
                          myMainArea.getChildren().add(myView);
                          break;
                    }
                    myView.setOnMouseClicked(m -> updateInfoBox(placeSprite));
                    myMainArea.getChildren().add(myView);
                }
                for (Object obj : change.getRemoved()) {
                    Node placeSprite = (Node) obj;
                    myMainArea.getChildren().remove(placeSprite);
                }
            }
        });
        try {
            myGameController =
                    new GameController(
                                       gameFile.getAbsolutePath(),
                                       displayList, availableTowers, background);
        }
        catch (InsufficientParametersException e) {
            return;
        }

        myGameController.startGame(1);
        
    }

    private TableView updateInfoBox (Sprite placeSprite) {
        return null;
        // TODO Auto-generated method stub
        // TableView<String> infoBox = new TableView<>();
        //
        //
        // TableColumn<String, String> name = new TableColumn<>();
        // name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().get));
        // TableColumn<String, String> value = new TableColumn<>();
        // name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>();
        // name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()
        // .getUsername()));
        // Class currentClass = placeSprite.getClass();
        // while(currentClass!=Object.class){
        // for(Field field:currentClass.getFields()){
        // if(field.getAnnotation(parameter.class)!=null&&field.getAnnotation(parameter.class).playerDisplay()){
        //
        // }
        // }
        // // Make a new row
        // currentClass = currentClass.getSuperclass();
        // }
        // return null;
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
        fileOpener.getExtensionFilters().add(new ExtensionFilter("Game Files", "*.gamefile"));
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
        mySidebar
                .setStyle("-fx-border-style: solid outside; -fx-border-size: 0 0 0 2; -fx-border-color: transparent transparent transparent black; -fx-padding: 0; -fx-background-color: #00fb10");
        // makeGameVarBox();
        // makeSpriteInfoBox();
        myTowerDisplay = makeTowerScrollBox();
        mySidebar.getChildren().add(myTowerDisplay);
        VBox.setMargin(myTowerDisplay, new Insets(0));
        return mySidebar;
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
        myTowerGrid.setPadding(new Insets(20));
        myTowerGrid.setAlignment(Pos.CENTER);
        double maxWidth = 0;
        for (Tower tower : towerList) {
            double towerWidth = tower.getImageView().getBoundsInParent().getWidth();
            if (towerWidth > maxWidth) {
                maxWidth = towerWidth;
            }
        }
        int numCols = (int) Math.floor((myTowerDisplay.getPrefWidth() - 40) / (maxWidth));
        if (numCols > towerList.size() && towerList.size() > 0) {
            numCols = towerList.size();
        }
        if (numCols == 0) {
            numCols = 1;
        }
        myTowerGrid.setHgap((myTowerDisplay.getPrefWidth() - 40 - numCols * maxWidth) /
                            (numCols - 1));
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints currCol = new ColumnConstraints();
            currCol.setMaxWidth(maxWidth);
            myTowerGrid.getColumnConstraints().add(currCol);
        }
        // if(numCols!=1){
        // }
        for (int i = 0; i < towerList.size(); i++) {
            ImageView myView = towerList.get(i).getImageView();
            myView.setId(towerList.get(i).getGUID());
            myView.setOnDragDetected(new EventHandler<MouseEvent>() {

                @Override
                public void handle (MouseEvent event) {
                    Dragboard db = myView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

                    /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();

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
        mainArea.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        mainArea.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                myGameController.addPlaceable(db.getString(),
                                              event.getSceneX() -
                                                      Math.floor(db.getImage().getWidth() / 2),
                                              event.getSceneY() -
                                                      Math.floor(db.getImage().getHeight() / 2));
                success = true;
            }
            /*
             * // * let the source know whether the string was successfully
             * // * transferred and used
             * //
             */
                event.setDropCompleted(success);
                event.consume();
            });

        return mainArea;
    }

}
