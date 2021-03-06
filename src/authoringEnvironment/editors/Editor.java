package authoringEnvironment.editors;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;


/**
 * This class builds the general editor structure for all individual editor tabs
 * viewable from the main environment
 * 
 * @author Callie Mao
 * @author Johnny Kumpf
 * @author Kevin He
 */

public abstract class Editor extends Tab {

    protected String editorType;
    private Group contentRoot;
    protected Controller myController;
    protected boolean isOverlayActive = false;

    private static final String englishSpecsFile = "resources/display/main_environment_english";
    protected static final ResourceBundle tabNames = ResourceBundle
            .getBundle(englishSpecsFile);

    private static final String englishPartsFile = "resources/display/part_names_english";
    protected static final ResourceBundle partNames = ResourceBundle.getBundle(englishPartsFile);

    // TODO: don't use protected

    protected static final double CONTENT_WIDTH = AuthoringEnvironment
            .getEnvironmentWidth();
    protected static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment
            .getEnvironmentHeight();

    public Editor (Controller controller, String name) {
        myController = controller;
        editorType = name;
        contentRoot = configureUI();
        this.setContent(contentRoot);
        this.setText(tabNames.getString(editorType));
        this.setClosable(false);
    }

    protected abstract Group configureUI ();

    public String getName () {
        return editorType;
    }

    public void hideOverlay () {

    }

    public boolean isOverlayActive () {
        return isOverlayActive;
    }

    public abstract void update ();

}
