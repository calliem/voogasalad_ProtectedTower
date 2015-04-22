package engine.element.sprites;

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

    /**
     * Method for getting a new instance of a specific enemy
     * 
     * @param enemyID GUID of the template enemy
     * @return New enemy object with the parameters of the template enemy
     */

    public Enemy getEnemy (String enemyID) {
        return (Enemy) super.getSprite(enemyID);
    }
}
