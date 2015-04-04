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

public class ImageSelector extends VBox{
    private ImageView preview;
    private String filePath;
    
    public ImageSelector(Stage s){
        super(20);
        setAlignment(Pos.CENTER);
        
        HBox fileSelection = new HBox(10);

        StackPane textDisplay = new StackPane();
        
        Text fileDisplay = new Text("Choose an image...");
        Rectangle textBox = new Rectangle(150, 24);
        textBox.setFill(Color.WHITE);

        filePath = "images/img_not_available.png";
        preview = new ImageView(new Image(filePath));
        ScaleImage.scaleByHeight(preview, 100);
        
        Button loader = new Button("Browse");
        loader.setMaxWidth(65);
        loader.setOnAction((event) -> {
            openFileChooser(s, fileDisplay);
        });
        fileDisplay.setTextAlignment(TextAlignment.LEFT);
        textDisplay.getChildren().addAll(textBox, fileDisplay);

        fileSelection.getChildren().addAll(textDisplay, loader);
        fileSelection.setAlignment(Pos.CENTER);
        
        getChildren().addAll(preview, fileSelection);
    }
    
    /**
     * @param fileDisplay
     * @param preview
     * @return
     */
    private void openFileChooser (Stage stage, Text fileDisplay) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG Images (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage.getScene().getWindow());

        if(file!=null){
            String fileName = file.getName();
            fileDisplay.setText(fileName);
            filePath = String.format("images/%s", fileName);
            preview = new ImageView(new Image(filePath));
            ScaleImage.scaleByHeight(preview, 100);
            this.getChildren().remove(0);
            this.getChildren().add(0, preview);
        }
    }
    
    public String getSelectedImageFile(){
        return filePath;
    }
}
