package engine.element.sprites;

import javafx.scene.image.ImageView;


/**
 * This class represents the tower object in the game, which usually does not move and is used to
 * defend a map from Enemy objects. The tower shoots projectiles which target enemies.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Tower extends GameSprite {
    
    public Tower () {
        super();
    }

    // TODO remove once testing is over
    public Tower (ImageView test) {
        super.setImageView(test);
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public Tower clone () {
        return null;
    }

    /**
     * The tower does not move.
     */
    @Override
    public void move () {
        return;
    }

<<<<<<< HEAD
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
    public void update (int counter) {
        // TODO Auto-generated method stub
        System.out.println("Tower updated");
    }

=======
>>>>>>> 4838a7a1e3157787ba9a4c1ef41d3ed0e7dc7e14
}
