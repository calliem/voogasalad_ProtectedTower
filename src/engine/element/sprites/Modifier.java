package engine.element.sprites;

import java.util.Map;

import javafx.scene.image.ImageView;
import engine.InsufficientParametersException;


/**
 * This class represents an object which may carry a modification to set upon another object, such
 * as a status effect.
 * 
 * @author Qian Wang
 *
 */
public class Modifier extends Sprite {

	public Modifier (Map<String, Object> params) throws InsufficientParametersException {
        super(params);
    }
    
    public Modifier (ImageView img) throws InsufficientParametersException {
        super(img);
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
	public void collide(Sprite sprite) {
		// TODO Auto-generated method stub
		
	}

}
