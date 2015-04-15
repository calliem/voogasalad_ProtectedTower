package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


/**
 * Factory for producing enemies
 * 
 * @author Bojia Chen
 *
 */

public class EnemyFactory {
    private Map<String, Map<String, Object>> myEnemies;
    private final static String MY_CLASS_NAME = "engine.sprites.Enemy";

    public EnemyFactory () {
        myEnemies = new HashMap<>();
    }

    /**
     * Adds new enemies to the list of all possible enemies, can be called with a map of enemy GUID
     * to the parameters map of that enemy, or also with a single GUID and a single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // for (String enemyID : allEnemies.keySet()) {
        // Map<String, Object> enemyProperties = allEnemies.get(enemyID);
        // this.add(enemyID, enemyProperties);
        // }
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see EnemyFactory#add(Map)
     * 
     * @param enemyID String of the GUID of the enemy
     * @param enemyProperties the properties Map<String, Object> object of the enemy
     */
    public void add (String enemyID, Map<String, Object> enemyProperties) {
        myEnemies.put(enemyID, enemyProperties);
    }

    @Deprecated
    public void addEnemy (Map<String, Object> enemyProperties) {
        String enemyID =
                (String) enemyProperties.get("Group") + "_" + (String) enemyProperties.get("Name");
        myEnemies.put(enemyID, enemyProperties);
    }

    /**
     * Given a GUID, returns the enemy object with a prefilled parameters map and values that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Enemy object
     */
    public Enemy getEnemy (String guid) {
        if (!myEnemies.containsKey(guid)) { throw new InvalidParameterException(guid +
                                                                                " is an undefined enemy"); }

        Enemy enemy = (Enemy) Reflection.createInstance(MY_CLASS_NAME);
        enemy.setParameterMap(myEnemies.get(guid));

        return enemy;
    }
}
