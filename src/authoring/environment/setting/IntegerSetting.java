package authoring.environment.setting;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class IntegerSetting extends Setting {
    private TextField value;
    
    public IntegerSetting(String label){
        super(label);
    }
    
    @Override
    protected void setupInteractionLayout(){
        value = new TextField("0");
        value.setOnAction((e) -> {
           System.out.println(value.getText()); 
        });
        this.getChildren().add(value);
    }
    
    @Override
    public String getParameterValue(){
        return value.getText();
    }
}
