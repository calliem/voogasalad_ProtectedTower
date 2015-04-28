package engine.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import annotations.parameter;
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
public class Round implements UpdateAndReturnable, Endable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> waves;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> quantities; // TODO: Verify if this parameter still exists
    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private List<Double> sendRate;   // TODO: Change to time?
    @parameter(settable = true, playerDisplay = true, defaultValue = "0")
    private List<Integer> spawnLocation;

    private List<Wave> myWaves;
    private List<Wave> myActiveWaves;
    private int myMaxWaveDelay; // defines how many frames to wait between sending waves
    private int myWaveDelay = 0;
    private int myActiveWaveIndex = 0;
    private int myTimer = 0;
    private Wave myActiveWave;

    public Round () {
        myWaves = new ArrayList<>();
        myActiveWaves = new ArrayList<>();
        myActiveWave = myWaves.get(myActiveWaveIndex);
        //TODO: Make sure that waves, quantities, sendRate, and spawnLocation are same size
    }

    @Override
    public boolean hasEnded () {
        // return myActiveWaveIndex == myWaves.size();
        return myActiveWaveIndex == waves.size() && myActiveWaves.isEmpty();
    }

    @Override
    public Map<Object, List<String>> update (int counter) {
        myTimer++;
        Map<Object, List<String>> tempReturnMap = null;
        while (myWaveDelay == sendRate.get(myActiveWaveIndex) && !hasEnded()) {
            myWaveDelay = 0;
            myActiveWaves.add(myWaves.get(myActiveWaveIndex++));
            // myActiveWave = myWaves.get(++myActiveWaveIndex);
        }
        for (Wave activeWave : myActiveWaves) {
            if (activeWave.hasEnded()) {
                myActiveWaves.remove(activeWave);
            }
            else {
                tempReturnMap = new HashMap<>();
                return myActiveWave.update(counter);
            }
        }

        return tempReturnMap;
    }

}
