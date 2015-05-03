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
    public static final String TILE_FOLDER = "/Tile";

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

    protected static Set<String> foldersToCreate () {
        Set<String> toAdd = gameDirectories();
        toAdd.add(InstanceManager.PARTS_FILE_DIRECTORY);
        toAdd.add(IMAGE_DATA_FOLDER);
        toAdd.add(TILE_FOLDER);
        return toAdd;
    }

}
