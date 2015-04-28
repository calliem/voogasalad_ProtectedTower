package engine.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import annotations.parameter;
import engine.Endable;
import engine.UpdateAndReturnable;


/**
 * This class contains a wave, which is a set of enemies who are sent out from one spawn point. The
 * enemies may be sent all at once, randomly, or spaced evenly.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Wave implements UpdateAndReturnable, Endable {

    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private List<List<String>> myEnemies;
    @parameter(settable = true, playerDisplay = false, defaultValue = "1.0")
    private double mySendRate;

    private int myEnemyIndex = 0;
    private int myTimer = 0;

    public Wave () {
        myEnemies = new ArrayList<>();
    }

    @Override
    public boolean hasEnded () {
        return myEnemyIndex == myEnemies.size();
    }

    @Override
    public Map<Object, List<String>> update (int counter) {
        Map<Object, List<String>> tempReturnMap = null;
        if (++myTimer == mySendRate && !hasEnded()) {
            myTimer = 0;
            tempReturnMap = new HashMap<>();
            //TODO: be aware of pathID and pass that up instead of null
            tempReturnMap.put(null, myEnemies.get(myEnemyIndex++));
        }
        return tempReturnMap; // Null if no enemies to return
    }

}
