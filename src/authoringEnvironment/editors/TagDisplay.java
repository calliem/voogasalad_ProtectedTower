package authoringEnvironment.editors;

import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.Tag;
import authoringEnvironment.objects.TagGroup;

public class TagDisplay extends HBox{
    private static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment
            .getEnvironmentHeight();
    private static final int CONTENT_WIDTH = 100;
    private static final int PADDING = 5;
    private static final int PROMPT_WIDTH = 200;
    private static final int PROMPT_HEIGHT = 150;
    private static final int TAG_WIDTH = 75;
    private static final int TAG_HEIGHT = 20;
    private static final int BUTTON_ARC_SIZE = 20;
    private static final Color ADD_BUTTON_COLOR = Color.GREEN;
    private static final Color BUTTON_HOVERED_COLOR = Color.DARKGREEN;
    private static final String DISPLAY_TITLE = "Tags";
    private static final double PROMPT_OPACITY = 0.8;
    private static final int PROMPT_ARC_SIZE = 10;
    private static final double TAG_DRAG_ADJUSTMENT = 70;
    private static final int FADE_DURATION = 500;
    private static final int ERROR_DISPLAY_DURATION = 1000;
    private static final int PROMPT_CONTENT_WIDTH = PROMPT_WIDTH - 4*PADDING;
    private static final String NAME_PROMPT_TEXT = "Enter tag name...";
    private static final String PROMPT_TITLE = "New Tag!";
    private static final String NAME_ERROR = "Enter a name for your tag!";
    private static final String TAG_EXISTS_ERROR = "That tag already exists!";
    
    private static final int TEXT_HEIGHT = 19;
    private static final double START_VALUE = 0.0;
    private static final double END_VALUE = 1.0;
    
    private Controller myController;
    private StackPane myContent;
    private VBox tagDisplay;
    private Rectangle background;
    private StackPane myPrompt;
    private boolean promptActive = false;
    private ObservableList<Tag> tagsList;
    private List<TagGroup> tagGroupsList;
    private List<Node> nodesList;
    
    private static final String INTERFACE_TEXT = "resources/display/interface_text";
    private static final ResourceBundle displayText = ResourceBundle.getBundle(INTERFACE_TEXT);
    
