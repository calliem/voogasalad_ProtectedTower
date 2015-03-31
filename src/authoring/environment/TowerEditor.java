package authoring.environment;

import authoring.environment.setting.*;
import java.util.ArrayList;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoring.environment.objects.TowerView;

public class TowerEditor extends PropertyEditor{
    private Group myRoot;
    private StackPane myContent;
    private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();
    
    public ArrayList<TowerView> getTowers(){
        return new ArrayList<>();
    }

    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        myRoot = new Group();
        myContent = new StackPane();
        
        // TODO remove magic number
        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, Color.GRAY);
        
        // TODO remove magic numbers
        HBox row = new HBox(10);
        
        TowerView tower = new TowerView(40,40);
        tower.setOnMousePressed((e) -> handleTowerEdit(tower.getEditorOverlay()));
        
        TowerView tower2 = new TowerView(40,40);
        Rectangle tower3 = new Rectangle(40,40,Color.WHITE);
        
        row.getChildren().addAll(tower, tower2, tower3);
        row.setAlignment(Pos.TOP_CENTER);
        row.setMaxHeight(40);
        row.setTranslateY(10);
        
        myContent.getChildren().addAll(background, row);
        StackPane.setAlignment(row, Pos.TOP_CENTER);
        myRoot.getChildren().add(myContent);
        
        return myRoot;
    }
    
    private void handleTowerEdit(StackPane overlay){
        System.out.println("clicked");
        showEditScreen(overlay);
    }
    
    private void showEditScreen(StackPane overlay){
        myRoot.getChildren().add(overlay);
        scaleEditScreen(0.0, 1.0, overlay);
    }
    
    private void hideEditScreen(StackPane overlay){
        ScaleTransition scale = scaleEditScreen(1.0, 0.0, overlay);
        scale.setOnFinished((e) -> myRoot.getChildren().remove(overlay));
    }
    
    private ScaleTransition scaleEditScreen(double from, double to, StackPane overlay){
        ScaleTransition scale = new ScaleTransition(Duration.millis(400), overlay);
        scale.setFromX(from);
        scale.setFromY(from);
        scale.setToX(to);
        scale.setToY(to);
        scale.setCycleCount(1);
        scale.play();
        
        return scale;
    }
}



