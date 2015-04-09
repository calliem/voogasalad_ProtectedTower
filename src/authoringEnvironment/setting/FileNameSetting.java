package authoringEnvironment.setting;

import imageselector.GraphicFileChooser;
import java.io.File;
import protectedtower.Main;

/**
 * 
 * @author Kevin He
 * @author Johnny
 *
 */
public class FileNameSetting extends Setting{
    private GraphicFileChooser spriteFileChooser;
    
    public FileNameSetting(String paramName, String defaultVal){
        super(paramName, defaultVal);
    }

    @Override
    protected void setupInteractionLayout(){
        spriteFileChooser = new GraphicFileChooser(Main.getStage(), "Select a File...", null);
        spriteFileChooser.setAdditionalOptions(true);
        spriteFileChooser.addExtensionFilter("xml");
        
        this.getChildren().add(spriteFileChooser);
    }
    
    public boolean parseField(){
        dataAsString = spriteFileChooser.getSelectedFileNameProperty().getValue();
        return new File(dataAsString).isFile();
    }

    public String getParameterValue(){
        return getDataAsString();
    }
}
