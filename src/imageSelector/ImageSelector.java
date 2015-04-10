package imageselector;

import imageselector.util.ScaleImage;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Creates a visual interface for selecting and uploading an image.
 * Includes a preview of the selected image, and a UI for a fileChooser.
 * 
 * Create an images folder in your src folder and add selectable images there.
 * 
 * @author Kevin He
 *
 */
public class ImageSelector extends VBox {
    private ImageView preview;
    private String filePath;
    protected ResourceBundle myResources;
    private GraphicFileChooser fileSelection;
    
    private double previewImageHeight = 100;
    private double previewImageWidth = 100;
    private static final int PADDING = 10;
    private static final String NOT_AVAILABLE = "imageselector/img_not_available.png";
    private static final String SELECTOR_RESOURCES = "imageselector/SelectorText.properties";
    
    /**
     * Creates an ImageSelector object.
     * 
     * @param stage the stage that the application is displayed on.
     * This is required to display an open-file dialog window.
     */
    public ImageSelector () {
        super(2*PADDING);
        setAlignment(Pos.CENTER);

//        myResources = ResourceBundle.getBundle(SELECTOR_RESOURCES);
        
        fileSelection = new GraphicFileChooser("Choose an image...", NOT_AVAILABLE);

        filePath = NOT_AVAILABLE;
        preview = new ImageView(new Image(filePath));
        
//        Button loader = fileSelection.getButton();
        StringProperty file = new SimpleStringProperty();
        file.bind(fileSelection.getSelectedFileNameProperty());
        file.addListener((obs, oldValue, newValue) -> {
            filePath = fileSelection.getSelectedFileName();
            uploadImage();
            setPreviewImageSize(previewImageWidth, previewImageHeight);
        });

        getChildren().addAll(preview, fileSelection);
    }

    /**
     * This method opens the file chooser dialog window and updates the preview image.
     * 
     */
    private void uploadImage () {
        preview = new ImageView(new Image(filePath));
        this.getChildren().remove(0);
        this.getChildren().add(0, preview);
    }
    
    public void addExtensionFilter(String extension){
        fileSelection.addExtensionFilter(extension);
    }
    
    /**
     * Allows the user to specify the dimensions of the preview image. 
     * 
     * @param width     the width of the image
     * @param height    the height of the image
     */
    public void setPreviewImageSize(double width, double height){
        previewImageWidth = width;
        previewImageHeight = height;
        ScaleImage.scale(preview, width, height);
    }
    
    public void setPreviewImageHeight(double height){
        previewImageHeight = height;
        ScaleImage.scaleByHeight(preview, height);
    }
    
    public void setPreviewImageWidth(double width){
        previewImageWidth = width;
        ScaleImage.scaleByWidth(preview, width);
    }

    /**
     * Returns the file path of the selected image.
     * @return filePath the file path of the selected image
     */
    public String getSelectedImageFile () {
        return filePath;
    }
    
    /**
     * Returns the image that the user selected.
     * @return preview  the image that the user selected
     */
    public ImageView getSelectedImage (){
        return new ImageView(new Image(filePath));
    }
}
