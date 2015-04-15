package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;


/**
 * Superclass for sprite factories
 * 
 * @author Bojia Chen
 *
 */

public class SpriteFactory {

    private Map<String, Map<String, Object>> mySprites;
    private String MY_CLASS_NAME;

    public SpriteFactory (String className) {
        MY_CLASS_NAME = className;
        mySprites = new HashMap<>();
    }

    public void addSprite (Map<String, Object> spriteProperties) {
        mySprites.put((String) spriteProperties.get("guid"), spriteProperties);
    }

    protected void checkID (String spriteID) {
        if (!mySprites.containsKey(spriteID))
            throw new InvalidParameterException(spriteID + " is an undefined " + MY_CLASS_NAME);
    }

    protected Map<String, Object> getSpriteParameters (String spriteID) {
        return mySprites.get(spriteID);
    }
}
