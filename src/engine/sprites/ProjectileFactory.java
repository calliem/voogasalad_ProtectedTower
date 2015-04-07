package engine.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import engine.element.sprites.Projectile;


/**
 * Factory for producing projectiles
 * 
 * @author Bojia Chen
 *
 */

public class ProjectileFactory {

    private Map<String, Map<String, Object>> myProjectiles;
    private final static String MY_CLASS_NAME = "engine.sprites.Projectile";

    public ProjectileFactory () {
        myProjectiles = new HashMap<>();
    }

    public void addProjectile (Map<String, Object> projectileProperties) {
        String enemyID =
                (String) projectileProperties.get("Group") + "_" +
                        (String) projectileProperties.get("Name");
        myProjectiles.put(enemyID, projectileProperties);
    }

    public Projectile getProjectile (String userInput) {
        if (!myProjectiles.containsKey(userInput)) { throw new InvalidParameterException(userInput +
                                                                                         " is an undefined projectile"); }

        Projectile projectile = (Projectile) Reflection.createInstance(MY_CLASS_NAME);
        projectile.setParameterMap(myProjectiles.get(userInput));

        return projectile;
    }
}
