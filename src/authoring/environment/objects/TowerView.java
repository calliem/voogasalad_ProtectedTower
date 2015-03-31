package authoring.environment.objects;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoring.environment.MainEnvironment;
import authoring.environment.setting.IntegerSetting;
import authoring.environment.setting.Setting;

public class TowerView extends SpriteView{
    private VBox editableContent;
    private StackPane overlayContent;
    
    private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();
    
    public TowerView(int width, int height){
        Rectangle tower = new Rectangle(width, height, Color.WHITE);
        Text test = new Text("Test");
        getChildren().addAll(tower, test);
        
        setupEditableContent();
        setupOverlayContent();
        
        Tooltip info = new Tooltip("Test");
        Tooltip.install(this, info);
    }
    
    public void setupEditableContent(){
        editableContent = new VBox(10);
        editableContent.setAlignment(Pos.TOP_CENTER);
        editableContent.setTranslateY(10);
        
        Text title = new Text("Tower");
        title.setFont(new Font(30));
        title.setFill(Color.WHITE);
        
        Setting test = new IntegerSetting("health");
        Button close = new Button("Close");
        
        editableContent.getChildren().addAll(title, test, close);
    }
    
    public void setupOverlayContent(){
        overlayContent = new StackPane();
        
        Rectangle overlayBackground = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        overlayBackground.setOpacity(0.6);
        
        overlayContent.getChildren().addAll(overlayBackground, editableContent);
    }
    
    public StackPane getEditorOverlay(){
        return overlayContent;
    }
}
