package imageselectorTEMP;

import imageselectorTEMP.util.ScaleImage;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


/**
 * Creates a visual interface for selecting and uploading an image.
 * Includes a preview of the selected image, and a UI for a fileChooser.
 * 
 * Create an images folder in your src folder and add selectable images there.
 * 
 * @author Kevin He
 * @author Bojia Chen
 *
 */
public class ImageSelector extends VBox {
    private ImageView preview;
    private StringProperty filePath;
    protected ResourceBundle myResources;
    private GraphicFileChooser fileSelection;

    private double previewImageHeight = 100;
    private double previewImageWidth = 100;
    private static final int PADDING = 10;
    private static final String NOT_AVAILABLE = "imageselectorTEMP/img_not_available.png";
//    private static final String SELECTOR_RESOURCES = "imageselector/SelectorText.properties";
    private static final String IMAGE_PROMPT = "Choose an image...";

    /**
     * Creates an ImageSelector object.
     * 
     * @param stage the stage that the application is displayed on.
     *        This is required to display an open-file dialog window.
     */
    public ImageSelector () {
        super(2 * PADDING);
        setAlignment(Pos.CENTER);
        filePath = new SimpleStringProperty();
        fileSelection = new GraphicFileChooser(IMAGE_PROMPT, NOT_AVAILABLE);
        fileSelection.addExtensionFilterByType("Image");

        filePath.setValue(NOT_AVAILABLE);
        preview = new ImageView(new Image(filePath.getValue()));

        filePath.bindBidirectional(fileSelection.getSelectedFileNameProperty());
        filePath.addListener( (obs, oldValue, newValue) -> {
            uploadImage();
            setPreviewImageSize(previewImageWidth, previewImageHeight);
        });

        getChildren().addAll(preview, fileSelection);
    }
    
    public void addExtensionFilter(String extension){
        fileSelection.addExtensionFilter(extension);
    }

    /**
     * This method opens the file chooser dialog window and updates the preview image.
     * 
     */
    private void uploadImage () {
        preview = new ImageView(new Image(filePath.getValue()));
        this.getChildren().remove(0);
        this.getChildren().add(0, preview);
    }

    /**
     * Allows the user to specify the dimensions of the preview image.
     * 
     * @param width the width of the image
     * @param height the height of the image
     */
    public void setPreviewImageSize (double width, double height) {
        previewImageWidth = width;
        previewImageHeight = height;
        ScaleImage.scale(preview, width, height);
    }

    public void setPreviewImageHeight (double height) {
        previewImageHeight = height;
        ScaleImage.scaleByHeight(preview, height);
    }

    public void setPreviewImageWidth (double width) {
        previewImageWidth = width;
        ScaleImage.scaleByWidth(preview, width);
    }

    /**
     * Returns the file path of the selected image.
     * 
     * @return filePath the file path of the selected image
     */
    public String getSelectedImageFile () {
        return filePath.getValue();
    }

    /**
     * Returns the image that the user selected.
     * 
     * @return preview the image that the user selected
     */
    public ImageView getSelectedImage () {
        return new ImageView(new Image(filePath.getValue()));
    }
    
    public void setSelectedImageFile(String path){
        if(path.equals(NOT_AVAILABLE)){
            clear();
        }
        else{
            filePath.setValue(path);
        }
    }
    
    public void clear(){
        filePath.setValue(NOT_AVAILABLE);
        fileSelection.getFileDisplay().setText(IMAGE_PROMPT);
    }
}
