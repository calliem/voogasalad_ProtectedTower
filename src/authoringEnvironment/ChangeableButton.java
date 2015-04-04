package authoringEnvironment;

import javafx.scene.control.Button;

public class ChangeableButton {
	
	private boolean isSelected;

	public ChangeableButton(String s){
		Button edit = new Button(s);
		
		isSelected = false; 

		/*edit.setOnAction((e) -> {
			if (!editing) {
				startEditing(editControls, edit, add);
			} else {
				finishEditing(editControls, edit);
			}
			editing = !editing;
		});
		editControls.getChildren().add(edit);
		return editControls;*/

		
	}
	
	
	
	
	
}
