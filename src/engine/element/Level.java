package engine.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import annotations.parameter;
import engine.Endable;
import engine.Reflectable;
import engine.UpdateAndReturnable;
import engine.factories.GameElementFactory;


/**
 * Contains all information about a single level within a game. Has methods for checking win/loss
 * conditions, updating the scene, and moving between rounds and waves. The level also contains a
 * pointer to another level which will be called if the win conditions are met - if the loss
 * conditions are met, a generic "Game Over" is loaded before the same level is reloaded.
 * 
 * @author Bojia Chen
 * @author Qian Wang
 */

public class Level implements UpdateAndReturnable, Endable, Reflectable, Comparable<Level> {

    private static final String PARAMETER_ROUND = "Round";

    @parameter(settable = true, playerDisplay = true, defaultValue = "20")
    private Integer myLives;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> myRounds;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> myConditions;
    /**
     * Identifies the level number. Used to determine order.
     */
    @parameter(settable = true, playerDisplay = true, defaultValue = "0")
    private Integer myNumber;

    private int myActiveRoundIndex = 0;
    private Round myActiveRound;
    private GameElementFactory myGameElementFactory;

    // TODO: Win/Lose Conditions

    public Level () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        myLives = (Integer) parameters.get("myLives");
        myRounds = (List<String>) parameters.get("myRounds");
        myConditions = (List<String>) parameters.get("myConditions");
        myNumber = (Integer) parameters.get("myNumber");
    }

    public void setGameElementFactory (GameElementFactory factory) {
        myGameElementFactory = factory;
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
            setActiveRound();
            return true;
        }
    }

    private void setActiveRound () {
        String roundGUID = myRounds.get(myActiveRoundIndex);
        myActiveRound = (Round) myGameElementFactory.getGameElement(PARAMETER_ROUND, roundGUID);
    }

    @Override
    public boolean hasEnded () {
        return myActiveRoundIndex == myRounds.size();
    }

    @Override
    public Map<Object, List<String>> update (int counter) {
        return myActiveRound.update(counter);
    }

    @Override
    public int compareTo (Level other) {
        int thisLevel = this.myNumber;
        int otherLevel = other.myNumber;
        return thisLevel - otherLevel;
    }

}
