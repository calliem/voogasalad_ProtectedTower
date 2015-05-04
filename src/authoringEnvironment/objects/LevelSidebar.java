package authoringEnvironment.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.Variables;
import authoringEnvironment.map.MapWorkspace;


/**
 * This class defines all the features of the sidebar for the level editor. The user can select
 * options here that will allow him/her to place or updates objects on the map in level editor.
 * 
 * @author Callie Mao
 *
 */

public class LevelSidebar extends Sidebar {

    private Controller myController;

    private FileChooser fileChooser;
    private VBox roundOrder;
    private UpdatableDisplay mapDisplay;
    private VBox roundOrderAccordian;
    private List<RoundDisplay> rounds;
    private static final int PADDING = 10;
    public static final String ROUNDS_KEY = "Rounds";
    public static final String GAMEMAP_KEY = "GameMap";
    private String myMapKey;
    private String myKey;
    private String myName;

    private ObservableList<GameObject> myMapList;

    public LevelSidebar (ResourceBundle resources,
                         ObservableList<GameObject> observableList,
                         MapWorkspace mapWorkspace, Controller c) {
        super(resources, observableList, mapWorkspace);
        myMapList = FXCollections.observableList(observableList);

        mapDisplay =
                new LevelUpdatableDisplay(this, c, InstanceManager.GAMEMAP_PARTNAME,
                                          UPDATABLEDISPLAY_ELEMENTS,
                                          Variables.THUMBNAIL_SIZE_MULTIPLIER, mapWorkspace);

        observableList.addListener(new ListChangeListener<GameObject>() {
            @Override
            public void onChanged (javafx.collections.ListChangeListener.Change<? extends GameObject> change) {
                mapDisplay.updateDisplay();
            }
        });

        myName = "UnnamedLevel";
        myController = c;
        myKey = Controller.KEY_BEFORE_CREATION;
        rounds = new ArrayList<RoundDisplay>();
        fileChooser = new FileChooser();
        System.out.println("mycontroller in choosing: " + myController);
        fileChooser.setInitialDirectory(myController.getDirectoryToPartFolder("Round"));

        createMapSettings();
    }

    protected void createMapSettings () {
        VBox selectMap = createAccordionTitleText(getResources().getString("SelectMap"));
        selectMap.getChildren().add(mapDisplay);
        roundOrderAccordian = createAccordionTitleText("RoundOrder");
        roundOrder = new VBox(PADDING);
        roundOrder.getChildren().add(selectRoundAndSaveButtons());
        roundOrderAccordian.getChildren().add(roundOrder);
    }

    private VBox selectRoundAndSaveButtons () {
        VBox selectAndSave = new VBox(PADDING);
        Button select = new Button("Select Round");
        select.setOnAction(e -> selectRound(selectAndSave));
        Button save = new Button("Save Level");
        save.setOnAction(e -> saveLevel());
        selectAndSave.getChildren().addAll(select, save);
        return selectAndSave;
    }

    private void selectRound (VBox buttons) {
        File file = fileChooser.showOpenDialog(null);
        Map<String, Object> roundData = myController.loadPart(file
                .getAbsolutePath());
        roundOrder.getChildren().remove(buttons);
        RoundDisplay toAdd = new RoundDisplay((String) roundData
                .get(InstanceManager.NAME_KEY), (String) roundData
                .get(InstanceManager.PART_KEY_KEY), roundOrder, rounds, PADDING);
        roundOrder.getChildren().add(toAdd);
        rounds.add(toAdd);

        roundOrder.getChildren().add(buttons);
    }

    private void saveLevel () {
        Map<String, Object> part = new HashMap<String, Object>();
        List<String> roundKeys = new ArrayList<String>();
        for (RoundDisplay rd : rounds) {
            roundKeys.add(rd.getRoundKey());
        }

        part.put(GAMEMAP_KEY, myMapKey);
        part.put(ROUNDS_KEY, roundKeys);

        try {
            myKey = myController.addPartToGame("Level", myName, myKey, part);
        }
        catch (MissingInformationException e) {
            e.printStackTrace();
        }
        getMapWorkspace().displayMessage(getResources().getString("SaveSuccessful"), Color.ORCHID);
    }

    public void setKey (String key) {
        myMapKey = key;
    }

    protected void setContent (GridPane container) {
        container.getChildren().add(nameLevel());

    }

    private HBox nameLevel () {
        HBox box = new HBox(PADDING);
        box.getChildren().add(new Label("Level Name:"));
        TextField t = new TextField();
        t.setPromptText("Name your level");
        box.getChildren().add(t);
        Button b = new Button("Save");
        b.setOnAction(e -> {
            myName = t.getText();
        });
        box.getChildren().add(b);
        return box;
    }
}
