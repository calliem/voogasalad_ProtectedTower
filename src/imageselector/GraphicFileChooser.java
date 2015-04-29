package imageselector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

/**
 * @author Kevin He
 * @author Callie Mao (edits to public methods for use from external classes)
 *
 */
public class GraphicFileChooser extends StackPane {
    private static final int PADDING = 10;
    private static final int FILE_DISPLAY_WIDTH = 150;
    private static final int FILE_DISPLAY_HEIGHT = 24;
    private static final int LOAD_BUTTON_WIDTH = 75;

    private HBox selector;
    private Button loader;
    private FileChooser fileChooser;
    private Text fileDisplay;
    private StringProperty filePath;
    private StackPane textDisplay;

    private Map<String, List<String>> fileExtensions;

    public GraphicFileChooser (String prompt, String defaultFile) {
        selector = new HBox(PADDING);
        textDisplay = new StackPane();
        fileExtensions = new HashMap<>();
        loadDefaultExtensions();
        filePath = new SimpleStringProperty(defaultFile);

        fileDisplay = new Text(prompt);
        Rectangle textBox = new Rectangle(FILE_DISPLAY_WIDTH, FILE_DISPLAY_HEIGHT);
        textBox.setFill(Color.WHITE);
        textBox.setArcHeight(5);
        textBox.setArcWidth(5);

        fileChooser = new FileChooser();
        loader = new Button("Browse");
        loader.setOnMousePressed( (e) -> selectFile());
        loader.setMaxWidth(LOAD_BUTTON_WIDTH);

        fileDisplay.setTextAlignment(TextAlignment.LEFT);
        textDisplay.getChildren().addAll(textBox, fileDisplay);

        selector.getChildren().addAll(textDisplay, loader);
        selector.setAlignment(Pos.CENTER);

        getChildren().add(selector);
    }

    public GraphicFileChooser (String prompt) {
        this(prompt, null);
    }

    private void selectFile () {
        fileChooser.setInitialDirectory(new File("./src/images"));
        File file = fileChooser.showOpenDialog(null);
 
        if (file != null) {
            String fileName = file.getName();
            fileDisplay.setText(fileName);
            filePath.setValue(String.format("images/%s", fileName));
        }
    }

    public Text getFileDisplay () {
        return fileDisplay;
    }

    public StringProperty getSelectedFileNameProperty () {
        return filePath;
    }

    public String getSelectedFileName () {
        return filePath.getValue();
    }
    
    public void setSelectedFileName(String s){
        filePath.set(s);
    }

    protected Button getButton () {
        return loader;
    }

    protected FileChooser getFileChooser () {
        return fileChooser;
    }

    public void addExtensionCategory (String categoryName, List<String> fileTypes) {
        fileExtensions.put(categoryName, fileTypes);
    }

    private void loadDefaultExtensions () {
        List<String> images = new ArrayList<>();
        images.add("*.png");
        images.add("*.jpg");
        images.add("*.gif");

        List<String> text = new ArrayList<>();
        text.add("*.xml");

        fileExtensions.put("Image", images);
        fileExtensions.put("Text", text);
    }

    /**
     * Allows the user to add a valid extension for the file chooser.
     * 
     * @param extension the extension name (i.e. jpg or png)
     */
    public void addExtensionFilterByType (String type) {
        String preview = "";
        for(String extension : fileExtensions.get(type)){
            if(fileExtensions.get(type).indexOf(extension) != 0){
                preview += ", ";
            }
            preview += extension;
        }
        
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(String.format("%s Files (%s)",
                                                              type, preview), fileExtensions.get(type));
        fileChooser.getExtensionFilters().add(extFilter);
    }

    /**
     * Allows the user to add a valid image extension for the file chooser.
     * 
     * @param extension the extension name (i.e. jpg or png)
     */
    public void addExtensionFilter (String extension) {
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(String.format("%s Files (*.%s)",
                                                              extension.toUpperCase(),
                                                              extension.toLowerCase()),
                                                String.format("*.%s", extension.toLowerCase()));
        fileChooser.getExtensionFilters().add(extFilter);
    }

}
