package engine.element.sprites;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import annotations.parameter;


/**
 * This class represent the main game object, one which carry many parameters and have complex
 * actions, such as towers and enemies.
 * 
 * @author Qian Wang
 *
 */
public abstract class GameSprite extends MoveableSprite {

    @parameter(settable = true, playerDisplay = true, defaultValue = "100")
    private Integer health;
    /**
     * Holds the ID's of the next sprites that may be spawned or upgraded from the current sprite
     */
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private Set<String> nextSprites;

    public GameSprite () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        health = (Integer) parameters.get("health");
        nextSprites = (Set<String>) parameters.get("nextSprites");
    }

    // Getters and setters

    protected Integer getHealth () {
        return health;
    }

    protected void decreaseHealth (Integer amount) {
        health -= amount;
    }

    /**
     * @return the nextSprites
     */
    public Set<String> getNextSprites () {
        return Collections.unmodifiableSet(nextSprites);
    }

}
