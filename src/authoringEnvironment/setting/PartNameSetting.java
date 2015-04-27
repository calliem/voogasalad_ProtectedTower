package authoringEnvironment.setting;

import authoringEnvironment.Controller;


public class PartNameSetting extends StringSetting {


    public PartNameSetting (Controller controller, String part, String label, String parameterName, String value) {
        super(controller, part, label, parameterName, value);
    }
    
    @Override
    public boolean parseField () {
        if (textBox().getText().length() > 25 || textBox().getText().length() < 1) {
            displayErrorAlert("Please enter a string between 1-25 characters!");
            return false;
        }
        else if (myController.nameAlreadyExists(partType, textBox().getText()) &&
                 !textBox().getText().equals(dataAsString)) {
            displayErrorAlert("A " + partType.toLowerCase() + " with that name already exists!");
            return false;
        }
        hideErrorAlert();
        return true;
    }
}
