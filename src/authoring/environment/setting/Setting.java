package authoring.environment.setting;

import imageselector.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Creates a Setting object that allows the user
 * to edit a certain parameter of a sprite.
 * 
 * @author Kevin He
 *
 */
public abstract class Setting extends HBox{
    private String label;
    protected ImageView error;
    
    public Setting(String label){
        //TODO: remove magic number
        super(10);
        this.setAlignment(Pos.CENTER);
        
        error = new ImageView(new Image(String.format("images/%s.png", "error")));
        ScaleImage.scale(error, 20, 20);
        
        this.label = label;
        Text parameter = new Text(String.format("%s:", label));
        parameter.setFill(Color.WHITE);
        
        this.getChildren().add(parameter);
        setupInteractionLayout();
    }
    
    /**
     * Sets up the interactive portion of the setting object
     * and the features that the user can edit (i.e. a
     * textfield)
     */
    protected abstract void setupInteractionLayout();
    
    /**
     * Returns the name of the parameter represented
     * by this object.
     * @return label    the name of the parameter
     */
    public String getParameterName(){
        return label;
    }
    
    /**
     * Gets the value of the parameter.
     * @return the user-edited value of the parameter
     */
    public abstract String getParameterValue();
    
    /**
     * Hides the error alert for this parameter.
     */
    protected void hideErrorAlert(){
        this.getChildren().remove(error);
    }
    
    /**
     * Displays the error alert for this parameter.
     */
    protected void displayErrorAlert(String message){
        if(this.getChildren().get(0) != error){
            this.getChildren().add(0, error);
        }
        Tooltip tooltip = new Tooltip(message);
        Tooltip.install(error, tooltip);
    }
    
    /**
     * Parses the parameter field and displays an error if
     * the data in the field is not of the correct type.
     * @return true if the user-entered data is correctly formatted
     */
    public abstract boolean parseField();
    
    public abstract void displaySavedValue();
}
