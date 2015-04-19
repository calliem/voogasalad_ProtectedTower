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
import engine.TowerManager;
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
import engine.element.sprites.TowerFactory;
import engine.element.sprites.WaveFactory;


/**
 * This class holds the layout of the game, including the locations of the game elements like grid
 * cells and towers/enemies. It also call on these objects to update their location and behaviors,
 * while functioning as a controller to tell objects what to do, such as telling towers what enemies
 * are within range.
 * 
 * @author Michael Yang
 * @author Qian Wang
 *
 */
public class Layout extends GameElement implements Updateable {

    private static final String PARAMETER_SIZE = "TileSize";

    /**
     * List of Javafx objects so that new nodes can be added for the player to display
     */
    private List<Node> myNodeList;
    /**
     * Contains the map of the current game
     */
    private GameMap myGameMap;
    // Lists of game elements
    private List<Sprite> towerList;
    private List<Sprite> enemyList;
    private List<Sprite> projectileList;
    // Factories to create game elements
    private TowerManager myTowerManager;
    private TowerFactory myTowerFactory;
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
        // TODO fix this dependency, get rid of TowerManager?
        myTowerFactory = new TowerFactory();
        myTowerManager = new TowerManager(myTowerFactory);
        myTowerFactory.addManager(myTowerManager);
        myEnemyFactory = new EnemyFactory();
        myProjectileFactory = new ProjectileFactory();
        myGridCellFactory = new GridCellFactory();
        myGameMapFactory = new MapFactory();
        myRoundFactory = new RoundFactory();
        myWaveFactory = new WaveFactory();
    }

    public void setMap (String mapName) {
        myGameMap = myGameMapFactory.getMap(mapName);
        Rectangle pBounds =
                new Rectangle(myGameMap.getCoordinateHeight(), myGameMap.getCoordinateWidth());
        myQuadTree = new Quadtree(1, pBounds);
    }

    /**
     * Temporary method to hardcode a collision table for testing purposes
     */
    public void makeCollisionTable () {
        List<Consumer<Sprite>> actionList = new ArrayList<Consumer<Sprite>>();
        actionList.add(e -> e.collide(null));
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

    @Override
    public void update (int counter) {
        for (int i = 0; i < counter; i++)
            updateSprites();
    }

    public List<Sprite> updateSprites () {
        checkCollisions();
        for (Sprite s : projectileList) {
            // s.update();
        }
        for (Sprite t : towerList) {
            // give every tower the enemies within its range
            // t.enemiesInRange(getEnemiesInRange(t));
            // fire projectiles
            // if (t.getProjectile() != null){
            // spawnProjectile(t.getProjectile(), t.getLocation());
            // }
        }
        for (Sprite s : enemyList) {
            // check if dead or to be removed
            if ((int) s.getParameter("HP") <= 0)
                enemyList.remove(s);
            // else
            // s.update();
        }
        // collision checking either before or after
        // probably before so that update can handle removing sprites too
        return getSprites();
    }

    private void checkCollisions () {
        createQuadTree(getSprites());
        for (Sprite s : getSprites()) {
            List<Sprite> sprites = getPossibleCollisions(s);
            for (Sprite t : sprites)
                if (collides(createHitBox(s), createHitBox(t))){
                    myCollisionTable.applyCollisionAction(s, t);
                }
        }
    }

    public void placeTower (String tower, Point2D loc) {
        // loc param can probably be removed because the tower can just hold its location to be
        // placed at
        Tower temp = myTowerManager.getTower(tower);
        temp.setLocation(loc);
        if (canPlace(temp, loc))
            towerList.add(temp);
    }

    public boolean canPlace (Sprite tower, Point2D loc) {
        // collision checking and tag checking
        Rectangle towerHitBox = createHitBox(tower);
        boolean collision = false;
        List<Sprite> collidable = getCollisions(tower, towerList);
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
                          (double) s.getParameter("range"));
    }

    private Rectangle createHitBox (Sprite s) {
        return new Rectangle(s.getLocation().getX(), s.getLocation().getY(),
                             (double) s.getParameter("BoundingWidth"),
                             (double) s.getParameter("BoundingHeight"));
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
        List<Sprite> collidable = getCollisions(tower, enemyList);
        for (Sprite c : collidable) {
            if (collides(createRange(tower), createHitBox(c)))
                enemies.add(c);
        }
        return enemies;
    }

    private void createQuadTree (List<Sprite> inserts) {
        myQuadTree.clear();
        for (Sprite e : inserts)
            myQuadTree.insert(e);
    }

    private List<Sprite> getPossibleCollisions (Sprite target) {
        List<Sprite> collidable = new ArrayList<>();
        myQuadTree.retrieve(collidable, target);
        return collidable;
    }

    private List<Sprite> getCollisions (Sprite target, List<Sprite> inserts) {
        createQuadTree(inserts);
        return getPossibleCollisions(target);
    }

    public void spawnEnemy (List<String> enemy, Point2D loc) {
        for (String s : enemy) {
            Enemy e = myEnemyFactory.getEnemy(s);
            e.setLocation(loc);
            enemyList.add(e);
        }
    }

    public void spawnProjectile (String projectile, Point2D loc) {
        Projectile proj = myProjectileFactory.getProjectile(projectile);
        proj.setLocation(loc);
        projectileList.add(proj);
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
        spritesList.addAll(towerList);
        spritesList.addAll(enemyList);
        spritesList.addAll(projectileList);
        return spritesList;
    }

    // TODO refactor next methods
    public void initializeTowers (Map<String, Map<String, Object>> allObjects) {
        myTowerFactory.add(allObjects);
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
