package engine.element.sprites;

/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */
public class Projectile extends MoveableSprite {

    private boolean hasCollided;

    public Projectile () {
        super();
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide (Sprite sprite) {
        // TODO Auto-generated method stub
        hasCollided = true;
    }

    @Override
    public void move () {
        // TODO Auto-generated method stub

    }

}
