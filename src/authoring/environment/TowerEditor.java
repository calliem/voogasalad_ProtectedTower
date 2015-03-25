package authoring.environment;

import java.util.ArrayList;

public interface TowerEditor {
	
	public ArrayList<Tower> getTowers(){};

}



Game Authoring Environment 

public interface MapEditor(){ public Cell[][] getTiles(){} public ArrayList getPaths(){} //overlayed smaller-celled grid }

public interface ProjectileEditor(){ public ArrayList getProjectiles(){} }

public interface TowerEditor(){ public ArrayList getTowers(){} }

public interface UnitEditor(){ public ArrayList getUnits(){} }

public interface LevelEditor(){ public ArrayList getLevels(){} }