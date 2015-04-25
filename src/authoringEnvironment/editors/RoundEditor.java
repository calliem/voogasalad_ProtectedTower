package authoringEnvironment.editors;

import java.util.ResourceBundle;
import authoringEnvironment.Controller;
import authoringEnvironment.RoundSidebar;
import authoringEnvironment.Sidebar;


/**
 * Sets up the round editor that allows the user to pick a map and designate spawn points for
 * selected waves of units that will spawn at one time in a level
 * 
 * @author Megan Gutter
 */
public class RoundEditor extends MainEditor {
    private Sidebar mySidebar;
    private ResourceBundle myResources;

    public RoundEditor (Controller c, String name) {
        super(c, name);
        mySidebar = new RoundSidebar(myResources, getMaps(), getMapWorkspace());
    }
    
    

}
