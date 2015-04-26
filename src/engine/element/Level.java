package engine.element;

import java.util.ArrayList;
import java.util.List;
import annotations.parameter;
import engine.Endable;
import engine.UpdateAndReturnable;


/**
 * Contains all information about a single level within a game. Has methods for checking win/loss
 * conditions, updating the scene, and moving between rounds and waves. The level also contains a
 * pointer to another level which will be called if the win conditions are met - if the loss
 * conditions are met, a generic "Game Over" is loaded before the same level is reloaded.
 * 
 * @author Bojia Chen
 * @author Qian Wang
 */

public class Level implements UpdateAndReturnable, Endable, Comparable<Level> {

    @parameter(settable = true, playerDisplay = true, defaultValue = "20")
    private Integer lives;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> rounds;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> quantities;
    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private Double sendRate;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> conditions;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0")
    private Integer number;

    private List<Round> myRounds;
    private int myActiveRoundIndex = 0;
    private Round myActiveRound;

    // TODO: Win/Lose Conditions

    public Level () {
        myRounds = new ArrayList<>();
        myActiveRound = myRounds.get(myActiveRoundIndex);
    }

    /**
     * Method called by Player when ready to start next Round
     * 
     * @return True if able to start next round
     */

    public boolean startNextRound () {
        if (myActiveRound.hasEnded()) {
            myActiveRoundIndex++;
        }
        if (hasEnded()) {
            return false;
        }
        else {
            myActiveRound = myRounds.get(myActiveRoundIndex);
            return true;
        }
    }

    @Override
    public boolean hasEnded () {
        return myActiveRoundIndex == myRounds.size();
    }

    @Override
    public List<String> update (int counter) {
        return myActiveRound.update(counter);
    }

    @Override
    public int compareTo (Level other) {
        int thisLevel = this.number;
        int otherLevel = other.number;
        return thisLevel - otherLevel;
    }

}
