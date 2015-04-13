package protectedtower;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu {
    private static final double BACKGROUND_OPACITY = 0.8;
    
    private static final double PRE_EXPAND_WIDTH = 0.01;
    private static final int SCENE_BUTTON_WIDTH = 200;
    private static final int SCENE_BUTTON_HEIGHT = 80;
    private static final int GO_BUTTON_WIDTH = 100;
    private static final int GO_BUTTON_HEIGHT = 50;
    private static final int BORDER_WIDTH = 2;
    
    private static final int GALLERY_WIDTH = 600;
    private static final int GALLERY_HEIGHT = 400;
    
    private static final String TITLE = "Protected Tower Game Creator";
    private static final String AUTHORING_LABEL = "Authoring Environment";
    private static final String AUTHORING_INFO = "Create a game!";
    private static final String PLAYER_LABEL = "Game Player";
    private static final String PLAYER_INFO = "Play a game!";
    private static final String FONT = "Helvetica";
    
    private Stage myStage;
    private Group myRoot;
    private Scene myScene;
    private Dimension2D myDimensions;
    
    private boolean moving = false;
    private Scene sceneSelected;

    public MainMenu(Stage stage){
        myStage = stage;
    }

    public Scene initScene(Dimension2D dimensions){
        myDimensions = dimensions;
        myRoot = new Group();

        StackPane splashContent = new StackPane();

        Rectangle background = new Rectangle(myDimensions.getWidth(), myDimensions.getHeight());
        background.setOpacity(BACKGROUND_OPACITY);

        Text title = new Text(TITLE);
        title.setFont(new Font(42));
        title.setFill(Color.WHITE);
        animateText(title);

        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.CENTER);

        Rectangle test = new Rectangle(GALLERY_WIDTH, GALLERY_HEIGHT, Color.WHITE);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        
        StackPane authoring = createButton(AUTHORING_LABEL, AUTHORING_INFO);
        
        StackPane player = createButton(PLAYER_LABEL, PLAYER_INFO);
        buttons.getChildren().addAll(authoring, player);
        
        StackPane go = makeGoButton();
        
        mainContent.getChildren().addAll(buttons, go);
        
        authoring.setOnMouseClicked(e -> {
            if(!mainContent.getChildren().contains(test)){
                mainContent.getChildren().add(1, test);
                entryAnimation(mainContent, test, go);
            }
            sceneSelected = Main.getScenes()[1];
        });

        go.setOnMouseClicked(e -> {
            myStage.setScene(sceneSelected);
            myStage.show();
        });
        
        splashContent.getChildren().addAll(background, title, mainContent);
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        myRoot.getChildren().add(splashContent);

        myScene = new Scene(myRoot, myDimensions.getWidth(), myDimensions.getHeight(), Color.WHITE); 
        return myScene;
    }

    private StackPane makeGoButton () {
        StackPane go = new StackPane();
        Rectangle goButton = new Rectangle(GO_BUTTON_WIDTH, GO_BUTTON_HEIGHT, Color.RED);
        goButton.setArcWidth(10);
        goButton.setArcHeight(10);
        goButton.setStrokeWidth(BORDER_WIDTH);
        goButton.setStroke(Color.RED);
        Text goText = new Text("Go"); //TODO
        goText.setFill(Color.WHITE);
        goText.setFont(new Font(FONT, 20));

        go.getChildren().addAll(goButton, goText);
        goButton.setOnMouseEntered(e -> {
            goButton.setFill(Color.DARKRED);
        });
        go.setOnMouseExited(e -> {
            goButton.setFill(Color.RED);
        });
        go.setVisible(false);
        return go;
    }

    /**
     * @param container
     * @param content
     */
    private void entryAnimation (VBox container, Rectangle content, StackPane go) {
        moving = true;
        TranslateTransition moveButtons = new TranslateTransition(Duration.millis(800), container);
        moveButtons.setFromY(210); //TODO
        moveButtons.setToY(30); //TODO
        moveButtons.setOnFinished(ae -> moving = false);
        
        ScaleTransition expandY = new ScaleTransition(Duration.millis(500), content);
        expandY.setFromY(0.0);
        expandY.setFromX(PRE_EXPAND_WIDTH);
        expandY.setToY(1.0);
        expandY.setToX(PRE_EXPAND_WIDTH);
        
        ScaleTransition expandX = new ScaleTransition(Duration.millis(700), content);
        expandX.setFromX(PRE_EXPAND_WIDTH);
        expandX.setToX(1.0);
        
        go.setVisible(true);
        FadeTransition buttonAppear = new FadeTransition(Duration.millis(500), go);
        buttonAppear.setFromValue(0.0);
        buttonAppear.setToValue(1.0);
        
        SequentialTransition pt = new SequentialTransition(moveButtons, expandY, expandX, buttonAppear);
        pt.play();
    }
    
    private StackPane createButton(String label, String description){
        StackPane button = new StackPane();
        Rectangle buttonBackground = new Rectangle(SCENE_BUTTON_WIDTH, SCENE_BUTTON_HEIGHT, Color.WHITE);
        buttonBackground.setArcWidth(10);
        buttonBackground.setArcHeight(10);
        buttonBackground.setStrokeWidth(BORDER_WIDTH);
        buttonBackground.setStroke(Color.GOLDENROD);
        Text labelDisplay = new Text(label);
        labelDisplay.setFont(new Font(FONT, 18));
        labelDisplay.setWrappingWidth(150);
        labelDisplay.setTextAlignment(TextAlignment.CENTER);
        button.getChildren().addAll(buttonBackground, labelDisplay);
        
        button.setOnMouseEntered(e -> {
            if(!moving){
                buttonBackground.setFill(Color.DARKGRAY);
                labelDisplay.setFill(Color.WHITE);
                labelDisplay.setText(description);
            }
        });
        
        button.setOnMouseExited(e -> {
            buttonBackground.setFill(Color.WHITE);
            labelDisplay.setFill(Color.BLACK);
            labelDisplay.setText(label);
        });
        
        return button;
    }

    private void animateText(Text title){
        TranslateTransition move = new TranslateTransition(Duration.millis(1000), title);
        move.setFromY(45);
        move.setToY(55);
        move.setAutoReverse(true);
        move.setCycleCount(Animation.INDEFINITE);
        move.play();
    }
}
