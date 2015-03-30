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

	//adds a default part to userParts with the name "Part_x" where x the number of parts the user has created
	public Map<String, Object> addPart(String partType){
		Map<String, Object> newPart = GameCreator.createDefaultPart(partType);
		String partName =  partType + "_" + "Part_" + new Integer(partsCreated++).toString();
		userParts.put(partName, newPart);
		return newPart;
	}

	public static String getPartType(Object o){
		String className = o.getClass().toString();
		return className.substring(0, className.indexOf("Editor"));
	}
	
	//updates data
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
	public void writePartToXML(String partName){
		XStream stream = new XStream(new DomDriver());
		String partType = partTypeFromName(partName);
		String partFileName = partName + ".xml";
		String dirLocation = userDataPackage + "\\" + gameName + "\\" + partType;
		File partFile = new File(dirLocation, partFileName);
		
		try {
			PrintStream out = new PrintStream(partFile);
			out.println(stream.toXML(userParts.get(partName)));
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Map<String, Object> getPartFromXML(String gameName, String partName) throws IOException{
		XStream stream = new XStream(new DomDriver());
		String fileLocation = userDataPackage + "\\" + 
		gameName + "\\" +
		partTypeFromName(partName) + "\\" + 
		partName + ".xml";
		File partFile = new File(fileLocation);
		return (Map<String, Object>) stream.fromXML(partFile);
	}
	
	private static String partTypeFromName(String partName){
		return partName.substring(0, partName.indexOf("_"));
	}
	//from stack overflow
	private static String readFile(String fileName) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (fileName));
	    String         line = null;
	    StringBuilder  xmlText = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while((line = reader.readLine()) != null) {
	        xmlText.append(line).append(ls);
	    }
	    return xmlText.toString();
	}
	
	//writes all the parts to their repsective files
	public void writeAllToXML(){
		for(String partName : userParts.keySet())
			writePartToXML(partName);
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
		//gameManager.writeAllToXML();
		gameManager.writeAllToXML();
		try {
			System.out.println("from xml: " + InstanceManager.getPartFromXML("TestGame", "Tower_Part_0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



