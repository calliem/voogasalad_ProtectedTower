package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import engine.element.GameMap;


/**
 * Factory for creating different maps/layouts of the board
 * 
 * @author Qian Wang
 *
 */
public class MapFactory {
    private Map<String, Map<String, Object>> myMaps;
    private final static String MY_CLASS_NAME = "engine.element.GameMap";

    public MapFactory () {
        myMaps = new HashMap<>();
    }

    /**
     * Adds new gamemaps to the list of all possible gamemaps, can be called with a map of
     * gamemap GUID to the parameters map of that gamemap, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see MapFactory#add(Map)
     * 
     * @param id String of the GUID of the object
     * @param properties the properties Map<String, Object> object of the object
     */
    public void add (String id, Map<String, Object> properties) {
        myMaps.put(id, properties);
    }

    /**
     * Given a GUID, returns the corresponding object with a prefilled parameters map and values
     * that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return GameMap object
     */
    public GameMap getProjectile (String guid) {
        if (!myMaps.containsKey(guid)) { throw new InvalidParameterException(guid +
                                                                             " is an undefined projectile"); }

        GameMap obj = (GameMap) Reflection.createInstance(MY_CLASS_NAME);
        obj.setParameterMap(myMaps.get(guid));

        return obj;
    }
}
