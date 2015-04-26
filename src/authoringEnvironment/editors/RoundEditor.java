package authoringEnvironment.editors;

import java.util.ResourceBundle;
import javafx.scene.Group;
import authoringEnvironment.Controller;
import authoringEnvironment.RoundSidebar;


/**
 * Sets up the round editor that allows the user to pick a map and designate spawn points for
 * selected waves of units that will spawn at one time in a level
 * 
 * @author Megan Gutter
 */
public class RoundEditor extends MainEditor {

    public RoundEditor (Controller c, String name) {
        super(c, name);
    }
    
//    @Override
//    public Group configureUI() {
//        Group visuals = new Group();
//    }

}
