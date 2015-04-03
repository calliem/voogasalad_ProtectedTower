/**
 * @author Johnny Kumpf
 */
package authoringEnvironment;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import authoring.environment.setting.Setting;

public class Game {

	private static String userDataPackage = System.getProperty("user.dir").concat("/src/userData");
	private static final String paramListFile = "resources/part_parameters";
	private static final String paramSpecsFile = "resources/parameter_datatype";
	private static final String gameFileDir = "/AllPartsData";
	public static final ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
	private static Set<String> dirsToBeCreated = paramLists.keySet();

	private static InstanceManager currentGame = new InstanceManager();


	/**
	 * 
	 * Creates a new game and all the appropriate sub directories
	 * @param gameName The name of the new game to create
	 * @param rootDir The place where the game and subsequent folders will be created
	 */
	public static void createNewGame(String gameName, String rootDir){
		String gameDirectory = rootDir + "/" + gameName;
		String[] nameAndDirectory = new String[2];
		nameAndDirectory[0] = gameName;
		nameAndDirectory[1] = gameDirectory;
		setUserDataPackage(rootDir);
		createGameFolders(gameName);
		//writes the rootDir string into a file
		//this file is stored in rootDir/gameName, i.e.: Users/Johnny/Documents/KingdomRush/KingdomRush.game
		XMLWriter.toXML(nameAndDirectory, gameName + ".game", gameDirectory);
	}
	
	/**
	 * Creates subdirectories for each kind of part, i.e. "Tower", "Unit", etc. in a 
	 * subdirectory of ... userData\gameName
	 * @param gameName The name of the new game we're going to create subdirectories for
	 */
	public static void createGameFolders(String gameName){
		XMLWriter.createDirectories(userDataPackage, dirsToBeCreated);
		new File(userDataPackage + gameFileDir).mkdirs();
	}
	
	public static void setUserDataPackage(String rootDir){
		userDataPackage = rootDir;
	}
	
	public static void addPart(String partType, String partName, List<String> params, List<Object> data){
		currentGame.addPart(partType, partName, params, data);
	}

	/**
	 * Saves all the parts and the Map<partName, [part data]> into an XML file called gameName + "Parts.xml"
	 * Ex: "TestGameParts.xml"
	 * @param gameManager the InstanceManager of the game that's being saved
	 */
	public static void saveGame(){
		currentGame.writeAllPartsToXML();
		currentGame.writeGameToXML();
	}

	/**
	 * Loads in the Map<partName, [part data]> representing all the parts of the game
	 * @param gameName The name of the game for which to load in the parts
	 * @return
	 */
	public static Map<String, Map<String, Object>> loadGame(String pathToRootDirectoryFile){
		String[] nameAndDirectory = (String[]) XMLWriter.fromXML(pathToRootDirectoryFile);
		setUserDataPackage(nameAndDirectory[1]);
		String dir = userDataPackage + gameFileDir;
		currentGame = new InstanceManager(nameAndDirectory[0], (Map<String, Map<String, Object>>) XMLWriter.fromXML(dir));
		return (Map<String, Map<String, Object>>) XMLWriter.fromXML(dir);
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
