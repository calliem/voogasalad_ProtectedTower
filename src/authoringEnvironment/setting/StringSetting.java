package authoringEnvironment.setting;

import authoringEnvironment.Controller;

public class StringSetting extends Setting{
    private boolean checkName = false;
    private static final int MAX_LENGTH = 25;
    private static final int MIN_LENGTH = 1;
    private static final String LENGTH_ERROR = "Please enter a string between " + MIN_LENGTH + "-" + MAX_LENGTH + "characters!";
    private static final String EXISTS_ERROR = "A %s with that name already exists!";
    
    public StringSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }

    @Override
    public Object getParameterValue () {
        return dataAsString;
    }

    @Override
    public boolean parseField () {
//        System.out.println("checkName (" + getParameterName() + "): " + checkName);
        if(textBox().getText().length() > MAX_LENGTH || textBox().getText().length() < MIN_LENGTH){
            displayErrorAlert(LENGTH_ERROR);
            return false;
        }
        if (myController.nameAlreadyExists(partType, textBox().getText()) &&
                !textBox().getText().equals(dataAsString) && checkName) {
            displayErrorAlert(String.format("A %s with that name already exists!", partType.toLowerCase()));
            return false;
        }
        hideErrorAlert();
        return true;
    }
    
    public void setCheckName(boolean check){
        checkName = check;
    }
}
