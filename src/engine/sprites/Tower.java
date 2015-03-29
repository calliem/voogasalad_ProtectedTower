package engine.sprites;

import java.util.List;
import engine.InsufficientParametersException;


public class Tower extends GameSprite {

    public Tower () throws InsufficientParametersException {
        super();
        // TODO Auto-generated constructor stub
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

    public String getName () {
        return null;
    }

    @Override
    public void target (Sprite s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void move () {
        // TODO Auto-generated method stub

    }

    @Override
    public List<String> getParameters () {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Tower> getUpgrades () {
        // TODO Auto-generated method stub
        return null;
    }

    public void addUpgrade (Tower t) {
        // TODO Auto-generated method stub

    }

}
