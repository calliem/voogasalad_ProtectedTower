package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


/**
 * Factory for producing projectiles
 * 
 * @author Bojia Chen
 *
 */

public class ProjectileFactory {

    private Map<String, Map<String, Object>> myProjectiles;
    private final static String MY_CLASS_NAME = "engine.element.sprites.Projectile";

    public ProjectileFactory () {
        myProjectiles = new HashMap<>();
    }

    /**
     * Adds new projectiles to the list of all possible projectiles, can be called with a map of
     * projectile GUID to the parameters map of that projectile, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // for (String projectileID : allProjectiles.keySet()) {
        // Map<String, Object> projectileProperties = allProjectiles.get(projectileID);
        // this.add(projectileID, projectileProperties);
        // }
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see ProjectileFactory#add(Map)
     * 
     * @param projectileID String of the GUID of the projectile
     * @param projectileProperties the properties Map<String, Object> object of the projectile
     */
    public void add (String projectileID, Map<String, Object> projectileProperties) {
        myProjectiles.put(projectileID, projectileProperties);
    }

    @Deprecated
    public void addProjectile (Map<String, Object> projectileProperties) {
        String enemyID =
                (String) projectileProperties.get("Group") + "_" +
                        (String) projectileProperties.get("Name");
        myProjectiles.put(enemyID, projectileProperties);
    }

    /**
     * Given a GUID, returns the projectile object with a prefilled parameters map and values that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Projectile object
     */
    public Projectile getProjectile (String guid) {
        if (!myProjectiles.containsKey(guid)) { throw new InvalidParameterException(guid +
                                                                                    " is an undefined projectile"); }

        Projectile projectile = (Projectile) Reflection.createInstance(MY_CLASS_NAME);
        projectile.setParameterMap(myProjectiles.get(guid));

        return projectile;
    }
}
