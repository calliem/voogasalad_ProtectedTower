package authoringEnvironment.setting;

import imageselectorTEMP.GraphicFileChooser;
import authoringEnvironment.Controller;

/**
 * 
 * @author Kevin He
 * @author Johnny
 *
 */
public class FileNameSetting extends Setting{
    private GraphicFileChooser spriteFileChooser;
    
    public FileNameSetting(Controller controller, String part, String paramName, String defaultVal){
        super(controller, part, paramName, defaultVal);
    }

    @Override
    protected void setupInteractionLayout(){
        spriteFileChooser = new GraphicFileChooser("Select a File...", null);
        spriteFileChooser.addExtensionFilter("Text");
        
        basicLayout.getChildren().add(spriteFileChooser);
    }
    
    @Override
    public boolean parseField(){
        dataAsString = spriteFileChooser.getSelectedFileNameProperty().getValue();
        if(dataAsString == null){
            displayErrorAlert("Please choose a file!");
            return false;
        }
        hideErrorAlert();
        return true;
    }
    
    @Override
    public boolean processData(){
        return parseField();
    }

    public String getParameterValue(){
        return dataAsString;
    }
    
    @Override
    public void displaySavedValue(){
        
    }
}
