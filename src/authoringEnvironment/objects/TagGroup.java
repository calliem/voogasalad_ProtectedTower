package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class TagGroup extends Group{
    private VBox tagListDisplay;
    private List<Tag> tagList;
    private int tagCount = 0;
    private Text tagCountDisplay;
    private StackPane countDisplay;
    
    private ScrollPane overlayView;
    private Rectangle overlayBackground;
    
    private static final int PADDING = 5;
    private static final int TAG_WIDTH = 75;
    private static final int TAG_HEIGHT = 20;
    private static final int OVERLAY_HEIGHT = 200;
    
    public TagGroup(){
        tagListDisplay = new VBox(PADDING);
        tagListDisplay.setTranslateY(PADDING);
        tagListDisplay.setAlignment(Pos.TOP_CENTER);
        tagList = new ArrayList<>();
        
        countDisplay = new StackPane();
        Rectangle countDisplayBody = new Rectangle(TAG_WIDTH, TAG_HEIGHT, Color.DARKGRAY);
        countDisplayBody.setArcWidth(10);
        countDisplayBody.setArcHeight(10);
        
        tagCountDisplay = new Text(tagCount+"");
        tagCountDisplay.setFill(Color.WHITE);
        tagCountDisplay.setFont(new Font(12));
        
        Tooltip tooltip = new Tooltip("Click here to see all tags...");
        tooltip.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(countDisplay, tooltip);
        
        countDisplay.getChildren().addAll(countDisplayBody, tagCountDisplay);
        
        overlayView = new ScrollPane();
        
        StackPane overlayContent = new StackPane();
        overlayBackground = new Rectangle(TAG_WIDTH+2*PADDING, OVERLAY_HEIGHT);
        overlayBackground.setOpacity(0.8);
        overlayContent.getChildren().addAll(overlayBackground, tagListDisplay);
        
        overlayView.setContent(overlayContent);
        overlayView.setVbarPolicy(ScrollBarPolicy.NEVER);
        overlayView.setHbarPolicy(ScrollBarPolicy.NEVER);
        overlayView.setMaxWidth(TAG_WIDTH+2*PADDING);
        overlayView.setMaxHeight(OVERLAY_HEIGHT);
        
        this.getChildren().add(countDisplay);
    }
    
    public void addTag(Tag tag){
        tagCount++;
        tagCountDisplay.setText(tagCount+"");
        
        ScaleTransition scale = new ScaleTransition(Duration.millis(100), tagCountDisplay);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.3);
        scale.setToY(1.3);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
        
        tagList.add(tag);
        tag.hideButton();
//        
//        this.getChildren().remove(countDisplay);
//        this.getChildren().add(tagListDisplay);
        
        System.out.println(tagList);
        adjustBackground();
        tagListDisplay.getChildren().add(tag);
        System.out.println(tagListDisplay.getChildren());
    }
    
    private void adjustBackground(){
        if(tagCount*(TAG_HEIGHT + PADDING) + PADDING > OVERLAY_HEIGHT){
            overlayBackground.setHeight(tagCount*(TAG_HEIGHT + PADDING) + PADDING);
        }
    }
    
    public void addTags(Tag ...tags){
        for(Tag tag : tags){
            addTag(tag);
        }
    }
    
    public ScrollPane getOverlay(){
        return overlayView;
    }
}
