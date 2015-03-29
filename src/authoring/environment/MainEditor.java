/**
 * Displays the general layout for MainEditor classes/subclasses (ie. GameMap, WaveEditor) consisting of a sidebar and a generic map.
 * @author Callie Mao
 */

package authoring.environment;

import javafx.scene.Group;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public abstract class MainEditor extends Editor {

	private GridPane myPane;

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
		
		
		

		/*
		 * mySidebar = new SideBar(); myWorkspace = new Workspace(myDimensions,
		 * mySidebar); drawer = new Drawer(myWorkspace); CustomizationBar
		 * customizationBar = new CustomizationBar( myTurtles, drawer,
		 * myWorkspace, myStage, myDimensions); root.add(customizationBar, 0,
		 * 0); root.add(configureAddTurtlesButton(), 1, 0);
		 * root.add(myWorkspace, 0, 1); root.add(mySidebar, 1, 1, 1, 2);
		 * 
		 * myParser = createNewParser(this); mySidebar.setParser(myParser);
		 * customizationBar.setParser(myParser);
		 * 
		 * myEditor = new Editor(myParser, mySidebar, myDimensions);
		 * root.add(myEditor, 0, 2); return root;
		 */
		Text text = new Text("hello");
		myPane.add(text, 1, 0);
		root.getChildren().addAll(myPane);
		return root;
	}

	private void setGridPaneConstraints(GridPane pane) {
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(100);
		pane.getRowConstraints().add(row0);

		ColumnConstraints col0 = new ColumnConstraints();
		col0.setPercentWidth(75);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(25);
		pane.getColumnConstraints().add(col0);
		pane.getColumnConstraints().add(col1);
	}

	protected GridPane getPane() {
		return myPane;
	}

}