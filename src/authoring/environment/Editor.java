/**
 * General editor structure 
 * @author Callie Mao
 */

package authoring.environment;

import javafx.scene.Group;

public abstract class Editor { 
    //interface?
    //or use a ArrayList<?> getObjects() method in this superclass?

    protected abstract Group configureUI(); //or make it a Node instead of a gridPane?
}
