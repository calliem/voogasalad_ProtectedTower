package authoringEnvironment;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import authoring.environment.setting.Setting;

public class GameCreator {

	private static final String paramListFile = "resources/part_parameters";
	private static final String paramSpecsFile = "resources/parameter_datatype";
	private static final String gameFileDir = "\\AllPartsData";
	public static final ResourceBundle paramLists = ResourceBundle.getBundle(paramListFile);
	private static Set<String> dirsToBeCreated = paramLists.keySet();
	private static final String editorPackage = System.getProperty("user.dir").concat("\\src\\authoringEnvironment\\editors");
	private static final List<String> abstractEditors = listFromArray(new String[] {"Editor", "MainEditor", "PropertyEditor"});
	private static final List<String> spriteEditors = listFromArray(new String[] {"TowerEditor", "ProjectileEditor", "UnitEditor"});


	/**
	 * Generates the Settings objects the Overlay UI needs to allow the user to edit
	 * all of this part's parameters.
	 * @param partType The type of part we need a Settings list for, i.e. "Tower"
	 * @return The corresponding Settings list
	 */
	public static List<Setting> generateSettingsList(String partType){

		List<Setting> settingsList = new ArrayList<Setting>();
		ResourceBundle paramSpecs = ResourceBundle.getBundle(paramSpecsFile);

		String[] params = paramLists.getString(partType).split("\\s+");

		for(String param : params){
			String[] typeAndDefault = paramSpecs.getString(param).split("\\s+");
			String dataType = typeAndDefault[0];
			String defaultVal = typeAndDefault[1];

			settingsList.add(generateSetting(partType, param, defaultVal, dataType));
		}

		return settingsList;
	}

	/**
	 * Generates one setting object from the 4 parameters given
	 * @param partType The type of part, i.e. "Tower"
	 * @param param The name of the parameter the Setting is being generated for, i.e. "HP"
	 * @param defaultVal The default value of the Setting, i.e. "0"
	 * @param dataType The type of the data, i.e. "Integer"
	 * @return The Setting object corresponding to these parameters
	 */

	public static Setting generateSetting(String partType, String param,
			String defaultVal, String dataType) {
		Class<?> c = String.class;
		Setting s = null;
		try {
			c = Class.forName(dataType + "Setting");
		} catch (ClassNotFoundException e) {
			// display error message
		}
		;
		try {
			s = (Setting) c.getConstructor(String.class, String.class, String.class)
					.newInstance(partType, param, defaultVal);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// display error message, don't let the null value be used
		}

		return s;
	}

	/**
	 * Gives the user Editor strings and booleans (main tab or sprite tab) of tabs to create
	 * @return The map of editors and booleans that go with them to create tabs for (main = true, sprite = false)
	 */
	public static Map<String, Boolean> tabsToCreate(){
		Map<String, Boolean> tabsToMake = new HashMap<String, Boolean>();
		for(String s : editorsToCreate())
			if(!abstractEditors.contains(s))
				tabsToMake.put(s, !spriteEditors.contains(s));
		return tabsToMake;
	}

	/**
	 * Returns the array of strings like "LevelEditor" that tabs need to be created for
	 * @return The array of editors to create
	 */
	public static String[] editorsToCreate(){
		File editors = new File(editorPackage);
		System.out.println(editorPackage);
		File[] allEditors = editors.listFiles();
		String[] editorNames = new String[allEditors.length];
		for(int i = 0; i < allEditors.length; i++){
			String untrimmedName = allEditors[i].getName();
			//trim off ".java"
			editorNames[i] = untrimmedName.substring(0, untrimmedName.indexOf("."));
			System.out.println(editorNames[i]);
		}
		return editorNames;
	}

	/**
	 * because String[]'s don't have .contains
	 * @param s array to be converted to List<String>
	 * @return s in List form
	 */
	public static List<String> listFromArray(String[] s){
		List<String> l = new ArrayList<String>();
		for(String word : s)
			l.add(word);
		return l;
	}
}
