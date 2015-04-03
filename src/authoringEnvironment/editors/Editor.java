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
    //interface?
    //or use a ArrayList<?> getObjects() method in this superclass?
	
	private Dimension2D myDimensions;
	private ResourceBundle myResources;
    protected Stage myStage;

	//private double tabIndex;
	
	public Editor(Dimension2D dim, ResourceBundle resources, Stage s){
		myDimensions = dim;
		myStage = s;
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
    
    public static void main(String[] args){
    	List<String> l = new ArrayList<String>();
    	l.add(6, "hello");
    	l.add(4, "at 4");
    	l.add(5, "at 5");
    	System.out.println(l);
    }
    
}
