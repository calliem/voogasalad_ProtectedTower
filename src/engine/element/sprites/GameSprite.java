package engine.element.sprites;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import engine.UpdateAndReturnable;
import annotations.parameter;


/**
 * This class represent the main game object, one which carry many parameters and have complex
 * actions, such as towers and enemies.
 * 
 * @author Qian Wang
 *
 */
public abstract class GameSprite extends MoveableSprite implements UpdateAndReturnable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "100")
    private Integer health;
    /**
     * Holds the ID's of the next sprites that may be spawned or upgraded from the current sprite
     */
    @parameter(settable = false, playerDisplay = true, defaultValue = "null")
    private List<String> nextSprites;
    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private Sprite nextSpritesList;

    // Getters and setters

    protected Integer getHealth () {
        return health;
    }

    protected void decreaseHealth (Integer amount) {
        health -= amount;
    }

    /**
     * @return List<String> of the next Sprites
     */
    public List<String> getNextSprites () {
        return Collections.unmodifiableList(nextSprites);
    }


}
