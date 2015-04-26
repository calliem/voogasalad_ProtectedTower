package engine.element.sprites;

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
    private Integer HP;
    /**
     * Holds the ID's of the next sprites that may be spawned or upgraded from the current sprite
     */
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private Set<String> nextSprites;

    public GameSprite () {
        super();
    }

}
