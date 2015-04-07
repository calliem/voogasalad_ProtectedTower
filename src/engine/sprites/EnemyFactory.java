package engine.sprites;

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

    public void addEnemy (Map<String, Object> enemyProperties) {
        String enemyID =
                (String) enemyProperties.get("Group") + "_" + (String) enemyProperties.get("Name");
        myEnemies.put(enemyID, enemyProperties);
    }

    public Enemy getEnemy (String userInput) {
        if (!myEnemies.containsKey(userInput)) {
            throw new InvalidParameterException(userInput + " is an undefined enemy");
        }

        Enemy enemy = (Enemy) Reflection.createInstance(MY_CLASS_NAME);
        enemy.setParameterMap(myEnemies.get(userInput));

        return enemy;
    }
}
