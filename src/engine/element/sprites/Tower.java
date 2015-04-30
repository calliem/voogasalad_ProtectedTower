package engine.element.sprites;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> projectiles;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double cost;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double buildTime;

    private Set<GameElement> myTargets;

    // // TODO remove once testing is over
    // public Tower (Map<String,Object> parameters, ImageView test) {
    // super.setImageView(test);
    // }

    public Tower () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);

        attackSpeed = (Double) parameters.get("attackSpeed");
        attackRange = (Double) parameters.get("attackRange");
        attackPriority = (String) parameters.get("attackPriority");
        projectiles = (List<String>) parameters.get("projectiles");
        cost = (Double) parameters.get("cost");
        buildTime = (Double) parameters.get("buildTime");
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
