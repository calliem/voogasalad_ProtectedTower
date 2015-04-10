package engine.element.sprites;

import java.util.Map;

import javafx.scene.image.ImageView;
import engine.InsufficientParametersException;


/**
 * This class represent the main game object, one which carry many parameters and have complex
 * actions, such as towers and enemies.
 * 
 * @author Qian Wang
 *
 */
public abstract class GameSprite extends MoveableSprite {
    // TODO Add parameter map (settings object?) as instance variables and method to manipulate it

	public GameSprite (Map<String, Object> params) throws InsufficientParametersException {
        super(params);
    }
    
    public GameSprite (ImageView img) throws InsufficientParametersException {
        super(img);
    }

}
