package engine.element;

import java.util.ArrayList;
import java.util.List;
import annotations.parameter;
import engine.Endable;
import engine.UpdateAndReturnable;
import engine.element.sprites.Projectile;
import engine.element.sprites.Sprite;


/**
 * Contains all information about a single level within a game. Has methods for checking win/loss
 * conditions, updating the scene, and moving between rounds and waves. The level also contains a
 * pointer to another level which will be called if the win conditions are met - if the loss
 * conditions are met, a generic "Game Over" is loaded before the same level is reloaded.
 * 
 * @author Bojia Chen
 * @author Qian Wang
 */

public class Level extends GameElement implements UpdateAndReturnable, Endable, Comparable<Level> {

    private static final String PARAMETER_NUMBER = "Number";
    @parameter(settable=true,playerDisplay=true)
    private Double HP = 100.0;
    @parameter(settable=true,playerDisplay=true)
    private List<String> rounds = null;
    @parameter(settable=true,playerDisplay=true)
    private List<String> quantities = null;
    @parameter(settable=true,playerDisplay=true)
    private Double sendRate = 1.0;
    @parameter(settable=true,playerDisplay=true)
    private List<String> conditions = null;
    @parameter(settable=true,playerDisplay=true)
    private Integer number = 0;
    private List<Round> myRounds;
    private double myHealth;
    private int myLives;
    private int myActiveRoundIndex = 0;
    private Round myActiveRound;

    // TODO: Win/Lose Conditions

    public Level () {
        myRounds = new ArrayList<>();
        myActiveRound = myRounds.get(myActiveRoundIndex);
    }

    /**
     * Method called by Player when ready to start next Round
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
    public int compareTo (Level o) {
        int thisLevel = (int) this.getParameter(PARAMETER_NUMBER);
        int otherLevel = (int) o.getParameter(PARAMETER_NUMBER);
        return thisLevel - otherLevel;
    }

}
