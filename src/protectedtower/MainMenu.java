package protectedtower;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        
        Button test = new Button("Authoring Environment");
        test.setOnAction(e -> {
            myStage.setScene(Main.getScenes()[1]);
            myStage.show();
        });
        
        myRoot.getChildren().add(test);
        
        myScene = new Scene(myRoot, myDimensions.getWidth(), myDimensions.getHeight(), Color.WHITE); 
        return myScene;
    }
}
