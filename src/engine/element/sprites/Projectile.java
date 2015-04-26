package engine.element.sprites;

import javafx.scene.image.Image;
import annotations.parameter;


/**
 * This class represents the projectiles shot out by certain Sprites, like towers.
 * 
 * @author Qian Wang
 *
 */

public class Projectile extends MoveableSprite {
    @parameter(settable = true, playerDisplay = true)
    private Image imagePath;
    @parameter(settable = true, playerDisplay = true)
    private String name = "Unnamed";
    @parameter(settable = true, playerDisplay = true)
    private String type = "Basic";
    @parameter(settable = true, playerDisplay = true)
    private Double damage = 100.0;
    @parameter(settable = true, playerDisplay = true)
    private Double speed = 1.0;
    @parameter(settable = true, playerDisplay = true)
    private Double boundingHeight = 10.0;
    @parameter(settable = true, playerDisplay = true)
    private Double boundingWidth = 10.0;
    @parameter(settable = true, playerDisplay = true)
    private String group = null;
    private static final String PARAMETER_SPEED = "Speed";

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
        // TODO Change this to use annotated variables
        super.setLocation(super.getLocation()
                .add(super.getHeading().multiply((double) super.getParameter(PARAMETER_SPEED))));
    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub

    }
}
