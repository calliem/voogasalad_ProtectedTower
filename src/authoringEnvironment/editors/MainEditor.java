package authoringEnvironment.editors;

import authoringEnvironment.Controller;
import authoringEnvironment.MainEnvironment;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.TileMap;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Displays the general layout for MainEditor classes/subclasses (ie. GameMap,
 * WaveEditor) consisting of a sidebar and a generic map.
 * 
 * @author Callie Mao
 */

public abstract class MainEditor extends Editor {

	private GridPane myPane;
	private MapWorkspace myMapWorkspace;

	public static final double SIDEBAR_WIDTH_MULTIPLIER = .25;
	public static final double MAP_WIDTH_MULTIPLIER = .75; // THIS IS REPLICATED
															// WITH THOSE
															// VARIABLES IN MAP
															// WORKSPACE
	public static final double MAP_HEIGHT_PERCENT = 100; // THIS IS REPLICATED
															// WITH THOSE
															// VARIABLES IN MAP
															// WORKSPACE

	public MainEditor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a sidebar and general map layout to be utilized by subclasses
	 */
	// TODO: return groupor return a parent so that I can directly return a
	// gridpane here?
	@Override
	protected void configureUI() {
		createGridPane();
		myMapWorkspace = new MapWorkspace();
		myPane.add(myMapWorkspace, 0, 0);
		getChildren().add(myPane);
	}

	private void createGridPane() {
		myPane = new GridPane();
		setGridPaneConstraints(myPane);
	}

	public MapWorkspace getMapWorkspace() {
		return myMapWorkspace;
	}

	public TileMap getActiveMap() {
		return myMapWorkspace.getActiveMap();
	}

	private void setGridPaneConstraints(GridPane pane) {
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(MAP_HEIGHT_PERCENT);
		pane.getRowConstraints().add(row0);
		ColumnConstraints col0 = new ColumnConstraints();
		col0.setPrefWidth(MainEnvironment.getEnvironmentWidth() * MAP_WIDTH_MULTIPLIER);
		ColumnConstraints col1 = new ColumnConstraints();
		// col1.setPrefWidth((MainEnvironment.getEnvironmentWidth() *
		// SIDEBAR_WIDTH_MULTIPLIER); TODO: add this back
		pane.getColumnConstraints().add(col0);
		// pane.getColumnConstraints().add(col1);
	}

	protected GridPane getPane() {
		return myPane;
	}

	@Override
	public void update() {
		MapEditor mapEditor = (MapEditor) Controller.getEditor(Controller.MAPS);
		if (!getMapWorkspace().getChildren().contains(mapEditor.getActiveMap())) {
			getMapWorkspace().updateWithNewMap(mapEditor.getActiveMap());
		}
	}
}