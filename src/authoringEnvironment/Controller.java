package authoringEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 *
 */

public class Controller {

    private InstanceManager currentGame;
    private Map<String, ObservableList<String>> partTypeToKeyList;
    
    protected Controller (InstanceManager IM) {
        currentGame = IM;
        partTypeToKeyList = new HashMap<String, ObservableList<String>>();
        populateKeyList();
    }

    protected Controller (String gameName, String rootDir) {
        this(new InstanceManager(gameName, rootDir));
    }

    /**
     * This method adds a part to the current game. Information about that part
     * is specified in the parameters. If the part already exists, it will be
     * updated to have the data passed. Additionally, the part's key, used to
     * get a copy of its data later, is added to the controller's map.
     * 
     * @param partType
     *        The type of part, i.e. "Tower"
     * @param partName
     *        The name of the part, i.e. "MyFirstTower"
     * @param params
     *        A list of the strings representing the parameters this part
     *        requires
     * @param data
     *        The data corresponding to those parameters.
     */
    public String addPartToGame (String partType, String partName,
                                 List<String> params, List<Object> data) {
        return addKey(currentGame.addPart(partType, partName, params, data));
    }

    /**
     * Adds a part to the game and adds the key to the controller's key map.
     * 
     * @param partType
     *        The type of part to be added, i.e. "Tower"
     * @param part
     *        The map representing all the part's data. Must contain a
     *        "Name" key.
     */
    public String addPartToGame (String partType, Map<String, Object> part) {
        return addKey(currentGame.addPart(partType, part));
    }

    /**
     * Adds a part to the game and adds the key to the controller's key map.
     * 
     * @param partType
     *        The type of part to be added, i.e. "Tower"
     * @param settings
     *        A list of Settings objects that hold all the parts data
     */
    public String addPartToGame (String partType, List<Setting> settings) {
        Map<String, Object> partToAdd = new HashMap<String, Object>();
        for (Setting s : settings){
            partToAdd.put(s.getParameterName(), s.getParameterValue());
//            if(s.getParameterName().equals(InstanceManager.idKey)){
//                
//            }
        }
        return addPartToGame(partType, partToAdd);
    }
    
//    /**
//     * Removes a part from the game file.
//     * 
//     * @param partType  the type of part that is being removed
//     * @param partName  the name of the part that is being removed
//     * @return true     if the list of parts contained the specified part
//     */
//    public boolean removePartFromGame(String partType, String partName){
//        return partTypeToKeyList.get(partType).remove(partName);
//    }

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
        if (partTypeToKeyList.get(partType) == null) {
            initializePartList(partType);
        }
        return partTypeToKeyList.get(partType);
    }

    /**
     * @param partType
     */
    private void initializePartList (String partType) {
        ArrayList<String> newList = new ArrayList<>();
        partTypeToKeyList.put(partType, FXCollections.observableList(newList));
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
    public String getImageForKey (String key) throws NoImageFoundException {
        Map<String, Object> partCopy = getPartCopy(key);
        if (!partCopy.keySet().contains(InstanceManager.imageKey))
            throw new NoImageFoundException("No image is specified for part: "
                                            + key);
        return (String) partCopy.get(InstanceManager.imageKey);
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

    public void specifyPartImage (String partKey, String imageFilePath) {
        currentGame.specifyPartImage(partKey, imageFilePath);
        System.out.println("key: " + partKey + " image: " + imageFilePath);
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
            String nameThatExists = (String) getPartCopy(key).get(InstanceManager.nameKey);
            if (nameThatExists.equalsIgnoreCase(nameToCheck))
                return true;
        }

        return false;
    }
    
    public boolean partAlreadyExists(String partType, String id){
        List<String> keys = getKeysForPartType(partType);
        for(String key : keys){
            if(getPartCopy(key).get(InstanceManager.idKey).equals(id))
                return true;
        }
        return false;
    }

    public Map<String, Object> loadPart (String fullPartFilePath) {
        Map<String, Object> part = (Map<String, Object>) XMLWriter.fromXML(fullPartFilePath);
        String partKey = (String) part.get(InstanceManager.partKeyKey);
        currentGame.addPart(partKey, part);
        addKey(partKey);
        return part;
    }

    private String addKey (String key) {
        String partType = key.substring(key.indexOf(".") + 1, key.length());
        if (!partTypeToKeyList.keySet().contains(partType)) {
            initializePartList(partType);
        }
        partTypeToKeyList.get(partType).add(key);
        return key;
    }

    private void populateKeyList () {
        for (String key : currentGame.getAllPartData().keySet()) {
            addKey(key);
        }
    }
}
