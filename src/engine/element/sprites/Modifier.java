package engine.element.sprites;

import engine.InsufficientParametersException;


/**
 * This class represents an object which may carry a modification to set upon another object, such
 * as a status effect.
 * 
 * @author Qian Wang
 *
 */
public class Modifier extends Sprite {

    public Modifier (){
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

}
