//THIS ENTIRE FILE IS PART OF MY MASTERPIECE
//JOHN KUMPF
package authoringEnvironment;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
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

    private static final int PARTTYPE_INDEX_IN_KEY = 1;

    
    public static final String KEY_BEFORE_CREATION = "Key not initialized yet";

    private InstanceManager currentGame;
    private Map<String, ObservableList<String>> partTypeToKeyList;
    private ObservableList<String> gameTags;

    protected Controller (InstanceManager instance) {
        currentGame = instance;
        partTypeToKeyList = new HashMap<String, ObservableList<String>>();
        populateKeyList();
        gameTags = FXCollections.observableArrayList();
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
    public String addPartToGame (String key,
                                 String partType,
                                 String partName,
                                 Map<String, Object> partialPartMap)
                                                                    throws MissingInformationException {
        Map<String, Object> fullPartMap = partialPartMap;
        fullPartMap.put(InstanceManager.NAME_KEY, partName);
        fullPartMap.put(InstanceManager.PART_TYPE_KEY, partType);
        if (key.equals(KEY_BEFORE_CREATION))
            return addKey(currentGame.addPart(key, fullPartMap));
        return addKey(currentGame.addPart(fullPartMap));
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
    public String addPartToGame (String key,
                                 String partName,
                                 String partType,
                                 List<Setting> settings)
                                                        throws MissingInformationException {
        return addPartToGame(key, partName, partType, generateMapFromSettings(settings));

    }

    /**
     * Takes a list of settings and a part type and creates an appropriate Map<String, Object> from
     * that.
     * 
     * @throws MissingInformationException
     */
    private Map<String, Object> generateMapFromSettings (List<Setting> settings)
                                                                                throws MissingInformationException {
        Map<String, Object> partToAdd = new HashMap<String, Object>();
        for (Setting s : settings) {
            partToAdd.put(s.getParameterName(), s.getParameterValue());
        }
        // addPartToGame(partType, partToAdd);
        return partToAdd;
    }

    public boolean addNewTag (String tag) {
        if (!gameTags.contains(tag)) {
            return gameTags.add(tag);
        }
        return false;
    }

    public boolean tagExists (String tag) {
        return gameTags.contains(tag);
    }

    public boolean removeTag (String tag) {
        return gameTags.remove(tag);
    }

    public ObservableList<String> getTagsList () {
        return gameTags;
    }

    /**
     * Adds a key to the Controller's map of part type to list of keys of that part type that exist
     * in the game.
     */
    private String addKey (String key) {
        String partType = key.substring(key.indexOf('.') + 1);
        if (!partTypeToKeyList.keySet().contains(partType))
            partTypeToKeyList.put(partType, FXCollections.observableList(new ArrayList<String>()));
        if (!partTypeToKeyList.get(partType).contains(key))
            partTypeToKeyList.get(partType).add(key);
        System.out.println("key added: " + key);
        return key;
    }

    public boolean addTagToPart (String partKey, String tag) {
        if (currentGame.containsKey(partKey)) {
            currentGame.addTagToPart(partKey, tag);
            return true;
        }
        System.out.println(partKey + " part not found");
        return false;
    }

    public boolean removeTagFromPart (String partKey, String tag) {
        if (currentGame.containsKey(partKey)) {
            return currentGame.removeTagFromPart(partKey, tag);
        }
        return false;
    }

    public File getDirectoryToPartFolder (String partType) {
        return new File(currentGame.getRootDirectory() + "/" + partType);
    }

    public boolean deletePart (String partKey) {
        return currentGame.deletePart(partKey);
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
    public ObservableList<String> getKeysForPartType (String partType) {
        System.out.println(partTypeToKeyList);
        if (!partTypeToKeyList.keySet().contains(partType)) {
            partTypeToKeyList.put(partType,
                                  FXCollections.observableArrayList(new ArrayList<String>()));
        }
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
    public Image getImageForKey (String key) throws NoImageFoundException {
        Map<String, Object> partCopy = getPartCopy(key);
        if (!partCopy.keySet().contains(InstanceManager.IMAGE_KEY))
            throw new NoImageFoundException("No image is specified for part");
        String absoluteImageFilePath = currentGame.getRootDirectory() +
                                       (String) partCopy.get(InstanceManager.IMAGE_KEY);
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            RenderedImage toWrite = ImageIO.read(new File(absoluteImageFilePath));
            ImageIO.write(toWrite, "png", os);
            InputStream imageInputStream = new ByteArrayInputStream(os.toByteArray());
            return new Image(imageInputStream);
        }
        catch (IOException e1) {
            e1.printStackTrace();
            throw new NoImageFoundException("something went wrong");
        }
    }

    public void specifyPartImage (String partKey, String imageFilePath) {
        System.out.println("partkey " + partKey + " space " + imageFilePath);
        currentGame.specifyPartImage(partKey, imageFilePath);
    }

    public void specifyPartImage (String partKey, Image image) {
        currentGame.specifyPartImage(partKey, image);
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

    public String getNameForPart (String key) {
        return (String) currentGame.getAllPartData().get(key).get(InstanceManager.NAME_KEY);
    }

}
