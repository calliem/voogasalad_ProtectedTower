package authoringEnvironment;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import util.misc.SetHandler;
import util.player.ReflectionUtil;
import annotations.parameter;
import authoringEnvironment.editors.Editor;
import authoringEnvironment.setting.Setting;


/**
 * 
 * @author Kevin He
 * @author Johnny Kumpf
 * @author Callie Mao
 */
public class ProjectReader {

    private static final String EDITORS_PACKAGE_LOCATION = "authoringEnvironment.editors.";
    private static final String classListFile = "resources/class_list";
    private static final String englishSpecsFile = "resources/display/main_environment_english";
    private static final ResourceBundle classLists = ResourceBundle.getBundle(classListFile);
    private static final String paramListFile = "resources/part_parameters";
    private static final String editorPackage = System.getProperty("user.dir")
            .concat("/src/authoringEnvironment/editors");
    private static final List<String> abstractEditors = SetHandler
            .listFromArray(new String[] { "Editor", "MainEditor", "PropertyEditor" });
    private static final String tabOrder = System.getProperty("user.dir") +
                                           "/src/resources/display/main_environment_english.properties";
    private static final String settingsPackage = "authoringEnvironment.setting.";

    /**
     * The getParamsNoTypeOrName method is called by WaveStrip and currently has an error because
     * this are commented out. Do we need to rewrite these? I think they're bad, but would mean a bigg rewrite we dont have time for.  Theyre fine for now
     */
    public static String[] getParamListForPart (String partType) throws ClassNotFoundException {
        Class<?> currentClass = Class.forName(partType);
        Field[] myFields = currentClass.getDeclaredFields();
        List<Field> neededFields = new ArrayList<>();
        for (Field field : myFields) {
            if (field.getAnnotation(parameter.class).settable()) {
                neededFields.add(field);
            }
        }
        return null;
    }

    public static List<String> getParamsNoTypeOrName (String partType) throws ClassNotFoundException {
        String[] params = getParamListForPart(partType);
        List<String> finalList = new ArrayList<String>();
        for (String param : params) {
            if (!param.equals(InstanceManager.NAME_KEY)
                && !param.equals(InstanceManager.PART_TYPE_KEY))
                finalList.add(param);
        }
        return finalList;
    }

    /**
     * Generates the Settings objects the Overlay UI needs to allow the user to
     * edit all of this part's parameters.
     * 
     * @param partType
     *        The type of part we need a Settings list for, i.e. "Tower"
     * @return The corresponding Settings list
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static List<Setting> generateSettingsList (Controller controller, String partType)
                                                                                             throws ClassNotFoundException,
                                                                                             IllegalArgumentException,
                                                                                             IllegalAccessException {
        System.out.println("genreate stginsgl list calle " + classLists.getString(partType));
        Class<?> currentClass = Class.forName(classLists.getString(partType));
        List<Class<?>> classesWithFields = ReflectionUtil.getPackageParentList(currentClass);
        List<Setting> settingsList = new ArrayList<Setting>();
        for (Class<?> myClass : classesWithFields) {
            Field[] myFields = myClass.getDeclaredFields();
            for (Field field : myFields) {
                String paramName = null;
                System.out.println("field" + field);
                if (field.getAnnotation(parameter.class) != null &&
                    field.getAnnotation(parameter.class).settable()) {
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) type;
                        Type paramType = pt.getActualTypeArguments()[0];
                        paramName = paramType.getTypeName();
                        int lastClassindex = paramName.lastIndexOf(".") + 1;
                        paramName = paramName.substring(lastClassindex);
                    }
                    settingsList.add(generateSetting(controller, partType, field.getName(),
                                                     paramName, field
                                                             .getAnnotation(parameter.class)
                                                             .defaultValue(),
                                                     field.getType().getSimpleName()));
                }
            }
        }
        return settingsList;
    }

    /**
     * This commented out code below was previously used when loading from a properties file
     * The above code is the same method but for use with annotations and reflection
     */
//     public static List<Setting> generateSettingsListOld (Controller controller, String partType){
//     // System.out.println("genreate stginsgl list calle");
//     List<Setting> settingsList = new ArrayList<Setting>();
//     ResourceBundle paramSpecs = ResourceBundle.getBundle(paramSpecsFile);
//     String[] params = getParamListForPart(partType);
//     System.out.println("params for " + partType + ": " + SetHandler.listFromArray(params));
//     List<String> paramsList = SetHandler.listFromArray(params);
//     Collections.sort(paramsList);
//     System.out.println("sorted? param list: " + paramsList);
//     paramsList = SetHandler.trimBeforeDot(paramsList);
//     for (String param : paramsList) {
//     String[] typeAndDefault = paramSpecs.getString(param).split("\\s+");
//     String dataType = typeAndDefault[0];
//     String defaultVal = typeAndDefault[1];
//     settingsList.add(generateSetting(controller, partType, param, defaultVal,
//     dataType));
//     }
//     ResourceBundle paramSpecs = ResourceBundle.getBundle(paramSpecsFile);
//     String[] params = getParamListForPart(partType);
//     System.out.println("params for " + partType + ": "
//     + SetHandler.listFromArray(params));
//     List<String> paramsList = SetHandler.listFromArray(params);
//     Collections.sort(paramsList);
//     System.out.println("sorted? param list: " + paramsList);
//     paramsList = SetHandler.trimBeforeDot(paramsList);
//     for (String param : paramsList) {
//     String[] typeAndDefault = paramSpecs.getString(param).split("\\s+");
//     String dataType = typeAndDefault[0];
//     String defaultVal = typeAndDefault[1];
//     }
//     }

