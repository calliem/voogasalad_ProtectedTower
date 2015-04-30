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
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import util.pathsearch.pathalgorithms.NoPathExistsException;
import engine.ActionManager;
import engine.CollisionChecker;
import engine.Updateable;
import engine.element.sprites.Enemy;
import engine.element.sprites.GameElement;
import engine.element.sprites.GameMap;
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
 * @author Sean Scott
 *
 */
public class Layout implements Updateable {

    /**
     * List of Javafx objects so that new nodes can be added for the player to display
     */
    private List<Sprite> myNodeList;
    private Group myBackgroundNode;
    /**
     * Contains the map of the current game
     */
    private GameMap myGameMap;
    private double[] myGoalCoordinates;
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

    private final int ROW_INDEX = 0;
    private final int COLUMN_INDEX = 1;

    public Layout (List<Sprite> myNodes, Group background) {
        myNodeList = myNodes;
        myBackgroundNode = background;
        myCollisionChecker = new CollisionChecker();
    }

    /**
     * Sets the factory used to produce all game objects
     * 
     * @param factory GameElementFactory object
     */
    public void setFactory (GameElementFactory factory) {
        myGameElementFactory = factory;
    }

    /**
     * Temporary method to remove a sprite from the game
     * 
     * @param sprite Sprite to be removed
     */
    // TODO Poor design to have a method for every kind of sprite, need to think of a better way to
    // do this without repeating code
    public void removeSprite (GameElement sprite) {
        myProjectileList.remove(sprite);
        myEnemyList.remove(sprite);
        myTowerList.remove(sprite);
        myNodeList.remove(sprite);
    }

    /**
     * Temporary method to hardcode a collision table for testing purposes
     */
    // TODO remove later
    public void makeCollisionTable () {
        List<BiConsumer<GameElement, GameElement>> actionList =
                new ArrayList<BiConsumer<GameElement, GameElement>>();
        actionList.add( (e, f) -> e.onCollide(f));
        actionList.add( (e, f) -> updatePathTest(e));
        String[] spritePair = { "Enemy", "Projectile" };
        String[] spritePairPath = { "Enemy", "Tower" };
        List<Integer>[] actionPair = (List<Integer>[]) new Object[2];
        List<Integer> action1 = Arrays.asList(new Integer[] { 0 });
        actionPair[0] = action1;
        List<Integer> action2 = Arrays.asList(new Integer[] { 0 });
        actionPair[1] = action2;
        List<Integer>[] actionPairPath = (List<Integer>[]) new Object[2];
        action1 = Arrays.asList(new Integer[] { 1 });
        actionPairPath[0] = action1;
        Map<String[], List<Integer>[]> collisionMap = new HashMap<String[], List<Integer>[]>();
        collisionMap.put(spritePair, actionPair);
        collisionMap.put(spritePairPath, actionPairPath);
        setActionManager(new ActionManager(collisionMap, actionList));
    }

