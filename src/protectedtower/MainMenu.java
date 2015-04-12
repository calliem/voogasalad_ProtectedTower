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
        background.setOpacity(0.8);

        Text title = new Text("Protected Tower Game Creator");
        title.setFont(new Font(42));
        title.setFill(Color.WHITE);
        animateText(title);

        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.CENTER);

        Rectangle test = new Rectangle(600, 400, Color.WHITE);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        
        StackPane authoring = createButton("Authoring Environment", "Create a game!");
        
        StackPane player = createButton("Game Player", "Play a game!");
        buttons.getChildren().addAll(authoring, player);
        
        StackPane go = new StackPane();
        Rectangle goButton = new Rectangle(100, 50, Color.RED);
        goButton.setArcWidth(10);
        goButton.setArcHeight(10);
        goButton.setStrokeWidth(2);
        goButton.setStroke(Color.RED);
        Text goText = new Text("Go");
        goText.setFill(Color.WHITE);
        go.getChildren().addAll(goButton, goText);
        go.setOnMouseEntered(e -> {
            goButton.setFill(Color.DARKRED);
        });
        go.setOnMouseExited(e -> {
            goButton.setFill(Color.RED);
        });
        go.setVisible(false);
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

    /**
     * @param container
     * @param content
     */
    private void entryAnimation (VBox container, Rectangle content, StackPane go) {
        moving = true;
        TranslateTransition moveButtons = new TranslateTransition(Duration.millis(800), container);
        moveButtons.setFromY(210);
        moveButtons.setToY(30);
        moveButtons.setCycleCount(1);
        moveButtons.setOnFinished(ae -> moving = false);
        
        ScaleTransition expandY = new ScaleTransition(Duration.millis(500), content);
        expandY.setFromY(0.0);
        expandY.setFromX(0.01);
        expandY.setToY(1.0);
        expandY.setToX(0.01);
        expandY.setCycleCount(1);
        
        ScaleTransition expandX = new ScaleTransition(Duration.millis(700), content);
        expandX.setFromX(0.01);
        expandX.setToX(1);
        expandX.setCycleCount(1);
        
        go.setVisible(true);
        FadeTransition buttonAppear = new FadeTransition(Duration.millis(500), go);
        buttonAppear.setFromValue(0.0);
        buttonAppear.setToValue(1.0);
        
        SequentialTransition pt = new SequentialTransition(moveButtons, expandY, expandX, buttonAppear);
        pt.play();
    }
    
    private StackPane createButton(String label, String description){
        StackPane button = new StackPane();
        Rectangle buttonBackground = new Rectangle(200, 80, Color.WHITE);
        buttonBackground.setArcWidth(10);
        buttonBackground.setArcHeight(10);
        buttonBackground.setStrokeWidth(2);
        buttonBackground.setStroke(Color.GOLDENROD);
        Text labelDisplay = new Text(label);
        labelDisplay.setFont(new Font("Helvetica", 18));
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
