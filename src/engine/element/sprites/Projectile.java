package engine.element.sprites;

import annotations.parameter;


/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */

public class Projectile extends MoveableSprite {

    @parameter(settable = true, playerDisplay = true, defaultValue = "1")
    private Integer damage;

    // Getters and setters

    public Integer getDamage () {
        return damage;
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide (GameElement element) {
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
