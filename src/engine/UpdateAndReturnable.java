package engine;

import java.util.List;


/**
 * This interface provides methods to update objects that need to return lists of enemies
 * 
 * @author Bojia Chen
 *
 */
public interface UpdateAndReturnable {

    /**
     * This method is called by functions with handle animation and updating, and contains code to
     * change the state of an object on each update. An integer is inputed so that each class may
     * use it to set events in action, depending on if the counter has reached a certain value or is
     * a multiple of a certain number. At the end of the function call, GUIDs of enemies (or any
     * moveableSprite, in the future) to be added to the map during that iteration are returned back
     * up the call stack.
     * 
     * @param counter
     */
    public List<String> update (int counter);
}
