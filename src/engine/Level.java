package engine;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains all information about a single level within a game. Has methods for checking win/loss
 * conditions, updating the scene, and moving between rounds and waves. The level also contains a
 * pointer to another level which will be called if the win conditions are met - if the loss
 * conditions are met, a generic "Game Over" is loaded before the same level is reloaded.
 * 
 * @author Bojia Chen
 * @author Qian Wang
 */
public class Level implements Updateable, Endable {
    private List<Round> myRounds;
    private double myHealth;
    private int myLives;

    // TODO: Win/Lose Conditions

    public Level () {
        myRounds = new ArrayList<>();
    }

    @Override
    public boolean hasEnded () {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub

    }

}
