package authoringEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.IntArray2DToImageConverter.src.ImageToInt2DArray;
import util.IntArray2DToImageConverter.src.IntArray2DToImageConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.setting.Setting;


/**
 * Controller is the class that manages data flow between editors and between
 * the AE and the InstanceManager. A Controller controls 1 game, for 1
 * AuthoringEnvironment. To work on two games at the same time, a new instance
 * of AuthroingEnvironment with a new Controller will be instantiated. All
 * Editor tabs use their respective instance of the Controller to call methods
 * that allow them to add parts to the game. Additionally, each Editor can get
 * information about what exists in other editor windows from the controller,
 * which becomes relevant when the user wants to do something like select a
 * projectile for his tower from the other projectiles he's created.
 * 
 * @author Johnny Kumpf
 * @author Callie Mao
 *
 */

public class Controller {

    private static final String DIFFERENT_LIST_SIZE_MESSAGE =
            "Lists passed must contain same number of elements.";
    public static final String KEY_BEFORE_CREATION = "Key not initialized yet";

    private InstanceManager currentGame;
    private Map<String, ObservableList<String>> partTypeToKeyList;
    private ObservableList<GameObject> myMaps;

    protected Controller (InstanceManager instance) {
        currentGame = instance;
        partTypeToKeyList = new HashMap<String, ObservableList<String>>();
        populateKeyList();
        myMaps = FXCollections.observableArrayList();
    }

    protected Controller (String gameName, String rootDir) {
        this(new InstanceManager(gameName, rootDir));
    }

    // Adding parts

    /**
     * Adds a part to the game at a specified key. The part added is completely defined by the map
     * parameter.
     * 
     * @param key The key at which to add this part.
     * @param fullPartMap The map that specifies this part's parameters and their corresponding data
     * @return The key where this part was added
     * @throws MissingInformationException If the map to be added is missing critical information
     */
    public String addPartToGame (String key, Map<String, Object> fullPartMap)
                                                                             throws MissingInformationException {
        return addKey(currentGame.addPart(key, fullPartMap));
    }

    /**
     * Generates a key for this part, then adds it to the game
     * 
     * @param partMapMinusKey The part map before the key is added to it, which will be added to the
     *        game
     * @return The key where this part was added
     * @throws MissingInformationException If the map to be added is missing critical information
     */
    public String addPartToGame (Map<String, Object> partMapMinusKey)
                                                                     throws MissingInformationException {
        return addKey(currentGame.addPart(partMapMinusKey));
    }

    /**
     * Adds a part specified by a list of settings objects to the game at the given key
     * 
     * @param key The key at which to add the part
     * @param partType The kind of part, i.e. "Tower"
     * @param settings The settings objects that contain all this part's parameters and their data
     * @return The key at which this part was added
     * @throws MissingInformationException If the map to be added is missing critical information
     */
    public String addPartToGame (String key, String partType, List<Setting> settings)
                                                                                     throws MissingInformationException {
        return addKey(currentGame.addPart(key, generateMapFromSettings(partType, settings)));

    }

    /**
     * Adds a part specified by a list of settings objects for which the key is auto-generated
     * 
     * @param partType The type of part, i.e. "Tower"
     * @param settings The settings objects that contain all the part's parameters and their data
     * @return The key at which this part was added
     * @throws MissingInformationException If the map to be added is missing critical information
     */
    public String addPartToGame (String partType, List<Setting> settings)
                                                                         throws MissingInformationException {
        return addKey(currentGame.addPart(generateMapFromSettings(partType, settings)));
    }

    /**
     * Takes a list of settings and a part type and creates an appropriate Map<String, Object> from
     * that.
     */
    private Map<String, Object> generateMapFromSettings (String partType, List<Setting> settings) {
        Map<String, Object> partToAdd = new HashMap<String, Object>();
        for (Setting s : settings) {
            partToAdd.put(s.getParameterName(), s.getParameterValue());
        }
        partToAdd.put(InstanceManager.PART_TYPE_KEY, partType);
        System.out.println("toadd: " + partToAdd);
        return partToAdd;
    }

    /**
     * Adds a part to the game at the given key where the data is specified by lists of parameters
     * and their data with corresponding indices.
     * 
     * @param key The key at which this part will be added.
     * @param partType The type of part, i.e. "Tower"
     * @param partName The name of the part, given by user
     * @param params The parameters this part has, i.e. "HP", "Damage", "Range"
     * @param data The data corresponding to those parameters
     * @return The key at which this part was added
     * @throws MissingInformationException If the map to be added is missing critical information
     * @throws DataFormatException If the lists passed are different sizes
     */
    public String addPartToGame (String key,
                                 String partType,
                                 String partName,
                                 List<String> params,
                                 List<Object> data) throws MissingInformationException,
                                                   DataFormatException {
        return addKey(currentGame.addPart(key,
                                          generateMapFromLists(partType, partName, params, data)));
    }

    /**
     * Adds a part whose data is specified by lists of parameters and their data of corresponding
     * indices, for which the key will be auto-generated.
     * 
     * @param partType The type of part, i.e. "Tower"
     * @param partName The name of the part, given by user
     * @param params The parameters this part needs
     * @param data The data corresponding to those parameters
     * @return The key at which this part was added
     * @throws MissingInformationException If the map to be added is missing critical information
     * @throws DataFormatException If the lists passed are different sizes.
     */
    public String addPartToGame (String partType,
                                 String partName,
                                 List<String> params,
                                 List<Object> data) throws MissingInformationException,
                                                   DataFormatException {

        return addKey(currentGame.addPart(generateMapFromLists(partType, partName, params, data)));

    }

