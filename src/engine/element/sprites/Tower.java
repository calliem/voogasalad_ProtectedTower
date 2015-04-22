package engine.element.sprites;

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
        // TODO Auto-generated constructor stub
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

}
