package engine.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Point2D;
import util.reflection.Reflection;
import annotations.parameter;
import engine.Bank;
import engine.Endable;
import engine.Updateable;
import engine.conditions.Condition;
import engine.element.sprites.Shop;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;
import engine.factories.GameElementFactory;


/**
 * This class holds an instance of an entire tower defense game. It keeps the levels, towers,
 * enemies, and their properties.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Game implements Updateable, Endable {

    private static final String PACKAGE_LOCATION_LEVEL = "engine.element.Level";
    @parameter(settable = true, playerDisplay = true, defaultValue = "20")
    private Integer lives;

    private List<Condition> myConditions;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevelIndex;
    private Bank myBank;
    private Shop myShop;
    private int myPoints;
    private GameElementFactory myGameElementFactory;



    public Game (List<Sprite> nodes, Map<String, Object> parameters) {
    myLayout = new Layout(nodes);
    myGameElementFactory = new GameElementFactory();
    myLayout.setFactory(myGameElementFactory);

    lives = (Integer) parameters.get("Lives");
        myConditions = new ArrayList<Condition>();
        myConditions.add(new Condition(this, e -> e.lives == 0, e -> e.lose()));
        myConditions.add(new Condition(this, e -> e.myActiveLevelIndex >= myLevels.size(), e -> e.win()));
        myLevels = new ArrayList<>();
        myConditions = new ArrayList<Condition>();

        myActiveLevelIndex = 0;
        myBank = new Bank(0);
        myShop = new Shop(myLayout, myBank, new ArrayList<>());
        myPoints = 0;
    }

    /**
     * Checks if the user has played through all levels.
     * 
     * @return true if all levels have ended
     */
    public boolean hasEnded () {
        return myActiveLevelIndex >= myLevels.size();
    }

    /**
     * Calls update methods on the current Level object and then the Layout object. Checks game
     * conditions for win/lose.
     * 
     * @see Updateable#update(int)
     */
    public void update () {

        System.out.println("Beginning cycle ");
        myConditions.forEach(c -> c.check());
        Map<Object, List<String>> enemiesToSpawn =
                myLevels.get(myActiveLevelIndex).update();
        for (Object loc : enemiesToSpawn.keySet()) {
            myLayout.spawnEnemy(enemiesToSpawn.get(loc), (String) loc);
        }
        myLayout.update();

    }

    /**
     * Adds levels to the list of all levels, fills their parameter maps, and sorts levels by
     * number. This method is called by the GameController to add levels when loading the game.
     * 
     * @param levels Map<String, Map<String, Object>> mapping GUID to parameters map for each level
     */
    public void addLevels (Map<String, Map<String, Object>> levels) {
        for (String key : levels.keySet()) {
            Level tempLevel = (Level) Reflection.createInstance(PACKAGE_LOCATION_LEVEL);
            tempLevel.addInstanceVariables(levels.get(key));
            tempLevel.setGameElementFactory(myGameElementFactory);
            myLevels.add(tempLevel);
        }
        Collections.sort(myLevels);
        System.out.println("Levels added to game");
    }

    /**
     * Used to tell the Layout what possible objects there are to instantiate
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param allObjects Map<String, Map<String, Object>> object representing mapping of GUID to
     *        parameter map
     */
    public void addGameElement (String className, Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeGameElement(className, allObjects);
    }

    public void placeTower (String id, double sceneX, double sceneY) {
        Point2D loc = new Point2D(sceneX, sceneY);
        myLayout.placeTower(id, loc);
    }

    public List<Tower> getAllTowerObjects (Set<String> towerIDs) {
        List<Tower> towers = new ArrayList<>();
        for (String id : towerIDs) {
            towers.add((Tower) myGameElementFactory.getGameElement("Tower", id));
        }
        return towers;
    }

    public void updateBackgroundTest (String key) {
        myLayout.setMap(key);
        myLevels.get(0).startNextRound();
    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub

    }
    
    public void win() {
    	
    }
    
    public void lose() {
    	
    }
}
