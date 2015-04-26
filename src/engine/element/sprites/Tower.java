package engine.element.sprites;

import annotations.parameter;
import javafx.scene.image.ImageView;


/**
 * This class represents the tower object in the game, which usually does not move and is used to
 * defend a map from Enemy objects. The tower shoots projectiles which target enemies.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
//TODO: update default values
public class Tower extends GameSprite {
    @parameter(settable=true,playerDisplay=true, defaultValue = "1.0")
    private Double attackSpeed;
    @parameter(settable=true,playerDisplay=true, defaultValue = "1.0")
    private Double attackRange;
    @parameter(settable=true,playerDisplay=true)
    private String projectile = null;
    @parameter(settable=true,playerDisplay=true,  defaultValue = "Close")
    private String attackPriority;
    @parameter(settable=true,playerDisplay=true, defaultValue = "10.0")
    private Double boundingHeight = 10.0;
    @parameter(settable=true,playerDisplay=true, defaultValue = "10.0")
    private Double boundingWidth = 10.0;
    @parameter(settable=true,playerDisplay=true)
    private String group = null;
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

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub
        System.out.println("Tower updated");
    }
}
