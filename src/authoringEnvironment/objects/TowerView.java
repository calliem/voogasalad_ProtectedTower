package authoringEnvironment.objects;

import authoringEnvironment.Controller;

/**
 * Creates the visual tower object containing
 * the tower's image, name, and the overlay that
 * pops up when the object is clicked.
 * 
 * @author Kevin He
 *
 */
public class TowerView extends SpriteView{
    
    public TowerView(Controller c, String name, String imageFile){
        super(c, name, imageFile);
    }
}