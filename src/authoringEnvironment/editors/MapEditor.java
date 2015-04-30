package authoringEnvironment.editors;

import java.util.ResourceBundle;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapSidebar;


/**
 * Sets up the map editor that allows the user to create a map utilizing individual tiles, set paths
 * along their map, and save these dynamically so that these components are updated on all other
 * relevant tabs
 * 
 * @author Callie Mao
 */
public class MapEditor extends MainEditor {

    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
    private static final ResourceBundle myResources = ResourceBundle
            .getBundle(DEFAULT_RESOURCE_PACKAGE + "map_editor_english");

    private MapSidebar mySidebar;

    public MapEditor (Controller c, String name) {
        super(c, name);
        mySidebar = new MapSidebar(myResources, getMaps(), getMapWorkspace(), c);
        getPane().add(mySidebar, 1, 0);
    }

    @Override
    public void update () {
        mySidebar.updateTileDisplay();

    }
}