    public TagDisplay(Controller controller, List<TagGroup> tagGroups, List<Node> nodes){
        super(2*PADDING);
        this.setAlignment(Pos.TOP_LEFT);
        
        myController = controller;
        tagGroupsList = tagGroups;
        nodesList = nodes;
        myContent = new StackPane();
        myPrompt = new StackPane();
        createPrompt();
        tagsList = FXCollections.observableArrayList();
        
        VBox contentDisplay = new VBox(PADDING);
        contentDisplay.setAlignment(Pos.TOP_CENTER);
        contentDisplay.setTranslateY(PADDING);
        tagDisplay = new VBox(PADDING);
        tagDisplay.setAlignment(Pos.TOP_CENTER);
        
        Text title = new Text(DISPLAY_TITLE);
        title.setFill(Color.WHITE);
        
        StackPane addButton = new StackPane();
        Rectangle button = new Rectangle(TAG_WIDTH, TAG_HEIGHT, ADD_BUTTON_COLOR);
        button.setArcWidth(BUTTON_ARC_SIZE);
        button.setArcHeight(BUTTON_ARC_SIZE);
        Text plus = new Text(displayText.getString("Add"));
        plus.setFill(Color.WHITE);
        addButton.getChildren().addAll(button, plus);
        
        addButton.setOnMouseEntered(e -> {
            button.setFill(BUTTON_HOVERED_COLOR);
        });
        addButton.setOnMouseExited(e -> {
            button.setFill(ADD_BUTTON_COLOR);
        });
        addButton.setOnMousePressed(e -> {
            if(!promptActive){
                showTagPrompt();
                promptActive = true;
            }
        });
        
        contentDisplay.getChildren().addAll(title, addButton, tagDisplay);
        
        background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        background.setOpacity(PROMPT_OPACITY);
        
        myContent.getChildren().addAll(background, contentDisplay);
        
        ScrollPane displayPane = new ScrollPane();
        displayPane.setMaxHeight(CONTENT_HEIGHT);
        displayPane.setMaxWidth(CONTENT_WIDTH);
        displayPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        displayPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        displayPane.setContent(myContent);
        
        ObservableList<String> existingTags = myController.getTagsList();
        existingTags.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change change){
                refresh();
                for(TagGroup group : tagGroupsList){
                    group.update();
                }
            }
        });
        
        this.getChildren().add(displayPane);
    }
    
    private void createPrompt(){
        Rectangle promptBackground = new Rectangle(PROMPT_WIDTH, PROMPT_HEIGHT);
        promptBackground.setArcWidth(PROMPT_ARC_SIZE);
        promptBackground.setArcHeight(PROMPT_ARC_SIZE);
        promptBackground.setOpacity(PROMPT_OPACITY);
        
        VBox content = new VBox(2*PADDING);
        
        Text message = new Text(PROMPT_TITLE);
        message.setFill(Color.WHITE);
        TextField name = new TextField();
        name.setPromptText(NAME_PROMPT_TEXT);
        name.setMaxWidth(PROMPT_CONTENT_WIDTH);
        
        Text error = new Text();
        error.setWrappingWidth(PROMPT_CONTENT_WIDTH);
        error.setTextAlignment(TextAlignment.CENTER);
        error.setFill(Color.RED);
        
        Button create = new Button(displayText.getString("Create"));
        create.setOnAction(e -> {
            String newTagName = name.getText();
            if(!myController.tagExists(newTagName) && newTagName.length() != 0){
                addTag(new Tag(newTagName));
                myController.addNewTag(newTagName);

                hideTagPrompt(name);
            }
            else if(newTagName.length() == 0){
                displayError(error, NAME_ERROR);
            }
            else {
                displayError(error, TAG_EXISTS_ERROR);
            }
        });
        
        Button cancel = new Button(displayText.getString("Cancel"));
        cancel.setOnAction(e -> {
            hideTagPrompt(name);
        });
        
        HBox buttons = new HBox(2*PADDING);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(create, cancel);
        
        content.setAlignment(Pos.TOP_CENTER);
        content.setTranslateY(PADDING*2);
        content.getChildren().addAll(message, name, error, buttons);
        
        myPrompt.getChildren().addAll(promptBackground, content);
        StackPane.setAlignment(promptBackground, Pos.TOP_CENTER);
        myPrompt.setTranslateY(2*PADDING);
    }

    public void setupDraggableTags(Group visuals){
        for(Tag tag : tagsList){
            Tag newTag = new Tag(tag.getLabel());
            newTag.hideButton();
            
            tag.getTagBody().setOnMousePressed(e -> {
//                newTag.setLocation(e.getSceneX(), e.getSceneY());
                newTag.setTranslateX(e.getSceneX());
                newTag.setTranslateY(e.getSceneY()-TAG_DRAG_ADJUSTMENT);
                visuals.getChildren().add(newTag);
            });
            
            tag.getTagBody().setOnMouseDragged(e -> {
                if(newTag.getTranslateX() >= 0 && newTag.getTranslateY() >= 0){
                    newTag.setTranslateX(e.getSceneX());
                    newTag.setTranslateY(e.getSceneY()-TAG_DRAG_ADJUSTMENT);
                }
                else{
                    visuals.getChildren().remove(newTag);
                }
            });
            
            tag.getTagBody().setOnMouseReleased(e -> {
                visuals.getChildren().remove(newTag);
                checkCollisions(newTag);
            });
        }
    }
    
    private void checkCollisions(Tag tag){
        for(Node node: nodesList){
            if(tag.getBoundsInParent().intersects(node.getBoundsInParent()) ){
                tag.setTranslateX(0);
                tag.setTranslateY(0);
                tagGroupsList.get(nodesList.indexOf(node)).addTag(tag);
            }
        }
    }
    
    private FadeTransition fadePrompt(double from, double to){
        FadeTransition fade = new FadeTransition(Duration.millis(FADE_DURATION), myPrompt);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.play();
        return fade;
    }
    
    private void showTagPrompt(){
        fadePrompt(0.0, 1.0);
        this.getChildren().add(myPrompt);
    }
    
    private void hideTagPrompt(TextField field){
        fadePrompt(1.0, 0.0).setOnFinished(e -> {
            promptActive = false;
            field.setText("");
            this.getChildren().remove(myPrompt);
        });
    }
    
    private void displayError(Text display, String message){
        display.setVisible(true);
        display.setText(message);
        PauseTransition pause = new PauseTransition(Duration.millis(ERROR_DISPLAY_DURATION));
        pause.play();
        pause.setOnFinished(e -> display.setVisible(false));
    }
    
    private void refresh(){
        tagDisplay.getChildren().removeAll(tagDisplay.getChildren());
        tagsList.clear();
        List<String> tagList = myController.getTagsList();
        for(String tag : tagList){
            addTag(new Tag(tag));
        }
    }
    
    public ObservableList<Tag> getTagsList(){
        return tagsList;
    }
    
    public void addTag(Tag tag){
        tagDisplay.getChildren().add(tag);
        tagsList.add(tag);
        tag.getButton().setOnMouseClicked(e -> {
            tag.playDeleteAnimation().setOnFinished(ae -> {
                tagDisplay.getChildren().remove(tag);
                tagsList.remove(tag);
                myController.removeTag(tag.getLabel());
            });
        });
        adjustBackground();
    }
    
    private void adjustBackground () {
        int numTags = tagDisplay.getChildren().size();
        int contentSize = 2*PADDING + TEXT_HEIGHT + (numTags + 1) *(TAG_HEIGHT + PADDING);
        if(contentSize > CONTENT_HEIGHT){
            background.setHeight(contentSize);
        }
    }
    
    public void addTags(Tag ...tags){
        for(Tag tag : tags){
            addTag(tag);
        }
    }
}
