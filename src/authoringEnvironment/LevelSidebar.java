package authoringEnvironment;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;


/**
 * This class defines all the features of the sidebar for the level editor. The user can select
 * options here that will allow him/her to place or updates objects on the map in level editor.
 * 
 * @author Callie Mao
 *
 */

public class LevelSidebar extends Sidebar {

    private Editor myMapEditor;
    private ObservableList<GameObject> myMapList;
    private UpdatableDisplay mapDisplay;
    // private ObservableList<Node> myRounds;

    private static final int LISTVIEW_HEIGHT = 200;

    public LevelSidebar (ResourceBundle resources,
                         ObservableList<GameObject> maps,
                         MapWorkspace mapWorkspace) {
        super(resources, maps, mapWorkspace);
        myMapList = FXCollections.observableList(maps);
        maps.addListener(new ListChangeListener<GameObject>() {
            @Override
            public void onChanged (javafx.collections.ListChangeListener.Change<? extends GameObject> change) {
                System.out.println("maplist changed!");
                mapDisplay.updateDisplay((List<GameObject>) change.getList());
            }
        });

        System.out.println("MAP CHANGE LISTENER IS CREATED");
        createMapSettings();

        // setOnMouseClicked(e -> {System.out.println(myMapList);});
        // setOnKeyPressed(e -> {if (keyEvent.getCode() == KeyCode.ENTER));

        // e -> updateMapDisplay());

        setOnKeyPressed(e -> handleKeyInput(e));

        /*
         * new ListChangeListener<Item>(){
         * 
         * }
         */
    }

    @Override
    protected void createMapSettings () {
        VBox selectMap = createTitleText(getResources().getString("SelectMap"));
        createTitleText(getResources().getString("PlaceWave"));
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

        mapDisplay = new MapUpdatableDisplay(myMapList, 3, getMapWorkspace());
        // temp.getChildren().add(mapDisplay);
        selectMap.getChildren().add(mapDisplay);

        createTitleText(getResources().getString("RoundOrder"));
        // getChildren().add(createListView(myRounds, LISTVIEW_HEIGHT));
    }

    private void updateMapDisplay () {
        // myMapEditor = Controller.getEditor(Controller.MAPS); //update map editor from the
        // controller
        // myMapList = FXCollections.observableArrayList(myMapEditor.getObjects());

    }

    private  void handleKeyInput (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.RIGHT) {
            System.out.println(myMapList);
        }
}
}
