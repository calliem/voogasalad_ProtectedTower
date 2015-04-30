package engine.element.sprites;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import annotations.parameter;
import engine.AttackPriority;


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
    private List<String> projectiles = new ArrayList<String>();
    // Use above projectile to read from data file
    // Use below projectile in front end to assign sprite objects
    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private Sprite projectileList;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double cost;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double buildTime;
    private int myTimer = 0;

    private List<GameElement> myTargets = new ArrayList<>();
    private AttackPriority myPriority = new AttackPriority(new Point2D(500, 0));

    public Tower () {

    }

    // TODO remove once testing is over
    public Tower (ImageView test) {
        super.setImageView(test);
    }

    @Override
    public void setLocation (Point2D location) {
        super.setLocation(location);
        myPriority = new AttackPriority(location);
    }
    
    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);

        attackSpeed = (Double) parameters.get("AttackSpeed");
        attackRange = (Double) parameters.get("AttackRange");
        attackPriority = (String) parameters.get("AttackPriority");
        projectiles = new ArrayList<String>();
        projectiles.add((String) parameters.get("Projectile"));
        System.out.println(this + " has this many projectiles " + projectiles.size());
        cost = (Double) parameters.get("Cost");
        buildTime = (Double) parameters.get("BuildTime");
    }    // TODO remove once testing is over

    public String getProjectile () {
        return projectiles.get(0);
    }

    public void setPriority (String priority) {
        attackPriority = priority;
    }

    /**
     * Adds new sprites for the tower to target
     * 
     * @param sprites Set<GameElement> object of sprites
     */
    public void addTargets (Set<GameElement> sprites) {
        myTargets.clear();
        sprites.forEach(s -> myTargets.add(s));
    }

    public GameElement getTarget () {
        return myPriority.getTarget(attackPriority.toLowerCase(), myTargets);
    }

    @Override
    public void target (Sprite sprite) {
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
    }

    @Override
    public Map<Object, List<String>> update () {
        move();
        Map<Object, List<String>> spawnMap = new HashMap<Object, List<String>>();
        // System.out.println(myTimer +" time before, as after "+ attackSpeed
        // +"I have "+projectiles.size());
        if (myTimer >= attackSpeed && !myTargets.isEmpty()) {
            spawnMap.put(this.getLocation(), projectiles);
            myTimer = 0;
        }
        myTimer++;
        return spawnMap;
    }

    public double getCost () {
        return cost;
    }

    @Override
    public void fixField (String fieldToModify, Object value) {
        Field[] possibleFields = this.getClass().getDeclaredFields();
        for (Field field : possibleFields){
            if(field.getName()==fieldToModify){
                try {
                    field.set(field.getType(), field.getType().getClass().cast(value));
                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    System.err.print("Modifier application failed");
                }
            }
        }
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
