package engine.element.sprites;

import javafx.geometry.Point2D;
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
    private Integer damage;
    @parameter(settable = true, playerDisplay = true)
    private boolean homing = true;
    private GameElement target;
    
    // Getters and setters

    public Integer getDamage () {
        return damage;
    }
    
    public GameElement getTarget(){
    	return target;
    }
    
    public void setTarget(GameElement g){
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
    	if (homing)
    		updateHeading();
        super.setLocation(super.getLocation()
                .add(super.getHeading().multiply(super.getSpeed())));
    }

    private void updateHeading() {
    	if(!target.getState().equals(DEAD_STATE))
   			this.setHeading(new Point2D(target.getLocationX()-this.getLocationX() , target.getLocationY() - this.getLocationY()));
	}

	@Override
    public void update (int counter) {
        this.move();
    }


}
