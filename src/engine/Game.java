package engine;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private Bank myBank;
	private int myExperiencePoints;
	private List<Level> myLevels;
	
	public Game() {
		myBank = new Bank();
		myExperiencePoints = 0;
		myLevels = new ArrayList<>();
	}
	
	public void endGame(){}
	
}
