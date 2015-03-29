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
	protected Group configureUI() {
		Group root = new Group();
		myPane = new GridPane();
		setGridPaneConstraints(myPane);
		myPane.setGridLinesVisible(true);
		
		myActiveMap = new TileMap(100.0, 100.0, 5.0); 
		//TODO: remove test values
		
		Text text = new Text("hello");
		myPane.add(myActiveMap, 1, 0);
		myPane.add(text, 2, 0);
		root.getChildren().addAll(myPane);
		return root;
	}
	
	public void setActiveMap(TileMap map){
		myActiveMap = map;
		//TODO: display the new active map
	}

	private void setGridPaneConstraints(GridPane pane) {
		System.out.println(getWidth());
		System.out.println(getHeight());
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(100); //why does it not cover everything?
		pane.getRowConstraints().add(row0);

		ColumnConstraints col0 = new ColumnConstraints();
		col0.setPrefWidth(getWidth() * .8);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth(getWidth() * .2);
		pane.getColumnConstraints().add(col0);
		pane.getColumnConstraints().add(col1);
	}

	protected GridPane getPane() {
		return myPane;
	}

}