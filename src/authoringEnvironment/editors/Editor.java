package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * This class builds the general editor structure for all individual editor tabs
 * viewable from the main environment
 * 
 * @author Callie Mao
 * @author Johnny Kumpf
 * @author Kevin He
 */

public abstract class Editor extends Tab {

    protected String tabName;
    private Group contentRoot;
    protected Controller myController;
    protected boolean isOverlayActive = false;
    
    //TODO: don't use protected

    protected static final double CONTENT_WIDTH = AuthoringEnvironment
            .getEnvironmentWidth();
    protected static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment
            .getEnvironmentHeight();

    public Editor (Controller controller, String name) {
        myController = controller;
        tabName = name;
        contentRoot = configureUI();
        this.setContent(contentRoot);
        this.setText(tabName);
        this.setClosable(false);
    }

    protected abstract Group configureUI ();

    public String getName () {
        return tabName;
    }

    // to be used by backend
    public void displayError (String s) {
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Text text = new Text(s);
        Button button = new Button("Ok");
        button.setOnMouseClicked(e -> stage.hide()); // this doesn't seem to
                                                     // work.... also hide()
                                                     // doesn't actually
                                                     // close() it right..?
        root.getChildren().addAll(text, button);

        Scene scene = new Scene(root, 400, 200);// getWidth() / 4, getHeight() /
                                                // 6);

        stage.setTitle("Error");
        stage.setScene(scene);
        stage.show();
    }

    protected void promptSpriteCreation () {
        // TODO Auto-generated method stub
        // this doesn't quite belong in this editor superclass. consider alternatives.

    }
    
    public void hideOverlay(){
        
    }
    
    public boolean isOverlayActive(){
        return isOverlayActive;
    }

}
