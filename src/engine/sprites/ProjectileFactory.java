package engine.sprites;

import java.util.HashMap;
import java.util.Map;

public class ProjectileFactory {
	
	private Map<String, String> myProjectiles;

	public ProjectileFactory() {
		myProjectiles = new HashMap<>();
	}
	
	public Projectile getProjectile(String userInput) {
		String projectileName = null;
		if (myProjectiles.containsKey(userInput)) {
			//TODO: set projectile Paramters
		}
		
		Projectile projectile = null;
		//projectile = new Projectile();
		
		return projectile;
	}
}
