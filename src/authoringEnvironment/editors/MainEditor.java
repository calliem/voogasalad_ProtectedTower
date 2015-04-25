package authoringEnvironment.editors;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;


/**
 * Displays the general layout for MainEditor classes/subclasses (ie. GameMap,
 * WaveEditor) consisting of a sidebar and a generic map. Users will be able to
 * interact with both the map and the sidebar in subsequent subclasses.
 * 
 * @author Callie Mao
 */

public abstract class MainEditor extends Editor {

    private GridPane myPane;
    private MapWorkspace myMapWorkspace;
    private ObservableList<String> myMaps;

    private static final double SIDEBAR_WIDTH_MULTIPLIER = .25;
    private static final double MAP_WIDTH_MULTIPLIER = .75; // THIS IS REPLICATED
    // WITH THOSE
    // VARIABLES IN MAP
    // WORKSPACE
    private static final double MAP_HEIGHT_PERCENT = 100; // THIS IS REPLICATED
    private static final String DARK_TAB_CSS = "-fx-base: #3c3c3c;";

    // WITH THOSE
    // VARIABLES IN MAP
    // WORKSPACE

    public MainEditor (Controller c, String name) {
        super(c, name);
        this.setStyle(DARK_TAB_CSS);
        myMaps = (ObservableList<String>) c.getKeysForPartType(name);
    }

    /**
     * Creates a sidebar and general map layout to be utilized by subclasses
     */
    // TODO: return groupor return a parent so that I can directly return a
    // gridpane here?
    @Override
    protected Group configureUI () {
        Group visuals = new Group();
        createGridPane();
        myMapWorkspace = new MapWorkspace();
        myPane.add(myMapWorkspace, 0, 0);
        visuals.getChildren().add(myPane);
        return visuals;
    }

    private void createGridPane () {
        myPane = new GridPane();
        setGridPaneConstraints(myPane);
    }

    public MapWorkspace getMapWorkspace () {
        return myMapWorkspace;
    }

    public TileMap getActiveMap () {
        return myMapWorkspace.getActiveMap();
    }

    public ObservableList<String> getMaps () {
        return myMaps;
    }

    private void setGridPaneConstraints (GridPane pane) {
        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(MAP_HEIGHT_PERCENT);
        pane.getRowConstraints().add(row0);
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPrefWidth(AuthoringEnvironment.getEnvironmentWidth()
                          * MAP_WIDTH_MULTIPLIER);
        ColumnConstraints col1 = new ColumnConstraints();
        // col1.setPrefWidth((MainEnvironment.getEnvironmentWidth() *
        // SIDEBAR_WIDTH_MULTIPLIER); TODO: add this back
        pane.getColumnConstraints().add(col0);
        // pane.getColumnConstraints().add(col1);
    }

    protected GridPane getPane () {
        return myPane;
    }

        // update in level editor will update the maps and paths and other stuff
        // update in map editor will update the paths and tiles
        //

     /*   System.out.println("====================leveleditor update method called=================");
        MapEditor mapEditor = (MapEditor) Controller.getEditor("Maps"); //test 
        if (!getMapWorkspace().getChildren().contains(mapEditor.getActiveMap().getMap())) {
            System.out.println("stored Maps map workspace: ");
            mapEditor.getMapWorkspace();
            // .getChildren().remove(mapEditor.getActiveMap().getMap());
            System.out.println("this specific map workspace: ");
            getMapWorkspace();
            System.out.println("--");
            // .getChildren().add(mapEditor.getActiveMap().getMap());
            Text text = new Text("test");
            getMapWorkspace().getChildren().add(text);

            // mapEditor.getMapWorkspace().getChildren().remove(mapEditor.getActiveMap().getMap());
            getMapWorkspace().getChildren().add(mapEditor.getActiveMap().getMap());
        }*/

    }
    /*
     * public void update(){ MapEditor mapEditor = (MapEditor)
     * Controller.getEditor(Controller.MAPS);
     * if(!getMapWorkspace().getChildren().contains(mapEditor.getActiveMap())){
     * getMapWorkspace().updateWithNewMap(mapEditor.getActiveMap()); } }
     */

