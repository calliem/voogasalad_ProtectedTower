package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


/**
 * Superclass for sprite factories
 * 
 * @author Bojia Chen
 *
 */

public class SpriteFactory {

    private Map<String, Map<String, Object>> mySprites;
    private String PARAMETER_MY_CLASS_NAME;

    public SpriteFactory (String className) {
        PARAMETER_MY_CLASS_NAME = className;
        mySprites = new HashMap<>();
    }

    /**
     * Adds new sprites to the list of all possible sprites, can be called with a map of
     * sprite GUID to the parameters map of that sprite, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see SpriteFactory#add(Map)
     * 
     * @param guid String of the GUID of the sprite
     * @param spriteProperties the properties Map<String, Object> object of the sprite
     */
    public void add (String guid, Map<String, Object> spriteProperties) {
        mySprites.put(guid, spriteProperties);
    }

    /**
     * Should only be called on by the specific factories. Returns a new sprite given the GUID of
     * the template sprite.
     * 
     * @param spriteID GUID of template sprite
     * @return New instance of sprite with same parameters as template sprite
     */

    protected Sprite getSprite (String spriteID) {
        checkID(spriteID);
        Sprite sprite = (Sprite) Reflection.createInstance(PARAMETER_MY_CLASS_NAME);
        sprite.setParameterMap(getParameters(spriteID));
        return sprite;
    }

    protected void checkID (String spriteID) {
        if (!mySprites.containsKey(spriteID))
            throw new InvalidParameterException(spriteID + " is an undefined " +
                                                PARAMETER_MY_CLASS_NAME);
    }

    protected Map<String, Object> getParameters (String spriteID) {
        return mySprites.get(spriteID);
    }
}
