package engine.element;

import java.util.ArrayList;
import java.util.List;
import engine.Endable;
import engine.UpdateAndReturnable;
import engine.element.sprites.Enemy;


/**
 * This class contains a wave, which is a set of enemies who are sent out from one spawn point. The
 * enemies may be sent all at once, randomly, or spaced evenly.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Wave extends GameElement implements UpdateAndReturnable, Endable {
    private List<Enemy> myEnemies;
    private double mySendRate;
    private int myNumSent = 0;
    private int timer;

    public Wave () {
        myEnemies = new ArrayList<>();
        timer = 0;
    }

    @Override
    public boolean hasEnded () {
        if (myNumSent >= myEnemies.size()) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> update (int counter) {
        if (timer++ == mySendRate) {
            myNumSent++;
            timer = 0;
            // TODO return (list of) enemy GUIDs
        }
        return null; // No enemies to return
    }

}
