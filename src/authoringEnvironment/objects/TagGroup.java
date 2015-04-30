package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import authoringEnvironment.Controller;
import authoringEnvironment.util.Scaler;

public class TagGroup extends Group{
    private VBox tagListDisplay;
    private List<String> tagList;
    private List<Tag> tagObjects;
    private int tagCount = 0;
    private Text tagCountDisplay;
    private StackPane countDisplay;
    
    private ScrollPane overlayView;
    private Rectangle overlayBackground;
    private StackPane closeButton;
    private Controller myController;
    private String myKey;
    
    private static final int PADDING = 5;
    private static final int TAG_WIDTH = 75;
    private static final int TAG_HEIGHT = 20;
    private static final int ARC_SIZE = 20;
    private static final int OVERLAY_HEIGHT = 200;
    private static final int OVERLAY_WIDTH = 100;
    private static final double EXPANDED_RATIO = 1.3;
    private static final double REGULAR_RATIO = 1.0;
    private static final int SCALE_DURATION = 100;
    private static final int COUNT_FONT_SIZE = 12;
    private static final int CLOSE_BUTTON_FONT = 10;
    private static final double OVERLAY_BACKGROUND_OPACITY = 0.8;
    private static final double START_VALUE = 0.0;
    private static final double END_VALUE = 1.0;
    private static final int SCALE_CYCLES = 2;
    private static final String TOOLTIP_MESSAGE = "Click here to see all tags...";
    
    private static final String INTERFACE_TEXT = "resources/display/interface_text";
    private static final ResourceBundle displayText = ResourceBundle.getBundle(INTERFACE_TEXT);
    
    public TagGroup(Controller controller, String partKey){
        myKey = partKey;
        tagListDisplay = new VBox(PADDING);
        tagListDisplay.setTranslateY(PADDING);
        tagListDisplay.setAlignment(Pos.TOP_CENTER);
        tagList = new ArrayList<>();
        tagObjects = new ArrayList<>();
        myController = controller;
        
        countDisplay = new StackPane();
        Rectangle countDisplayBody = new Rectangle(TAG_WIDTH, TAG_HEIGHT, Color.DARKGRAY);
        countDisplayBody.setArcWidth(ARC_SIZE/2);
        countDisplayBody.setArcHeight(ARC_SIZE/2);
        
        tagCountDisplay = new Text(tagCount+"");
        tagCountDisplay.setFill(Color.WHITE);
        tagCountDisplay.setFont(new Font(COUNT_FONT_SIZE));
        
        Tooltip tooltip = new Tooltip(TOOLTIP_MESSAGE);
        tooltip.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(countDisplay, tooltip);
        
        countDisplay.getChildren().addAll(countDisplayBody, tagCountDisplay);
        
        overlayView = new ScrollPane();
        
        StackPane overlayContent = new StackPane();
        overlayBackground = new Rectangle(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        overlayBackground.setOpacity(OVERLAY_BACKGROUND_OPACITY);
        
        closeButton = new StackPane();
        Rectangle button = new Rectangle(TAG_WIDTH, TAG_HEIGHT, Color.RED);
        button.setArcWidth(ARC_SIZE);
        button.setArcHeight(ARC_SIZE);
        Text close = new Text(displayText.getString("Close"));
        close.setFont(new Font(CLOSE_BUTTON_FONT));
        close.setFill(Color.WHITE);
        closeButton.getChildren().addAll(button, close);
        closeButton.setOnMouseEntered(e -> button.setFill(Color.DARKRED));
        closeButton.setOnMouseExited(e -> button.setFill(Color.RED));
        
        tagListDisplay.getChildren().add(closeButton);
        
        overlayContent.getChildren().addAll(overlayBackground, tagListDisplay);
        
        overlayView.setContent(overlayContent);
        overlayView.setVbarPolicy(ScrollBarPolicy.NEVER);
        overlayView.setHbarPolicy(ScrollBarPolicy.NEVER);
        overlayView.setMaxWidth(OVERLAY_WIDTH);
        overlayView.setMaxHeight(OVERLAY_HEIGHT);
        
        this.getChildren().add(countDisplay);
    }
    
    public void setupListeners (StackPane object) {
        this.setOnMousePressed(e -> {
            Scaler.scaleOverlay(START_VALUE, END_VALUE, overlayView);
            object.getChildren().add(overlayView);
        });
        
        this.getCloseButton().setOnMousePressed(e -> {
            Scaler.scaleOverlay(END_VALUE, START_VALUE, overlayView);
            object.getChildren().remove(overlayView);
        });
    }
    
    public StackPane getCloseButton(){
        return closeButton;
    }
    
    public void setKey(String key){
        myKey = key;
    }
    
    public void update(){
        for(Tag tag : tagObjects){
            if(!myController.tagExists(tag.getLabel())){
                removeTag(tag);
                return;
            }
        }
    }
    
    private void removeTag(Tag tag){
        tagListDisplay.getChildren().remove(tag);
        tagList.remove(tag.getLabel());
        tagObjects.remove(tag);
        myController.removeTagFromPart(myKey, tag.getLabel());
        
        showDecrease();
    }
    
    private void showDecrease(){
        tagCount--;
        tagCountDisplay.setText(tagCount+"");
        animateCountChange();
    }
    
    private void showIncrease(){
        tagCount++;
        tagCountDisplay.setText(tagCount+"");
        animateCountChange();
    }
    
    public void addTag(Tag tag){
        if(!tagList.contains(tag.getLabel())){
            showIncrease();

            Tag tagToAdd = new Tag(tag.getLabel());
            tagList.add(tagToAdd.getLabel());
            tagObjects.add(tagToAdd);
            myController.addTagToPart(myKey, tagToAdd.getLabel());
            tagToAdd.getButton().setOnMousePressed(e -> {
                tagToAdd.playDeleteAnimation().setOnFinished(ae -> removeTag(tagToAdd));
            });

            adjustBackground();
            tagListDisplay.getChildren().add(tagToAdd);
        }
    }
    
    public List<String> getTagList(){
        return tagList;
    }
    
    private void animateCountChange(){
        ScaleTransition scale = new ScaleTransition(Duration.millis(SCALE_DURATION), tagCountDisplay);
        scale.setFromX(REGULAR_RATIO);
        scale.setFromY(REGULAR_RATIO);
        scale.setToX(EXPANDED_RATIO);
        scale.setToY(EXPANDED_RATIO);
        scale.setAutoReverse(true);
        scale.setCycleCount(SCALE_CYCLES);
        scale.play();
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
