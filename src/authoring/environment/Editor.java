package authoring.environment;

import javafx.scene.layout.GridPane;

public abstract class Editor { 
    //interface?
    //or use a ArrayList<?> getObjects() method in this superclass?

    protected abstract GridPane configureUI(); //or make it a Node instead of a gridPane?
}
