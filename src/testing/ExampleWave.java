package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleWave {

    public static final Map<String, Object> WAVE_1 = generateExampleWave();

    private static Map<String, Object> generateExampleWave () {
        Map<String, Object> wave = new HashMap<String, Object>();

        List<Double> mySendTimes = new ArrayList<Double>();
        mySendTimes.add(0.0);
        mySendTimes.add(1.0);
        mySendTimes.add(2.0);
        mySendTimes.add(3.0);
        mySendTimes.add(10.0);
        List<String> myEnemies = new ArrayList<String>();
        myEnemies.add("Part0.Enemy");
        myEnemies.add("Part1.Enemy");
        myEnemies.add("Part2.Enemy");
        myEnemies.add("Part1.Enemy");
        myEnemies.add("Part2.Enemy");
        
        wave.put(InstanceManager.NAME_KEY, "TestWave");
        wave.put(InstanceManager.PART_TYPE_KEY, "Wave");
        wave.put(InstanceManager.PART_KEY_KEY, "TestWavePart0.Wave");
        wave.put("mySendTimes",mySendTimes);
        wave.put("myEnemies",myEnemies);
        return wave;
    }
}
