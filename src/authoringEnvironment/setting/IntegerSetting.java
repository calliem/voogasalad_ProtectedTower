package authoringEnvironment.setting;

import authoringEnvironment.Controller;

/**
 * A Setting object for integer parameters.
 * 
 * @author Kevin He
 * @author Johnny
 *
 */
public class IntegerSetting extends Setting {
    private Integer dataAsInteger;
    
    public IntegerSetting(Controller controller, String part, String label, String defaultVal){
        super(controller, part, label, defaultVal);
    }
    
    @Override
    public Integer getParameterValue(){
    	return dataAsInteger;
    }
    
    @Override
    public boolean parseField () {
        try{
            dataAsInteger = Integer.parseInt(textBox().getText());
            hideErrorAlert();
            return true;
        }
        catch(NumberFormatException e){
            displayErrorAlert("This is not a number!");
            return false;
        }
    }
    
}
