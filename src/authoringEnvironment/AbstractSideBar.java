package authoringEnvironment;

import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.TileMap;

public abstract class AbstractSideBar extends VBox { //extend gridpane pls
	
	private ResourceBundle myResources;
	private TileMap myActiveMap;
	ObservableList<TileMap> myMaps;
	
	private static final double PADDING = MainEnvironment.getEnvironmentWidth()/128; //maybe set the spacing dynamically instead
	private static final double LISTVIEW_HEIGHT = MainEnvironment.getEnvironmentHeight()/6;
	private static final double TITLE_FONT_SIZE = MainEnvironment.getEnvironmentWidth()/85;


	public AbstractSideBar(ResourceBundle resources, TileMap activeMap, ObservableList<TileMap> maps){
		myResources = resources;
		myActiveMap = activeMap;
		setDimensionRestrictions();
		createMapSettings();
	}
	
	protected abstract void createMapSettings();
	
	protected void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(TITLE_FONT_SIZE));
		title.setUnderline(true);
		getChildren().add(title);
	}
	
	protected ListView<PathView> createListView(ObservableList<PathView> items, int height) {
		ListView<PathView> list = new ListView<PathView>();
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
