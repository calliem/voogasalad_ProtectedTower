package authoringEnvironment.setting;

import imageselectorTEMP.ImageSelector;
import javafx.scene.layout.HBox;
import authoringEnvironment.Controller;

public class ImageViewSetting extends Setting {
    private ImageSelector selector;

    public ImageViewSetting (Controller controller, String part, String label, String parameterName, String value) {
        super(controller, part, label, parameterName, value);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void setupInteractionLayout(){
        basicLayout.getChildren().remove(0);
        
        selector = new ImageSelector();
        selector.setPreviewImageHeight(100);
        
        error.setVisible(false);
        basicLayout.getChildren().addAll(selector, error);
    }
    
    @Override
    public void setParameterValue(Object value){
        dataAsString = (String) value;
        selector.setSelectedImageFile((String) value);
    }

    @Override
    public Object getParameterValue () {
        return dataAsString;
    }

    @Override
    public void displaySavedValue (){
        selector.setSelectedImageFile(dataAsString);
    }
    
    @Override
    public boolean parseField () {
        dataAsString = selector.getSelectedImageFile();
        return true;
    }
    
    @Override
    public boolean processData(){
        return parseField();
    }
}
