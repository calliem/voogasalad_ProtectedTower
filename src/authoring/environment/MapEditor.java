/**
 * Sets up the map editor that allows the user to create a map utilizing individual tiles, set paths along their map, and save these dynamically so that these components are updated on all other relevant tabs
 * @author Callie Mao
 */

package authoring.environment;

import java.util.ArrayList;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import authoring.environment.objects.Path;
import authoring.environment.objects.Sprite;

public class MapEditor extends MainEditor {
	
	private TileMap myActiveMap;
	
	private static final double DEFAULT_MAP_WIDTH = 600;// getWidth()*.8; //TODO: get the .8 from above class. also getWidth() is not static and so it cannot be used. maybe make it static or just mathis this a final variale? 
	private static final double DEFAULT_MAP_HEIGHT = 1000; //getHeight();
	

	public MapEditor(Dimension2D dim) {
		super(dim);
		// TODO Auto-generated constructor stub
	}

	public Sprite[][] getTiles() {
		// TODO return actual map tiles
		return new Sprite[0][0];
	}

	public ArrayList<Path> getPaths() { // to be accessed by Levels
		// TODO return actual paths
		return new ArrayList<>();
	}

	public ArrayList<Sprite> getMaps() {
		// TODO return actual GameMaps
		return new ArrayList<>();
	};

	@Override
	protected void createMap() {
		// TODO Auto-generated method stub
		
		myActiveMap = new TileMap(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, 200);
		//TODO: remove test values
		
//		Tile tile = new Tile(100);
		
		getPane().add(myActiveMap.getMap(), 0, 0);
	//	getPane().add(tile, 2, 0);
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
