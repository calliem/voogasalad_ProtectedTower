package authoringEnvironment.objects;

import java.util.List;
import javafx.collections.ListChangeListener;
import authoringEnvironment.Controller;

/**
 * Extends FlowEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public class RoundStrip extends FlowStrip {
    private Controller myController;
    private UpdatableDisplay mapDisplay;

    public RoundStrip (String type, String componentName, Controller c) {
        super(type, componentName, c);
        myController = c;
    }

    @Override
    protected void addAtLeftOfRow () {
//        mapDisplay = new LevelUpdatableDisplay(maps, 3, mapWorkspace); // remove default values TODO
//
//        maps.addListener(new ListChangeListener<GameObject>() {
//            @Override
//            public void onChanged (javafx.collections.ListChangeListener.Change<? extends GameObject> change) {
//                mapDisplay.updateDisplay((List<GameObject>) change.getList());
//            }
//        });
    }

    @Override
    protected void saveData (String componentName) {
        // TODO Auto-generated method stub
        
    }


}
