package engine.element;

import java.util.ArrayList;
import java.util.List;
import engine.Endable;
import engine.UpdateAndReturnable;


/**
 * This class contains a wave, which is a set of enemies who are sent out from one spawn point. The
 * enemies may be sent all at once, randomly, or spaced evenly.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Wave extends GameElement implements UpdateAndReturnable, Endable {
    private List<List<String>> myEnemies;
    private double mySendRate;
    private int myEnemyIndex = 0;
    private int myTimer = 0;

    public Wave () {
        myEnemies = new ArrayList<>();
    }

    @Override
    public boolean hasEnded () {
        return myEnemyIndex == myEnemies.size();
    }

    @Override
    public List<String> update (int counter) {
        if (++myTimer == mySendRate && !hasEnded()) {
            myTimer = 0;
            return myEnemies.get(++myEnemyIndex);
        }
        return null; // No enemies to return
    }

}
