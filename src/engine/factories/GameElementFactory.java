package engine.factories;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import engine.element.GameElement;


/**
 * This class acts a way to generate new instances of a specific game element. A map will store
 * a list of possible enemies/towers/etc that are available in the game, each differentiated by the
 * class and the parameters map that is used to initialize the object. Specific ones will be
 * available to be called through the use of a GUID which maps an ID to a specific parameter map.
 * This is an implementation of the Prototype Manager, or a Factory method which uses cloning
 * instead of instantiation. New objects are made to be clones of the existing set of possible
 * objects.
 * 
 * @author Bojia Chen
 * @author Qian Wang
 */

public class GameElementFactory {

    private static final String MY_CLASS_LOCATION = "engine.element.GameElement";

    /**
     * Holds all the possible parameters maps used to initialize all game elements. This is a
     * triple-layered map, with the innermost layer being a map of parameter name to parameter
     * value. This comprises the parameter map. The next layer maps a GUID to a specific parameter
     * map; this represent a specific object. The outermost layer maps the class name to the
     * specific objects of that class, such as "Tower", to a map of all possible objects of type
     * Tower.
     */
    private Map<String, Map<String, Map<String, Object>>> myGameElements;
    /**
     * Holds a map of a part name to the package to use to reflect
     */
    Map<String, String> myPartTypeToPackage = new HashMap<>();

    /**
     * Constructor which initializes a new map of class name to map of guid to parameter map
     */
    public GameElementFactory () {
        fillPackageMap();
        myGameElements = new HashMap<>();
        for (String s : myPartTypeToPackage.keySet()) {
            myGameElements.put(s, new HashMap<>());
        }
    }

    // TODO replace this with loading from data file
    private void fillPackageMap () {
        myPartTypeToPackage.put("Tower", "engine.element.sprites.Tower");
        myPartTypeToPackage.put("Enemy", "engine.element.sprites.Enemy");
        myPartTypeToPackage.put("Projectile", "engine.element.sprites.Projectile");
        myPartTypeToPackage.put("GridCell", "engine.element.sprites.GridCell");
        myPartTypeToPackage.put("GameMap", "engine.element.GameMap");
        myPartTypeToPackage.put("Round", "engine.element.Round");
        myPartTypeToPackage.put("Wave", "engine.element.Wave");
    }

    /**
     * Adds new game elements to the list of all possible game elements. This method can be called
     * with a map of GUID to parameter map, or with a single GUID and single parameter map.
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param allObjects Map<String, Map<String, Object>> object representing mapping of GUID to
     *        parameter map
     */
    public void add (String className, Map<String, Map<String, Object>> allObjects) {
        allObjects.keySet().forEach(t -> this.add(className, t, allObjects.get(t)));
    }

    /**
     * @see GameElementFactory#add(String, Map)
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param guid String of the GUID of the game element
     * @param properties the parameter map, Map<String, Object> object of the sprite
     */
    public void add (String className, String guid, Map<String, Object> properties) {
        myGameElements.get(className).put(guid, properties);
    }

    /**
     * Returns a new instance/clone of a certain game element, given the class name and the GUID of
     * that game element. If specified GUID does not exist, an InvalidParameterException is thrown.
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param guid String of the GUID of the game element
     * @return New instance of sprite with same parameters as template sprite
     */
    public GameElement getGameElement (String className, String guid) {
        if (myGameElements.get(className).containsKey(guid)) {
            GameElement element = (GameElement) Reflection.createInstance(MY_CLASS_LOCATION);
            element.setParameterMap(getParameters(className, guid));
            return element;
        }
        else {
            throw new InvalidParameterException(guid + "is not defined as a type of " + className);
        }
    }

    /**
     * Returns a specific parameter map given the class and guid
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param guid String of the GUID of the game element
     * @return Map<String, Object> representing the parameter map, a mapping of parameter name to
     *         parameter value for a specific object
     */
    private Map<String, Object> getParameters (String className, String guid) {
        return myGameElements.get(className).get(guid);
    }
}
