package authoring.environment.setting;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IntegerSetting extends Setting {
    private int value;
    private TextField editableField;
    
    public IntegerSetting(String label){
        super(label);
    }
    
    @Override
    protected void setupInteractionLayout(){
        editableField = new TextField("0");
        this.getChildren().add(editableField);
    }
    
    @Override
    public String getParameterValue(){
        return editableField.getText();
    }

    @Override
    public boolean parseField () {
        try{
            value = Integer.parseInt(editableField.getText());
            hideErrorAlert();
            return true;
        }
        catch(NumberFormatException e){
            displayErrorAlert("This is not a number!");
            return false;
        }
    }
    
    @Override
    public void displaySavedValue () {
        editableField.setText(""+value);
    }
}
