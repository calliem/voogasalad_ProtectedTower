package authoringEnvironment;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;


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

    private static final String INSTANCE_MANAGER_FILE_NAME = "GameManager.xml";
    public static final String PARTS_FILE_DIRECTORY = "/AllPartData";

    public static final String PART_KEY_KEY = "PartKey";
    public static final String PART_TYPE_KEY = "PartType";
    public static final String NAME_KEY = "Name";
    public static final String TAGS_KEY = "Tags";
    public static final String ROUNDS_KEY = "Rounds";
    public static final String IMAGE_KEY = "image";
    public static final String COLOR_KEY = "Color";

    public static final String GAMEMAP_PARTNAME = "GameMap";
    public static final String TILE_PARTNAME = "Tile";
    public static final String PATH_PARTNAME = "Path";

    private static final String NO_KEYS_MISSING = "no keys missing";

    /**
     * a map of all the parts the user has created each part is represented by a map mapping the
     * part's parameters to their data the fields look like:
     * Map<partName, Map<parameterName, parameterData>>
     */
    private Map<String, Map<String, Object>> userParts;
    private String gameName;
    private String rootDirectory;

    private static int partID;

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

    protected String addPart (Map<String, Object> fullPartMap) throws MissingInformationException {
        String key = generateKey(fullPartMap);
        fullPartMap.put(PART_KEY_KEY, key);
        return addPart(key, fullPartMap);
    }

    protected String addPart (String key, Map<String, Object> fullPartMap)
                                                                          throws MissingInformationException {
        fullPartMap.put(TAGS_KEY, getTagList(key));
        fullPartMap.put(PART_KEY_KEY, key);
        String missingKey = checkMissingInformation(fullPartMap);
        if (!missingKey.equals(NO_KEYS_MISSING))
            throw new MissingInformationException(missingKeyErrorMessage(missingKey));
        System.out.println("~~~~~~Part: " + fullPartMap + "\n~~~~~~added at: " + key);
        userParts.put(key, fullPartMap);
        writePartToXML(fullPartMap);
        return key;
    }

    private List<String> getTagList (String key) {
        try {
            Map<String, Object> oldPart = userParts.get(key);
            if (oldPart.keySet().contains(TAGS_KEY)) {
                System.out.println(key + " tags preserved as " + userParts.get(key).get(TAGS_KEY));
                return (List<String>) oldPart.get(TAGS_KEY);

            }
            else {
                System.out.println("tags not preserved for " + key);
                return new ArrayList<String>();
            }
        }
        catch (NullPointerException e) {
            System.out.println("key didn't exist yet");
            return new ArrayList<String>();
        }
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

    protected boolean deletePart (String partKey) {
        if (userParts.keySet().contains(partKey)) {
            userParts.remove(partKey);
            return true;
        }
        return false;
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
    private void writeAllPartsToXML () {
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
    protected String saveGame () {
        clearGameFolders();
        writeAllPartsToXML();
        System.out.println("writing to xml manager");
        return XMLWriter.toXML(this, INSTANCE_MANAGER_FILE_NAME, rootDirectory +
                                                                 PARTS_FILE_DIRECTORY);
    }

    private void clearGameFolders () {
        for (String subFolder : GameCreator.gameDirectories()) {
            File[] files = new File(rootDirectory + "/" + subFolder).listFiles();
            for (File f : files)
                f.delete();
        }
    }

    /**
     * Loads the InstanceManager object that's stored in a data file. In order
     * to get the root directory of this game, simply remove the filename of
     * the .gamefile file.
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

    protected void specifyPartImage (String partKey, String imageFilePath) {
        String realFilePath = "src/" + imageFilePath;
        String locationAfterRootDirectory =
                GameCreator.IMAGE_DATA_FOLDER + "/" +
                        partKey.substring(0, partKey.indexOf(".")) + "Image.png";
        String writeLocation = rootDirectory + locationAfterRootDirectory;
        try {
            File imageToSave = new File(realFilePath);
            RenderedImage toWrite = ImageIO.read(imageToSave);
            System.out.println(ImageIO.write(toWrite, "png", new File(writeLocation)));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        userParts.get(partKey).put(IMAGE_KEY, locationAfterRootDirectory);
    }

    protected void specifyPartImage (String partKey, Image image) {
        String locationAfterRootDirectory =
                GameCreator.IMAGE_DATA_FOLDER + "/" +
                        partKey.substring(0, partKey.indexOf(".")) + "Image.png";
        String writeLocation = rootDirectory + locationAfterRootDirectory;
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(writeLocation));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        userParts.get(partKey).put(IMAGE_KEY, locationAfterRootDirectory);

    }

    protected void addTagToPart (String partKey, String tag) {
        Map<String, Object> addTagTo = userParts.get(partKey);
        if (addTagTo.containsKey(TAGS_KEY)) {
            List<String> tagList = (List<String>) userParts.get(partKey).get(TAGS_KEY);
            tagList.add(tag);
            addTagTo.put(TAGS_KEY, tagList);
        }
        else {
            List<String> tagList = new ArrayList<String>();
            tagList.add(tag);
            addTagTo.put(TAGS_KEY, tagList);
        }
        try {
            addPart(partKey, addTagTo);
        }
        catch (MissingInformationException e) {
            System.err.println("Something's gone terribly wrong.");
        }

    }

    protected boolean removeTagFromPart (String partKey, String tag) {
        Map<String, Object> removeFrom = userParts.get(partKey);
        if (removeFrom.containsKey(TAGS_KEY)) {
            List<String> tagList = (List<String>) userParts.get(partKey).get(TAGS_KEY);
            boolean removed = tagList.remove(tag);
            removeFrom.put(TAGS_KEY, tagList);
            return removed;
        }
        return false;
    }

    protected boolean containsKey (String key) {
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

    protected Map<String, Object> getPartFromXML (String fileLocation)
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

    protected String getName () {
        return gameName;
    }

    protected String getRootDirectory () {
        return rootDirectory;
    }
}
