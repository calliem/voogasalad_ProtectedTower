package engine.element;

import java.util.ArrayList;
import java.util.List;
import engine.Endable;
import engine.UpdateAndReturnable;
import engine.element.sprites.Enemy;


/**
 * This class holds a round, which contains multiple waves of enemies. The round may send one wave
 * or multiple waves spaced out.
 * 
 * @author Qian Wang
 *
 */
public class Round extends GameElement implements UpdateAndReturnable, Endable {
    private List<Wave> myWaves;
    private int myDelay; // defines how many frames to wait between sending waves
    private int myCurrentDelay = 0;
    private int myActiveWaveIndex = 0;

    public Round () {
        myWaves = new ArrayList<>();
    }

    @Override
    public boolean hasEnded () {
        if (myActiveWaveIndex >= myWaves.size()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Enemy> update (int counter) {
        if (myCurrentDelay == 0) {
            return myWaves.get(myActiveWaveIndex).update(counter);
        }
        if (myWaves.get(myActiveWaveIndex).hasEnded()) {
            myCurrentDelay++;
            if (myCurrentDelay > myDelay) {
                myCurrentDelay = 0;
                myActiveWaveIndex++;
            }
        }
        return null;
    }

}
