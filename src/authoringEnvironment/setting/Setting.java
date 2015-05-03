package authoringEnvironment.setting;

import imageselector.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import authoringEnvironment.Controller;

/**
 * Creates a Setting object that allows the user
 * to edit a certain parameter of a sprite.
 * 
 * @author Kevin He
 * @author Johnny
 *
 */
public abstract class Setting extends VBox{
    private String label;
    protected String partType;
    protected Controller myController;
    protected HBox basicLayout;
    protected ImageView error;
    protected String dataAsString;
    private TextField editableField;
    private Text parameter;
    
    private static final int FIELD_WIDTH = 125;
    private static final int PADDING = 10;
    private static final int MESSAGE_SIZE = 20;
    private static final String IMAGE_FILE_FORMAT = "images/%s.png";
    private static final String ERROR_IMAGE_FILE = "error"; 
    
    public Setting(Controller controller, String part, String label, String value){
        //TODO: remove magic number
        super(PADDING);
        this.setAlignment(Pos.CENTER);
        
        myController = controller;
        partType = part;
        dataAsString = value;
        basicLayout = new HBox(PADDING);
        basicLayout.setAlignment(Pos.CENTER_RIGHT);
        
        error = new ImageView(new Image(String.format(IMAGE_FILE_FORMAT, ERROR_IMAGE_FILE)));
        ScaleImage.scale(error, MESSAGE_SIZE, MESSAGE_SIZE);
        error.setVisible(false);
        
        this.label = label;
        parameter = new Text(String.format("%s:", label));
        parameter.setFill(Color.WHITE);
        
        basicLayout.getChildren().add(parameter);
        this.getChildren().add(basicLayout);
        setupInteractionLayout();
        parseField();
    }
    
    /**
     * Change the color of the label text
     * @param textColor desired color of the label
     */
    public void setTextColor(Color textColor){
        parameter.setFill(textColor);
    }
    
    /**
     * Sets up the interactive portion of the setting object
     * and the features that the user can edit (i.e. a
     * textfield)
     */
    
    protected void setupInteractionLayout(){
        editableField = new TextField(dataAsString);
        editableField.setMaxWidth(FIELD_WIDTH);
        editableField.setMinWidth(FIELD_WIDTH);
        editableField.setAlignment(Pos.CENTER);
        
        basicLayout.getChildren().addAll(editableField, error);
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
    
    public void setParameterValue(Object value){
        dataAsString = (String) value;
        editableField.setText((String) value);
    }

    /**
     * Displays the error alert for this parameter.
     */
    protected void displayErrorAlert(String message){
        error.setVisible(true);
        Tooltip tooltip = new Tooltip(message);
        Tooltip.install(error, tooltip);
    }
    
    /**
     * Hides the error alert for this parameter.
     */
    protected void hideErrorAlert(){
        error.setVisible(false);
    }

    public String getDataAsString(){
        return dataAsString;
    }
    
    protected TextField textBox(){
    	return editableField;
    }
    
    /**
     * Parses the parameter field and displays an error if
     * the data in the field is not of the correct type.
     * @return true     if the user-entered data is correctly formatted
     */
    public abstract boolean parseField();
    
    /**
     * Processes the data and updates dataAsString and the textField.
     * @return true     if user-entered data is correctly formatted
     */
    public boolean processData(){
        boolean readable = parseField();
        if(readable){
            dataAsString = editableField.getText();
        }
        return readable;
    }
    
    /**
     * Displays previously saved value of dataAsString. If user-entered
     * data is incorrectly formatted, this is the last value of dataAsString
     * (before the error).
     */
    public void displaySavedValue(){
        editableField.setText(dataAsString);
    }
}
