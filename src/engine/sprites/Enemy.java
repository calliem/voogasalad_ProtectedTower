package engine.sprites;

import java.util.List;
import engine.InsufficientParametersException;


/*
 * This class represents a sprite that moves across the screen and shoots/is shot at by towers.
 */
public class Enemy extends MoveableSprite {

    public Enemy () throws InsufficientParametersException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void target (Sprite s) {
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
    public List<String> getParameters () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void move () {
        // TODO Auto-generated method stub

    }
}
