package authoring.environment;

import java.io.File;
import java.io.FileNotFoundException;
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
        String partName =  partType + "_Part_" + new Integer(partsCreated++).toString();
        userParts.put(partName, newPart);
        return newPart;
    }

    public static String getPartType(Class c){
        return c.toString().substring(0, c.toString().indexOf("Editor"));
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
        partToBeUpdated.put(param, data);
    }

    //5:09am
    public void writePartToXML(String partName){
        XStream stream = new XStream(new DomDriver());
        String partType = partName.substring(0, partName.indexOf("_"));
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
    }




}


