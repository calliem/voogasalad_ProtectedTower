package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TagGroup extends Group{
    private VBox tagListDisplay;
    private List<Tag> tagList;
//    private VBox tagsDisplay;
    private int tagCount = 0;
    private Text tagCountDisplay;
    private StackPane countDisplay;
    private StackPane overlayView;
    
    private static final int PADDING = 10;
    private static final int TAG_WIDTH = 75;
    private static final int TAG_HEIGHT = 20;
    
    public TagGroup(){
        tagListDisplay = new VBox(PADDING);
        tagList = new ArrayList<>();
        
        countDisplay = new StackPane();
        Rectangle countDisplayBody = new Rectangle(TAG_WIDTH, TAG_HEIGHT);
        countDisplayBody.setOpacity(0.9);
        tagCountDisplay = new Text(tagCount+"");
        tagCountDisplay.setFill(Color.WHITE);
        
        Tooltip tooltip = new Tooltip("Click here to see all tags...");
        tooltip.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(countDisplay, tooltip);
        
        countDisplay.getChildren().addAll(countDisplayBody, tagCountDisplay);
        
        overlayView = new StackPane();
        Rectangle overlayBackground = new Rectangle(300, 500);
        overlayView.getChildren().add(overlayBackground);
        
        this.getChildren().add(tagListDisplay);
    }
    
    public void addTag(Tag tag){
        if(tagCount > 5){
            this.getChildren().remove(tagListDisplay);
            this.getChildren().add(countDisplay);
            
            overlayView.getChildren().add(tagListDisplay);
        }
        
        tagCount++;
        tagCountDisplay.setText(tagCount+"");
        tagList.add(tag);
        tagListDisplay.getChildren().add(tag);
    }
    
    public StackPane getOverlay(){
        return overlayView;
    }
}
