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
import authoringEnvironment.setting.Setting;

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

	private InstanceManager currentGame;
	private Map<String, List<String>> partTypeToKeyList;

	protected Controller(InstanceManager IM) {
		currentGame = IM;
		populateKeyList();
	}

	protected Controller(String gameName, String rootDir) {
		this(new InstanceManager(gameName, rootDir));
	}

	public void addPartToGame(String partType, String partName,
			List<String> params, List<Object> data) {
		addKey(currentGame.addPart(partType, partName, params, data));
	}

	public void addPartToGame(String partType, Map<String, Object> part) {
		addKey(currentGame.addPart(partType, part));
	}

	public void addPartToGame(String partType, List<Setting> settings) {
		Map<String, Object> partToAdd = new HashMap<String, Object>();
		for (Setting s : settings)
			partToAdd.put(s.getParameterName(), s.getParameterValue());
		addPartToGame(partType, partToAdd);
	}

	public Map<String, String> getSpriteFileMap(String partTabName) {
		Map<String, String> keysAndImagePaths = new HashMap<String, String>();
		List<String> partKeys = getKeysForPartType(partTabName);
		for (String key : partKeys) {
			Map<String, Object> partCopy = getPartCopy(key);
			if (partCopy.keySet().contains(InstanceManager.imageKey))
				keysAndImagePaths.put(key,
						(String) partCopy.get(InstanceManager.imageKey));
		}
		return keysAndImagePaths;
	}

	public List<String> getKeysForPartType(String partType) {
		return partTypeToKeyList.get(partType.toLowerCase());
	}

	public Map<String, Object> getPartCopy(String partKey) {
		return currentGame.getAllPartData().get(partKey);
	}

	private String addKey(String key) {
		String partType = key.substring(key.indexOf(".") + 1, key.length());
		if (!partTypeToKeyList.keySet().contains(partType))
			partTypeToKeyList.put(partType, new ArrayList<String>());
		partTypeToKeyList.get(partType).add(key);
		return key;
	}

	private void populateKeyList() {
		for (String key : currentGame.getAllPartData().keySet())
			addKey(key);
	}
}
