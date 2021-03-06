package authoringEnvironment.setting;

import authoringEnvironment.Controller;

/**
 * 
 * @author Johnny
 *
 */
public class DoubleSetting extends Setting{

    private Double dataAsDouble;
    private static final String ERROR_MESSAGE = "This is not a decimal!";

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
            displayErrorAlert(ERROR_MESSAGE);
            return false;
        }
    }
    
    @Override
    public void setParameterValue(Object value){
        dataAsDouble = (Double) value;
        textBox().setText(dataAsDouble+"");
    }

    public Double getParameterValue(){
        return dataAsDouble;
    }
}
