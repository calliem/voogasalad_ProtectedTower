package engine.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import annotations.parameter;
import engine.Endable;
import engine.UpdateAndReturnable;
import engine.factories.GameElementFactory;


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
    private List<String> myWaves;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<Double> mySendTimes;
    @parameter(settable = true, playerDisplay = true, defaultValue = "null")
    private List<String> myWavePaths;

    private static final String PARAMETER_WAVE = "Wave";

    private List<Wave> myActiveWaves;
    private GameElementFactory myGameElementFactory;
    private int myCurrentWaveIndex = 0;
    private int myTimer = 0;

    public Round (Map<String,Object> parameters){
        myWaves = (List<String>) parameters.get("myWaves");
        mySendTimes = (List<Double>) parameters.get("mySendTimes");
        myWavePaths = (List<String>) parameters.get("myWavePaths");
        myActiveWaves = new ArrayList<>();
        // TODO: Make sure that waves, quantities, sendRate, and spawnLocation are same size
    }

    public void setGameElementFactory (GameElementFactory factory) {
        myGameElementFactory = factory;
    }

    @Override
    public boolean hasEnded () {
        return myCurrentWaveIndex == myWaves.size() && myActiveWaves.isEmpty();
    }

    @Override
    public Map<Object, List<String>> update (int counter) {
        Map<Object, List<String>> tempReturnMap = new HashMap<>();

        while (myTimer == mySendTimes.get(myCurrentWaveIndex) && !hasEnded()) {
            String waveGUID = myWaves.get(myCurrentWaveIndex);
            String wavePath = myWavePaths.get(myCurrentWaveIndex++);
            Wave waveToAdd = (Wave) myGameElementFactory.getGameElement(PARAMETER_WAVE, waveGUID);
            waveToAdd.setPath(wavePath);
            myActiveWaves.add(waveToAdd);
            // myActiveWave = myWaves.get(++myActiveWaveIndex);
        }

        for (Wave activeWave : myActiveWaves) {
            if (activeWave.hasEnded()) {
                myActiveWaves.remove(activeWave);
            }
            else {
                mergeMaps(activeWave.update(counter), tempReturnMap);
            }
        }

        myTimer++;

        return tempReturnMap;
    }

    /**
     * Helper method for merging the contents of one map into another. Returs immediately if source
     * map is null. Initializes new map if destination map is null.
     * 
     * @param from Source map
     * @param to Destination map
     */
    private void mergeMaps (Map<Object, List<String>> from, Map<Object, List<String>> to) {
        if (from == null) {
            return;
        }
        for (Object obj : from.keySet()) {
            if (to.containsKey(obj)) {
                to.get(obj).addAll(from.get(obj));
            }
            else {
                to.put(obj, from.get(obj));
            }
        }
    }

}
