package protectedtower;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu {
    private Stage myStage;
    private Group myRoot;
    private Scene myScene;
    private Dimension2D myDimensions;
    
    public MainMenu(Stage stage){
        myStage = stage;
    }
    
    public Scene initScene(Dimension2D dimensions){
        myDimensions = dimensions;
        myRoot = new Group();
        
        StackPane splashContent = new StackPane();
        
        VBox layout = new VBox(100);
        layout.setAlignment(Pos.CENTER);
        
        Rectangle background = new Rectangle(myDimensions.getWidth(), myDimensions.getHeight());
        background.setOpacity(0.8);
        
        Text title = new Text("Protected Tower Game Creator");
        title.setFont(new Font(42));
        title.setFill(Color.WHITE);
        animateText(title);
        
        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);
        
        Button toAuthoring = new Button("Authoring Environment");
        toAuthoring.setOnAction(e -> {
            myStage.setScene(Main.getScenes()[1]);
            myStage.show();
        });
        
        Button toPlayer = new Button("Game Player");
        buttons.getChildren().addAll(toAuthoring, toPlayer);
        
        layout.getChildren().addAll(title, buttons);
        splashContent.getChildren().addAll(background, layout);
        
        myRoot.getChildren().add(splashContent);
        
        myScene = new Scene(myRoot, myDimensions.getWidth(), myDimensions.getHeight(), Color.WHITE); 
        return myScene;
    }
    
    private void animateText(Text title){
        TranslateTransition move = new TranslateTransition(Duration.millis(500), title);
        move.setFromY(-5);
        move.setToY(5);
        move.setAutoReverse(true);
        move.setCycleCount(Animation.INDEFINITE);
        move.play();
    }
}
