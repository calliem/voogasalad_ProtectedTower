package authoringEnvironment.editors;

import java.util.List;
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
    
    private Controller myController;
    private StackPane myContent;
    private VBox tagDisplay;
    private Rectangle background;
    private StackPane myPrompt;
    private boolean promptActive = false;
    private ObservableList<Tag> tagsList;
    
    public TagDisplay(Controller controller){
        super(2*PADDING);
        this.setAlignment(Pos.TOP_LEFT);
        
        myController = controller;
        myContent = new StackPane();
        myPrompt = new StackPane();
        createPrompt();
        tagsList = FXCollections.observableArrayList();
        
        VBox contentDisplay = new VBox(PADDING);
        contentDisplay.setAlignment(Pos.TOP_CENTER);
        contentDisplay.setTranslateY(PADDING);
        tagDisplay = new VBox(PADDING);
        tagDisplay.setAlignment(Pos.TOP_CENTER);
        
        Text title = new Text("Tags");
        title.setFill(Color.WHITE);
        
        StackPane addButton = new StackPane();
        Rectangle button = new Rectangle(75, 20, Color.GREEN);
        button.setArcWidth(20);
        button.setArcHeight(20);
        Text plus = new Text("Add");
        plus.setFill(Color.WHITE);
        addButton.getChildren().addAll(button, plus);
        
        addButton.setOnMouseEntered(e -> {
            button.setFill(Color.DARKGREEN);
        });
        addButton.setOnMouseExited(e -> {
            button.setFill(Color.GREEN);
        });
        addButton.setOnMousePressed(e -> {
            if(!promptActive){
                showTagPrompt();
                promptActive = true;
            }
        });
        
        contentDisplay.getChildren().addAll(title, addButton, tagDisplay);
        
        background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        background.setOpacity(0.7);
        
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
            }
        });
        
        this.getChildren().add(displayPane);
    }
    
    private void createPrompt(){
        Rectangle promptBackground = new Rectangle(200, 300);
        promptBackground.setArcWidth(10);
        promptBackground.setArcHeight(10);
        promptBackground.setOpacity(0.8);
        
        VBox content = new VBox(2*PADDING);
        
        Text message = new Text("New Tag!");
        message.setFill(Color.WHITE);
        TextField name = new TextField();
        name.setPromptText("Enter tag name...");
        name.setMaxWidth(180);
        
        Text error = new Text();
        error.setFill(Color.RED);
        
        Button create = new Button("Create");
        create.setOnAction(e -> {
            String newTagName = name.getText();
            if(!myController.tagExists(newTagName)){
                addTag(new Tag(newTagName));
                myController.addNewTag(newTagName);

                name.setText("");
                hideTagPrompt();
            }
            else {
                displayError(error, "That tag already exists!");
            }
        });
        
        content.setAlignment(Pos.TOP_CENTER);
        content.setTranslateY(PADDING*2);
        content.getChildren().addAll(message, name, error, create);
        
        myPrompt.getChildren().addAll(promptBackground, content);
        StackPane.setAlignment(promptBackground, Pos.TOP_CENTER);
        myPrompt.setTranslateY(2*PADDING);
    }

    public void setupDraggableTags(Group visuals, List<TagGroup> groups, List<Node> nodes){
        for(Tag tag : tagsList){
            Tag newTag = new Tag(tag.getLabel());
            newTag.hideButton();
            
            double adjustY = 70;
            
            tag.getTagBody().setOnMousePressed(e -> {
                newTag.setLocation(e.getSceneX(), e.getSceneY());
                newTag.setTranslateX(e.getSceneX());
                newTag.setTranslateY(e.getSceneY()-adjustY);
                visuals.getChildren().add(newTag);
            });
            
            tag.getTagBody().setOnMouseDragged(e -> {
                if(newTag.getTranslateX() >= 0 && newTag.getTranslateY() >= 0){
                    newTag.setTranslateX(e.getSceneX());
                    newTag.setTranslateY(e.getSceneY()-adjustY);
                }
                else{
                    visuals.getChildren().remove(newTag);
                }
            });
            
            tag.getTagBody().setOnMouseReleased(e -> {
                visuals.getChildren().remove(newTag);
                checkCollisions(newTag, groups, nodes);
            });
        }
    }
    
    private void checkCollisions(Tag tag, List<TagGroup> groups, List<Node> nodes){
        for(Node node: nodes){
            if(tag.getBoundsInParent().intersects(node.getBoundsInParent())){
                tag.setTranslateX(0);
                tag.setTranslateY(0);
                groups.get(nodes.indexOf(node)).addTag(tag);
            }
        }
    }
    
    private void showTagPrompt(){
        FadeTransition scale = new FadeTransition(Duration.millis(500), myPrompt);
        scale.setFromValue(0);
        scale.setToValue(1.0);
        scale.play();
        this.getChildren().add(myPrompt);
    }
    
    private void hideTagPrompt(){
        FadeTransition scale = new FadeTransition(Duration.millis(500), myPrompt);
        scale.setFromValue(1.0);
        scale.setToValue(0);
        scale.play();
        promptActive = false;
        this.getChildren().remove(myPrompt);
    }
    
    private void displayError(Text display, String message){
        display.setVisible(true);
        display.setText(message);
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
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
        //first padding is top, (19+Padding) is for text, and numTags*(20+PADDING) is for the vbox
        int contentSize = PADDING + (19 + PADDING) + (20+PADDING) + numTags*(20 + PADDING);
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
