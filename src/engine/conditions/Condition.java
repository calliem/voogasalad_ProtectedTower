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
     * @param livesRemaining the current health/score/etc of the game
     * @return true if the condition is reached
     */
    public abstract boolean checkCondition (int livesRemaining);

    /**
     * Performs the action that will occur when a condition is reached. This is done by calling on
     * methods in other classes which need to be updated.
     * 
     * @param livesRemaining the current health/score/etc of the game
     */
    public abstract void act (int livesRemaining);
}
