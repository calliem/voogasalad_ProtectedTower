package engine.element.sprites;

import annotations.parameter;


/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */

public class Projectile extends MoveableSprite {

    @parameter(settable = true, playerDisplay = true)
    private Double damage = 100.0;

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
    }

    @Override
    public void move () {
        super.setLocation(super.getLocation()
                .add(super.getHeading().multiply(super.getSpeed())));
    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub

    }
}
