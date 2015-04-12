/**
 * @author Johnny Kumpf
 */

package authoringEnvironment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoringEnvironment.setting.Setting;
import util.misc.SetHandler;
import javafx.collections.ObservableList;

public class InstanceManager {
	// public static final ResourceBundle paramLists =
	// ResourceBundle.getBundle("resources/part_parameters");
	private static final String defaultSaveLocation = System.getProperty(
			"user.dir").concat("/src/myTowerGames");
	private static final String gameRootDirectory = System.getProperty(
			"user.dir").concat("/src/exampleUserData");
	public static final String partTypeKey = "PartType";
	public static final String nameKey = "Name";
	private static final String missingNameKey = "Map passed must contain a \"Name\" key. Key is case sensitive.";
	private static final String listSizesDiffer = "Lists passed must contain same number of elements.";
	private static final String partFileName = "GameParts.xml";
	private static final String IMFileName = "GameManager.xml";
	public static final String partsFileDir = "/AllPartData";
	private static final String partKeyKey = "PartKey";
	public static final String imageKey = "Image";

	// a map of all the parts the user has created
	// each part is represented by a map mapping the part's parameters to their
	// data
	// the fields look like: Map<partName, Map<parameterName, parameterData>>
	// private Map<String, Map<String, Object>> userParts;

	private Map<String, Map<String, Object>> userParts;
	private String gameName;
	private String rootDirectory;

	/**
	 * Generates an instance manager for a game. An InstanceManager has a name
	 * (the game name), data, which is part data stored as a List of maps, and a
	 * root directory, in which all the parts are saved in various folders.
	 * 
	 * @param name
	 *            The name of the game for which this InstanceManager manages
	 *            part data.
	 * @param partData
	 *            The data maps corresponding to all the parts in this game
	 * @param rootDir
	 *            The root directory in which all these parts and all other game
	 *            information is stored.
	 */
	public InstanceManager(String name,
			Map<String, Map<String, Object>> partData, String rootDir) {
		gameName = name;
		userParts = partData;
		rootDirectory = rootDir;
	}

	public InstanceManager() {
		this("Unnamed_Game", new HashMap<String, Map<String, Object>>(),
				defaultSaveLocation + "/Unnamed_Game");
	}

	public InstanceManager(String name) {
		this(name, new HashMap<String, Map<String, Object>>(),
				defaultSaveLocation + "/" + name);
	}

	public InstanceManager(String name, String rootDir) {
		this(name, new HashMap<String, Map<String, Object>>(), rootDir);
	}

	public InstanceManager(String name,
			Map<String, Map<String, Object>> partData) {
		this(name, partData, defaultSaveLocation + "/" + name);
	}

	/**
	 * This is one way parts will be added from the Editor windows like
	 * TowerEditor If convenient, any editor can pass the addPart method two
	 * lists, one of parameter names and the other of data (with corresponding
	 * indeces). A name and partType parameter will be added to the Map before
	 * it's added to the list of parts.
	 * 
	 * @param partType
	 *            The type of part, i.e. "Tower"
	 * @param partName
	 *            The name of the part, i.e. "IceShooterTower"
	 * @param params
	 *            List of the parameters that this part needs, i.e. "HP",
	 *            "Range", "Projectile"
	 * @param data
	 *            List of corresponding data values for those params, i.e. 1,
	 *            1.0, "Projectile1.xml"
	 * @return The part that was created and added top user's parts
	 */
	public String addPart(String partType, String partName,
			List<String> params, List<Object> data) {
		Map<String, Object> toAdd = new HashMap<String, Object>();
		try {
			toAdd = generatePartMap(params, data);
			toAdd.put(nameKey, partName);
			addPart(partType, toAdd);
		} catch (DataFormatException e) {
			System.err
					.println("Part could not be generated and was not added.");
		}
		return (String) toAdd.get(partKeyKey);
	}

	private Map<String, Object> generatePartMap(List<String> params,
			List<Object> data) throws DataFormatException {
		if (params.size() != data.size())
			throw new DataFormatException(listSizesDiffer);
		Map<String, Object> part = new HashMap<String, Object>();
		for (int i = 0; i < params.size(); i++)
			part.put(params.get(i), data.get(i));
		return part;
	}

	public String addPart(String partType, List<Setting> settings) {
		Map<String, Object> partToAdd = new HashMap<String, Object>();
		for (Setting s : settings)
			partToAdd.put(s.getParameterName(), s.getParameterValue());
		return addPart(partType, partToAdd);
	}
	
	public void specifyPartImage(String partKey, String imageFilePath){
		userParts.get(partKey).put(imageKey, imageFilePath);
	}

