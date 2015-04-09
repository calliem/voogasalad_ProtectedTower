package authoringEnvironment;

import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import authoringEnvironment.objects.TileMap;

public class LevelSidebar extends Sidebar{


	public LevelSidebar(ResourceBundle resources, List<Node> maps) {
		super(resources, maps);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createMapSettings() {
		// TODO Auto-generated method stub
		createTitleText(getResources().getString("Rounds"));

		
	}

}
