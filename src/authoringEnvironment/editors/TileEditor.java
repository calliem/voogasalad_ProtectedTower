package authoringEnvironment.editors;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;


/**
 * This class extends SpriteEditor and allows the user to create a tile object. Because of slow runtimes from large image sizes, users are currently restricted to only being able to utilize rectanglular tiles with customized colors. If applicable, the tiles can be given tags that can later be utilized in the MapEditor to set specific terrain tags, and the tags can later be matched by the user when placing towers. 
 * @author Kevin He
 *
 */
public class TileEditor extends Editor{
    private Group myRoot;
    private static final Color BACKGROUND_COLOR = Color.GRAY;
    
    public TileEditor(Controller c, String name) {
        super(c, name);
    }

    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        myRoot = new Group();
        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        return myRoot;
    }
}
