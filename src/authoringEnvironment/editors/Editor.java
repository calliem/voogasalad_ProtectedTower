/**
 * General editor structure 
 * @author Callie Mao
 */

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.stage.Stage;

public abstract class Editor {
	// interface?
	// or use a ArrayList<?> getObjects() method in this superclass?

	protected Dimension2D myDimensions;
	protected ResourceBundle myResources;
	protected Stage myStage;
	
	public Editor(Dimension2D dim, ResourceBundle resources){
		myDimensions = dim;
		myResources = resources;
	}

    public abstract Group configureUI(); //or make it a Node instead of a gridPane?
    
    protected double getWidth(){
    	return myDimensions.getWidth();
    }
    
    protected double getHeight(){
    	return myDimensions.getHeight();
    }
    
    protected ResourceBundle getResources(){
    	return myResources;
    }
    
   
    
}
