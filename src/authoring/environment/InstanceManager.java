package authoring.environment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class InstanceManager {

	private int partsCreated = 0;
	private static final String paramListFile = "resources.part_parameters.properties";
	private static final String paramSpecsFile = "resources.parameter_datatype.properties";
	//a map of all the parts the user has created
	//each part is represented by a map mapping the part's parameters to their data
	//the fields look like: Map<partName, Map<parameterName, parameterData>>
	private Map<String, Map<String, Object>> userParts;

	//adds a default part to userParts with the name "Part_x" where x the number of parts the user has created
	public void addPart(String partType){
		Map<String, Object> newPart = createDefaultPart(partType);
		String partName = "Part_" + new Integer(partsCreated++).toString();
		userParts.put(partName, newPart);
	}

	public static String getPartType(Class c){
		return c.toString().substring(0, c.toString().indexOf("Editor"));
	}

	//creates a default part of partType's type
	public Map<String, Object> createDefaultPart(String partType){

		Map<String, Object> part = new HashMap<String, Object>();
		ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
		ResourceBundle paramSpecs = ResourceBundle.getBundle(paramSpecsFile);

		String[] params = paramLists.getString(partType).split("\\s+");

		for(String paramName : params){
			String[] typeAndDefault = paramSpecs.getString(paramName).split("\\s+");
			String dataType = typeAndDefault[0];
			String defaultVal = typeAndDefault[1];

			part.put(paramName, makeDefaultData(dataType, defaultVal));
		}
		return part;
	}

	//creates an Object of class "dataType" and value "defualtVal"
	private Object makeDefaultData(String dataType, String defaultVal){
		
		Class<?> c = Integer.class;
		Object data = new Integer("0");

		try{
			c = Class.forName(dataType);
		}catch (ClassNotFoundException e){
			System.out.println(dataType + "class not found");
			//do something, but this shouldn't happen if the properties file is correct
		}

		try{
			data = c.getConstructor(String.class).newInstance(defaultVal);
		}
		catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e){
			//hopefully this won't happen
			System.out.println("Constructor couldn't be called with a String");
		}

		return data;
	}




}


