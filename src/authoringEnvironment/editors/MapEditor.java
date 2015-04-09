/**
 * Sets up the map editor that allows the user to create a map utilizing individual tiles, set paths along their map, and save these dynamically so that these components are updated on all other relevant tabs
 * @author Callie Mao
 */

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import authoringEnvironment.Controller;
import authoringEnvironment.MapSidebar;
import authoringEnvironment.Sidebar;
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.objects.TileMap;

public class MapEditor extends MainEditor {

	//TODO: store tags within map editor or within each individual map? different maps can have same and different tags. how will the tower editor display the drop down menu? all of them? 
	
    private TileMap myActiveMap;
    private static final int DEFAULT_MAP_ROWS = 14;// getWidth()*.8; //TODO: get the .8 from above class. also getWidth() is not static and so it cannot be used. maybe make it static or just mathis this a final variale? 
    private static final int DEFAULT_MAP_COLS = 19; //getHeight();
    private static final int DEFAULT_TILE_SIZE = 50; //based on height since monitor height < width and that is usually the limiting factor
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "map_editor_english");
	private ObservableList<TileMap> myMaps;

    public MapEditor(Dimension2D dim, Stage s) {
        super(dim, s);
        getPane().add(new Sidebar(myResources, myActiveMap, myMaps),1,0); //TODO: check map dependency
        myMaps = FXCollections.observableArrayList();

    }

    public SpriteView[][] getTiles(){
        // TODO return actual map tiles
        return new SpriteView[0][0];
    }

	public ObservableList<TileMap> getMaps() {
		// TODO return actual GameMaps
		return myMaps;
	};

    @Override
	protected void createMap() {
        // TODO Auto-generated method stub
        myActiveMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);	
        getMapWorkspace().getChildren().add(myActiveMap.getMap());
        
        //need to somehow update here as well
     /*   MapEditor mapEditor = (MapEditor) Controller.getEditor("Maps");  //TODO: find a way to get sthe same resource file but to also use myResources.getString() on the proper resource file //how to avoid this issue of more dependencies on this string name. lots of code will have to change in order to change this; maybe use indexes instead?
    	getMapWorkspace().getChildren().add(mapEditor.getActiveMap().getMap());*/
    }

    public void setActiveMap(TileMap map){
        myActiveMap = map;
        //TODO: display the new active map
    }
    
    public TileMap getActiveMap(){
    	return myActiveMap;
    }

	

/*	@Override
	public void update() {
		System.out.println("updated mapeditor!");	
		//getMapWorkspace().getChildren().add(myActiveMap.getMap()); //why can't just do this? isn't it bad to have to call the controller for the exact same editor? or maybe to get its new state? yeah that sounds about right
		
	}*/
}