    private void updatePathTest (GameElement e) {
        Enemy enemy = (Enemy) e;
        GridCell[][] grid = myGameMap.getMap();
        int[] startIndices =
                myGameMap.getRowColAtCoordinates(enemy.getLocationX(), enemy.getLocationY());
        int[] endIndices =
                myGameMap.getRowColAtCoordinates(myGoalCoordinates[1], myGoalCoordinates[0]);
        try {
            enemy.planPath(grid, startIndices[0], startIndices[1], endIndices[0], endIndices[1]);
        }
        catch (NoPathExistsException e1) {
            e1.printStackTrace();
        }
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
        // myBackgroundNode.getChildren().add(myGameMap.getBackgroundImage());
        if (myNodeList.size() == 0) {
            myNodeList.add(myGameMap);
        }
        else {
            myNodeList.add(0, myGameMap);
        }
        System.out.println("Background set!");
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
            myNodeList.add(temp);
            myTowerList.add(temp);
            System.out.println(temp.getGUID() + " added to the node list in Layout");
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
    public boolean canPlace (GameElement tower, Point2D location) {
        // collision checking and tag checking
        // collision checking
        // boolean collisions = true;
        // myCollisionChecker.createQuadTree(myTowerList);
        // Set<GameElement> possibleInteractions = myCollisionChecker.findCollisionsFor(tower);
        // if (possibleInteractions.size() == 0)
        // collisions = false;
        // // tag checking
        // boolean tags = true;
        // myCollisionChecker.createQuadTree(this.getGridCells());
        // Set<GameElement> possibleGridCells = myCollisionChecker.findCollisionsFor(tower);
        // for (GameElement c : possibleGridCells) {
        // if (!tagsInCommon(c, tower))
        // tags = false;
        // }
        // return !collisions && tags;
        return true;
    }

    /**
     * Checks if the cell and the tower have at least one tag in common
     * 
     * @param cell GridCell object to check
     * @param tower Sprite object to check
     * @return true if the two have a tag in common
     */
    private boolean tagsInCommon (GameElement cell, GameElement tower) {
        List<String> cellTags = cell.getTags();
        for (String tag : tower.getTags()) {
            if (cellTags.contains(tag)) { return true; }
        }
        return false;
    }

    /**
     * Returns a list of all the gridCells in myGameMap
     * 
     * @return List<GameElement> all the gridCells in myGameMap
     */
    private List<GameElement> getGridCells () {
        GridCell[][] myMap = myGameMap.getMap();
        List<GameElement> gridCells = new ArrayList<>();
        for (int i = 0; i < myMap.length; i++)
            for (int j = 0; j < myMap[i].length; j++)
                gridCells.add((GameElement) myMap[i][j]);
        return gridCells;
    }

    // TODO refactor and combine spawnEnemy and spawnProjectile methods

    /**
     * Creates one or multiple new Enemy object and adds it to the map at the specified location.
     * 
     * @param enemyIDs List<String> of IDs of Enemy objects to place
     * @param location Point2D representing location on grid
     */
    public void spawnEnemy (List<String> enemyIDs, String pathID) {
        enemyIDs.forEach(i -> spawnEnemy(i, pathID));
    }

    /**
     * Creates a new Enemy object and adds it to the map at the specified location
     * 
     * @param enemyID String ID of Enemy object to place
     * @param location Point2D representing location on grid
     */
    public void spawnEnemy (String enemyID, String pathID) {
        Enemy e = (Enemy) myGameElementFactory.getGameElement("Enemy", enemyID);
        Point2D location = null; // TODO: Lookup spawn point given pathID
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
        updateSpriteTargeting();
        removeDeadSprites();
    }

    /**
     * Removes all GameElements that have a statetag of dead.
     */

    private void removeDeadSprites () {
        for (GameElement g : this.getSprites()) {
            if (g.getState().equals(GameElement.DEAD_STATE))
                this.removeSprite(g);
        }
    }

    /**
     * Updates the positions of all sprites.
     */
    private void updateSpriteLocations () {
        // Move enemies
        // myEnemyList.forEach(e -> e.move());
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
            Set<GameElement> possibleInteractions = myCollisionChecker.findCollisionsFor(sprite);
            for (GameElement other : possibleInteractions) {
                // TODO determine how many interactions should be made to each sprite
                myActionManager.applyAction(sprite, other);
            }
        }
    }

    /**
     * Updates the targeting of towers.
     */
    private void updateSpriteTargeting () {
        myCollisionChecker.createQuadTree(this.getSprites());
        for (Tower tower : myTowerList) {
            tower.addTargets(myCollisionChecker.findTargetable(tower));
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

    // TODO implement this
    public void addToScene (Sprite s) {
        myNodeList.add(s);
    }

    // TODO remove
    public void updateBackgroundTest (String key) {
        this.setMap(key);
    }
}
