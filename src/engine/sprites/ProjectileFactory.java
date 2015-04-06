package engine.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


public class ProjectileFactory {

    private Map<String, String> myProjectiles;
    private final static String MY_CLASS_NAME = "engine.sprites.Projectile";

    public ProjectileFactory () {
        myProjectiles = new HashMap<>();
        // TODO: Fill projectile map
    }

    public Projectile getProjectile (String userInput) {
        if (!myProjectiles.containsKey(userInput)) {
            throw new InvalidParameterException(userInput + " is an undefined projectile");
        }

        Projectile projectile = (Projectile) Reflection.createInstance(MY_CLASS_NAME);
        // TODO: set projectile Parameters

        return projectile;
    }
}
