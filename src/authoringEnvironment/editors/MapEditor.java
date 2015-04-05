/**
 * Sets up the map editor that allows the user to create a map utilizing individual tiles, set paths along their map, and save these dynamically so that these components are updated on all other relevant tabs
 * @author Callie Mao
 */

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.stage.Stage;
import authoringEnvironment.Sidebar;
import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.objects.TileMap;

public class MapEditor extends MainEditor {


    private TileMap myActiveMap;
    private static final double DEFAULT_MAP_WIDTH = 950;// getWidth()*.8; //TODO: get the .8 from above class. also getWidth() is not static and so it cannot be used. maybe make it static or just mathis this a final variale? 
    private static final double DEFAULT_MAP_HEIGHT = 700; //getHeight();
    private static final double DEFAULT_TILE_SIZE = 50;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "map_editor_english");

    public MapEditor(Dimension2D dim, Stage s) {
        super(dim, s);
    }

    @Override
    public Group configureUI(){
        Group root = super.configureUI();
        getPane().add(new Sidebar(myResources),1,0);
        return root;
    }

    public SpriteView[][] getTiles(){
        // TODO return actual map tiles
        return new SpriteView[0][0];
    }

	public ArrayList<SpriteView> getMaps() {
		// TODO return actual GameMaps
		return new ArrayList<>();
	};


    protected void createMap() {
        // TODO Auto-generated method stub
        myActiveMap = new TileMap(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, DEFAULT_TILE_SIZE);		
        getPane().add(myActiveMap.getMap(), 0, 0);
    }

    public void setActiveMap(TileMap map){
        myActiveMap = map;
        //TODO: display the new active map
    }

    /*
     * @Override protected Group configureUI() { // TODO Auto-generated method
     * stub return null; }
     */
}
