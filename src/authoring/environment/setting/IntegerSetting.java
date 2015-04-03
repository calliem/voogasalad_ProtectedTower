package authoring.environment.setting;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * A Setting object for integer parameters.
 * 
 * @author Kevin He
 *
 */
public class IntegerSetting extends Setting {
<<<<<<< HEAD
	private TextField value;

	public IntegerSetting(String label) {
		super(label);
	}

	@Override
	protected void setupInteractionLayout() {
		value = new TextField("0");
		value.setOnAction((e) -> {
			System.out.println(value.getText());
		});
		this.getChildren().add(value);
	}

	@Override
	public String getParameterValue() {
		return value.getText();
	}
=======
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
>>>>>>> 8895d74c0cf256fc1f2bc1a4062df4283a1b093a
}
