package engine.element.sprites;

import java.util.Map;
import javafx.geometry.Point2D;
import annotations.parameter;
import engine.Updateable;


/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */

public class Projectile extends MoveableSprite implements Updateable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "10.0")
    private Double damage;
    @parameter(settable = true, playerDisplay = true)
    private Boolean homing = true;

    private GameElement target;

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

    public GameElement getTarget () {
        return target;
    }

    public void setTarget (GameElement g) {
        target = g;
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
        super.setLocation(super.getLocation().add(super.getHeading().multiply(super.getSpeed())));
    }

    @Override
    public void update () {
        if (homing) {
            updateHeading();
        }
        this.move();
    }

    private void updateHeading () {
        if (!target.getState().equals(DEAD_STATE)) {
            this.setHeading(new Point2D(target.getLocationX() - this.getLocationX(), target
                    .getLocationY() - this.getLocationY()));
        }
    }

    @Override
    public void fixField (String fieldToModify, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setField (String fieldToModify, String value, Double duration) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeField (String fieldToModify, String value, Double duration) {
        // TODO Auto-generated method stub

    }

}
