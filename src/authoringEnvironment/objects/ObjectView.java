package authoringEnvironment.objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import authoringEnvironment.Controller;

public class ObjectView extends StackPane{
    protected VBox objectLayout;
    protected TagGroup tagGroup;
    protected Controller myController;
    private BooleanProperty exists;
    private DeleteButton deleteButton;
    
    public ObjectView(Controller controller){
        super();
        myController = controller;
        
        tagGroup = new TagGroup(myController);
        tagGroup.setupListeners(this);
        
        objectLayout = new VBox(5);
        objectLayout.setAlignment(Pos.CENTER);
        
        exists = new SimpleBooleanProperty(true);

        this.getChildren().add(objectLayout);
    }
    
    public TagGroup getTagGroup(){
        return tagGroup;
    }

    public BooleanProperty isExisting () {
        return exists;
    }
    
    public void initiateEditableState () {
        deleteButton = new DeleteButton(20);
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
