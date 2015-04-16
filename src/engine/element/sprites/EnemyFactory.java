package engine.element.sprites;

import util.reflection.Reflection;


/**
 * Factory for producing enemies
 * 
 * @author Bojia Chen
 *
 */

public class EnemyFactory extends SpriteFactory {
    private final static String MY_CLASS_NAME = "engine.sprites.Enemy";


    public EnemyFactory () {
        super(MY_CLASS_NAME);
    }

    public Enemy getEnemy (String enemyID) {
        super.checkID(enemyID);

        Enemy enemy = (Enemy) Reflection.createInstance(MY_CLASS_NAME);
        enemy.setParameterMap(super.getParameters(enemyID));
        return enemy;
    }
}
