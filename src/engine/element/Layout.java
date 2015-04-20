package engine.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import engine.CollisionManager;
import engine.Quadtree;
import engine.Updateable;
import engine.element.sprites.Enemy;
import engine.element.sprites.EnemyFactory;
import engine.element.sprites.GridCell;
import engine.element.sprites.GridCellFactory;
import engine.element.sprites.MapFactory;
import engine.element.sprites.Projectile;
import engine.element.sprites.ProjectileFactory;
import engine.element.sprites.RoundFactory;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;
import engine.element.sprites.TowerManager;
import engine.element.sprites.WaveFactory;


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

    private static final String PARAMETER_SIZE = "TileSize";
    private static final String PARAMETER_HP = "HP";
    private static final String PARAMETER_RANGE = "Range";
    private static final String PARAMETER_BOUNDINGHEIGHT = "BoundingHeight";
    private static final String PARAMETER_BOUNDINGWIDTH = "BoundingWidth";
    private static final int INITIAL_QUADTREE_REGIONS = 1;

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
    private TowerManager myTowerManager;
    private EnemyFactory myEnemyFactory;
    private ProjectileFactory myProjectileFactory;
    private GridCellFactory myGridCellFactory;
    private MapFactory myGameMapFactory;
    private RoundFactory myRoundFactory;
    private WaveFactory myWaveFactory;
    /**
     * Quad tree object used for collision
     */
    private Quadtree myQuadTree = null;
    /**
     * Table which contains interactions between game elements
     */
    private CollisionManager myCollisionTable;

    // private List<List<GridCell>> spriteMap; not sure if necessary yet
    // private List<Sprite> spritesList; not sure if necessary

    public Layout (List<Node> nodes) {
        myNodeList = nodes;
        myTowerManager = new TowerManager();
        myEnemyFactory = new EnemyFactory();
        myProjectileFactory = new ProjectileFactory();
        myGridCellFactory = new GridCellFactory();
        myGameMapFactory = new MapFactory();
        myRoundFactory = new RoundFactory();
        myWaveFactory = new WaveFactory();
    }

    /**
     * Temporary method to hardcode a collision table for testing purposes
     */
    public void makeCollisionTable () {
        List<Consumer<Sprite>> actionList = new ArrayList<Consumer<Sprite>>();
        actionList.add(e -> e.onCollide(null));
        String[] spritePair = { "Enemy", "Projectile" };

        @SuppressWarnings("unchecked")
        List<Integer>[] actionPair = (List<Integer>[]) new Object[2];

        List<Integer> action1 = Arrays.asList(new Integer[] { 0 });
        actionPair[0] = action1;
        List<Integer> action2 = Arrays.asList(new Integer[] { 0 });
        actionPair[1] = action2;
        Map<String[], List<Integer>[]> collisionMap = new HashMap<String[], List<Integer>[]>();
        collisionMap.put(spritePair, actionPair);
        setCollisionTable(new CollisionManager(collisionMap, actionList));
    }

    public void setCollisionTable (CollisionManager table) {
        myCollisionTable = table;
    }

    /**
     * Creates a specific map and updates the current game map object in the layout. The specified
     * GUID of the map name is used to know which map to instantiate. An array of gridcells are made
     * and added to a map object, which is then set as the current map.
     * 
     * @param mapName GUID of the map
     */
    public void setMap (String mapName) {
        myGameMap = myGameMapFactory.getMap(mapName);
        Rectangle pBounds =
                new Rectangle(myGameMap.getCoordinateHeight(), myGameMap.getCoordinateWidth());
        myQuadTree = new Quadtree(INITIAL_QUADTREE_REGIONS, pBounds);
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
        Tower temp = myTowerManager.getTower(towerID);
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
    public boolean canPlace (Sprite tower, Point2D location) {
        // collision checking and tag checking
        Rectangle towerHitBox = createHitBox(tower);
        boolean collision = false;
        List<Sprite> collidable = getCollisions(tower, myTowerList);
        for (Sprite c : collidable) {
            if (collides(towerHitBox, createHitBox(c))) {
                collision = true;
            }
        }
        // if there are any collisions with other towers, then collision stays true
        // if no collisions then the tower can be placed and collision is false
        boolean place = true;
        /*
         * tag checking for taking in terrain in consideration
         * for (GridCell c: occupiedGridCells(tower))
         * if(!tagsInCommon(c.getTags(),tower.getTags())) //if the cell has none of the tags of the
         * tower then can't place so place is false{
         * place = false;
         * break;
         * }
         */
        return place && !collision;
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
        Enemy e = myEnemyFactory.getEnemy(enemyID);
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
        Projectile proj = myProjectileFactory.getProjectile(projectileID);
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
        for (Sprite enemy : myEnemyList) {
            // check if dead or to be removed
            // if ((int) enemy.getParameter(PARAMETER_HP) <= 0)
            // myEnemyList.remove(enemy);
            // else
            // enemy.update();
        }
        // Check if projectiles hit enemies and reduce health/remove from map
        for (Sprite projectile : myProjectileList) {
            // projectile.update();
        }
        // Check if towers are within range of shooting enemies and shoot
        for (Sprite tower : myTowerList) {
            // give every tower the enemies within its range
            // tower.enemiesInRange(getEnemiesInRange(tower));
            // fire projectiles
            // if (tower.getProjectile() != null) {
            // spawnProjectile(tower.getProjectile(), tower.getLocation());
            // }
        }
    }

    // Collision checking methods

    private void checkCollisions () {
        createQuadTree(getSprites());
        for (Sprite s : getSprites()) {
            List<Sprite> sprites = getPossibleCollisions(s);
            for (Sprite t : sprites) {
                if (collides(createHitBox(s), createHitBox(t))) {
                    myCollisionTable.applyCollisionAction(s, t);
                }
            }
        }
    }

    /**
     * @return List<Sprite> of all active sprites on the map
     */
    private List<Sprite> getSprites () {
        List<Sprite> spritesList = new ArrayList<>();
        spritesList.addAll(myTowerList);
        spritesList.addAll(myEnemyList);
        spritesList.addAll(myProjectileList);
        return spritesList;
    }

    private Circle createRange (Sprite s) {
        return new Circle(s.getLocation().getX(), s.getLocation().getY(),
                          (double) s.getParameter(PARAMETER_RANGE));
    }

    private Rectangle createHitBox (Sprite s) {
        return new Rectangle(s.getLocation().getX(), s.getLocation().getY(),
                             (double) s.getParameter(PARAMETER_BOUNDINGWIDTH),
                             (double) s.getParameter(PARAMETER_BOUNDINGHEIGHT));
    }

    private boolean collides (Shape sprite1, Shape sprite2) {
        return sprite1.getBoundsInParent().intersects(sprite2.getBoundsInParent());
    }

    private boolean tagsInCommon (List<String> cellTags, List<String> towerTags) {
        boolean common = false;
        for (String tag : towerTags)
            if (cellTags.contains(tag))
                return true;
        return common;
    }

    private Set<Sprite> getEnemiesInRange (Sprite tower) {
        Set<Sprite> enemies = new HashSet<>();
        // find enemies in range (collision checking)
        List<Sprite> collidable = getCollisions(tower, myEnemyList);
        for (Sprite c : collidable) {
            if (collides(createRange(tower), createHitBox(c)))
                enemies.add(c);
        }
        return enemies;
    }

    private void createQuadTree (List<? extends Sprite> inserts) {
        myQuadTree.clear();
        for (Sprite e : inserts)
            myQuadTree.insert(e);
    }

    private List<Sprite> getPossibleCollisions (Sprite target) {
        List<Sprite> collidable = new ArrayList<>();
        myQuadTree.retrieve(collidable, target);
        return collidable;
    }

    private List<Sprite> getCollisions (Sprite target, List<? extends Sprite> inserts) {
        createQuadTree(inserts);
        return getPossibleCollisions(target);
    }

    private Set<GridCell> occupiedGridCells (Sprite sprite) {
        Set<GridCell> occupied = new HashSet<>();
        // radiate outwards from currentGridCell and check for collisions with GridCells
        checkAdjacent(currentGridCell(sprite), occupied, sprite);
        return occupied;
    }

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

    private GridCell currentGridCell (Sprite sprite) {
        // return terrainMap[sprite.x/gridSize][sprite.y/gridSize]
        return null;
    }

    // Loading game methods

    // TODO refactor below methods
    public void initializeTowers (Map<String, Map<String, Object>> allTowers) {
        myTowerManager.add(allTowers);
    }

    public void initializeEnemies (Map<String, Map<String, Object>> allObjects) {
        myEnemyFactory.add(allObjects);
    }

    public void initializeProjectiles (Map<String, Map<String, Object>> allObjects) {
        myProjectileFactory.add(allObjects);
    }

    public void initializeGridCells (Map<String, Map<String, Object>> allObjects) {
        myGridCellFactory.add(allObjects);
    }

    public void initializeGameMaps (Map<String, Map<String, Object>> allObjects) {
        myGameMapFactory.add(allObjects);
    }

    public void initializeRounds (Map<String, Map<String, Object>> allObjects) {
        myRoundFactory.add(allObjects);
    }

    public void initializeWaves (Map<String, Map<String, Object>> allObjects) {
        myWaveFactory.add(allObjects);
    }
}
