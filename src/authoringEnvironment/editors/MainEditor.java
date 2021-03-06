package authoringEnvironment.editors;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.Variables;
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
    private ObservableList<GameObject> myMaps;

    private static final double SIDEBAR_WIDTH_MULTIPLIER = .25;

    protected static final String DARK_TAB_CSS = "-fx-base: #3c3c3c;";

    // WITH THOSE
    // VARIABLES IN MAP
    // WORKSPACE

    public MainEditor (Controller c, String name) {
        super(c, name);
        this.setStyle(DARK_TAB_CSS);
        myMaps = c.getMaps();
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
        myMapWorkspace = new MapWorkspace(myController);
        System.out.println("MAP WORKSPACE CREATED");
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

    public ObservableList<GameObject> getMaps () {
        return myMaps;
    }

    private void setGridPaneConstraints (GridPane pane) {
        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(Variables.WORKSPACE_HEIGHT_MULTIPLIER);
        pane.getRowConstraints().add(row0);
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPrefWidth(AuthoringEnvironment.getEnvironmentWidth()
                          * Variables.WORKSPACE_WIDTH_MULTIPLIER);
        ColumnConstraints col1 = new ColumnConstraints();
        // col1.setPrefWidth((MainEnvironment.getEnvironmentWidth() *
        // SIDEBAR_WIDTH_MULTIPLIER); TODO: add this back
        pane.getColumnConstraints().add(col0);
        // pane.getColumnConstraints().add(col1);
    }

    protected GridPane getPane () {
        return myPane;
    }

    public abstract void update ();
}
