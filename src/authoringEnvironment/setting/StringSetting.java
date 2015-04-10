package authoringEnvironment.setting;

public class StringSetting extends Setting{

    public StringSetting (String label, String value) {
        super(label, value);
    }

    @Override
    public Object getParameterValue () {
        return dataAsString;
    }

    @Override
    public boolean parseField () {
        if(textBox().getText().length() > 25 || textBox().getText().length() < 1){
            displayErrorAlert("Please enter a string between 1-25 characters!");
            return false;
        }
        hideErrorAlert();
        return true;
    }
}
