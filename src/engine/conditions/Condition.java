package engine.conditions;

/**
 * This abstract class is used as a base class for objects which hold code to check a condition
 * during the game, such as a win condition, lose condition, or condition which triggers an event
 * like hitting a certain score.
 * 
 * @author Qian Wang
 *
 */
public abstract class Condition {

    // Abstract methods

    /**
     * This method contains the code which checks the specific condition this class implements.
     * 
     * @return true if the condition is reached
     */
    public abstract boolean checkCondition ();
}
