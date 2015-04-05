package authoringEnvironment.setting;

/**
 * 
 * @author Johnny
 *
 */
public class DoubleSetting extends Setting{

	private Double dataAsDouble;
	
	public DoubleSetting(String paramName, String defaultVal){
		super(paramName, defaultVal);
	}
	
	@Override
	public boolean parseField(){
		 try{
	            dataAsDouble = Double.parseDouble(textBox().getText());
	            hideErrorAlert();
	            return true;
	        }
	        catch(NumberFormatException e){
	            displayErrorAlert("This is not a number!");
	            return false;
	        }
	}
	
	public Double getParameterValue(){
		return dataAsDouble;
	}
}
