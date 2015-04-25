package authoringEnvironment.objects;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.Variables;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.LevelUpdatableDisplay;
import authoringEnvironment.objects.UpdatableDisplay;



/**
 * This class defines all the features of the sidebar for the level editor. The user can select
 * options here that will allow him/her to place or updates objects on the map in level editor.
 * 
 * @author Callie Mao
 *
 */

public class LevelSidebar extends Sidebar {

    private Editor myMapEditor;
    private ObservableList<String> myMapList;
    private UpdatableDisplay mapDisplay;

    // private ObservableList<Node> myRounds;

    private static final int LISTVIEW_HEIGHT = 200;

    public LevelSidebar (Controller c, ResourceBundle resources,
                         ObservableList<String> maps,
                         MapWorkspace mapWorkspace) {
        super(c, resources, maps, mapWorkspace);
        myMapList = FXCollections.observableList(maps);
        mapDisplay = new LevelUpdatableDisplay(c, Variables.PARTNAME_MAP, UPDATABLEDISPLAY_ELEMENTS, mapWorkspace); // remove default values TODO

        maps.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged (javafx.collections.ListChangeListener.Change<? extends String> change) {
                mapDisplay.updateDisplay((List<String>) change.getList());
            }
        });

        createMapSettings();
    }

    @Override
    protected void createMapSettings () {
        VBox selectMap = createAccordionTitleText(getResources().getString("SelectMap"));
        createAccordionTitleText(getResources().getString("PlaceWave"));
        Button createRound = new Button(getResources().getString("CreateRound"));

        // Editor mapEditor = Controller.getEditor(Controller.MAPS);

        // ListView mapList = createListView(FXCollections.observableArrayList(myMapList),
        // LISTVIEW_HEIGHT);
        // myRounds = FXCollections.observableArrayList();
        /*
         * mapList.setCellFactory(new Callback<ListView<String>,
         * ListCell<String>>() {
         * 
         * @Override
         * public ListCell<String> call(ListView<String> list) {
         * return new ColorRectCell();
         * }
         * }
         * );
         */

        // getChildren().add(mapList);

     //   mapDisplay =
       //         new LevelUpdatableDisplay(myMapList, UPDATABLEDISPLAY_ELEMENTS, getMapWorkspace());
        // temp.getChildren().add(mapDisplay);
        selectMap.getChildren().add(mapDisplay);

        createAccordionTitleText(getResources().getString("RoundOrder"));
        // getChildren().add(createListView(myRounds, LISTVIEW_HEIGHT));
    }

    protected void setContent (GridPane container) {
        container.getChildren().add(new Rectangle(20, 20, Color.WHITE));

    }
}
