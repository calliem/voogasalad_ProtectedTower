package authoring.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class InstanceManager {

	private int partsCreated = 0;
	private static final String paramListFile = "resources/part_parameters";
	private static final String paramSpecsFile = "resources/parameter_datatype";
	private static final String userDataPackage = System.getProperty("user.dir").concat("\\src\\userData");
	private static ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
	private static Set<String> dirsToBeCreated;
	
	//a map of all the parts the user has created
	//each part is represented by a map mapping the part's parameters to their data
	//the fields look like: Map<partName, Map<parameterName, parameterData>>
	private Map<String, Map<String, Object>> userParts;


	public InstanceManager(){
		userParts = new HashMap<String, Map<String, Object>>();
		dirsToBeCreated = dirsToMake();
	}

	//adds a default part to userParts with the name "Part_x" where x the number of parts the user has created
	public Map<String, Object> addPart(String partType){
		Map<String, Object> newPart = createDefaultPart(partType);
		String partName =  partType + "_" + "Part_" + new Integer(partsCreated++).toString();
		userParts.put(partName, newPart);
		return newPart;
	}

	public static String getPartType(Class c){
		return c.toString().substring(0, c.toString().indexOf("Editor"));
	}
	
	public void updatePart(String partName, String param, String newData){
		Map<String, Object> partToBeUpdated = userParts.get(partName);
		Object data = "data incorrectly added";
		try {
			data = partToBeUpdated
					.get(param)
					.getClass()
					.getConstructor(String.class)
					.newInstance(newData);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		partToBeUpdated.put(param,  data);
	}
	
	private Set<String> dirsToMake(){
		Set<String> dirsToMake = paramLists.keySet();
		for(String partType : dirsToMake)
			partType.concat("s");
		return dirsToMake;
	}

	//creates a default part of partType's type
	public Map<String, Object> createDefaultPart(String partType){

		Map<String, Object> part = new HashMap<String, Object>();
		
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
	private Object makeDefaultData(String dataType, String defaultVal){

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

	
	public String writeAllToXML(){
		
		XStream xstream = new XStream(new DomDriver());
		String xmlText = xstream.toXML(userParts);
		PrintStream out;
		try {
			File f = new File(userDataPackage, "testfile.xml");
			out = new PrintStream(f);
			System.out.println("SDfS");
			out.println(xmlText);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return xmlText;	
	}
	/*
	public String writePartToXML(String partName){
		String type = partName.substring(0, partName.indexOf("_"));
		String fileName = partName.concat(".xml");
		
		
	}*/
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
	
	public void createNewGameFolder(String gameName){
		String gameDir = userDataPackage.concat("\\").concat(gameName);
		new File(gameDir).mkdirs();
		for(String dir : dirsToBeCreated)
			new File(gameDir.concat("\\").concat(dir)).mkdirs();
	}

	public static void main (String[] args){
		InstanceManager gameManager = new InstanceManager();
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
		gameManager.createNewGameFolder("testGame");

	}




}


