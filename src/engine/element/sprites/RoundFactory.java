package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import engine.element.Round;


/**
 * Factory for creating different rounds
 * 
 * @author Qian Wang
 *
 */
public class RoundFactory {
    private Map<String, Map<String, Object>> myRounds;
    private final static String MY_CLASS_NAME = "engine.element.Round";

    public RoundFactory () {
        myRounds = new HashMap<>();
    }

    /**
     * Adds new rounds to the list of all possible rounds, can be called with a map of
     * round GUID to the parameters map of that round, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see RoundFactory#add(Map)
     * 
     * @param id String of the GUID of the object
     * @param properties the properties Map<String, Object> object of the object
     */
    public void add (String id, Map<String, Object> properties) {
        myRounds.put(id, properties);
    }

    /**
     * Given a GUID, returns the corresponding object with a prefilled parameters map and values
     * that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Round object
     */
    public Round getRound (String guid) {
        if (!myRounds.containsKey(guid)) { throw new InvalidParameterException(guid +
                                                                               " is an undefined projectile"); }
        Round obj = (Round) Reflection.createInstance(MY_CLASS_NAME);
        obj.setParameterMap(myRounds.get(guid));

        return obj;
    }
}
