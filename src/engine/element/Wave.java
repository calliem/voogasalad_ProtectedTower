package engine.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import annotations.parameter;
import engine.Endable;
import engine.Reflectable;
import engine.UpdateAndReturnable;


/**
 * This class contains a wave, which is a set of enemies who are sent out from one spawn point. The
 * enemies may be sent all at once, randomly, or spaced evenly.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Wave implements UpdateAndReturnable, Endable, Reflectable {

    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private List<String> myEnemies;
    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private List<Double> mySendTimes;

    private int myEnemyIndex = 0;
    private int myTimer = 0;
    private String myPath;

    public Wave () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        // TODO: make sure that myEnemies and mySendTimes are same size
        myEnemies = (List<String>) parameters.get("myEnemies");
        mySendTimes = (List<Double>) parameters.get("mySendTimes");

    }

    public void setPath (String pathGUID) {
        myPath = pathGUID;
    }

    @Override
    public boolean hasEnded () {
        return myEnemyIndex == myEnemies.size();
    }

    @Override
    public Map<Object, List<String>> update (int counter) {
        Map<Object, List<String>> tempReturnMap = new HashMap<>();

        List<String> enemiesToReturn = new ArrayList<>();
        while (!hasEnded() && myTimer == mySendTimes.get(myEnemyIndex) ) {
            enemiesToReturn.add(myEnemies.get(myEnemyIndex++));
        }
        tempReturnMap.put(myPath, enemiesToReturn);

        myTimer++;
        return tempReturnMap;
    }

}
