package engine;

/**
 * This interface provides methods to update certain objects, such as objects with movement.
 * 
 * @author Qian Wang
 *
 */
public interface Updateable {

    /**
     * This method is called by functions with handle animation and updating, and contains code to
     * change the state of an object on each update. An integer is inputted so that each class may
     * use it to set events in action, depending on if the counter has reached a certain value or is
     * a multiple of a certain number.
     * 
     * @param counter
     */
    public void update (int counter);
}
