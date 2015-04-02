package authoringEnvironment;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import authoring.environment.setting.Setting;

public class GameCreator {

	private static final String userDataPackage = System.getProperty("user.dir").concat("\\src\\userData");
	private static final String paramListFile = "resources/part_parameters";
	private static final String paramSpecsFile = "resources/parameter_datatype";
	private static final String gameFileDir = "\\GameFile";
	public static final ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
	private static Set<String> dirsToBeCreated = paramLists.keySet();


	/**
	 * Creates subdirectories for each kind of part, i.e. "Tower", "Unit", etc. in a 
	 * subdirectory of ... userData\gameName
	 * @param gameName The name of the new game we're going to create subdirectories for
	 */
	public static void createNewGameFolder(String gameName){
		String gameDir = userDataPackage.concat("\\").concat(gameName);
		new File(gameDir).mkdirs();
		for(String dir : dirsToBeCreated)
			new File(gameDir.concat("\\").concat(dir)).mkdirs();
		//adds a directory for storing the Map<partName, [it's params and data]>
		new File(gameDir + gameFileDir).mkdirs();
	}

	/**
	 * Saves all the parts and the Map<partName, [part data]> into an XML file called gameName + "Parts.xml"
	 * Ex: "TestGameParts.xml"
	 * @param gameManager the InstanceManager of the game that's being saved
	 */
	public static void saveGame(InstanceManager gameManager){
		gameManager.writeAllPartsToXML();
		gameManager.writeGameToXML();
	}

	/**
	 * Loads in the Map<partName, [part data]> representing all the parts of the game
	 * @param gameName The name of the game for which to load in the parts
	 * @return
	 */
	public static Map<String, Map<String, Object>> loadGame(String gameName){
		String dir = userDataPackage + "\\" + gameName + gameFileDir;
		return (Map<String, Map<String, Object>>) XMLWriter.fromXML(dir);
	}

	/**
	 * 
	 * @param partType The type of part, i.e. "Tower"
	 * @param param The name of the parameter the Setting is being generated for, i.e. "HP"
	 * @param defaultVal The default value of the Setting, i.e. "0"
	 * @param dataType The type of the data, i.e. "Integer"
	 * @return The Setting object corresponding to these parameters
	 */

	public static Setting generateSetting(String partType, String param, String defaultVal, String dataType){
		Class<?> c = String.class;
		Setting s = null;
		try{
			c = Class.forName(dataType + "Setting");
		}
		catch(ClassNotFoundException e){
			//display error message
		};
		try{
			s = (Setting) c.getConstructor(String.class, String.class, String.class).newInstance(partType, param, defaultVal);
		}
		catch  (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e){
			//display error message, don't let the null value be used
		}

		return s;
	}

	/**
	 * 
	 * @param partType The type of part we need a Settings list for, i.e. "Tower"
	 * @return The corresponding Settings list
	 */
	public static List<Setting> generateSettingsList(String partType){

		List<Setting> settingsList = new ArrayList<Setting>();
		ResourceBundle paramSpecs = ResourceBundle.getBundle(paramSpecsFile);

		String[] params = paramLists.getString(partType).split("\\s+");

		for(String param : params){
			String[] typeAndDefault = paramSpecs.getString(param).split("\\s+");
			String dataType = typeAndDefault[0];
			String defaultVal = typeAndDefault[1];

			settingsList.add(generateSetting(partType, param, defaultVal, dataType));
		}

		return settingsList;
	}

	/**
	 * 
	 * @param partType Part type name, i.e. "Tower"
	 * @return the Map<String, Object> representing the part's default data
	 * Currently generates this from properties file, this is going to change
	 * in how it's done, but I'm leaving it for now
	 */
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
	//also going to change, probably will be an unnecessary method by the end
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
