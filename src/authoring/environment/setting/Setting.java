package authoring.environment.setting;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Setting extends HBox{
    private String label;
    
    protected Setting(String label){
        //TODO: remove magic number
        super(10);
        this.setAlignment(Pos.CENTER);
        
        this.label = label;
        Text parameter = new Text(label);
        parameter.setFill(Color.WHITE);
        
        this.getChildren().add(parameter);
        setupInteractionLayout();
    }
    
    protected void setupInteractionLayout(){

    }
    
    protected String getParameterName(){
        return label;
    }
}
