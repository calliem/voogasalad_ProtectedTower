package authoringEnvironment.editors;

import java.util.ResourceBundle;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.LevelSidebar;
import authoringEnvironment.objects.UpdatableDisplay;


/**
 * Sets up the wave editor that allows the user to specify a specific map and the specific waves
 * that will appear at specific spawn points. The user will also be able to customize delay in
 * timing before each wave is sent out
 * 
 * @author Callie Mao
 */

// private ObservableList<Round> myRounds;

public class LevelEditor extends MainEditor {

    private UpdatableDisplay mapDisplay;
    private LevelSidebar mySidebar; 

    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/"; // TODO: stop
                                                                                 // duplicating this
                                                                                 // default resource
                                                                                 // package line
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE +
                                                                  "level_editor_english");

    public LevelEditor (Controller c, String name) {
        super(c, name);
        mySidebar = new LevelSidebar(myResources, getMaps(), getMapWorkspace(), c);
        getPane().add(mySidebar, 1, 0); 
        myController = c;
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

    
}
