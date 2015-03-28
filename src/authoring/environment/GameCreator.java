package authoring.environment;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class GameCreator {
	
	private static final String userDataPackage = System.getProperty("user.dir").concat("\\src\\userData");
	private static final String paramListFile = "resources/part_parameters";
	private static final String paramSpecsFile = "resources/parameter_datatype";
	public static final ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
	private static Set<String> dirsToBeCreated = paramLists.keySet();
	
	
	public static void createNewGameFolder(String gameName){
		String gameDir = userDataPackage.concat("\\").concat(gameName);
		new File(gameDir).mkdirs();
		for(String dir : dirsToBeCreated)
			new File(gameDir.concat("\\").concat(dir)).mkdirs();
	}
	
	//creates a default part of partType's type
	public static Map<String, Object> createDefaultPart(String partType){

		Map<String, Object> part = new HashMap<String, Object>();
		//ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
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
		private static Object makeDefaultData(String dataType, String defaultVal){

			Class<?> c = String.class;
			Object data = "N/A";

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
