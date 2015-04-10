package engine.element.sprites;

import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;
import engine.InsufficientParametersException;


/**
 * This class represents a grid cell on the board for a tower defense game. The grid contains a type
 * which identifies it as a certain type, so the game knows what behaviors may occur on a specific
 * grid cell,
 * 
 * @author Qian Wang
 *
 */
public class GridCell extends Sprite {

	private List<String> myTags;
	
	public GridCell (Map<String, Object> params) throws InsufficientParametersException {
        super(params);
    }
    
    public GridCell (ImageView img) throws InsufficientParametersException {
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
