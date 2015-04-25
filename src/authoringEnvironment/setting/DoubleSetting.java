package authoringEnvironment.setting;

import authoringEnvironment.Controller;

/**
 * 
 * @author Johnny
 *
 */
public class DoubleSetting extends Setting{

    private Double dataAsDouble;

    public DoubleSetting(Controller controller, String part, String paramName, String defaultVal){
        super(controller, part, paramName, defaultVal);
    }

    @Override
    public boolean parseField(){
        try{
            dataAsDouble = Double.parseDouble(textBox().getText());
            hideErrorAlert();
            return true;
        }
        catch(NumberFormatException e){
            displayErrorAlert("This is not a decimal!");
            return false;
        }
    }

    public Double getParameterValue(){
        return dataAsDouble;
    }
}
