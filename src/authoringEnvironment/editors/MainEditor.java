
package authoringEnvironment.editors;

import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Displays the general layout for MainEditor classes/subclasses (ie. GameMap, WaveEditor) consisting of a sidebar and a generic map.
 * @author Callie Mao
 */

public abstract class MainEditor extends Editor {

	private GridPane myPane;
	private StackPane myMapWorkspace;
	
	public static final double SIDEBAR_WIDTH_MULTIPLIER = .25;
	public static final double MAP_WIDTH_MULTIPLIER = .75;
	public static final double MAP_HEIGHT_PERCENT = 100;
	
	public MainEditor(Dimension2D dim, Stage s) {
		super(dim, s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a sidebar and general map layout to be utilized by subclasses
	 */
	//TODO: return groupor return a parent so that I can directly return a gridpane here?
	public Node configureUI() {

		Group root = new Group();
		createGridPane();
		myMapWorkspace = new StackPane();
		Rectangle background = new Rectangle(myDimensions.getWidth()*MAP_WIDTH_MULTIPLIER, 0.9 * myDimensions.getHeight(), Color.web("2A2A29"));
		myMapWorkspace.getChildren().add(background);
		myPane.add(myMapWorkspace, 0, 0);
		createMap();

		// does it dynamically update or will i have to say
		// TODO remove magic number
		//is using MainEnvironment.myDimensions.getWidth() bad?
	
		root.getChildren().add(myPane);
		return root;
	}

	private void createGridPane() {
		myPane = new GridPane();
		setGridPaneConstraints(myPane);
		myPane.setGridLinesVisible(true); //TODO: remove the showing gridlines
	}
	
	public StackPane getMapWorkspace(){
		return myMapWorkspace;
	}

	private void setGridPaneConstraints(GridPane pane) {
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(MAP_HEIGHT_PERCENT);
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

	protected abstract void createMap();

}