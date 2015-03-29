/**
 * Sets up the map editor that allows the user to create a map utilizing individual tiles, set paths along their map, and save these dynamically so that these components are updated on all other relevant tabs
 * @author Callie Mao
 */

package authoring.environment;

import java.util.ArrayList;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import authoring.environment.objects.Path;
import authoring.environment.objects.Sprite;

public class MapEditor extends MainEditor {

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

	/*
	 * @Override protected Group configureUI() { // TODO Auto-generated method
	 * stub return null; }
	 */
}
