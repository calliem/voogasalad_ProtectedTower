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

        List<Integer> delay = new ArrayList<Integer>();
        delay.add(0);
        delay.add(1);
        delay.add(2);
        delay.add(3);
        delay.add(10);
        List<String> unitKeys = new ArrayList<String>();
        unitKeys.add("TestGame_Part1.Enemy");
        unitKeys.add("TestGame_Part2.Enemy");
        unitKeys.add("TestGame_Part3.Enemy");
        unitKeys.add("TestGame_Part4.Enemy");
        unitKeys.add("TestGame_Part5.Enemy");
        unitKeys.add("TestGame_Part6.Enemy");
        wave.put(InstanceManager.NAME_KEY, "TestWave");
        wave.put(InstanceManager.PART_TYPE_KEY, "Wave");
        wave.put(InstanceManager.PART_KEY_KEY, "TestGame_Part7.Wave");
        return wave;
    }
}
