package authoringEnvironment.setting;

import imageselector.ImageSelector;
import javafx.scene.layout.HBox;
import authoringEnvironment.Controller;

public class ImageViewSetting extends Setting {
    private ImageSelector selector;
    private static final int PREVIEW_IMAGE_HEIGHT = 100;
    private static final int LABEL_INDEX = 0;

    public ImageViewSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void setupInteractionLayout(){
        basicLayout.getChildren().remove(LABEL_INDEX);
        
        selector = new ImageSelector();
        selector.setPreviewImageHeight(PREVIEW_IMAGE_HEIGHT);
        
        error.setVisible(false);
        basicLayout.getChildren().addAll(selector, error);
    }
    
    @Override
    public void setParameterValue(Object value){
        System.out.println("type of: " + value);
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
