package engine.conditions;

/**
 * This class checks if a player wins after defeating all waves of enemies.
 * 
 * @author Qian Wang
 *
 */
public class FinishWinCondition extends Condition {

    @Override
    public boolean checkCondition (int livesRemaining) {
        return livesRemaining > 0;
    }

    @Override
    public void act (int livesRemaining) {
        if (checkCondition(livesRemaining)) {
            // TODO end game
        }
    }
}
