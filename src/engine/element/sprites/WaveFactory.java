package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import engine.element.Wave;


/**
 * Factory for creating different waves
 * 
 * @author Qian Wang
 *
 */
public class WaveFactory {
    private Map<String, Map<String, Object>> myWaves;
    private final static String MY_CLASS_NAME = "engine.element.Wave";

    public WaveFactory () {
        myWaves = new HashMap<>();
    }

    /**
     * Adds new waves to the list of all possible waves, can be called with a map of
     * wave GUID to the parameters map of that wave, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see WaveFactory#add(Map)
     * 
     * @param id String of the GUID of the object
     * @param properties the properties Map<String, Object> object of the object
     */
    public void add (String id, Map<String, Object> properties) {
        myWaves.put(id, properties);
    }

    /**
     * Given a GUID, returns the corresponding object with a prefilled parameters map and values
     * that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Wave object
     */
    public Wave getWave (String guid) {
        if (!myWaves.containsKey(guid)) { throw new InvalidParameterException(guid +
                                                                              " is an undefined projectile"); }
        Wave obj = (Wave) Reflection.createInstance(MY_CLASS_NAME);
        obj.setParameterMap(myWaves.get(guid));

        return obj;
    }
}
