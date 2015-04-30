package player;

import groovy.ui.Console;
import imageselector.util.ScaleImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import annotations.parameter;
import authoringEnvironment.objects.Sidebar;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.util.Callback;
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
    private Pane mySidebar;
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

//        myMainArea.setStyle("-fx-background-color: #3333333");
        
        ObservableList<Sprite> displayList = FXCollections.observableArrayList(new ArrayList<>());
        displayList.addListener((ListChangeListener<Sprite>) change -> {
            while (change.next()) {
                for (Sprite obj : change.getAddedSubList()) {

              
                    ImageView myView = obj.getImageView();
                    if (displayList.size() == 1) {
                        ScaleImage.scaleNoPreserve(myView, myMainArea.getWidth()*0.7,
                                                   myMainArea.getHeight()*0.7);
                        myMainArea.getChildren().add(myView);
                        break;
                    }
                    myView.setOnMouseClicked(m -> updateInfoBox(obj));
                    myMainArea.getChildren().add(myView);
                }
                for (Sprite obj : change.getRemoved()) {
                    
                    System.err.println(myMainArea.getChildren().remove(obj.getImageView()));
                    
                }
            }
        });
        try {
            myGameController =
                    new GameController(
                                       gameFile.getAbsolutePath(),
                                       displayList, availableTowers);
        }
        catch (InsufficientParametersException e) {
            return;
        }

        myGameController.startGame(15);

    }

    private void updateInfoBox (Sprite placeSprite) {
        infoBox = new TableView<>();
        infoBox.setPrefHeight(myScreenHeight / 2);
        infoBox.setPrefWidth(myScreenWidth / 4);
        TableColumn<Field, String> name = new TableColumn<>("Field Name");
        TableColumn<Field, String> value = new TableColumn<>("Value");
        name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getName()));
        value.setCellValueFactory(new Callback<CellDataFeatures<Field, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call (CellDataFeatures<Field, String> param) {
                // TODO Auto-generated method stub
                String obj = null;
                try {
                    return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
                }
                catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }
        });

        myFields = FXCollections.observableArrayList(new ArrayList<>());
        infoBox.getColumns().add(name);
        infoBox.getColumns().add(value);
        myFields = FXCollections.observableArrayList(new ArrayList<>());
        Class<?> currentClass = placeSprite.getClass();
        while (currentClass != Object.class) {
            for (Field field : currentClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getAnnotation(parameter.class) != null &&
                    field.getAnnotation(parameter.class).playerDisplay()) {
                    myFields.add(field);
                }
            }
            // Make a new row
            currentClass = currentClass.getSuperclass();
        }
        infoBox.setItems(myFields);
        mySidebar.getChildren().add(infoBox);
    }

    ObservableList<Field> myFields;
    TableView<Field> infoBox;

    private TableView<Field> makeInfoBox () {
        TableColumn<Field, String> name = new TableColumn<>("Field Name");
        TableColumn<Field, String> value = new TableColumn<>("Value");
        name.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getName()));
        value.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().toString()));
        myFields = FXCollections.observableArrayList(new ArrayList<>());
        infoBox = new TableView<>();
        infoBox.getColumns().add(name);
        infoBox.getColumns().add(value);
        System.out.println("made table");
        return infoBox;
    }

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {
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

    public Scene getScene(){
        return myMainScene;
    }
    
    private MenuBar setUpMenu () {
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

    private Pane makeSideBar () {
        mySidebar = new Pane();
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
        infoBox = makeInfoBox();
        infoBox.setPrefHeight(myScreenHeight * 1 / 2);
        infoBox.setPrefWidth(myScreenWidth * 1 / 4);
        myTowerDisplay = makeTowerScrollBox();
        mySidebar.getChildren().addAll(infoBox, myTowerDisplay);
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
//        mainArea.setStyle("-fx-background-color: #3333333");
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