	/**
	 * Takes the partType and a map of parameters to data and uses that data to
	 * add the appropriate part to the list of parts. The map must already
	 * contain the key "Name" with the corresponding String data, or else name
	 * will be missing from the final part added.
	 * 
	 * @param partType
	 *            The type of part to be added, e.g. "Tower"
	 * @param part
	 *            The map representing the parts parameters and data. Must
	 *            include "Name" key.
	 * @return
	 */
	public String addPart(String partType, Map<String, Object> part) {
		part.put(partTypeKey, partType);
		String partKey = gameName + "_" + (String) part.get(nameKey) + "."
				+ partType;
		part.put(partKeyKey, partKey);
		try {
			writePartToXML(addPartToUserParts(part, partKey));
		} catch (DataFormatException e) {
			System.err.println("Part was not added.");
		}
		return partKey;
	}

	private Map<String, Object> addPartToUserParts(
			Map<String, Object> partToCheck, String partKey)
			throws DataFormatException {
		if (partToCheck.containsKey("Name")) {
			userParts.put(partKey, partToCheck);
			return partToCheck;
		} else
			throw new DataFormatException(missingNameKey);
	}

	/**
	 * Writes the part, passed as a Map, into an XML file in:
	 * rootDirectory/partType/partName.xml, for example:
	 * ...myTowerGames/Tower/IceShootingTower.xml
	 * 
	 * @param part
	 *            The part to write to XML
	 */
	private void writePartToXML(Map<String, Object> part) {
		String partType = (String) part.get(partTypeKey);
		String partFileName = (String) part.get(nameKey) + ".xml";
		String directory = rootDirectory + "/" + partType;
		XMLWriter.toXML(part, partFileName, directory);
	}

	/**
	 * Writes all parts of the current game into their respective files
	 */
	public void writeAllPartsToXML() {
		for (String key : userParts.keySet())
			writePartToXML(userParts.get(key));
	}

	/**
	 * Saves everything about the current state of the user's game into an xml
	 * file. This method writes all the parts to individual files, and then
	 * writes the InstanceManager to a separate file for retrieval in the
	 * authoring environement later.
	 * 
	 * @return the directory where the InstanceManager object was saved
	 */
	public String saveGame() {
		writeAllPartsToXML();
		// XMLWriter.toXML(userParts, partFileName, rootDirectory +
		// partsFileDir);
		System.out.println("writing to xml manager");
		XMLWriter.toXML(this, IMFileName, rootDirectory + partsFileDir);
		return rootDirectory + partsFileDir;
	}

	/**
	 * Loads the InstanceManager object that's stored in a data file. The path
	 * to this data file is found in the file that the String argument leads to.
	 * This path will lead to a .game file. This method can only be called by
	 * the authoring environment.
	 * 
	 * @param pathToRootDirFile
	 *            The location of the file that holds the path to the root
	 *            directory of the game being loaded.
	 * @return the InstanceManager of the game specified by the path in the file
	 *         specified by the argument
	 */
	protected static InstanceManager loadGameManager(String pathToRootDirFile) {
		String[] nameAndDirectory = (String[]) XMLWriter
				.fromXML(pathToRootDirFile);
		String rootDirectory = nameAndDirectory[1];
		return (InstanceManager) XMLWriter.fromXML(rootDirectory + partsFileDir
				+ "/" + IMFileName);
	}

	/**
	 * Gets a copy of the List of part data stored in the game specified by the
	 * argument.
	 * 
	 * @param pathToRootDirFile
	 *            The location of the file that holds the path to the root
	 *            directory of the game whose data is being loaded.
	 * @return The list reperesnting the data of all the parts in this game
	 */
	public static Map<String, Map<String, Object>> loadGameData(
			String pathToRootDirFile) {
		return loadGameManager(pathToRootDirFile).getAllPartData();
	}

	/**
	 * Gets a copy of the data representing all the parts of this game. Altering
	 * this list will not alter the actual game data in any way.
	 * 
	 * @return
	 * 
	 * @return A copy of all the part data in this game, stored as a list of
	 *         maps.
	 */
	public Map<String, Map<String, Object>> getAllPartData() {
		return new HashMap<String, Map<String, Object>>(userParts);
	}

	/**
	 * Gets the part type by looking at what comes before the first "_" in the
	 * name
	 * 
	 * @param partName
	 *            The name of the part
	 * @return The type of part, i.e. "Tower", "Projectile", "Unit", etc.
	 */
	/*
	 * private static String partTypeFromName(String partName) { return
	 * partName.substring(0, partName.indexOf("_")); }
	 */

	/*
	 * //if you're using a class like TowerEditor, get the word "Tower" from it
	 * //not sure if this method's useful yet, or in its best form public static
	 * String getPartType(Object o){ String className = o.getClass().toString();
	 * return className.substring(0, className.indexOf("Editor")); }
	 */

