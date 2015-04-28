package authoringEnvironment.objects;

import javafx.scene.layout.HBox;
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
    protected void addAtLeftOfRow (HBox content) {
        //ObservableList<GameObject> maps = myController.getMaps();
        //variables.parameter
//        ObservableList<String> mapKeys = myController.getKeysForPartType("Maps");
//        maps = myController.get
//        MapWorkspace mapWorkspace = new MapWorkspace();
//        mapDisplay = new LevelUpdatableDisplay(maps, 3, mapWorkspace); 
//
//        maps.addListener(new ListChangeListener<GameObject>() {
//            @Override
//            public void onChanged (javafx.collections.ListChangeListener.Change<? extends GameObject> change) {
//                mapDisplay.updateDisplay((List<GameObject>) change.getList());
//            }
//        });
//        content.getChildren().add(mapDisplay);
    }

    @Override
    protected void saveData (String componentName) {
        // TODO Auto-generated method stub

    }

}
