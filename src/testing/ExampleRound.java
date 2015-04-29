package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleRound {
    public static final Map<String,Map<String, Object>> ROUND = generateExampleRound();

    private static Map<String, Map<String, Object>> generateExampleRound () {
        Map<String,Map<String,Object>> part = new HashMap<String,Map<String,Object>>();
        
        Map<String, Object> round = new HashMap<String, Object>();

        List<String> myWaves = new ArrayList<String>();
        List<Double> mySendTimes = new ArrayList<Double>();
        List<String> myWavePaths = new ArrayList<String>();
        
        myWaves.add("TestWavePart0.Wave");
        myWaves.add("TestWavePart0.Wave");
        
        mySendTimes.add(0.0);
        mySendTimes.add(60.0);
        
        myWavePaths.add("testTODOPleaseRemove");
        
        

        round.put(InstanceManager.NAME_KEY, "DesktopTestRound");
        round.put(InstanceManager.PART_TYPE_KEY, "Round");
        round.put(InstanceManager.PART_KEY_KEY, "DesktopTestRound_Part0.Round");
        round.put("myWaves", myWaves);
        round.put("mySendTimes", mySendTimes);
        round.put("myWavePaths", myWavePaths);
        part.put("DesktopTestRound_Part0.Round", round);
        return part;
    }
}