    /**
     * Generates the appropriate map from a type, name, and two lists of parameters and data.
     */
    private Map<String, Object> generateMapFromLists (String partType,
                                                      String partName,
                                                      List<String> params,
                                                      List<Object> data) throws DataFormatException {
        if (params.size() != data.size()) {
            throw new DataFormatException(
                                          DIFFERENT_LIST_SIZE_MESSAGE);
        }
        Map<String, Object> toAdd = new HashMap<String, Object>();
        for (int i = 0; i < params.size(); i++) {
            toAdd.put(params.get(i), data.get(i));
        }
        toAdd.put(InstanceManager.NAME_KEY, partName);
        toAdd.put(InstanceManager.PART_TYPE_KEY, partType);
        return toAdd;
    }

    /**
     * Adds a key to the Controller's map of part type to list of keys of that part type that exist
     * in the game.
     */
    private String addKey (String key) {
        String partType = key.substring(key.indexOf(".") + 1);
        if (!partTypeToKeyList.keySet().contains(partType))
            partTypeToKeyList.put(partType, FXCollections.observableList(new ArrayList<String>()));
        partTypeToKeyList.get(partType).add(key);
        System.out.println("key added: " + key);
        return key;
    }

    public boolean addTagToPart (String partKey, String tag) {
        if (currentGame.containsKey(partKey)) {
            currentGame.addTagToPart(partKey, tag);
            return true;
        }
        return false;
    }
    
    

    // /**
    // * Removes a part from the game file.
    // *
    // * @param partType the type of part that is being removed
    // * @param partName the name of the part that is being removed
    // * @return true if the list of parts contained the specified part
    // */
    // public boolean removePartFromGame(String partType, String partName){
    // return partTypeToKeyList.get(partType).remove(partName);
    // }

    // The rest of the Controller, not adding parts

    /**
     * Gets all the keys for parts of partType that are currently part of this
     * game.
     * 
     * @param partType
     *        The type of part you want keys for, i.e. "Tower"
     * @return The list of keys of that type of part which currently exist in
     *         the editor.
     */
    public ObservableList<String> getKeysForPartType (String partType) {
        if (!partTypeToKeyList.keySet().contains(partType))
            return FXCollections.observableArrayList(new ArrayList<String>());
        return FXCollections.observableList(partTypeToKeyList.get(partType));
    }

    /**
     * Gets the full file path to the image stored in the part with specified
     * key, or throws an exception if no image was specified for that part.
     * 
     * @param key
     *        The key of the part for which the image file path should be
     *        returned
     * @return The full file path of the image for the part at that key
     * @throws NoImageFoundException
     */
    public Image getImageForKey (String key) throws NoImageFoundException {
        Map<String, Object> partCopy = getPartCopy(key);
        if (!partCopy.keySet().contains(InstanceManager.IMAGE_KEY))
            throw new NoImageFoundException("No image is specified for part: "
                                            + key);
        int[][] imageData = (int[][]) partCopy.get(InstanceManager.IMAGE_KEY);
        return IntArray2DToImageConverter.convert2DIntArrayToImage(imageData, 1);
        
    }

    public void addImageToPart (String partKey, Image image) {
        int[][] pixelArray = ImageToInt2DArray.convertImageTo2DIntArray(image, (int) image.getWidth(), (int) image.getHeight());
        currentGame.specifyPartImage(partKey, pixelArray);
    }

    /**
     * Gets a copy of the part of key partKey. All data is present, but
     * modifying the data won't change the actual data stored in the game.
     * 
     * @param partKey
     *        The key mapping to the part you want a copy of.
     * @return The map representing that part's parameters and corresponding
     *         data.
     */
    public Map<String, Object> getPartCopy (String partKey) {
        return currentGame.getAllPartData().get(partKey);
    }

    /**
     * Checks if a part with the given type and name already exists
     * 
     * @param partType The type of part
     * @param nameToCheck The name for which you want to know if a part already exists
     * @return True if the name is a duplicate, false if it's unique
     */
    public boolean nameAlreadyExists (String partType, String nameToCheck) {
        List<String> keys = getKeysForPartType(partType);
        if (keys == null)
            return false;

        for (String key : keys) {
            String nameThatExists = (String) getPartCopy(key).get(InstanceManager.NAME_KEY);
            if (nameThatExists.equalsIgnoreCase(nameToCheck))
                return true;
        }

        return false;
    }

    public Map<String, Object> loadPart (String fullPartFilePath) {
        Map<String, Object> part = (Map<String, Object>) XMLWriter.fromXML(fullPartFilePath);
        String partKey = (String) part.get(InstanceManager.PART_KEY_KEY);
        try {
            currentGame.addPart(partKey, part);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        addKey(partKey);
        return part;
    }

    private void populateKeyList () {
        for (String key : currentGame.getAllPartData().keySet()) {
            addKey(key);
        }
    }

    protected String saveGame () {
        return currentGame.saveGame();
    }

    public ObservableList<GameObject> getMaps () {
        return myMaps;
    }

    /*
     * public void setMaps (ObservableList<GameObject> maps) {
     * myMaps = maps;
     * }
     */
}
