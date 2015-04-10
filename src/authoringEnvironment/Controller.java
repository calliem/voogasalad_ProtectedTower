package authoringEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import authoringEnvironment.editors.MapEditor;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.objects.TileMap;

/**
 * This class is a singleton that holds a map of all the editors that are created and that are updated accordingly. 
 * @author Callie Mao
 *
 */

//Is this unnecessary?? This doesn't feel like it is useful. 
public class Controller {
	
	//these probably aren't very good since they are the same as  what is in the properties file. instead consider using files to share informationb etween tabs?
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "main_environment_english");
	
	public static final String MAPS = myResources.getString("MapEditor");
	public static final String LEVELS = myResources.getString("LevelEditor");
	public static final String TOWERS = myResources.getString("TowerEditor");
	
	//private static Controller controller = new Controller();
	private static  Map<String, Editor> myEditors = new HashMap<String, Editor>(); //is it bad that this is up here
	//private static List<Editor> editors = new ArrayList<Editor>();
	
	private Controller(){
	}
	
	public static void updateEditor(String s, Editor editor){
		myEditors.put(s, editor);
		System.out.println("Controller list of editors: " + myEditors);
	}
	
	public static Editor getEditor(String s){
		return myEditors.get(s);
	}

}
