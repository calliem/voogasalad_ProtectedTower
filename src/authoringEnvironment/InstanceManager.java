package authoringEnvironment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is a container which stores all the data in game creation. It acts as the backend for
 * the authoring environment and keeps track of all parts of the game that the user creates.
 * 
 * @author Johnny Kumpf
 */
public class InstanceManager {
    // public static final ResourceBundle paramLists =
    // ResourceBundle.getBundle("resources/part_parameters");
    private static final String DEFAULT_SAVE_LOCATION =
            System.getProperty("user.dir").concat("/gamedata/myTowerGames");
    private static final String GAME_ROOT_DIRECTORY =
            System.getProperty("user.dir").concat("/gamedata/exampleUserData");

    private static final String PARTS_FILE_NAME = "GameParts.xml";
    private static final String INSTANCE_MANAGER_FILE_NAME = "GameManager.xml";

    /**
     * a map of all the parts the user has created each part is represented by a map mapping the
     * part's parameters to their data the fields look like:
     * Map<partName, Map<parameterName, parameterData>>
     */
    private Map<String, Map<String, Object>> userParts;
    private String gameName;
    private String rootDirectory;
    private static int partID;

    public static final String PART_KEY_KEY = "PartKey";
    private static final String MISSING_NAME_KEY_MESSAGE =
            "Map passed must contain a \"Name\" key. Key is case sensitive.";

    public static final String PARTS_FILE_DIRECTORY = "/AllPartData";
    public static final String PART_TYPE_KEY = "PartType";
    public static final String NAME_KEY = "name";
    public static final String TAGS_KEY = "Tags";


    public static final String IMAGE_KEY = "imagePath";
    public static final String SAVE_PATH_KEY = "SavePath";

    private static final String NO_KEYS_MISSING = "no keys missing";

    /**
     * Generates an instance manager for a game. An InstanceManager has a name
     * (the game name), data, which is part data stored as a List of maps, and a
     * root directory, in which all the parts are saved in various folders.
     * 
     * @param name
     *        The name of the game for which this InstanceManager manages
     *        part data.
     * @param partData
     *        The data maps corresponding to all the parts in this game
     * @param rootDir
     *        The root directory in which all these parts and all other game
     *        information is stored.
     */
    public InstanceManager (String name,
                            Map<String, Map<String, Object>> partData, String rootDir) {
        gameName = name;
        userParts = partData;
        rootDirectory = rootDir;
        partID = 0;
    }

    public InstanceManager () {
        this("Unnamed_Game", new HashMap<String, Map<String, Object>>(),
             DEFAULT_SAVE_LOCATION + "/Unnamed_Game");

    }

    public InstanceManager (String name) {
        this(name, new HashMap<String, Map<String, Object>>(),
             DEFAULT_SAVE_LOCATION + "/" + name);
    }

    public InstanceManager (String name, String rootDir) {
        this(name, new HashMap<String, Map<String, Object>>(), rootDir);
    }

    public InstanceManager (String name,
                            Map<String, Map<String, Object>> partData) {
        this(name, partData, DEFAULT_SAVE_LOCATION + "/" + name);
    }

    public String addPart (Map<String, Object> fullPartMap) throws MissingInformationException {
        String key = generateKey(fullPartMap);
        fullPartMap.put(PART_KEY_KEY, key);
        return addPart(key, fullPartMap);
    }

    public String addPart (String key, Map<String, Object> fullPartMap)
                                                                       throws MissingInformationException {
        fullPartMap.put(PART_KEY_KEY, key);
        String missingKey = checkMissingInformation(fullPartMap);
        if (!missingKey.equals(NO_KEYS_MISSING))
            throw new MissingInformationException(missingKeyErrorMessage(missingKey));
        //keep the tags
        System.out.println(fullPartMap);
        System.out.println(key);
        System.out.println(userParts.get(key));
        System.out.println(userParts);
        fullPartMap.put(TAGS_KEY, userParts.get(key).get(TAGS_KEY));
        userParts.put(key, fullPartMap);
        writePartToXML(fullPartMap);
        return key;
    }

    private String missingKeyErrorMessage (String missingKey) {
        return "Map must contain \"" + missingKey + "\" key.";
    }

    private String generateKey (Map<String, Object> part) {
        return gameName + "_Part" + (partID++) + "." + part.get(PART_TYPE_KEY);
    }

