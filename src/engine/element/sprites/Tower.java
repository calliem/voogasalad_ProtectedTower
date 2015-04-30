package engine.element.sprites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.AttackPriority;
import javafx.geometry.Point2D;
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
    private Sprite projectileList;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double cost;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double buildTime;
    private int myTimer = 0;

    private List<GameElement> myTargets;
    private AttackPriority myPriority;
    
    // TODO remove once testing is over
    public Tower (ImageView test) {
        super.setImageView(test);
    }

    public String getProjectile(){
    	if (projectiles.size() == 1){
    		return projectiles.get(0);
    	}
    	return projectiles.get(0);
    }
    
    public void setPriority(String priority){
    	attackPriority = priority;
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
    public Map<Object, List<String>> update () {
        move();
        Map<Object, List<String>> spawnMap = new HashMap<Object, List<String>>();
        if(myTimer >= attackSpeed && !myTargets.isEmpty()){
            spawnMap.put(this.getLocation(), this.getNextSprites());
            myTimer = 0;
        }
        myTimer++;
        return spawnMap;
    }

}
