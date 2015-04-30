package authoringEnvironment.objects;

import imageselector.util.ScaleImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import util.misc.SetHandler;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.NoImageFoundException;
import authoringEnvironment.Variables;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.map.MapWorkspace;


/**
 * This class defines all the features of the sidebar for the level editor. The user can select
 * options here that will allow him/her to place or updates objects on the map in level editor.
 * 
 * @author Callie Mao
 *
 */

public class LevelSidebar extends Sidebar{

    private Editor myMapEditor;
    private Controller myController;

    private static final int LISTVIEW_HEIGHT = 200;
    private FileChooser fileChooser;
    private VBox roundOrder;
    private ObservableList<GameObject> myMapList;
    private UpdatableDisplay mapDisplay;
    private VBox roundOrderAccordian;
    private ScrollPane mapDisplayScrollPane;
    private StackPane mapDisplayStackPane;
    private List<RoundDisplay> rounds;
    private static final int PADDING = 10;
    public static final String ROUNDS_KEY = "Rounds";
    public static final String GAMEMAP_KEY = "GameMap";
    private String myMapKey;
    private String myKey;
    private String myName;
    private static final int ROW_SIZE = 3;
    
    public LevelSidebar(ResourceBundle resources,
                         ObservableList<GameObject> observableList,
                         MapWorkspace mapWorkspace, Controller c) {
        super(resources, observableList, mapWorkspace);
        myMapList = FXCollections.observableList(observableList);
        mapDisplay = new LevelUpdatableDisplay(this, c, Variables.PARTNAME_MAP, UPDATABLEDISPLAY_ELEMENTS, Variables.THUMBNAIL_SIZE_MULTIPLIER, mapWorkspace); // remove default values TODO

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

        part.put(InstanceManager.NAME_KEY, myName);
        part.put(InstanceManager.PART_TYPE_KEY, "Level");
        part.put(GAMEMAP_KEY, myMapKey);
        part.put(ROUNDS_KEY, roundKeys);

        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION))
                myKey = myController.addPartToGame(part);
            else
                myKey = myController.addPartToGame(myKey, part);
        }
        catch (MissingInformationException e) {
            e.printStackTrace();
        }
        getMapWorkspace().displayMessage("Save Successful", Color.ORCHID);
    }
    
    public void setKey(String key){
        myMapKey = key;
    }

    protected void setContent (GridPane container) {
        container.getChildren().add(nameLevel());

    }
    
    private HBox nameLevel(){
        HBox box = new HBox(PADDING);
        box.getChildren().add(new Label("Level Name:"));
        TextField t = new TextField();
        t.setPromptText("Name your level");
        box.getChildren().add(t);
        Button b = new Button ("Save");
        b.setOnAction(e-> {myName = t.getText();});
        box.getChildren().add(b);
        return box;
    }
}
