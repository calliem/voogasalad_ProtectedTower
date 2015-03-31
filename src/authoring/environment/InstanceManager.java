package authoring.environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class InstanceManager {

	private String gameName;
	private int partsCreated = 0;
	
	//public static final ResourceBundle paramLists = ResourceBundle.getBundle("resources/part_parameters");
	private static final String userDataPackage = System.getProperty("user.dir").concat("\\src\\userData");
	
	
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
	 * 
	 * @param partType the kind of part to be added
	 * @return the part that was added
	 */
	public Map<String, Object> addPart(String partType){
		Map<String, Object> newPart = GameCreator.createDefaultPart(partType);
		String partName =  partType + "_" + "Part_" + new Integer(partsCreated++).toString();
		userParts.put(partName, newPart);
		return newPart;
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
		Map<String, Object> partToBeUpdated = userParts.get(partName);
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
	
	//5:09am
	/**
	 * 
	 * @param partName The part to write to XML
	 */
	public void writePartToXML(String partName){
		String partType = partTypeFromName(partName);
		String partFileName = partName + ".xml";
		String dir= userDataPackage + "\\" + gameName + "\\" + partType;
		XMLWriter.toXML(userParts.get(partName), partFileName, dir);
	}
	
	/**
	 * 
	 * @param gameName The name of the game from which you want to load a part
	 * @param partName The name of the part from that game you want to load
	 * @return The part in Map<String, Object> form
	 * @throws IOException
	 */
	public static Map<String, Object> getPartFromXML(String gameName, String partName) throws IOException{
		String fileLocation = userDataPackage + "\\" + gameName + "\\" +
		partTypeFromName(partName) + "\\" + partName + ".xml";
		return (Map<String, Object>) XMLWriter.fromXML(fileLocation);
	}
	
	/**
	 * 
	 * @param partName The name of the part
	 * @return The type of part, i.e. Tower, Projectile, Unit, etc.
	 */
	private static String partTypeFromName(String partName){
		return partName.substring(0, partName.indexOf("_"));
	}
	
	
	/**
	 * writes all parts of the current game into their respective files
	 */
	public void writeAllToXML(){
		for(String partName : userParts.keySet())
			writePartToXML(partName);
	}
	
	@Override
	public String toString(){
		StringBuilder toPrint = new StringBuilder();
		toPrint.append("All Parts In Game: \n");
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
	

	public static void main (String[] args){
		InstanceManager gameManager = new InstanceManager("TestGame");
		GameCreator.createNewGameFolder(gameManager.getName());
		gameManager.addPart("Tower");
		gameManager.addPart("Unit");
		gameManager.addPart("Projectile");
		gameManager.addPart("Projectile");
		gameManager.addPart("Unit");
		gameManager.addPart("Unit");
		gameManager.addPart("Tower");
		System.out.println(gameManager);
		gameManager.updatePart("Tower_Part_0", "HP",  "5000");
		gameManager.updatePart("Tower_Part_0", "FireRate",  "8");
		gameManager.updatePart("Unit_Part_4", "Speed", "3");
		System.out.println(gameManager);
		
		gameManager.writeAllToXML();
		//example of overwriting a file
		//XMLWriter.toXML(new String("testing"), "Projectile_Part_2", 
				//userDataPackage + "\\TestGame\\Projectile");
		XMLWriter.toXML(new String("String theory"), "stringy");
		XMLWriter.toXML(new String("hascode class test"));
		try {
			System.out.println("from xml: " + InstanceManager.getPartFromXML("TestGame", "Tower_Part_0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



