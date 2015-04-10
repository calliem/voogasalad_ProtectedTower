/**
 * @author Johnny Kumpf
 */
package authoringEnvironment;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import authoringEnvironment.setting.Setting;

public class GameManager {

	private static String userDataLocation = System.getProperty("user.dir").concat("/src/userData");
	private static final String paramListFile = "resources/part_parameters";
	private static final String paramSpecsFile = "resources/parameter_datatype";
	private static final String partFileDir = "/AllPartsData";
	public static final ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
	private static final String partFileName = "GameParts.xml";

	private static InstanceManager currentGame = new InstanceManager();


	/**
	 * 
	 * Creates a new game and all the appropriate sub directories
	 * @param gameName The name of the new game to create
	 * @param rootDir The place where the game and subsequent folders will be created
	 */
	public static void createNewGame(String gameName, String rootDir){
		currentGame = new InstanceManager(gameName);
		String gameDirectory = rootDir + "/" + gameName;
		String[] nameAndDirectory = new String[2];
		nameAndDirectory[0] = gameName;
		nameAndDirectory[1] = gameDirectory;
		createGameFolders(gameName, rootDir);
		System.out.println("Game root dir: " + userDataLocation);
		//writes the rootDir string into a file
		//this file is stored in rootDir/gameName, i.e.: Users/Johnny/Documents/KingdomRush/KingdomRush.game
		XMLWriter.toXML(nameAndDirectory, gameName + ".game", gameDirectory);
	}

	/**
	 * Creates subdirectories for each kind of part, i.e. "Tower", "Unit", etc. in a 
	 * subdirectory of ... userData\gameName
	 * @param gameName The name of the new game we're going to create subdirectories for
	 */
	public static void createGameFolders(String gameName, String rootDir){
		Set<String> name = new HashSet<String>();
		name.add(gameName);
		XMLWriter.createDirectories(rootDir, name);
		setUserDataLocation(rootDir + "/" + gameName);
		XMLWriter.createDirectories(userDataLocation, dirsToBeCreated());
	}

	public static void setUserDataLocation(String rootDir){
		userDataLocation = rootDir;
	}

	public static void addPartToGame(String partType, String partName, List<String> params, List<Object> data){
		currentGame.addPart(partType, partName, params, data);
	}
	/*
	addPartToGame("Wave", "IceGuysWave", {"Units", "Times"}, data)
	List<Object> data = new List<Object>();
	//filenames
	list.add(new List<String>());
	//times
	list.add(new List<Double>());
	 */

	/**
	 * Saves all the parts and the Map<partName, [part data]> into an XML file called gameName + "Parts.xml"
	 * Ex: "TestGameParts.xml"
	 * @param gameManager the InstanceManager of the game that's being saved
	 */
	public static String saveGame(){
		currentGame.writeAllPartsToXML(userDataLocation);
		return XMLWriter.toXML(currentGame.getAllPartData(), partFileName, userDataLocation + partFileDir);
	}


	/**
	 * Loads in the Map<partName, [part data]> representing all the parts of the game
	 * @param gameName The name of the game for which to load in the parts
	 * @return
	 */
	public static List<Map<String, Object>> loadGame(String pathToRootDirectoryFile){
		String[] nameAndDirectory = (String[]) XMLWriter.fromXML(pathToRootDirectoryFile);
		setUserDataLocation(nameAndDirectory[1]);
		String dir = userDataLocation + partFileDir + "/" + partFileName;
		System.out.println("dir loading: " + dir);
		List<Map<String,Object>> allUserData = (List<Map<String, Object>>) XMLWriter.fromXML(dir);
		currentGame = new InstanceManager(nameAndDirectory[0], allUserData);
		return allUserData;
	}

	public static Map<String, Object> loadPart(String dir){
		System.out.println("loading: " + dir);
		try {
			return currentGame.getPartFromXML(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> loadPartFromFileName(String partType, String fileName){
		String dir = userDataLocation + "/" + partType + "/" + fileName;
		return loadPart(dir);
	}

	private static Set<String> dirsToBeCreated(){
		Set<String> toAdd = paramLists.keySet();
		toAdd.add(partFileDir);
		return toAdd;
	}

	/**
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
		String settingClass = "authoringEnvironment.editors." + dataType + "Setting";

		try{
			c = Class.forName(settingClass);
		}catch (ClassNotFoundException e){
			System.out.println(dataType + "class not found");
			//do something, but this shouldn't happen if the properties file is correct
		}

		try{
			data = ((Setting) (c.getConstructor(String.class, String.class, String.class)
					.newInstance("Doesn't", "Matter", defaultVal)))
					.getParameterValue();
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
