package engine.element.sprites;

import java.util.List;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import annotations.parameter;


/**
 * This class represents the tower object in the game, which usually does not move and is used to
 * defend a map from Enemy objects. The tower shoots projectiles which target enemies.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 * @author Greg McKeon
 *
 */
public class Tower extends GameSprite {

    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private Double attackSpeed;
    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private Double attackRange;
    @parameter(settable = true, playerDisplay = true, defaultValue = "Close")
    private String attackPriority;
    @parameter(settable = false, playerDisplay = true, defaultValue = "null")
    private List<String> projectiles;
    // Use above projectile to read from data file
    // Use below projectile in front end to assign sprite objects
    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private Projectile projectileList;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double cost;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double buildTime;
    /**
     * Holds the ID's of the next sprites that may be spawned or upgraded from the current sprite
     */
    @parameter(settable = false, playerDisplay = true, defaultValue = "null")
    private List<String> nextSprites;
    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private Tower nextSpritesList;

    private Set<GameElement> myTargets;

    // TODO remove once testing is over
    public Tower (ImageView test) {
        super.setImageView(test);
    }

    /**
     * Adds new sprites for the tower to target
     * 
     * @param sprites Set<GameElement> object of sprites
     */
    public void addTargets (Set<GameElement> sprites) {
        sprites.forEach(s -> myTargets.add(s));
    }

    @Override
    public void target (Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide (GameElement element) {
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
