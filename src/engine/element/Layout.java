package engine.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import engine.ActionManager;
import engine.CollisionChecker;
import engine.Updateable;
import engine.element.sprites.Enemy;
import engine.element.sprites.GridCell;
import engine.element.sprites.Projectile;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;
import engine.factories.GameElementFactory;


/**
 * This class holds the layout of the game, including the locations of the game elements like grid
 * cells and towers/enemies. It also call on these objects to update their location and behaviors,
 * while functioning as a controller to tell objects what to do, such as telling towers what enemies
 * are within range.
 * 
 * @author Michael Yang
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Layout extends GameElement implements Updateable {

    /**
     * List of Javafx objects so that new nodes can be added for the player to display
     */
    private List<Node> myNodeList;
    /**
     * Contains the map of the current game
     */
    private GameMap myGameMap;
    // Lists of game elements
    private List<Tower> myTowerList;
    private List<Enemy> myEnemyList;
    private List<Projectile> myProjectileList;
    // Factories to create game elements
    private GameElementFactory myGameElementFactory;
    /**
     * Class which handles checking for collisions
     */
    private CollisionChecker myCollisionChecker;
    /**
     * Table which contains interactions between game elements
     */
    private ActionManager myActionManager;

    public Layout (List<Node> nodes) {
        myNodeList = nodes;
        myGameElementFactory = new GameElementFactory();
        myCollisionChecker = new CollisionChecker();
    }

    /**
     * Temporary method to remove a projectile from the game
     * 
     * @param sprite Projectile to be removed
     */
    // TODO Poor design to have a method for every kind of sprite, need to think of a better way to
    // do this without repeating code
    public void removeProjectile (Sprite sprite) {
        myProjectileList.remove(sprite);
        myNodeList.remove(sprite.getImageView());
    }

    /**
     * Temporary method to hardcode a collision table for testing purposes
     */
    // TODO remove later
    public void makeCollisionTable () {
        List<BiConsumer<Sprite, Sprite>> actionList = new ArrayList<BiConsumer<Sprite, Sprite>>();
        actionList.add( (e, f) -> e.onCollide(f));
        String[] spritePair = { "Enemy", "Projectile" };
        List<Integer>[] actionPair = (List<Integer>[]) new Object[2];

        List<Integer> action1 = Arrays.asList(new Integer[] { 0 });
        actionPair[0] = action1;
        List<Integer> action2 = Arrays.asList(new Integer[] { 0 });
        actionPair[1] = action2;
        Map<String[], List<Integer>[]> collisionMap = new HashMap<String[], List<Integer>[]>();
        collisionMap.put(spritePair, actionPair);
        setActionManager(new ActionManager(collisionMap, actionList));
    }

    public void setActionManager (ActionManager table) {
        myActionManager = table;
    }

    /**
     * Creates a specific map and updates the current game map object in the layout. The specified
     * GUID of the map name is used to know which map to instantiate. An array of gridcells are made
     * and added to a map object, which is then set as the current map.
     * 
     * @param mapID GUID of the map
     */
    public void setMap (String mapID) {
        myGameMap = (GameMap) myGameElementFactory.getGameElement("GameMap", mapID);
        Rectangle bounds =
                new Rectangle(myGameMap.getCoordinateHeight(), myGameMap.getCoordinateWidth());
        myCollisionChecker.initializeQuadtree(bounds);
    }

    /**
     * Puts a new tower at a specified location on the map. This method is called by the Player to
     * place a new tower during the running of the game.
     * 
     * @param towerID String ID of Tower object to place
     * @param location Point2D representing location on the map
     */
    public void placeTower (String towerID, Point2D location) {
        // loc param can probably be removed because the tower can just hold its location to be
        // placed at
        Tower temp = (Tower) myGameElementFactory.getGameElement("Tower", towerID);
        temp.setLocation(location);
        if (canPlace(temp, location)) {
            myTowerList.add(temp);
        }
    }

    /**
     * Checks to see if a specific tower may be placed at a specific point on the map. This method
     * can be used by the Player to check if a tower placement is valid.
     * 
     * @param tower Tower object that is being tested for valid placement
     * @param location Point2D representing location on grid
     * @return true if specified tower may be placed at the specified location
     */
    // TODO implement from map class
    public boolean canPlace (Sprite tower, Point2D location) {
        // collision checking and tag checking
        // Rectangle towerHitBox = createHitBox(tower);
        // boolean collision = false;
        // List<Sprite> collidable = getCollisions(tower, myTowerList);
        // for (Sprite c : collidable) {
        // if (collides(towerHitBox, createHitBox(c))) {
        // collision = true;
        // }
        // }
        // if there are any collisions with other towers, then collision stays true
        // if no collisions then the tower can be placed and collision is false
        // boolean place = true;
        /*
         * tag checking for taking in terrain in consideration
         * for (GridCell c: occupiedGridCells(tower))
         * if(!tagsInCommon(c.getTags(),tower.getTags())) //if the cell has none of the tags of the
         * tower then can't place so place is false{
         * place = false;
         * break;
         * }
         */
        // return place && !collision;
        return true;
    }

    // TODO refactor and combine spawnEnemy and spawnProjectile methods

    /**
     * Creates one or multiple new Enemy object and adds it to the map at the specified location.
     * 
     * @param enemyIDs List<String> of IDs of Enemy objects to place
     * @param location Point2D representing location on grid
     */
    public void spawnEnemy (List<String> enemyIDs, Point2D location) {
        enemyIDs.forEach(i -> spawnEnemy(i, location));
    }

    /**
     * Creates a new Projectile object and adds it to the map at the specified location
     * 
     * @param enemyID String ID of Enemy object to place
     * @param location Point2D representing location on grid
     */
    public void spawnEnemy (String enemyID, Point2D location) {
        Enemy e = (Enemy) myGameElementFactory.getGameElement("Enemy", enemyID);
        e.setLocation(location);
        myEnemyList.add(e);
    }

    /**
     * Creates one or multiple new Projectile object and adds it to the map at the specified
     * location.
     * 
     * @param projectileIDs List<String> of IDs of Projectile objects to place
     * @param location Point2D representing location on grid
     */
    public void spawnProjectile (List<String> projectileIDs, Point2D location) {
        projectileIDs.forEach(i -> spawnProjectile(i, location));
    }

    /**
     * Creates a new Projectile object and adds it to the map at the specified location
     * 
     * @param projectileID String ID of Projectile object to place
     * @param location Point2D representing location on grid
     */
    public void spawnProjectile (String projectileID, Point2D location) {
        Projectile proj =
                (Projectile) myGameElementFactory.getGameElement("Projectile", projectileID);
        proj.setLocation(location);
        myProjectileList.add(proj);
    }

    // Update methods

    /**
     * Updates the sprite locations and checks for collisions/apply effects of collision.
     * 
     * @see Updateable#update(int)
     */
    @Override
    public void update (int counter) {
        updateSpriteLocations();
        updateSpriteCollisions();
    }

    /**
     * Updates the positions of all sprites.
     */
    private void updateSpriteLocations () {
        // Move enemies
        myEnemyList.forEach(e -> e.move());
        // Move projectiles
        myProjectileList.forEach(p -> p.move());
    }
    /**
     * Checks for collisions between all objects and applies the effects of collision, such as
     * changing velocity, or removing form the map.
     */
    private void updateSpriteCollisions () {
        // Check if enemies collide into towers and need to change their path
        // Check if projectiles hit enemies and reduce health/remove from map
        // Check if towers are within range of shooting enemies and shoot
        myCollisionChecker.createQuadTree(this.getSprites());
        for (Sprite sprite : this.getSprites()) {
            Set<Sprite> possibleInteractions = myCollisionChecker.findCollisionsFor(sprite);
            for (Sprite other : possibleInteractions) {
                // TODO determine how many interactions should be made to each sprite
                myActionManager.applyAction(sprite, other);
            }
        }
    }

    // Collision checking methods

    /**
     * Returns a list of all sprites in the map
     * 
     * @return List<Sprite> of all active sprites on the map
     */
    private List<Sprite> getSprites () {
        List<Sprite> spritesList = new ArrayList<>();
        spritesList.addAll(myTowerList);
        spritesList.addAll(myEnemyList);
        spritesList.addAll(myProjectileList);
        return spritesList;
    }

    @Deprecated
    private boolean tagsInCommon (List<String> cellTags, List<String> towerTags) {
        boolean common = false;
        for (String tag : towerTags)
            if (cellTags.contains(tag))
                return true;
        return common;
    }

    @Deprecated
    private Set<GridCell> occupiedGridCells (Sprite sprite) {
        Set<GridCell> occupied = new HashSet<>();
        // radiate outwards from currentGridCell and check for collisions with GridCells
        checkAdjacent(currentGridCell(sprite), occupied, sprite);
        return occupied;
    }

    @Deprecated
    private void checkAdjacent (GridCell current, Set<GridCell> occupied, Sprite sprite) {
        int[] x = { 1, 1, 0, -1, -1, 0, 1, -1 };
        int[] y = { 0, 1, 1, 0, -1, -1, -1, 1 };
        // need to know how to check if the sprite collides with the gridcell
        /*
         * if (sprite.intersects(current)){
         * if(occupied.contains(current))
         * return;
         * occupied.add(current);
         * for (int i = 0; i<x.length; i++){
         * checkAdjacent(terrainMap[current.x+x[i]][current.y+y[i]], occupied, sprite);
         * }
         * }
         */
    }

    @Deprecated
    private GridCell currentGridCell (Sprite sprite) {
        // return terrainMap[sprite.x/gridSize][sprite.y/gridSize]
        return null;
    }

    // Loading game methods

    /**
     * Method used to initialize the game element factory with what possible game elements this game
     * can create.
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param allObjects Map<String, Map<String, Object>> object representing mapping of GUID to
     *        parameter map
     */
    public void initializeGameElement (String className, Map<String, Map<String, Object>> allObjects) {
        myGameElementFactory.add(className, allObjects);
    }
}
