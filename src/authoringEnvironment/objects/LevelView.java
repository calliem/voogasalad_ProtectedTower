package authoringEnvironment.objects;

import java.util.List;
import java.util.Map;

/**
 * Class that holds all objects associated with a level including individual
 * waves (which include units, spawn points, time elapsed before each wave is spawned, etc.), the specific map, and 
 * lose points/lines that the enemies must cross
 * 
 * @author Callie Mao
 *
 */
public class LevelView {
	
	// The myWaves holds a map of all the waves mapped to their spawn point. This allows reusibility since there can be multiple of the same wave at different spawn points.
	private List<RoundView> myRounds; //contains units, spawn point, and time in between each unit
	//waves contains time between each unit,
	//round stores waves and rounds
	//levels contains time betweeen waves
	private List<Coordinate>
	
	public LevelView(){
		myWaves = new ArrayList<RoundView>();
	}

}
/*
 * waves map win condition lose condition
 */
