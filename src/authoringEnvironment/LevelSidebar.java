package authoringEnvironment;

import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.TileMap;

/**
 * This class defines all the features of the sidebar for the level editor. The user can select options here that will allow him/her to place or updates objects on the map in level editor.
 * @author Callie Mao
 *
 */

public class LevelSidebar extends Sidebar{


	public LevelSidebar(ResourceBundle resources, List<Node> maps, MapWorkspace mapWorkspace) {
		super(resources, maps, mapWorkspace);
		createMapSettings();
		
	}

	@Override
	protected void createMapSettings() {
		createTitleText(getResources().getString("SelectMap"));
		createTitleText(getResources().getString("PlaceWave"));
		
		Button createRound = new Button(getResources().getString("CreateRound"));
	//	getChildren().add(createListView(items, height));
		
		createTitleText(getResources().getString("Round Order"));
	//	getChildren().add(createListView(items, height));
	}

}
