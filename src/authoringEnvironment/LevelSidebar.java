package authoringEnvironment;

import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import authoringEnvironment.editors.MapWorkspace;
import authoringEnvironment.objects.TileMap;

public class LevelSidebar extends Sidebar{


	public LevelSidebar(ResourceBundle resources, List<Node> maps, MapWorkspace mapWorkspace) {
		super(resources, maps, mapWorkspace);
		// TODO Auto-generated constructor stub
		createMapSettings();
		
	}

	@Override
	protected void createMapSettings() {
		// TODO Auto-generated method stub
		createTitleText(getResources().getString("Rounds"));

		
	}

}
