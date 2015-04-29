package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import authoringEnvironment.Controller;
import authoringEnvironment.DataFormatException;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.ProjectReader;

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

    public WaveStrip (String type, String componentName, Controller c) {
        super(type, componentName, c);
        myController = c;
    }

    @Override
    protected void addAtLeftOfRow () {        
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

        List<Object> data = new ArrayList<Object>();
        data.add(partFileNames);
        data.add(times);
        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION))
                myKey = myController.addPartToGame(WAVE, componentName,
                                                   ProjectReader.getParamsNoTypeOrName(WAVE), data);
            else
                myKey =
                        myController.addPartToGame(myKey, WAVE, componentName,
                                                   ProjectReader.getParamsNoTypeOrName(WAVE), data);
        }
        catch (MissingInformationException | DataFormatException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }

}
