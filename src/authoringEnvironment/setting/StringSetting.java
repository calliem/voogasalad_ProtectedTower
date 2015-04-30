package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class StringSetting extends Setting{
    private boolean checkName = false;
    
    public StringSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }

    @Override
    public Object getParameterValue () {
        return dataAsString;
    }

    @Override
    public boolean parseField () {
        System.out.println("checkName (" + getParameterName() + "): " + checkName);
        if(textBox().getText().length() > 25 || textBox().getText().length() < 1){
            displayErrorAlert("Please enter a string between 1-25 characters!");
            return false;
        }
        if (myController.nameAlreadyExists(partType, textBox().getText()) &&
                !textBox().getText().equals(dataAsString) && checkName) {
            displayErrorAlert("A " + partType.toLowerCase() + " with that name already exists!");
            return false;
        }
        hideErrorAlert();
        return true;
    }
    
    public void setCheckName(boolean check){
        checkName = check;
    }
}
