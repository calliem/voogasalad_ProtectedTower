//this class probably won't work. 
package authoringEnvironment;

import authoringEnvironment.objects.TowerView;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ChangeableButton extends Button{
	
	private boolean isSelected;

	public ChangeableButton(String initialString, EventHandler<MouseEvent> initialEvent, String secondaryString,  EventHandler<MouseEvent> returnEvent){
		super(initialString);
		isSelected = false;
		setOnMouseClicked(e -> {
			if(!isSelected){
	         //   initialEvent; 
				System.out.println("test");
	        }
	        else{
	           // returnEvent;
	        	System.out.println("test");
	        }
			isSelected = !isSelected;
		});
	}
}
	
	
	
/*
    Button add = new Button("Add Tower");
    add.setTranslateX(-10);
    add.setOnMousePressed((e) -> {
        promptNewTowerName();
    });

    edit.setOnAction((e) -> {
        if(!editing){
            startEditing(editControls, edit, add);
        }
        else{
            finishEditing(editControls, edit);
        }
        editing = !editing;
    });
    editControls.getChildren().add(edit);
    return editControls;
	
	   private void finishEditing (HBox editControls, Button edit) {
	        editControls.getChildren().remove(0);
	        edit.setText("Edit");
	        for(TowerView tower: towersCreated){
	            tower.exitEditableState();
	        }
	    }

	    private void startEditing (HBox editControls, Button edit, Button add) {
	        editControls.getChildren().add(0, add);
	        edit.setText("Done");
	        for(TowerView tower: towersCreated){
	            tower.initiateEditableState();
	        }
	    }
	*/
	
	
