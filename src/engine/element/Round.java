package engine.element;

import java.util.ArrayList;
import java.util.List;
import engine.Endable;
import engine.GameElement;
import engine.Updateable;


/**
 * This class holds a round, which contains multiple waves of enemies. The round may send one wave
 * or multiple waves spaced out.
 * 
 * @author Qian Wang
 *
 */
public class Round extends GameElement implements Updateable, Endable {
    private List<Wave> myWaves;
    private int myDelay; // defines how many frames to wait between sending waves
    private int myCurrentDelay = 0;
    private int myActiveWaveIndex = 0;

    public Round () {
        myWaves = new ArrayList<>();
    }

    @Override
    public boolean hasEnded () {
        if (myActiveWaveIndex >= myWaves.size()) { return true; }
        return false;
    }

    @Override
    public void update (int counter) {
        if (myCurrentDelay == 0) {
            myWaves.get(myActiveWaveIndex).update(counter);
        }
        if (myWaves.get(myActiveWaveIndex).hasEnded()) {
            myCurrentDelay++;
            if (myCurrentDelay > myDelay) {
                myCurrentDelay = 0;
                myActiveWaveIndex++;
            }
        }
    }

}
