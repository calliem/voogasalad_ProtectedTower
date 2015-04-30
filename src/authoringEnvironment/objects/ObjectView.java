package authoringEnvironment.objects;

import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import authoringEnvironment.Controller;

public class ObjectView extends StackPane{
    protected VBox objectLayout;
    protected TagGroup tagGroup;
    protected Controller myController;
    protected String myKey;
    protected Button cancelButton;
    protected Button saveButton;
    private BooleanProperty exists;
    private DeleteButton deleteButton;
    
    private static final int PADDING = 5;
    private static final int DELETE_BUTTON_SIZE = 20;
    protected static final double OVERLAY_OPACITY = 0.8; 
    protected Text message;
    
    private static final String displayTextFile = "resources/display/interface_text";
    protected static final ResourceBundle displayText = ResourceBundle.getBundle(displayTextFile);
    
    public ObjectView(Controller controller){
        super();
        myController = controller;
        myKey = Controller.KEY_BEFORE_CREATION;
        
        message = new Text();
        message.setVisible(false);
        
        saveButton = new Button(displayText.getString("Save"));
        cancelButton = new Button(displayText.getString("Cancel"));
        
        tagGroup = new TagGroup(myController, myKey);
        tagGroup.setupListeners(this);
        
        objectLayout = new VBox(PADDING);
        objectLayout.setAlignment(Pos.CENTER);
        
        exists = new SimpleBooleanProperty(true);

        this.getChildren().add(objectLayout);
    }
    
    public TagGroup getTagGroup(){
        return tagGroup;
    }

    public Button getCloseButton () {
        return cancelButton;
    }
    
    public BooleanProperty isExisting () {
        return exists;
    }
    
    public void initiateEditableState () {
        deleteButton = new DeleteButton(DELETE_BUTTON_SIZE);
        deleteButton.setOnMousePressed(e -> {
            deleteButton.getDeleteAnimation(this).setOnFinished(ae -> exists.setValue(false));
        });
        this.getChildren().add(deleteButton);
        StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);
    }

    public void exitEditableState () {
        this.getChildren().remove(deleteButton);
    }
}
