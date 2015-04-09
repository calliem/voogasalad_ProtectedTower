

package authoringEnvironment.editors;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class builds the general editor structure for all individual editor tabs viewable from the main environment 
 * @author Callie Mao
 */


public abstract class Editor extends Group{
	// or use a ArrayList<?> getObjects() method in this superclass?
	//TODO: have later methods get myDimensions from a closer class not mainenvironment. THey are passed as parameters for a reason.

	protected Dimension2D myDimensions;
	protected Stage myStage;
	
	//private static final int ERROR_DISPLAY_WIDTH = 
	//private static final int ERROR_DISPLAY_HEIGHT = 
	
	public Editor(Dimension2D dim, Stage s){
//		displayError("test");
		myDimensions = dim;
		myStage = s;
		configureUI();
	}

    protected abstract void configureUI(); //or make it a Node instead of a gridPane?
    
    protected double getWidth(){
    	return myDimensions.getWidth();
    }
    
    protected double getHeight(){
    	return myDimensions.getHeight();
    }
    
    //to be used by backend 
   public void displayError(String s){
		Stage stage = new Stage();
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		Text text = new Text(s);
		
		Button button = new Button("Ok");
		button.setOnMouseClicked(e-> stage.hide()); //this doesn't seem to work.... also hide() doesn't actually close() it right..?
		root.getChildren().addAll(text, button);

		Scene scene = new Scene(root, 400, 200);//getWidth() / 4,	getHeight() / 6);

		stage.setTitle("Error"); //TODO: how to use this parameter? myResources.getString("Error"). How to add to the mainenvironment resources without the parser freaking out?
		//MainStageTitle=protected Tower()
		stage.setScene(scene);
		stage.show();	
	}
   
   public abstract void update();
   

   
/*   public static void getInstance(){
}*/
    
}
