package authoringEnvironment;

import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.TileMap;

/**
 * This class is the generic sidebar method that contains the resource file and general methods shared by all sidebars. Sidebars throughout the authoring environment will be of consistent proportional size and have consistency in how information (ie. titles and lists) are displayed.
 * @author Callie Mao
 *
 */

public abstract class Sidebar extends VBox { //extend gridpane pls
	
	private ResourceBundle myResources;
	private List<Node> myMaps; //can't seem to use list with this
	private MapWorkspace myMapWorkspace; //TODO: or use more general StackPane?
	
	private static final double PADDING = AuthoringEnvironment.getEnvironmentWidth()/128; //maybe set the spacing dynamically instead
	private static final double LISTVIEW_HEIGHT = AuthoringEnvironment.getEnvironmentHeight()/6;
	private static final double TITLE_FONT_SIZE = AuthoringEnvironment.getEnvironmentWidth()/85;

	public Sidebar(ResourceBundle resources, List<Node> maps, MapWorkspace mapWorkspace){
		
		myResources = resources;
		myMaps = maps;
		myMapWorkspace = mapWorkspace;
		setDimensionRestrictions();
		setSpacing(10);
		//createMapSettings();
	}
	
	protected MapWorkspace getMapWorkspace(){
		return myMapWorkspace;
	}
	

	protected ResourceBundle getResources(){
		return myResources;
	}

	protected List<Node> getMaps(){
		return myMaps;
	}
	
	protected abstract void createMapSettings();
	
	protected void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(TITLE_FONT_SIZE));
		title.setUnderline(true);
		getChildren().add(title);
	}
	
	protected ListView<Node> createListView(ObservableList<Node> items, int height) {
		ListView<Node> list = new ListView<Node>();
		list.setItems(items);
		list.setMaxWidth(Double.MAX_VALUE);
		list.setPrefHeight(LISTVIEW_HEIGHT);
		return list;
	}

	private void setDimensionRestrictions() {
		setPadding(new Insets(PADDING));
		//setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}
	
	
}
