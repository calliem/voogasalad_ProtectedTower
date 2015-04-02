

package authoringEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class InstanceManager {
	String gameName;
	private int partsCreated = 0;
	
	//public static final ResourceBundle paramLists = ResourceBundle.getBundle("resources/part_parameters");
	private static final String userDataPackage = System.getProperty("user.dir").concat("\\src\\userData");
	
	private static final String TOWER = "Tower";
	private static final String UNIT = "Unit";
	private static final String PROJECTILE = "Projectile";
	
	
	//a map of all the parts the user has created
	//each part is represented by a map mapping the part's parameters to their data
	//the fields look like: Map<partName, Map<parameterName, parameterData>>
	private Map<String, Map<String, Object>> userParts;

	public InstanceManager(String name){
		this();
		gameName = name;
	}
	
	public InstanceManager(){
		userParts = new HashMap<String, Map<String, Object>>();
		gameName = "Unnamed_Game";
		
	}

	/**
	 * This is how parts will be added from the Editor windows like TowerEditor
	 * @param partType The type of part, i.e. "Tower"
	 * @param partName The name of the part, i.e. "IceShooter"
	 * @param params List of the parameters that this part needs, i.e. "HP", "Range", "Projectile"
	 * @param data List of corresponding data values for those params, i.e. 1, 1.0, "Projectile1.xml"
	 * @return The part that was created and added top user's parts
	 */
	public Map<String, Object> addPart(String partType, String partName, List<String> params, List<Object> data){
		//appends the part type onto the start of the name
		//Ex: "IceTower" becomes "Tower_IceTower"
		partName = partType + "_" + partName;
		//populates the map of param name to data
		Map<String, Object> part = new HashMap<String, Object>();
		for(int i = 0; i < params.size(); i++)
			part.put(params.get(i),  data.get(i));
		//adds this part to user parts with it's updated part name
		userParts.put(partName, part);
		//writes this part to xml with the filename partName.xml (i.e. "Tower_IceTower.xml")
		writePartToXML(partName);
		return part;
	}

	/**
	 * Writes the part of partName into an XML file
	 * @param partName The part to write to XML
	 */
	private void writePartToXML(String partName){
		String partType = partTypeFromName(partName);
		String partFileName = partName + ".xml";
		String dir= userDataPackage + "\\" + gameName + "\\" + partType;
		XMLWriter.toXML(userParts.get(partName), partFileName, dir);
	}

	/**
	 * Gets the part type by looking at what comes before the first "_" in the name
	 * @param partName The name of the part
	 * @return The type of part, i.e. "Tower", "Projectile", "Unit", etc.
	 */
	private static String partTypeFromName(String partName){
		return partName.substring(0, partName.indexOf("_"));
	}

	/**
	 * Writes all parts of the current game into their respective files
	 */
	public void writeAllPartsToXML(){
		for(String partName : userParts.keySet())
			writePartToXML(partName);
	}
	
	/**
	 * Writes the Map<partName, [part data]> into an XML file called [gameName]Parts.xml
	 */
	public void writeGameToXML(){
		String dir = userDataPackage + "\\" + gameName + "\\GameFile";
		XMLWriter.toXML(userParts, gameName + "Parts.xml", dir);
	}

	/*
	//if you're using a class like TowerEditor, get the word "Tower" from it
	//not sure if this method's useful yet, or in its best form
	public static String getPartType(Object o){
		String className = o.getClass().toString();
		return className.substring(0, className.indexOf("Editor"));
	}*/

	/**
	 * 
	 * @param partName The name of the part you want to update
	 * @param param Which parameter of that part you want to update
	 * @param newData The new value of that parameter's data, as a String
	 */
	public void updatePart(String partName, String param, String newData){
		Map<String, Object> partToBeUpdated = userParts.
				get(partName);
		Object data = "data incorrectly added";
		try {
			data = partToBeUpdated.get(param).getClass().getConstructor(String.class).newInstance(newData);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		partToBeUpdated.put(param,  data);
	}


	public void updatePart(String partName, String param, Object newData){
		userParts.get(partName).put(param,  newData);
	}



	/**
	 * 
	 * @param gameName The name of the game from which you want to load a part
	 * @param partName The name of the part from that game you want to load
	 * @return The part in Map<String, Object> form
	 * @throws IOException
	 */
	public  Map<String, Object> getPartFromXML(String partName) throws IOException{
		String fileLocation = userDataPackage + "\\" + gameName + "\\" +
				partTypeFromName(partName) + "\\" + partName + ".xml";
		return (Map<String, Object>) XMLWriter.fromXML(fileLocation);
	}


	@Override
	public String toString(){
		StringBuilder toPrint = new StringBuilder();
		for(String partName : userParts.keySet())
			toPrint.append("Name: ")
			.append(partName)
			.append(", Params: ")
			.append(userParts.get(partName).toString())
			.append("\n");
		return toPrint.toString();
	}
	
	public String getName(){
		return gameName;
	}
	

	/**
	 * At the moment, this adds a default part, but this probably won't end up being used
	 * @param partType the kind of part to be added
	 * @return the part that was added
	 */
	public Map<String, Object> addPart(String partType){
		Map<String, Object> newPart = GameCreator.createDefaultPart(partType);
		String partName =  partType + "_" + "Part_" + new Integer(partsCreated++).toString();
		userParts.put(partName, newPart);
		return newPart;
	}


	public static void main (String[] args){
		InstanceManager gameManager = new InstanceManager("TestGame");
		GameCreator.createNewGameFolder(gameManager.getName());
		gameManager.addPart(TOWER);
		gameManager.addPart(UNIT);
		gameManager.addPart(PROJECTILE);
		gameManager.addPart(PROJECTILE);
		gameManager.addPart(UNIT);
		gameManager.addPart(UNIT);
		gameManager.addPart(TOWER);
		System.out.println(gameManager);
		
		//TODO: Remove hardcoded "magic values"
		//Or if this is a test, then ignore this.
		gameManager.updatePart("Tower_Part_0", "HP",  "5000");
		gameManager.updatePart("Tower_Part_0", "FireRate",  "8");
		gameManager.updatePart("Unit_Part_4", "Speed", "3");
		System.out.println(gameManager);


		gameManager.writeAllPartsToXML();
		//example of overwriting a file
		//XMLWriter.toXML(new String("testing"), "Projectile_Part_2", 
		//userDataPackage + "\\TestGame\\Projectile");
		String stringyDir = XMLWriter.toXML(new String("String theory"), "stringy");
		XMLWriter.toXML(new String("hascode class test"));
		String stringyLoaded = (String) XMLWriter.fromXML(stringyDir);
		System.out.println("Stringy test: " + stringyLoaded);
		try {
			System.out.println("from xml: " + gameManager.getPartFromXML("Tower_Part_0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



