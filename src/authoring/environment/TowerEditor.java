package authoring.environment;

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
import authoring.environment.objects.Tower;

public class TowerEditor extends PropertyEditor{
    private Group myRoot;
    private StackPane myContent;
    private static final double CONTENT_WIDTH = MainEnvironment.myDimensions.getWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.myDimensions.getHeight();
    
    
    public ArrayList<Tower> getTowers(){
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
        row.setAlignment(Pos.TOP_CENTER);
        row.setTranslateY(10);
        Rectangle tower = new Rectangle(40,40,Color.WHITE); //hard-coded placeholders
        tower.setOnMousePressed((e) -> handleTowerEdit());
        Tooltip t = new Tooltip("Test");
        Tooltip.install(tower, t);
        Rectangle tower2 = new Rectangle(40,40,Color.WHITE);
        Rectangle tower3 = new Rectangle(40,40,Color.WHITE);
        row.getChildren().addAll(tower, tower2, tower3);
        
        myContent.getChildren().addAll(background, row);
        myRoot.getChildren().addAll(myContent);
        return myRoot;
    }
    
    private void handleTowerEdit(){
        StackPane overlay = new StackPane();
        Rectangle overlayBackground = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        overlayBackground.setOpacity(0.6);
        
        VBox editorContent = new VBox(10);
        editorContent.setAlignment(Pos.TOP_CENTER);
        editorContent.setTranslateY(10);
        Text title = new Text("Tower");
        title.setFont(new Font(30));
        title.setFill(Color.WHITE);
        
        Button close = new Button("Close");
        close.setOnAction((e) -> hideEditScreen(overlay));
        
        editorContent.getChildren().addAll(title, close);
        
        overlay.getChildren().addAll(overlayBackground, editorContent);
        myContent.getChildren().add(overlay);
        showEditScreen(overlay);
    }
    
    private void showEditScreen(StackPane overlay){
        scaleEditScreen(0.0, 1.0, overlay);
    }
    
    private void hideEditScreen(StackPane overlay){
        scaleEditScreen(1.0, 0.0, overlay);
    }
    
    private void scaleEditScreen(double from, double to, StackPane overlay){
        ScaleTransition scale = new ScaleTransition(Duration.millis(400), overlay);
        scale.setFromX(from);
        scale.setFromY(from);
        scale.setToX(to);
        scale.setToY(to);
        scale.setCycleCount(1);
        scale.play();
    }
}



