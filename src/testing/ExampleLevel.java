package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleLevel {

    public static final Map<String, Object> LEVEL = generateExampleLevel();

    private static Map<String, Object> generateExampleLevel () {
        Map<String, Object> level = new HashMap<String, Object>();

        Integer myLives = 20;
        List<String> myRounds = new ArrayList<String>();
        List<String> myConditions = new ArrayList<String>();
        Integer myNumber = 0;
        
        myRounds.add("DesktopTestRound_Part0.Round");
        
        myConditions.add("testTODOPleaseRemove");
        
        
        level.put(InstanceManager.NAME_KEY, "DesktopTestlevel");
        level.put(InstanceManager.PART_TYPE_KEY, "level");
        level.put(InstanceManager.PART_KEY_KEY, "DesktopTestlevel_Part0.Level");
        level.put("myLives", myLives);
        level.put("myRounds",myRounds);
        level.put("myConditions",myConditions);
        level.put("myNumber", myNumber);
        return level;
    }
}
