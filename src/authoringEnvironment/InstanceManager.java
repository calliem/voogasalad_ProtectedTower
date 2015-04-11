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

import util.misc.SetHandler;
import javafx.collections.ObservableList;

public class InstanceManager {
	// public static final ResourceBundle paramLists =
	// ResourceBundle.getBundle("resources/part_parameters");
	private static final String userDataPackage = System
			.getProperty("user.dir").concat("/src/myTowerGames");
	private static final String gameRootDirectory = System.getProperty(
			"user.dir").concat("/src/exampleUserData");
	public static final String partTypeKey = "PartType";
	public static final String nameKey = "Name";
	public static final String noNameKey = "Map passed must contain a \"Name\" key. Key is case sensitive.";
	public static final String listSizesDiffer = "Lists passed must contain same number of elements.";

	// a map of all the parts the user has created
	// each part is represented by a map mapping the part's parameters to their
	// data
	// the fields look like: Map<partName, Map<parameterName, parameterData>>
	// private Map<String, Map<String, Object>> userParts;

	private List<Map<String, Object>> userParts;
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
	 * @param rootDirBeforeGameName
	 *            The root directory in which all these parts and all other game
	 *            information is stored.
	 */
	public InstanceManager(String name, List<Map<String, Object>> partData,
			String rootDirBeforeGameName) {
		gameName = name;
		userParts = partData;
		rootDirectory = rootDirBeforeGameName;
	}

	public InstanceManager() {
		this("Unnamed_Game", new ArrayList<Map<String, Object>>(),
				userDataPackage + "/Unnamed_Game");
	}

	public InstanceManager(String name) {
		this(name, new ArrayList<Map<String, Object>>(), userDataPackage + "/"
				+ name);
	}

	public InstanceManager(String name, List<Map<String, Object>> partData) {
		this(name, partData, userDataPackage + "/" + name);
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
	public Map<String, Object> addPart(String partType, String partName,
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
		return toAdd;
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
	public Map<String, Object> addPart(String partType, Map<String, Object> part) {
		part.put(partTypeKey, partType);
		try {
			addMapToParts(part);
		} catch (DataFormatException e) {
			System.err.println("Part was not added.");
		}
		return part;
	}

	private void addMapToParts(Map<String, Object> partToCheck)
			throws DataFormatException {
		if (partToCheck.keySet().contains("Name"))
			userParts.add(partToCheck);
		else
			throw new DataFormatException(noNameKey);
	}

	/**
	 * Writes the part of partName into an XML file
	 * 
	 * @param partName
	 *            The part to write to XML
	 */
	private void writePartToXML(Map<String, Object> part, String rootDir) {
		String partType = (String) part.get(partTypeKey);
		String partName = (String) part.get(nameKey);
		// partName = partType + "_" + partName;
		String partFileName = partName + ".xml";
		String dir = rootDir + "/" + partType;
		XMLWriter.toXML(part, partFileName, dir);
	}

	/**
	 * Gets the part type by looking at what comes before the first "_" in the
	 * name
	 * 
	 * @param partName
	 *            The name of the part
	 * @return The type of part, i.e. "Tower", "Projectile", "Unit", etc.
	 */
	private static String partTypeFromName(String partName) {
		return partName.substring(0, partName.indexOf("_"));
	}

	/**
	 * Writes all parts of the current game into their respective files
	 */
	public void writeAllPartsToXML(String rootDir) {
		for (Map<String, Object> part : userParts)
			writePartToXML(part, rootDir);
	}

	/**
	 * Writes the Map<partName, [part data]> into an XML file called
	 * [gameName]Parts.xml
	 */

	public List<Map<String, Object>> getAllPartData() {
		return new ArrayList<Map<String, Object>>(userParts);
	}

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
		for (Map<String, Object> part : userParts)
			toPrint.append(part.toString() + "\n");
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
		// putting an exmaple folder
		GameManager.createNewGame("ExampleGame", gameRootDirectory);
		// hardcode in an example part to show how it works
		List<String> params = new ArrayList<String>();
		params.add("HP");
		params.add("Range");
		List<Object> data = new ArrayList<Object>();
		data.add(new Integer(500));
		data.add(new Double(1.5));
		GameManager.addPartToGame("Tower", "MyFirstTower", params, data);
		List<String> params2 = new ArrayList<String>();
		params2.add("HP");
		params2.add("Speed");
		params2.add("Damage");
		List<Object> data2 = new ArrayList<Object>();
		data2.add(new Integer(100));
		data2.add(new Double(1.5));
		data2.add(new Double(10));
		GameManager.addPartToGame("Unit", "MyFirstEnemy", params2, data2);
		GameManager.saveGame();
		GameManager
				.loadPart("C:/Users/Johnny/workspace/voogasalad_ProtectedTower/src/exampleUserData/ExampleGame/Tower/MyFirstTower.xml");
		List<Map<String, Object>> game = GameManager.loadGame(gameRootDirectory
				+ "/ExampleGame/ExampleGame.game");
		System.out
				.println("load? "
						+ GameManager.loadPartFromFileName("Tower",
								"MyFirstTower.xml"));
		System.out.println("all data: ");
		System.out.println(game.toString());
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
