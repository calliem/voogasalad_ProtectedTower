/**
 * General editor structure 
 * @author Callie Mao
 */

package authoringEnvironment.editors;

import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;

public abstract class Editor { 
    //interface?
    //or use a ArrayList<?> getObjects() method in this superclass?
	
	private Dimension2D myDimensions;
	private ResourceBundle myResources;
	//private double tabIndex;
	
	public Editor(Dimension2D dim, ResourceBundle resources){
		myDimensions = dim;
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
