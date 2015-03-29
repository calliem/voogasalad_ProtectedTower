package engine.sprites;

import java.util.List;
import engine.InsufficientParametersException;


public class Projectile extends MoveableSprite {

    public Projectile () throws InsufficientParametersException {
        super();
        // TODO Auto-generated constructor stub
    }

    public Projectile (String s) throws InsufficientParametersException {
        super();
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
    public void target (Sprite s) {
        // TODO Auto-generated method stub

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
