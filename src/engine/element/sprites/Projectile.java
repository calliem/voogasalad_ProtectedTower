package engine.element.sprites;

import java.util.Map;
import engine.Updateable;
import annotations.parameter;


/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */

public class Projectile extends MoveableSprite implements Updateable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "1")
    private Double damage;

    public Projectile () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        damage = (Double) parameters.get("Damage");
    }

    // Getters and setters

    public Double getDamage () {
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
    public void update () {
        this.move();
    }


}
