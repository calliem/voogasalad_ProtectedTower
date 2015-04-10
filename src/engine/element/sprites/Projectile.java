package engine.element.sprites;

import java.util.Map;

import javafx.scene.image.ImageView;
import engine.InsufficientParametersException;


/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */
public class Projectile extends MoveableSprite {

	public Projectile (Map<String, Object> params) throws InsufficientParametersException {
        super(params);
    }
    
    public Projectile (ImageView img) throws InsufficientParametersException {
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
    public void move () {
        // TODO Auto-generated method stub

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

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub
        
    }

}
