
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

import javafx.collections.ObservableList;

public class InstanceManager {
	String gameName;
	private int partsCreated = 0;
	//public static final ResourceBundle paramLists = ResourceBundle.getBundle("resources/part_parameters");
	private static final String userDataPackage = System.getProperty("user.dir").concat("/src/userData");
	private static final String gameRootDirectory = System.getProperty("user.dir").concat("/src/exampleUserData");
	
	private static final String TOWER = "Tower";
	private static final String UNIT = "Unit";
	private static final String PROJECTILE = "Projectile";
	public static final String partTypeKey = "PartType";
	public static final String nameKey = "Name";

	// a map of all the parts the user has created
	// each part is represented by a map mapping the part's parameters to their
	// data
	// the fields look like: Map<partName, Map<parameterName, parameterData>>
//	private Map<String, Map<String, Object>> userParts;

	private List<Map<String, Object>> userParts;
	
	public InstanceManager(String name) {
		this();
		gameName = name;
	}

	public InstanceManager() {
		userParts = new ArrayList<Map<String, Object>>();
		gameName = "Unnamed_Game";
	}
	
	public InstanceManager(String name, List<Map<String, Object>> partData){
		this(name);
		userParts = partData;
	}

	/**
	 * This is how parts will be added from the Editor windows like TowerEditor
	 * 
	 * @param partType
	 *            The type of part, i.e. "Tower"
	 * @param partName
	 *            The name of the part, i.e. "IceShooter"
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
		// populates the map of param name to data
		Map<String, Object> part = new HashMap<String, Object>();
		for (int i = 0; i < params.size(); i++)
			part.put(params.get(i), data.get(i));
		part.put(partTypeKey, partType);
		part.put(nameKey, partName);
		// adds this part to user parts with it's updated part name
		userParts.add(part);
		// writes this part to xml with the filename partName.xml (i.e.
		// "Tower_IceTower.xml")
		return part;
	}
	
	public Map<String, Object> addPart(String partType, Map<String, Object> part){
		part.put(partTypeKey, partType);
		userParts.add(part);
		return part;
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
		//partName = partType + "_" + partName;
		String partFileName = partName + ".xml";
		String dir= rootDir + "/" + partType;
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
	
	public List<Map<String, Object>> getAllPartData(){
		return new ArrayList<Map<String, Object>>(userParts);
	}

	/*
	 * //if you're using a class like TowerEditor, get the word "Tower" from it
	 * //not sure if this method's useful yet, or in its best form public static
	 * String getPartType(Object o){ String className = o.getClass().toString();
	 * return className.substring(0, className.indexOf("Editor")); }
	 */

	/*
	/**
	 * 
	 * @param partName
	 *            The name of the part you want to update
	 * @param param
	 *            Which parameter of that part you want to update
	 * @param newData
	 *            The new value of that parameter's data, as a String
	 */
	/*
	public void updatePart(String partName, String param, String newData) {
		Map<String, Object> partToBeUpdated = userParts.get(partName);
		Object data = "data incorrectly added";
		try {
			data = partToBeUpdated.get(param).getClass().getConstructor(String.class)
					.newInstance(newData);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		partToBeUpdated.put(param, data);
	}

	public void updatePart(String partName, String param, Object newData) {
		userParts.get(partName).put(param, newData);
	}
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
	
	public Map<String, Object> getPartFromXML(String fileLocation) throws IOException {
		
		return (Map<String, Object>) XMLWriter.fromXML(fileLocation);
	}

	@Override
	public String toString() {
		StringBuilder toPrint = new StringBuilder();
		for (Map<String, Object> part: userParts)
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
	public Map<String, Object> addPart(String partType){
		Map<String, Object> newPart = GameManager.createDefaultPart(partType);
		String partName =  partType + "_" + "Part_" + new Integer(partsCreated++).toString();
		userParts.put(partName, newPart);
		return newPart;
	}
	*/

	public static void main(String[] args) {
		
		//gameRootDirectory will be chosen by the user, but here we're just putting an exmaple folder
		GameManager.createNewGame("ExampleGame", gameRootDirectory);
		//hardcode in an example part to show how it works
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
		GameManager.loadPart("C:/Users/Johnny/workspace/voogasalad_ProtectedTower/src/exampleUserData/ExampleGame/Tower/MyFirstTower.xml");
		List<Map<String, Object>> game = GameManager.loadGame(gameRootDirectory + "/ExampleGame/ExampleGame.game");
		System.out.println("load? " + GameManager.loadPartFromFileName("Tower", "MyFirstTower.xml"));
		System.out.println("all data: ");
		System.out.println(game.toString());
		/*
		InstanceManager gameManager = new InstanceManager("TestGame");
		GameManager.createGameFolders(gameManager.getName());
		gameManager.addPart(TOWER);
		gameManager.addPart(UNIT);
		gameManager.addPart(PROJECTILE);
		gameManager.addPart(PROJECTILE);
		gameManager.addPart(UNIT);
		gameManager.addPart(UNIT);
		gameManager.addPart(TOWER);
		System.out.println(gameManager);

		// TODO: Remove hardcoded "magic values"
		// Or if this is a test, then ignore this.
		gameManager.updatePart("Tower_Part_0", "HP", "5000");
		gameManager.updatePart("Tower_Part_0", "FireRate", "8");
		gameManager.updatePart("Unit_Part_4", "Speed", "3");
		System.out.println(gameManager);

		gameManager.writeAllPartsToXML();
		// example of overwriting a file
		// XMLWriter.toXML(new String("testing"), "Projectile_Part_2",
		// userDataPackage + "\\TestGame\\Projectile");
		String stringyDir = XMLWriter.toXML(new String("String theory"), "stringy");
		XMLWriter.toXML(new String("hascode class test"));
		String stringyLoaded = (String) XMLWriter.fromXML(stringyDir);
		System.out.println("Stringy test: " + stringyLoaded);
		try {
			System.out.println("from xml: " + gameManager.getPartFromXML("Tower_Part_0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}*/
	}
}
