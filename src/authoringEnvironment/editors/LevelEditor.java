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

    // private List<Node> myLevels;
    private UpdatableDisplay mapDisplay;
    private LevelSidebar mySidebar; // LevelSidebar is used instead of Sidebar because there are
                                    // functionality in LevelSidebar not provided in Sidebar

    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/"; // TODO: stop
                                                                                 // duplicating this
                                                                                 // default resource
                                                                                 // package line
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE +
                                                                  "level_editor_english");

    public LevelEditor (Controller c, String name) {
        super(c, name);
        mySidebar = new LevelSidebar(myResources, getMaps(), getMapWorkspace(), c);
        getPane().add(mySidebar, 1, 0); // TODO: don't hardcode the Maps editor. Find a way to get
        // it from teh existing one so that changing one thing in code won't require changes
        // everywhere
        // myLevels = new ArrayList<Node>();
        myController = c;

        // List<String> temp = myController.getKeysForPartType(MapEditor.MAP_PART_NAME);
        // for (String key : temp){
        // Map<String, Object> part = c.getPartCopy(key);
        // }
        // part.get(InstanceManager.nameKey);
        // part.get(MapEditor.TILE_MAP);
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

    /*
     * @Override
     * public void update() {
     * System.out.println("level editor updated"); // TODO Auto-generated method stub
     * MapEditor mapEditor = (MapEditor) Controller.getEditor("Maps"); //TODO: find a way to get
     * sthe same resource file but to also use myResources.getString() on the proper resource file
     * //how to avoid this issue of more dependencies on this string name. lots of code will have to
     * change in order to change this; maybe use indexes instead?
     * getMapWorkspace().getChildren().add(mapEditor.getActiveMap().getMap());
     * super.update();
     * getMapWorkspace().getActiveMap().removeTileListeners();
     * mySidebar.update();
     * }
     */
}
