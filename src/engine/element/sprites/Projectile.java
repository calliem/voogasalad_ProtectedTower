package engine.element.sprites;

import annotations.parameter;

/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */

public class Projectile extends MoveableSprite {
    @parameter(settable=true,playerDisplay=true)
    private String name = "Unnamed";
    @parameter(settable=true,playerDisplay=true)
    private String type = "Basic";
    @parameter(settable=true,playerDisplay=true)
    private Double damage = 100.0;
    @parameter(settable=true,playerDisplay=true)
    private Double speed = 1.0;
    @parameter(settable=true,playerDisplay=true)
    private Double boundingHeight = 10.0;
    @parameter(settable=true,playerDisplay=true)
    private Double boundingWidth = 10.0;
    @parameter(settable=true,playerDisplay=true)
    private String group = null;
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
        // TODO Auto-generated method stub

    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub
        
    }
}
