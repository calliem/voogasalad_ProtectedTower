package authoringEnvironment.setting;

import imageSelector.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
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
 * @author Johnny
 *
 */
public abstract class Setting extends HBox{
    private String label;
    protected ImageView error;
    protected String dataAsString;
    private TextField editableField;
    
    public Setting(String label, String value){
        //TODO: remove magic number
        super(10);
        dataAsString = value;
        this.setAlignment(Pos.CENTER);
        
        error = new ImageView(new Image(String.format("images/%s.png", "error")));
        ScaleImage.scale(error, 20, 20);
        
        this.label = label;
        Text parameter = new Text(String.format("%s:", label));
        parameter.setFill(Color.WHITE);
        
        this.getChildren().add(parameter);
        setupInteractionLayout();
        parseField();
    }
    
    /**
     * Sets up the interactive portion of the setting object
     * and the features that the user can edit (i.e. a
     * textfield)
     */
    
    //this is the same for every setting object
    //if not, we can have two setting subclasses, one for normal stuff, one for file selectors
    protected void setupInteractionLayout(){
        editableField = new TextField(dataAsString);
        this.getChildren().add(editableField);
    }
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
    public abstract Object getParameterValue();
    
    public String getDataAsString(){
    	return dataAsString;
    }
    /**
     * Hides the error alert for this parameter.
     */
    protected void hideErrorAlert(){
        this.getChildren().remove(error);
    }
    
    protected TextField textBox(){
    	return editableField;
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
    
    public boolean processData(){
        boolean readable = parseField();
        if(readable){
            dataAsString = editableField.getText();
        }
        return readable;
    }
    
    public void displaySavedValue(){
        editableField.setText(""+dataAsString);
    }
}
