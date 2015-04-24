package authoringEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import authoringEnvironment.editors.MapEditor;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;
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

    private InstanceManager currentGame;
    private Map<String, List<String>> partTypeToKeyList;
    private ObservableList<GameObject> myMaps;

    protected Controller (InstanceManager IM) {
        currentGame = IM;
        partTypeToKeyList = new HashMap<String, List<String>>();
        populateKeyList();
        myMaps = FXCollections.observableArrayList();
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
        for (Setting s : settings)
            partToAdd.put(s.getParameterName(), s.getParameterValue());
        return addPartToGame(partType, partToAdd);
    }

    /**
     * Gets all the keys for parts of partType that are currently part of this
     * game.
     * 
     * @param partType
     *        The type of part you want keys for, i.e. "Tower"
     * @return The list of keys of that type of part which currently exist in
     *         the editor.
     */
    public List<String> getKeysForPartType (String partType) {
        return partTypeToKeyList.get(partType);
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
        if (!partCopy.keySet().contains(InstanceManager.IMAGE_KEY))
            throw new NoImageFoundException("No image is specified for part: "
                                            + key);
        return (String) partCopy.get(key);
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
    }


    public Map<String, Object> loadPart (String fullPartFilePath) {
        Map<String, Object> part = (Map<String, Object>) XMLWriter.fromXML(fullPartFilePath);
        String partKey = (String) part.get(InstanceManager.PART_KEY_KEY);
        currentGame.addPart(partKey, part);
        addKey(partKey);
        return part;
    }

    private String addKey (String key) {
        String partType = key.substring(key.indexOf(".") + 1, key.length());
        if (!partTypeToKeyList.keySet().contains(partType))
            partTypeToKeyList.put(partType, new ArrayList<String>());
        partTypeToKeyList.get(partType).add(key);
        return key;
    }

    private void populateKeyList () {
        for (String key : currentGame.getAllPartData().keySet())
            addKey(key);
    }
    
    protected String saveGame(){
        return currentGame.saveGame();
    }

    public ObservableList<GameObject> getMaps () {
        return myMaps;
    }
    
  /*  public void setMaps (ObservableList<GameObject> maps) {
        myMaps = maps;
    }*/
}