    /**
     * Generates one setting object from the 4 parameters given
     * 
     * @param partType The type of part, i.e. "Tower"
     * @param param The name of the parameter the Setting is being generated for, i.e. "HP"
     * @param parameterClass
     * @param defaultVal The default value of the Setting, i.e. "0"
     * @param dataType The type of the data, i.e. "Integer"
     * @return The Setting object corresponding to these parameters
     */
    public static Setting generateSetting (Controller controller, String partType, String param,
                                           String paramName, String defaultVal, String dataType) {
        Class<?> c = String.class;
        Setting s = null;
        String settingToGet = settingsPackage + dataType + "Setting";
        // display error message
        try {
            c = Class.forName(settingToGet);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Setting class not found: " + settingToGet);
        }

        try {
            s =
                    (Setting) c.getConstructor(Controller.class, String.class, String.class,
                                               String.class,
                                               String.class)
                            .newInstance(controller, partType, param, paramName, defaultVal);
        }
        catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // display error message, don't let the null value be used
            System.err.println("Setting object couldn't be created");
        }
        return s;
    }

    public static List<Editor> getOrderedEditorsList (Controller c) {
        List<Editor> orderedEditorList = new ArrayList<Editor>();
        List<String> tabsToCreate = tabsToCreate();
        for (String s : getOrderedTabList()) {
            if (tabsToCreate.contains(s)) {
                Editor editorToAdd = null;
                String toCreate = EDITORS_PACKAGE_LOCATION + s;
                try {
                    // System.out.println("Being created: " + s);
                    editorToAdd = (Editor) Class.forName(toCreate)
                            .getConstructor(Controller.class, String.class, String.class)
                            .newInstance(c, s, s.substring(0, s.length() - 6)); // TODO: change last
                                                                                // input later
                }
                catch (InstantiationException | IllegalArgumentException e1) {
                    System.err.println("Constructor Editor() doesn't exist or was"
                                       + "incorrectly called");
                    System.err.println("Tab's Editor is currently null");
                    // e1.printStackTrace();
                }
                catch (ClassNotFoundException e1) {
                    System.err.println("Editor not found: " + toCreate);
                    System.err.println("Tab's Editor is currently null");
                    // e1.printStackTrace();
                }
                catch (IllegalAccessException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e1) {
                    System.err.println("Editor couldn't be created.");
                    // e1.printStackTrace();
                }
                orderedEditorList.add(editorToAdd);
            }
        }
        return orderedEditorList;
    }

    /**
     * Gives the user Editor strings and booleans (main tab or sprite tab) of tabs to create
     * 
     * @return The map of editors and booleans that go with them to create tabs
     *         for (main = true, sprite = false)
     */
    private static List<String> tabsToCreate () {
        List<String> tabsToMake = new ArrayList<String>();
        for (String s : editorsInPackage()) {
            if (!abstractEditors.contains(s)) {
                tabsToMake.add(s);
            }
        }
        return tabsToMake;
    }

    /**
     * Returns the array of strings like "LevelEditor" that tabs need to be created for
     * 
     * @return The String array of editors to create
     */
    private static String[] editorsInPackage () {
        File editors = new File(editorPackage);
        // System.out.println(editors.toString());
        // System.out.println(editorPackage);
        File[] allEditors = editors.listFiles();
        // System.out.println("All editors " + allEditors);
        String[] editorNames = new String[allEditors.length];
        for (int i = 0; i < allEditors.length; i++) {
            String untrimmedName = allEditors[i].getName();
            // trim off ".java"
            editorNames[i] = untrimmedName.substring(0, untrimmedName.indexOf("."));
            // System.out.println(editorNames[i]);
        }
        return editorNames;
    }

    /**
     * Gets the list of tabs to generate in the tab bar in the order specified in
     * main_environment_english.properties
     * 
     * @return The List<String> of tab names in order
     */
    public static List<String> getOrderedTabList () {
        ArrayList<String> tabList = new ArrayList<String>();
        try {
            Scanner s = new Scanner(new File(tabOrder));
            String nextEditor = "nothing";
            while (s.hasNextLine()) {
                nextEditor = s.nextLine();
                nextEditor.replaceAll("\\s+", "");
                int indexOfEquals = nextEditor.indexOf("=");
                // if nextEditor was a newLine or all whitespace, or something
                // else, don't try this
                if (indexOfEquals != -1) {
                    System.out.println("nextEditor: " + nextEditor + indexOfEquals);
                    tabList.add(nextEditor.substring(0, indexOfEquals));
                }
            }
            s.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tabList;
    }

}
