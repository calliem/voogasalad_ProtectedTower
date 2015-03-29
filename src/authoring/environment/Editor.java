/**
 * General editor structure 
 * @author Callie Mao
 */

package authoring.environment;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;

public abstract class Editor { 
    //interface?
    //or use a ArrayList<?> getObjects() method in this superclass?
	
	private Dimension2D myDimensions;
	
	public Editor(Dimension2D dim){
		myDimensions = dim;
	}

    protected abstract Group configureUI(); //or make it a Node instead of a gridPane?
    
    protected double getWidth(){
    	return myDimensions.getWidth();
    }
    
    protected double getHeight(){
    	return myDimensions.getHeight();
    }
}
