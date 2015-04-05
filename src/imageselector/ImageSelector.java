package imageselector;

import imageselector.util.ScaleImage;
import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    private FileChooser fileChooser;
    
    private double previewImageHeight = 100;
    private double previewImageWidth = 100;
    private static final int LOAD_BUTTON_WIDTH = 65;
    private static final int FILE_DISPLAY_WIDTH = 150;
    private static final int FILE_DISPLAY_HEIGHT = 24;
    private static final int PADDING = 10;
    private static final String NOT_AVAILABLE = "imageSelector/img_not_available.png";
    private static final String SELECTOR_RESOURCES = "imageSelector/SelectorText.properties";
    
    /**
     * Creates an ImageSelector object.
     * 
     * @param stage the stage that the application is displayed on.
     * This is required to display an open-file dialog window.
     */
    public ImageSelector (Stage stage) {
        super(2*PADDING);
        setAlignment(Pos.CENTER);

        ResourceBundle myResources = ResourceBundle.getBundle(SELECTOR_RESOURCES);
        
        HBox fileSelection = new HBox(PADDING);
        StackPane textDisplay = new StackPane();

        Text fileDisplay = new Text(myResources.getString("filePrompt"));
        Rectangle textBox = new Rectangle(FILE_DISPLAY_WIDTH, FILE_DISPLAY_HEIGHT);
        textBox.setFill(Color.WHITE);

        filePath = NOT_AVAILABLE;
        preview = new ImageView(new Image(filePath));

        fileChooser = new FileChooser();
        
        Button loader = new Button(myResources.getString("browse"));
        loader.setMaxWidth(LOAD_BUTTON_WIDTH);
        loader.setOnAction( (event) -> {
            openFileChooser(stage, fileDisplay);
            setPreviewImageSize(previewImageWidth, previewImageHeight);
        });
        fileDisplay.setTextAlignment(TextAlignment.LEFT);
        textDisplay.getChildren().addAll(textBox, fileDisplay);

        fileSelection.getChildren().addAll(textDisplay, loader);
        fileSelection.setAlignment(Pos.CENTER);

        getChildren().addAll(preview, fileSelection);
    }

    /**
     * This method opens the file chooser dialog window.
     * 
     * @param fileDisplay       the text object that displays what file the user has chosen
     * @param stage     the stage that the application is being displayed on
     */
    private void openFileChooser (Stage stage, Text fileDisplay) {
        File file = fileChooser.showOpenDialog(stage.getScene().getWindow());
        if (file != null) {
            String fileName = file.getName();
            fileDisplay.setText(fileName);
            filePath = String.format("images/%s", fileName);
            preview = new ImageView(new Image(filePath));
            this.getChildren().remove(0);
            this.getChildren().add(0, preview);
        }
    }

    /**
     * Allows the user to add a valid image extension for the file chooser.
     * @param extension the extension name (i.e. jpg or png)
     */
    public void addExtensionFilter (String extension) {
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(String.format("%s Images (*.%s)",
                                                              extension.toUpperCase(),
                                                              extension.toLowerCase()),
                                                String.format("*.%s", extension.toLowerCase()));
        fileChooser.getExtensionFilters().add(extFilter);
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
