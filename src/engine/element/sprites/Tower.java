package engine.element.sprites;

import java.util.Map;

import javafx.scene.image.ImageView;
import engine.InsufficientParametersException;


/**
 * This class represents the tower object in the game, which usually does not move and is used to
 * defend a map from Enemy objects. The tower shoots projectiles which target enemies.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Tower extends GameSprite {
    // TODO Specify instance variables specific to Tower, such as a build time. Add to the main    
    // parameter map

	public Tower (Map<String, Object> params) throws InsufficientParametersException {
        super(params);
    }
    
    public Tower (ImageView img) throws InsufficientParametersException {
        super(img);
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void collide (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public Tower clone () {
        return null;
    }

    /**
     * The tower does not move.
     */
    @Override
    public void move () {
        return;
    }

    @Override
    public boolean isTargetableBy (String type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCollidableWith (String type) {
        // TODO Auto-generated method stub
        return false;
    }

}
