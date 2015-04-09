

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoringEnvironment.Controller;
import authoringEnvironment.LevelSidebar;
import authoringEnvironment.MapSidebar;
import authoringEnvironment.objects.LevelView;
import authoringEnvironment.objects.TileMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Sets up the wave editor that allows the user to specify a specific map and the specific waves that will appear at specific spawn points. The user will also be able to customize delay in timing before each wave is sent out 
 * @author Callie Mao
 */

//private ObservableList<Round> myRounds;



public class LevelEditor extends MainEditor{
	
	public List<Node> myLevels;

	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/"; //TODO: stop duplicating this default resource package line
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "level_editor_english");
	
    public LevelEditor(Dimension2D dim, Stage s) {
		super(dim, s);
		 getPane().add(new LevelSidebar(myResources, Controller.getEditor("Maps").getObjects(), getMapWorkspace()),1,0); //TODO: don't hardcode the Maps editor. Find a way to get it from teh existing one so that changing one thing in code won't require changes everywhere
	     myLevels = new ArrayList<Node>();
	}

	@Override
	public List<Node> getObjects() {
		// TODO Auto-generated method stub
		return myLevels;
	}

	
	
/*	@Override
	public void update() {
		System.out.println("level editor updated");		// TODO Auto-generated method stub		
		MapEditor mapEditor = (MapEditor) Controller.getEditor("Maps");  //TODO: find a way to get sthe same resource file but to also use myResources.getString() on the proper resource file //how to avoid this issue of more dependencies on this string name. lots of code will have to change in order to change this; maybe use indexes instead?
    	getMapWorkspace().getChildren().add(mapEditor.getActiveMap().getMap());
	}*/
}
