package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleWave {

    public static final Map<String,Map<String, Object>> WAVE_1 = generateExampleWave();

    public static Map<String, Map<String, Object>> generateExampleWave () {
        Map<String, Map<String,Object>> part = new HashMap<String,Map<String,Object>>();
        Map<String, Object> wave = new HashMap<String, Object>();

        List<Double> mySendTimes = new ArrayList<Double>();
        mySendTimes.add(0.0);
        mySendTimes.add(10.0);
        mySendTimes.add(20.0);
        mySendTimes.add(30.0);
        mySendTimes.add(40.0);
        mySendTimes.add(50.0);
        mySendTimes.add(60.0);
        mySendTimes.add(70.0);
        mySendTimes.add(80.0);
        mySendTimes.add(90.0);
        mySendTimes.add(100.0);
        List<String> myEnemies = new ArrayList<String>();
        myEnemies.add("ExampleGame_Part0.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        myEnemies.add("ExampleGame_Part0.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        myEnemies.add("ExampleGame_Part0.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        myEnemies.add("ExampleGame_Part1.Enemy");
        myEnemies.add("ExampleGame_Part2.Enemy");
        
        wave.put(InstanceManager.NAME_KEY, "TestWave");
        wave.put(InstanceManager.PART_TYPE_KEY, "Wave");
        wave.put(InstanceManager.PART_KEY_KEY, "TestWavePart0.Wave");
        wave.put("mySendTimes",mySendTimes);
        wave.put("myEnemies",myEnemies);
        part.put("TestWavePart0.Wave", wave);
        return part;
    }
}
