package engine.element;

import java.util.ArrayList;
import java.util.List;
import engine.Endable;
import engine.UpdateAndReturnable;


/**
 * This class holds a round, which contains multiple waves of enemies. The round may send one wave
 * or multiple waves spaced out.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Round extends GameElement implements UpdateAndReturnable, Endable {
    private List<Wave> myWaves;
    private int myMaxWaveDelay; // defines how many frames to wait between sending waves
    private int myWaveDelay = 0;
    private int myActiveWaveIndex = 0;
    private Wave myActiveWave;

    public Round () {
        myWaves = new ArrayList<>();
        myActiveWave = myWaves.get(myActiveWaveIndex);
    }

    @Override
    public boolean hasEnded () {
        return myActiveWaveIndex == myWaves.size();
    }

    @Override
    public List<String> update (int counter) {
        if (myWaveDelay == myMaxWaveDelay && !hasEnded()) {
            myWaveDelay = 0;
            myActiveWave = myWaves.get(++myActiveWaveIndex);
        }
        if (myActiveWave.hasEnded()) {
            myWaveDelay++;
        }
        else {
            return myActiveWave.update(counter);
        }
        return null;
    }

}
