package engine;

/**
 * This interface specifies methods which allow objects to check if other objects have ended their
 * functions, such as a a game checking if a level has ended.
 * 
 * @author Qian Wang
 *
 */
public interface Endable {

    /**
     * This methods checks to see if an object is done performing its function.
     * 
     * @return true if object is finished performing its function
     */
    public boolean hasEnded ();
}
