package engine.sprites;

import java.util.List;

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

	private List<String> tags;
	
    public GridCell () throws InsufficientParametersException {
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
