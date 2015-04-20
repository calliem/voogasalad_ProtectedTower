package engine.element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import engine.CollisionTable;
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
    private CollisionTable myCollisionTable;

    // private List<List<GridCell>> spriteMap; not sure if necessary yet
    // private List<Sprite> spritesList; not sure if necessary

    public Layout (List<Node> nodes) {
        myNodeList = nodes;
        // TODO fix this dependency, get rid of TowerManager?
        myTowerManager = new TowerManager();
        myEnemyFactory = new EnemyFactory();
        myProjectileFactory = new ProjectileFactory();
        myGridCellFactory = new GridCellFactory();
        myGameMapFactory = new MapFactory();
        myRoundFactory = new RoundFactory();
        myWaveFactory = new WaveFactory();
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
        myQuadTree = new Quadtree(1, pBounds);
    }

    public void setCollisionTable (CollisionTable table) {
        myCollisionTable = table;
    }

    @Override
    public void update (int counter) {
        updateSpriteLocations();
        updateSpriteCollisions();
    }

    /**
     * Updates the positions of all sprites.
     */
    public void updateSpriteLocations () {
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

    private void checkCollisions () {
        createQuadTree(getSprites());
        for (Sprite s : getSprites()) {
            List<Sprite> sprites = getPossibleCollisions(s);
            for (Sprite t : sprites)
                if (collides(createHitBox(s), createHitBox(t)) &&
                    myCollisionTable.collisionCheck(s, t))
                    s.collide(t);
        }
    }

    public void placeTower (String tower, Point2D loc) {
        // loc param can probably be removed because the tower can just hold its location to be
        // placed at
        Tower temp = myTowerManager.getTower(tower);
        temp.setLocation(loc);
        if (canPlace(temp, loc))
            myTowerList.add(temp);
    }

    public boolean canPlace (Sprite tower, Point2D loc) {
        // collision checking and tag checking
        Rectangle towerHitBox = createHitBox(tower);
        boolean collision = false;
        List<Sprite> collidable = getCollisions(tower, myTowerList);
        for (Sprite c : collidable) {
            if (collides(towerHitBox, createHitBox(c)))
                collision = true;
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

    public void spawnEnemy (List<String> enemy, Point2D loc) {
        for (String s : enemy) {
            Enemy e = myEnemyFactory.getEnemy(s);
            e.setLocation(loc);
            myEnemyList.add(e);
        }
    }

    public void spawnProjectile (String projectile, Point2D loc) {
        Projectile proj = myProjectileFactory.getProjectile(projectile);
        proj.setLocation(loc);
        myProjectileList.add(proj);
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

    public List<Sprite> getSprites () {
        List<Sprite> spritesList = new ArrayList<>();
        spritesList.addAll(myTowerList);
        spritesList.addAll(myEnemyList);
        spritesList.addAll(myProjectileList);
        return spritesList;
    }

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
