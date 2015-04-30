package engine;

import java.util.Map;


/**
 * This interface provides way to require classes to implement a method to help set their instance
 * variables for reflection because of the difficulty with setting complex data types in
 * constructors using reflection.
 * 
 * @author Qian Wang
 *
 */
public interface Reflectable {

    /**
     * Sets the instances variables of a class when reflection is used to create the object
     * 
     * @param parameters Map<String, Object> object of instance variables names to values
     */
    public void addInstanceVariables (Map<String, Object> parameters);
}
