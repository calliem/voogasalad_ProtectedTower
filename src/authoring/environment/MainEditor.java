/**
 * Displays the general layout for MainEditor classes/subclasses (ie. GameMap, WaveEditor) consisting of a sidebar and a generic map.
 * @author Callie Map
 */

package authoring.environment;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public abstract class MainEditor extends Editor {
	
	public static final double SIDEBAR_WIDTH_MULTIPLIER = .2;
	public static final double MAP_WIDTH_MULTIPLIER = .8;
	public static final double MAP_HEIGHT_PERCENT = 100;
	
	public MainEditor(Dimension2D dim) {
		super(dim);
		// TODO Auto-generated constructor stub
	}

	private GridPane myPane;
	private TileMap myActiveMap;
	// interface?

	// or we could just have configureUI() return a parent?
	/**
	 * Creates a sidebar and general map layout to be utilized by subclasses
	 */
	
	
	//doing this wrong
	//create in mapeditor only and then call it in the other editor
	protected Group configureUI() {
		Group root = new Group();
		//root.
		//TODO: set background color to the same color as the tabs
		myPane = new GridPane();
		setGridPaneConstraints(myPane);
		myPane.setGridLinesVisible(true);
		
		myActiveMap = new TileMap(100, 100, 5);
		//TODO: remove test values
		
		Text text = new Text("hello");
		Tile tile = new Tile(100);
		myPane.add(myActiveMap.getMap(), 1, 0);
		myPane.add(text, 2, 0);
		myPane.add(tile, 2, 0);
		root.getChildren().addAll(myPane);
		return root;
	}
	
	public void setActiveMap(TileMap map){
		myActiveMap = map;
		//TODO: display the new active map
	}

	private void setGridPaneConstraints(GridPane pane) {
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(MAP_HEIGHT_PERCENT); //why does it not cover everything?
		pane.getRowConstraints().add(row0);

		ColumnConstraints col0 = new ColumnConstraints();
		col0.setPrefWidth(getWidth() * MAP_WIDTH_MULTIPLIER);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth(getWidth() * SIDEBAR_WIDTH_MULTIPLIER);
		pane.getColumnConstraints().add(col0);
		pane.getColumnConstraints().add(col1);
	}

	protected GridPane getPane() {
		return myPane;
	}

}