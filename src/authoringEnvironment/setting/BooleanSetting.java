package authoringEnvironment.setting;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import authoringEnvironment.Controller;

public class BooleanSetting extends Setting{
    private CheckBox checkbox;
    private boolean selected;

    public BooleanSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
        System.out.println("Tried");
        selected = Boolean.parseBoolean(value);
    }

    protected void setupInteractionLayout(){
        checkbox = new CheckBox();
        checkbox.setSelected(true);
        
        basicLayout.getChildren().addAll(checkbox, error);
    }
    
    @Override
    public Object getParameterValue () {
        // TODO Auto-generated method stub
        return checkbox.isSelected();
    }
    
    public void setParameterValue(Object value){
        dataAsString = (String) value;
        checkbox.setSelected((Boolean) value);
    }

    @Override
    public boolean parseField () {
        dataAsString = "" + checkbox.isSelected();
        selected = checkbox.isSelected();
        // TODO Auto-generated method stub
        return true;
    }
    
    public boolean processData(){
        return parseField();
    }
    
    public void displaySavedValue(){
        checkbox.setSelected(selected);
    }

}
