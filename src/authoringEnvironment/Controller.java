package authoringEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import authoringEnvironment.editors.MapEditor;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.objects.TileMap;

/**
 * This class is a singleton that holds a map of all the editors that are
 * created and that are updated accordingly.
 * 
 * @author Callie Mao
 * @author Johnny Kumpf
 *
 */

// Is this unnecessary?? This doesn't feel like it is useful.
public class Controller {

	// these probably aren't very good since they are the same as what is in the
	// properties file. instead consider using files to share information
	// between tabs?

	private Map<String, Editor> myEditors = new HashMap<String, Editor>();
	private InstanceManager currentGame;

	protected Controller(InstanceManager IM) {
		currentGame = IM;
	}

	protected Controller(String gameName, String rootDir) {
		this(new InstanceManager(gameName, rootDir));
	}

	public String addPartToGame(String partType, String partName,
			List<String> params, List<Object> data) {
		return currentGame.addPart(partType, partName, params, data);
	}

	public String addPartToGame(String partType, Map<String, Object> part) {
		return currentGame.addPart(partType, part);
	}

	public void addEditor(Editor e) {
		myEditors.put(e.getName(), e);
	}

	public Map<String, String> getSpriteFileMap(String partTabName) {
		Map<String, String> keysAndImagePaths = new HashMap<String, String>();
		List<String> partKeys = getKeysForParts(partTabName);
		for (String key : partKeys) {
			Map<String, Object> partCopy = getPartCopy(key);
			if (partCopy.keySet().contains(InstanceManager.imageKey))
				keysAndImagePaths.put(key,
						(String) partCopy.get(InstanceManager.imageKey));
		}
		return keysAndImagePaths;
	}

	public List<String> getKeysForParts(String partTabName) {
		Editor editorToQuery = myEditors.get(partTabName);
		return editorToQuery.getPartKeys();
	}

	public Map<String, Object> getPartCopy(String partKey) {
		return currentGame.getAllPartData().get(partKey);
	}

}