	/*
	 * /**
	 * 
	 * @param partName The name of the part you want to update
	 * 
	 * @param param Which parameter of that part you want to update
	 * 
	 * @param newData The new value of that parameter's data, as a String
	 */
	/*
	 * public void updatePart(String partName, String param, String newData) {
	 * Map<String, Object> partToBeUpdated = userParts.get(partName); Object
	 * data = "data incorrectly added"; try { data =
	 * partToBeUpdated.get(param).getClass().getConstructor(String.class)
	 * .newInstance(newData); } catch (InstantiationException |
	 * IllegalAccessException | IllegalArgumentException |
	 * InvocationTargetException | NoSuchMethodException | SecurityException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * partToBeUpdated.put(param, data); }
	 * 
	 * public void updatePart(String partName, String param, Object newData) {
	 * userParts.get(partName).put(param, newData); }
	 */

	/**
	 * 
	 * @param gameName
	 *            The name of the game from which you want to load a part
	 * @param partName
	 *            The name of the part from that game you want to load
	 * @return The part in Map<String, Object> form
	 * @throws IOException
	 */

	public Map<String, Object> getPartFromXML(String fileLocation)
			throws IOException {

		return (Map<String, Object>) XMLWriter.fromXML(fileLocation);
	}

	@Override
	public String toString() {
		StringBuilder toPrint = new StringBuilder();
		for (String key : userParts.keySet())
			toPrint.append("Key: " + key).append(" = \n")
					.append("    " + userParts.get(key).toString() + "\n");
		return toPrint.toString();
	}

	public String getName() {
		return gameName;
	}

	/**
	 * At the moment, this adds a default part, but this probably won't end up
	 * being used
	 * 
	 * @param partType
	 *            the kind of part to be added
	 * @return the part that was added
	 */
	/*
	 * public Map<String, Object> addPart(String partType){ Map<String, Object>
	 * newPart = GameManager.createDefaultPart(partType); String partName =
	 * partType + "_" + "Part_" + new Integer(partsCreated++).toString();
	 * userParts.put(partName, newPart); return newPart; }
	 */

	public static void main(String[] args) {

		// gameRootDirectory will be chosen by the user, but here we're just
		// putting an an example
		InstanceManager example = GameCreator.createNewGame(
				"TestingManagerGame", gameRootDirectory);
		// hardcode in an example part to show how it works
		List<String> params = new ArrayList<String>();
		params.add("HP");
		params.add("Range");
		List<Object> data = new ArrayList<Object>();
		data.add(new Integer(500));
		data.add(new Double(1.5));
		example.addPart("Tower", "MyFirstTower", params, data);
		List<String> params2 = new ArrayList<String>();
		params2.add("HP");
		params2.add("Speed");
		params2.add("Damage");
		List<Object> data2 = new ArrayList<Object>();
		data2.add(new Integer(100));
		data2.add(new Double(1.5));
		data2.add(new Double(10));
		example.addPart("Enemy", "MyFirstEnemy", params2, data2);
		example.saveGame();
		// this method is called with the path to the .game file, which will be
		// received from the user
		Map<String, Map<String, Object>> gamedata = InstanceManager
				.loadGameData(gameRootDirectory
						+ "/TestingManagerGame/TestingManagerGame.game");
		InstanceManager loadedIn = InstanceManager
				.loadGameManager(gameRootDirectory
						+ "/TestingManagerGame/TestingManagerGame.game");
		System.out.println("GAME DATA AS MAP: \n" + gamedata.toString());
		System.out.println("GAME AS MANAGER: \n" + loadedIn.toString());

		/*
		 * InstanceManager gameManager = new InstanceManager("TestGame");
		 * GameManager.createGameFolders(gameManager.getName());
		 * gameManager.addPart(TOWER); gameManager.addPart(UNIT);
		 * gameManager.addPart(PROJECTILE); gameManager.addPart(PROJECTILE);
		 * gameManager.addPart(UNIT); gameManager.addPart(UNIT);
		 * gameManager.addPart(TOWER); System.out.println(gameManager);
		 * 
		 * // TODO: Remove hardcoded "magic values" // Or if this is a test,
		 * then ignore this. gameManager.updatePart("Tower_Part_0", "HP",
		 * "5000"); gameManager.updatePart("Tower_Part_0", "FireRate", "8");
		 * gameManager.updatePart("Unit_Part_4", "Speed", "3");
		 * System.out.println(gameManager);
		 * 
		 * gameManager.writeAllPartsToXML(); // example of overwriting a file //
		 * XMLWriter.toXML(new String("testing"), "Projectile_Part_2", //
		 * userDataPackage + "\\TestGame\\Projectile"); String stringyDir =
		 * XMLWriter.toXML(new String("String theory"), "stringy");
		 * XMLWriter.toXML(new String("hascode class test")); String
		 * stringyLoaded = (String) XMLWriter.fromXML(stringyDir);
		 * System.out.println("Stringy test: " + stringyLoaded); try {
		 * System.out.println("from xml: " +
		 * gameManager.getPartFromXML("Tower_Part_0")); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace();
		 * 
		 * }
		 */
	}
}
