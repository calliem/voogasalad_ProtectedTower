package engine.element.sprites;

import java.util.List;
import javafx.scene.image.ImageView;
import annotations.parameter;


/**
 * This class represents the tower object in the game, which usually does not move and is used to
 * defend a map from Enemy objects. The tower shoots projectiles which target enemies.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Tower extends GameSprite {
    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private Double attackSpeed;
    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private Double attackRange;
    @parameter(settable = true, playerDisplay = true, defaultValue = "Close")
    private String attackPriority;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> projectiles;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double cost;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double buildTime;

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
