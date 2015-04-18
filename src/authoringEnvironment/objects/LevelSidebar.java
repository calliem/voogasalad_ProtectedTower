package authoringEnvironment.objects;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.map.MapWorkspace;

/**
 * This class defines all the features of the sidebar for the level editor. The user can select options here that will allow him/her to place or updates objects on the map in level editor.
 * @author Callie Mao
 *
 */

public class LevelSidebar extends Sidebar{

	private Editor myMapEditor; 
	private List<Node> myMapList;
	private ObservableList<Node> myRounds;
	
	private static final int LISTVIEW_HEIGHT = 200;
	
	public LevelSidebar(ResourceBundle resources, List<Node> maps, MapWorkspace mapWorkspace) {
		super(resources, maps, mapWorkspace);
		//myMapEditor = Controller.getEditor(Controller.MAPS);
		createMapSettings();
		
	}

	@Override
	protected void createMapSettings() {
		createTitleText(getResources().getString("SelectMap"));
		createTitleText(getResources().getString("PlaceWave"));
		
		Button createRound = new Button(getResources().getString("CreateRound"));
		
		//Editor mapEditor = Controller.getEditor(Controller.MAPS);
		
	//	ListView mapList = createListView(FXCollections.observableArrayList(myMapList), LISTVIEW_HEIGHT);
	//	myRounds = FXCollections.observableArrayList();
/*		mapList.setCellFactory(new Callback<ListView<String>, 
		            ListCell<String>>() {
		                @Override 
		                public ListCell<String> call(ListView<String> list) {
		                    return new ColorRectCell();
		                }
		            }
		        );*/
		 
		//getChildren().add(mapList);
		
		createTitleText(getResources().getString("RoundOrder"));
		getChildren().add(createListView(myRounds, LISTVIEW_HEIGHT));
	}
	
	public void update(){
		//myMapEditor = Controller.getEditor(Controller.MAPS); //update map editor from the controller
		//myMapList = FXCollections.observableArrayList(myMapEditor.getObjects());
		
	}

}
