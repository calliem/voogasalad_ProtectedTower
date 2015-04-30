package authoringEnvironment;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * This class is used to create a new game. Methods are included to create a new InstanceManager,
 * which is used in the creation of game elements for a game. The game can then be saved into xml
 * files.
 * 
 * @author Johnny Kumpf
 */
public class GameCreator {

    private static final String CLASS_LIST_FILE = "resources/class_list";
    private static final String GAME_EXTENSION = ".gamefile";
    public static final ResourceBundle CLASS_LIST =
            ResourceBundle.getBundle(CLASS_LIST_FILE);
    public static final String IMAGE_DATA_FOLDER = "/ImageData";

    // private static InstanceManager currentGame = new InstanceManager();

    /**
     * 
     * Creates a new game and all the appropriate sub directories
     * 
     * @param gameName
     *        The name of the new game to create
     * @param rootDirBeforeGameName
     *        The place where the game and subsequent folders will be
     *        created
     */
    public static InstanceManager createNewGame (String gameName, String rootDirBeforeGameName) {
        String gameDirectory = rootDirBeforeGameName + "/" + gameName;
        String gameFileName = gameName + GAME_EXTENSION;
        createGameFolders(gameName, rootDirBeforeGameName);
        XMLWriter.toXML(gameFileName, gameFileName, gameDirectory);
        return new InstanceManager(gameName, gameDirectory);
    }

    /**
     * Creates subdirectories for each kind of part, i.e. "Tower", "Unit", etc. in a subdirectory of
     * ... userdata\gameName
     * 
     * @param gameName String of the name of the game
     */
    private static void createGameFolders (String gameName, String rootDir) {
        Set<String> name = new HashSet<String>();
        name.add(gameName);
        XMLWriter.createDirectories(rootDir, name);
        XMLWriter.createDirectories(rootDir + "/" + gameName, foldersToCreate());
    }

    /**
     * creates a folder for each kind of part the user can make
     * 
     * @return
     */
    protected static Set<String> gameDirectories () {
        return CLASS_LIST.keySet();
    }
    
    protected static Set<String> foldersToCreate(){
        Set<String> toAdd = gameDirectories();
        toAdd.add(InstanceManager.PARTS_FILE_DIRECTORY);
        toAdd.add(IMAGE_DATA_FOLDER);
        return toAdd;
    }

    /*
     * public static void addPartToGame(String partType, String partName,
     * List<String> params, List<Object> data) {
     * currentGame.addPart(partType, partName, params, data);
     * }
     * 
     * 
     * public static void addPartToGame(String partType, List<Setting> settings) {
     * Map<String, Object> partToAdd = new HashMap<String, Object>();
     * for (Setting s : settings)
     * partToAdd.put(s.getParameterName(), s.getParameterValue());
     * currentGame.addPart(partType, partToAdd);
     * }
     */
    /*
     * addPartToGame("Wave", "IceGuysWave", {"Units", "Times"}, data)
     * List<Object> data = new List<Object>(); //filenames list.add(new
     * List<String>()); //times list.add(new List<Double>());
     */

    /**
     * Saves all the parts and the Map<partName, [part data]> into an XML file
     * called gameName + "Parts.xml" Ex: "TestGameParts.xml"
     * 
     * @param gameManager
     *        the InstanceManager of the game that's being saved
     */
    /*
     * public static String saveGame() {
     * currentGame.writeAllPartsToXML(userDataLocation);
     * return XMLWriter.toXML(currentGame.getAllPartData(), partFileName,
     * userDataLocation + partFileDir);
     * }
     */
    /**
     * Loads in the Map<partName, [part data]> representing all the parts of the
     * game
     * 
     * @param gameName
     *        The name of the game for which to load in the parts
     * @return
     */
    /*
     * public static List<Map<String, Object>> loadGame(
     * String pathToRootDirectoryFile) {
     * String[] nameAndDirectory = (String[]) XMLWriter
     * .fromXML(pathToRootDirectoryFile);
     * setUserDataLocation(nameAndDirectory[1]);
     * String dir = userDataLocation + partFileDir + "/" + partFileName;
     * System.out.println("dir loading: " + dir);
     * List<Map<String, Object>> allUserData = (List<Map<String, Object>>) XMLWriter
     * .fromXML(dir);
     * currentGame = new InstanceManager(nameAndDirectory[0], allUserData);
     * return allUserData;
     * }
     */

    /*
     * public static Map<String, Object> loadPart(String dir) {
     * System.out.println("loading: " + dir);
     * try {
     * return currentGame.getPartFromXML(dir);
     * } catch (IOException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     * return null;
     * }
     */

    /*
     * public static Map<String, Object> loadPartFromFileName(String partType,
     * String fileName) {
     * String dir = userDataLocation + "/" + partType + "/" + fileName;
     * return loadPart(dir);
     * }
     */

    /**
     * @param partType
     *        Part type name, i.e. "Tower"
     * @return the Map<String, Object> representing the part's default data
     *         Currently generates this from properties file, this is going to
     *         change in how it's done, but I'm leaving it for now
     */
    /*
     * public static Map<String, Object> createDefaultPart(String partType) {
     * 
     * Map<String, Object> part = new HashMap<String, Object>();
     * // ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
     * ResourceBundle paramSpecs = ResourceBundle.getBundle(paramSpecsFile);
     * 
     * String[] params = paramLists.getString(partType).split("\\s+");
     * 
     * for (String paramName : params) {
     * String[] typeAndDefault = paramSpecs.getString(paramName).split(
     * "\\s+");
     * String dataType = typeAndDefault[0];
     * String defaultVal = typeAndDefault[1];
     * 
     * part.put(paramName, makeDefaultData(dataType, defaultVal));
     * }
     * return part;
     * }
     */

    // creates an Object of class "dataType" and value "defualtVal"
    // also going to change, probably will be an unnecessary method by the end
    /*
     * private static Object makeDefaultData(String dataType, String defaultVal) {
     * 
     * Class<?> c = String.class;
     * Object data = "N/A";
     * String settingClass = "authoringEnvironment.editors." + dataType
     * + "Setting";
     * 
     * try {
     * c = Class.forName(settingClass);
     * } catch (ClassNotFoundException e) {
     * System.out.println(dataType + "class not found");
     * // do something, but this shouldn't happen if the properties file is
     * // correct
     * }
     * 
     * try {
     * data = ((Setting) (c.getConstructor(String.class, String.class,
     * String.class).newInstance("Doesn't", "Matter", defaultVal)))
     * .getParameterValue();
     * } catch (InstantiationException | IllegalAccessException
     * | IllegalArgumentException | InvocationTargetException
     * | NoSuchMethodException | SecurityException e) {
     * // hopefully this won't happen
     * System.out.println("Constructor couldn't be called with a String");
     * }
     * 
     * return data;
     * }
     */
}
