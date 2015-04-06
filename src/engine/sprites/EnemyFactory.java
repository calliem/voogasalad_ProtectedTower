package engine.sprites;

import engine.InsufficientParametersException;

public class EnemyFactory {

	public EnemyFactory() {

	}

	public Enemy getEnemy() {
		Enemy enemy = null;
		try {
			enemy = new Enemy();
		} catch (InsufficientParametersException e) {
			e.printStackTrace();
		}
		
		//TODO: set enemy parameters
		
		return enemy;
	}
}
