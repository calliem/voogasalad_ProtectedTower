package authoring.environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceManager {
	
	//a list of all the parts the user has created
	//each part is represented by a map mapping it's parameters to their data
	private List<Map<String, Object>> userParts;
	
	public static void addPart(String partName){
		//Map<String, Object> newPart = new HashMap<String, Object>();
		
	}
	
	public static String getPartType(Class c){
		return c.toString().substring(0, c.toString().indexOf("Editor"));
	}

}
