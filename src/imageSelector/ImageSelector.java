package imageSelector;

import imageSelector.util.ScaleImage;
import java.io.File;
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
 * @author Kevin He
 *
 */
public class ImageSelector extends VBox {
    private ImageView preview;
    private String filePath;
    private FileChooser fileChooser;
    
    /**
     * Creates an ImageSelector object.
     * 
     * @param stage the stage that the application is displayed on.
     * This is required to display an open-file dialog window.
     */
    public ImageSelector (Stage stage) {
        super(20);
        setAlignment(Pos.CENTER);

        HBox fileSelection = new HBox(10);

        StackPane textDisplay = new StackPane();

        Text fileDisplay = new Text("Choose an image...");
        Rectangle textBox = new Rectangle(150, 24);
        textBox.setFill(Color.WHITE);

        filePath = "imageSelector/img_not_available.png";
        preview = new ImageView(new Image(filePath));
        ScaleImage.scaleByHeight(preview, 100);

        fileChooser = new FileChooser();
        
        Button loader = new Button("Browse");
        loader.setMaxWidth(65);
        loader.setOnAction( (event) -> {
            openFileChooser(stage, fileDisplay);
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
            ScaleImage.scaleByHeight(preview, 100);
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
        ScaleImage.scale(preview, width, height);
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
