package engine;

import java.util.ArrayList;
import engine.sprites.Enemy;


/**
 * This class contains a wave, which is a set of enemies who are sent out from one spawn point. The
 * enemies may be sent all at once, randomly, or spaced evenly.
 * 
 * @author Qian Wang
 *
 */
public class Wave implements Updateable, Endable {
    private ArrayList<Enemy> myEnemies;
    private double mySendRate;
    private int myNumSent = 0;

    @Override
    public boolean hasEnded () {
        if (myNumSent >= myEnemies.size()) { return true; }
        return false;
    }

    @Override
    public void update (int counter) {
        if (counter % mySendRate == 0) {
            // TODO send enemy
            myNumSent++;
        }
    }

}
