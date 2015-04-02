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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class MainEditor extends Editor {
	
	public static final double SIDEBAR_WIDTH_MULTIPLIER = .25;
	public static final double MAP_WIDTH_MULTIPLIER = .75;
	public static final double MAP_HEIGHT_PERCENT = 100;
	
	public MainEditor(Dimension2D dim) {
		super(dim);
		// TODO Auto-generated constructor stub
	}
	


	private GridPane myPane;
	
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
		
		createMap();
		//does it dynamically update or will i have to say myPane = createmap();
		// TODO remove magic number
        /*Rectangle background = new Rectangle(MainEnvironment.myDimensions.getWidth(), 0.9 * MainEnvironment.myDimensions.getHeight());
        background.setStyle("-fx-border-color: red;"
                + "-fx-border-width: 1;"
                + "-fx-border-style: solid;"
                + "-fx-padding: 10;" 
                + "-fx-background-color: firebrick;");*/
        		
        		//"-fx-base: #3c3c3c;");
        
		
		root.getChildren().addAll(myPane); 

		return root;
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
	
	protected abstract void createMap();

}