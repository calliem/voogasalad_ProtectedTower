package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;
import authoringEnvironment.Controller;
import authoringEnvironment.DataFormatException;
import authoringEnvironment.MissingInformationException;


/**
 * Extends FlowEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */

public class WaveStrip extends FlowStrip {
    private Controller myController;
    private static final String WAVE = "Wave";
    private static final String ENEMIES_KEY = "Enemies";
    private static final String TIMES_KEY = "Times";

    public WaveStrip (String type, String componentName, Controller c) {
        super(type, componentName, c);
        myController = c;
    }

    @Override
    protected void addAtLeftOfRow (HBox content) {
    }

    @Override
    protected void saveData (String componentName) {
        List<String> partFileNames = new ArrayList<String>();
        List<Double> delays = new ArrayList<Double>();
        List<Double> times = new ArrayList<Double>();
        times.add(0.0);

        for (FlowView unit : myComponents) {
            partFileNames.addAll(unit.getFileNames());
            delays.addAll(unit.getDelays());
        }

        for (Double d : delays) {
            Double all = 0.0;
            for (Double t : times)
                all += t;
            times.add(all + d);
        }
        
        // Get rid of potential last element due to extra arrow/input space
        if (partFileNames.size() != times.size()) {
            times.remove(times.size() - 1);
        }
        
        List<String> params = new ArrayList<String>();
        params.add(ENEMIES_KEY);
        params.add(TIMES_KEY);
        List<Object> data = new ArrayList<Object>();
        data.add(partFileNames);
        data.add(times);
        System.out.println("times in wave ediotr: " + times);
        System.out.println("enemies in wave ediotr: " + partFileNames);
        
        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION)) {
                myKey = myController.addPartToGame(WAVE, componentName,
                                                   params, data);
            }
            else {
                myKey = myController.addPartToGame(myKey, WAVE, componentName,
                                                   params, data);
            }
        }
        catch (MissingInformationException | DataFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