    private String checkMissingInformation (Map<String, Object> partToCheck) {
        String missingKey = NO_KEYS_MISSING;
        List<String> necessaryKeys = new ArrayList<String>();
        necessaryKeys.add(NAME_KEY);
        necessaryKeys.add(PART_TYPE_KEY);
        necessaryKeys.add(PART_KEY_KEY);
        for (String key : necessaryKeys)
            if (!partToCheck.keySet().contains(key))
                missingKey = key;
        return missingKey;
    }

    /**
     * Writes the part, passed as a Map, into an XML file in:
     * rootDirectory/partType/partName.xml, for example:
     * ...myTowerGames/Tower/IceShootingTower.xml
     * 
     * @param part
     *        The part to write to XML
     * @return the path to the file that was written
     */
    private String writePartToXML (Map<String, Object> part) {
        String partType = (String) part.get(PART_TYPE_KEY);
        String partFileName = (String) part.get(NAME_KEY) + ".xml";
        String directory = rootDirectory + "/" + partType;
        return XMLWriter.toXML(part, partFileName, directory);
    }

    /**
     * Writes all parts of the current game into their respective files
     */
    public void writeAllPartsToXML () {
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
    public String saveGame () {
        writeAllPartsToXML();
        // XMLWriter.toXML(userParts, partFileName, rootDirectory +
        // partsFileDir);
        System.out.println("writing to xml manager");
        return XMLWriter.toXML(this, INSTANCE_MANAGER_FILE_NAME, rootDirectory +
                                                                 PARTS_FILE_DIRECTORY);
    }

    /**
     * Loads the InstanceManager object that's stored in a data file. The path
     * to this data file is found in the file that the String argument leads to.
     * This path will lead to a .game file. This method can only be called by
     * the authoring environment.
     * 
     * @param pathToRootDirFile
     *        The location of the file that holds the path to the root
     *        directory of the game being loaded.
     * @return the InstanceManager of the game specified by the path in the file
     *         specified by the argument
     */
    protected static InstanceManager loadGameManager (String pathToRootDirFile) {
        String toTrimOff = (String) XMLWriter.fromXML(pathToRootDirFile);
        String rootDirectory = pathToRootDirFile.substring(0, pathToRootDirFile.indexOf(toTrimOff));
        return (InstanceManager) XMLWriter.fromXML(rootDirectory + PARTS_FILE_DIRECTORY
                                                   + "/" + INSTANCE_MANAGER_FILE_NAME);
    }

    /**
     * Gets a copy of the List of part data stored in the game specified by the
     * argument.
     * 
     * @param pathToRootDirFile
     *        The location of the file that holds the path to the root
     *        directory of the game whose data is being loaded.
     * @return The list reperesnting the data of all the parts in this game
     */
    public static Map<String, Map<String, Object>> loadGameData (
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
    public Map<String, Map<String, Object>> getAllPartData () {
        return new HashMap<String, Map<String, Object>>(userParts);
    }

    public void specifyPartImage (String partKey, int[][] image) {
        userParts.get(partKey).put(IMAGE_KEY, image);
    }
    
    protected void addTagToPart(String partKey, String tag){
        Map<String, Object> addTagTo = userParts.get(partKey);
        if(addTagTo.containsKey(TAGS_KEY)){
           List<String> tagList =  (List<String>) userParts.get(TAGS_KEY);
           tagList.add(tag);
           addTagTo.put(TAGS_KEY, tagList);
        }
        else{
            List<String> tagList = new ArrayList<String>();
            tagList.add(tag);
            addTagTo.put(TAGS_KEY, tagList);
        }
        
    }
    
    public boolean containsKey(String key){
        return userParts.containsKey(key);
    }

    /**
     * 
     * @param gameName
     *        The name of the game from which you want to load a part
     * @param partName
     *        The name of the part from that game you want to load
     * @return The part in Map<String, Object> form
     * @throws IOException
     */

    public Map<String, Object> getPartFromXML (String fileLocation)
                                                                   throws IOException {

        return (Map<String, Object>) XMLWriter.fromXML(fileLocation);
    }

    @Override
    public String toString () {
        StringBuilder toPrint = new StringBuilder();
        for (String key : userParts.keySet())
            toPrint.append("Key: " + key).append(" = \n")
                    .append("    " + userParts.get(key).toString() + "\n");
        return toPrint.toString();
    }

    public String getName () {
        return gameName;
    }

    public static void main (String[] args) {
        Map<String, Map<String, Object>> data =
                InstanceManager
                        .loadGameData(System.getProperty("user.dir") +
                                      "/data/TestingTesting123/ExampleGame/ExampleGame.gamefile");
        System.out.println("data: " + data);
    }
}
