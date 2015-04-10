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

    public GridCell (){
        super();
    }
    private List<String> myTags;

    public GridCell (Map<String, Object> params) {
        super(params);
    }

    public GridCell (ImageView img) {
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

    public boolean isObstacle (String type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void collide (Sprite sprite) {
        // TODO Auto-generated method stub

    }
}
