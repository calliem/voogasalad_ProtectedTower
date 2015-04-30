package engine.element.sprites;

import java.util.Map;
import annotations.parameter;
import engine.UpdateAndReturnable;


/**
 * This class represent the main game object, one which carry many parameters and have complex
 * actions, such as towers and enemies.
 * 
 * @author Qian Wang
 *
 */
public abstract class GameSprite extends MoveableSprite implements UpdateAndReturnable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "100")
    private Double health;

    public GameSprite () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        health = (Double) parameters.get("Health");
    }

    // Getters and setters

    public Double getHealth () {
        return health;
    }

    protected void decreaseHealth (Double amount) {
        health -= amount;
    }

    // /**
    // * @return List<String> of the next Sprites
    // */
    // public List<String> getNextSprites () {
    // return Collections.unmodifiableList(nextSprites);
    // }

}
