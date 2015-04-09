package authoringEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//private static Controller controller = new Controller();
	private static  Map<String, Editor> myEditors = new HashMap<String, Editor>(); //is it bad that this is up here
	private static List<Editor> editors = new ArrayList<Editor>();
	
	private Controller(){
	}
	
	public static void updateEditor(String s, Editor editor){
		myEditors.put(s, editor);
		editors.add(editor);
		System.out.println("Controller list of editors: " + myEditors);
		System.out.println("Controller list of editors in an arraylist: " + editors);
	}
	
	public static Editor getEditor(String s){
		return myEditors.get(s);
	}
}
