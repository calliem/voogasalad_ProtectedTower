package authoringEnvironment.setting;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * A Setting object for integer parameters.
 * 
 * @author Kevin He
 * @author Johnny
 *
 */
public class IntegerSetting extends Setting {
    private Integer dataAsInteger;
    
    public IntegerSetting(String label, String defaultVal){
        super(label, defaultVal);
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
