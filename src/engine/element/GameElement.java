package engine.element;

import java.util.Collections;
import java.util.Map;


/**
 * Abstract superclass for all game elements. Contains a String -> Objects map for the specific
 * parameters used in each element of the game.
 * 
 * @author Qian Wang
 *
 */
public abstract class GameElement {
    /**
     * Holds the parameters of each object, like health/power/defense/etc
     */
    private Map<String, Object> myParameters;

    /**
     * Default constructor for use with reflection
     */
    public GameElement () {

    }

    // getters and setters

    /**
     * Loads an map of strings of parameters names to the object that carries their value
     * 
     * @param map of string to object
     */
    public void setParameterMap (Map<String, Object> map) {
        myParameters = map;
    }

    /**
     * Returns the specified parameter
     * 
     * @param name String of the parameter
     * @return Object of a certain type representing value of name
     */
    public Object getParameter (String name) {
        return myParameters.get(name);
    }

    /**
     * @return the full Map<String, Object> object representing all parameters
     */
    protected Map<String, Object> getAllParameters () {
        return Collections.unmodifiableMap(myParameters);
    }
}
