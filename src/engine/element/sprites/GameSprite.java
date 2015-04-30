package engine.element.sprites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private Double health;
    /**
     * Holds the ID's of the next sprites that may be spawned or upgraded from the current sprite
     */
    @parameter(settable = false, playerDisplay = true, defaultValue = "null")
    private List<String> nextSprites;
    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private Sprite nextSpritesList;

    public GameSprite () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        health = (Double) parameters.get("Health");
        nextSprites = new ArrayList<String>();
        nextSprites.add((String) parameters.get("NextSprites"));
    }

    // Getters and setters

    protected Double getHealth () {
        return health;
    }

    protected void decreaseHealth (Double amount) {
        health -= amount;
    }

    /**
     * @return List<String> of the next Sprites
     */
    public List<String> getNextSprites () {
        return Collections.unmodifiableList(nextSprites);
    }

}
