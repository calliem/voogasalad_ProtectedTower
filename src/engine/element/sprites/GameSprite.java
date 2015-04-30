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
    

    // Getters and setters

    public Integer getHealth () {
        return health;
    }

    protected void decreaseHealth (Integer amount) {
        health -= amount;
    }

//    /**
//     * @return List<String> of the next Sprites
//     */
//    public List<String> getNextSprites () {
//        return Collections.unmodifiableList(nextSprites);
//    }


}
