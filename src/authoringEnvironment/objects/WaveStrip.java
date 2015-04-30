package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;
import authoringEnvironment.Controller;


/**
 * Extends FlowEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */

public class WaveStrip extends FlowStrip {
    private static final String WAVE = "Wave";
    private static final String ENEMIES_KEY = "Enemies";
    private static final String TIMES_KEY = "Times";

    public WaveStrip (String type, String componentName, Controller c) {
        super(type, componentName, c);
    }

    @Override
    protected void addAtLeftOfRow (HBox content) {
    }

    @Override
    protected void saveData (String componentName) {
        List<String> partKeyNames = new ArrayList<String>();
        List<Double> delays = new ArrayList<Double>();

        for (FlowView unit : myComponents) {
            partKeyNames.addAll(unit.getFileNames());
            delays.addAll(unit.getDelays());
        }
        
        List<Double> times = getTimesFromZero(partKeyNames, delays);
        
        List<String> params = new ArrayList<String>();
        params.add(ENEMIES_KEY);
        params.add(TIMES_KEY);
        List<Object> data = new ArrayList<Object>();
        data.add(partKeyNames);
        data.add(times);
        //System.out.println("times in wave editor: " + times);
        //System.out.println("enemies in wave editor: " + partFileNames);
        
        saveToGame(WAVE, componentName, params, data);

    }
}
