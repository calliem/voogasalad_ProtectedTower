package authoring.environment;

import java.util.ArrayList;

import authoring.environment.objects.Line;
import authoring.environment.objects.GameMap;
import authoring.environment.objects.Tile;


public abstract class MapEditor extends MainEditor {

	public Tile[][] getTiles(){} 
	
	public ArrayList<Line> getPaths(){} //to be accessed by Levels
	
	public ArrayList<GameMap> getMaps(){}
	
}
