package engine.sprites;

import engine.InsufficientParametersException;


/**
 * This class represents a sprite that moves across the screen and shoots/is shot at by towers. The
 * Enemy object is the primary one which can damage a player's health as it move through the map.
 * Some may also be able to attack towers.
 * 
 * @author Qian Wang
 *
 */
public class Enemy extends MoveableSprite {

    public Enemy () throws InsufficientParametersException {
        super();
        // TODO Auto-generated constructor stub
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

}
