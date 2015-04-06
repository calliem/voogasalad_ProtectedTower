package engine.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


public class EnemyFactory {
    private Map<String, String> myEnemies;
    private final static String MY_CLASS_NAME = "engine.sprites.Enemy";

    public EnemyFactory () {
        myEnemies = new HashMap<>();
        // TODO: Fill enemy map
    }

    public Enemy getEnemy (String userInput) {
        if (!myEnemies.containsKey(userInput)) {
            throw new InvalidParameterException(userInput + " is an undefined enemy");
        }

        Enemy enemy = (Enemy) Reflection.createInstance(MY_CLASS_NAME);
        // TODO: set enemy Parameters

        return enemy;
    }
}
