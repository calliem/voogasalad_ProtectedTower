package authoringEnvironment.setting;

import java.util.List;
import authoringEnvironment.Controller;

public class ListSetting extends Setting{
    private static final String settingsPackage = "authoringEnvironment.setting.";

    private String listString;
    private Class mySettingType;
    public ListSetting(Controller controller, String part, String label, String parameterName, String defaultVal) throws ClassNotFoundException{
        super(controller, part, label, parameterName, defaultVal);
        System.out.println(parameterName);
        String settingToGet = settingsPackage + parameterName + "Setting";
        mySettingType = Class.forName(settingToGet);
    }

    @Override
    public boolean parseField(){
            listString = textBox().getText();
            if(!listString.endsWith(".")){
                displayErrorAlert("Insert commas to delineate items, list must end with a period");
                return false;
            }
            hideErrorAlert();
            return true;

    }

    public String getParameterValue(){
        return listString;
    }
}
