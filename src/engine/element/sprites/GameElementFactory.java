package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import engine.element.GameElement;
import util.reflection.Reflection;


/**
 * Superclass for sprite factories
 * 
 * @author Bojia Chen
 *
 */

public class GameElementFactory {

    private Map<String, Map<String, Object>> myGameElements;
    private String PARAMETER_MY_CLASS_NAME;

    public GameElementFactory (String className) {
        PARAMETER_MY_CLASS_NAME = className;
        myGameElements = new HashMap<>();
    }

    /**
     * Adds new sprites to the list of all possible sprites, can be called with a map of
     * sprite GUID to the parameters map of that sprite, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allObjects Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allObjects) {
        allObjects.keySet().forEach(t -> this.add(t, allObjects.get(t)));
    }

    /**
     * @see GameElementFactory#add(Map)
     * 
     * @param guid String of the GUID of the sprite
     * @param properties the properties Map<String, Object> object of the sprite
     */
    public void add (String guid, Map<String, Object> properties) {
        myGameElements.put(guid, properties);
    }

    /**
     * Should only be called on by the specific factories. Returns a new sprite given the GUID of
     * the template sprite.
     * 
     * @param guid GUID of template sprite
     * @return New instance of sprite with same parameters as template sprite
     */
    protected GameElement getGameElement (String guid) {
        checkID(guid);
        Sprite sprite = (Sprite) Reflection.createInstance(PARAMETER_MY_CLASS_NAME);
        sprite.setParameterMap(getParameters(guid));
        return sprite;
    }

    protected void checkID (String guid) {
        if (!myGameElements.containsKey(guid))
            throw new InvalidParameterException(guid + " is an undefined " +
                                                PARAMETER_MY_CLASS_NAME);
    }

    protected Map<String, Object> getParameters (String guid) {
        return myGameElements.get(guid);
    }
}
