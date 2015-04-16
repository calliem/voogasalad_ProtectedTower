package authoringEnvironment.objects;

import imageselectorTEMP.util.ScaleImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import authoringEnvironment.Controller;
import authoringEnvironment.setting.SpriteSetting;

/**
 * Creates the visual selector for adding a unit/wave and the time delay. Stores
 * the information for the WaveEditor
 * 
 * @author Megan Gutter
 *
 */

public class FlowView extends HBox {
    private TextField delayTextField;
    private FileChooser fileChooser;
    private Controller myController;
    private int myHeight;
    private final static String ENEMIES = "Enemies";
    private final static String TIMES = "Times";
    private static final int PADDING = 10;
    private List<String> partFileNames;
    private List<Double> delays;
    
    private VBox selector;

    /**
     * Creates the visual and input elements for the "timeline" in the
     * WaveEditor. Contains buttons to add a unit or wave and the delay time
     * between units/waves in a wave.
     * 
     * @param height
     *            Height of the hbox display
     */
    public FlowView(int height, Controller c) {
        super(PADDING);
        this.setAlignment(Pos.CENTER);
        c = myController;
        myHeight = height;
        fileChooser = new FileChooser();
        partFileNames = new ArrayList<String>();
        delays = new ArrayList<Double>();

        selector = new VBox(0.5*PADDING);
        selector.setAlignment(Pos.CENTER);
        
        ArrayList<String> options = new ArrayList<>();
        options.add("Unit");
        options.add("Wave");
        ObservableList<String> optionsList = FXCollections.observableArrayList(options);
        final ComboBox<String> partSelectorBox = new ComboBox<>(optionsList);
        Tooltip tooltip = new Tooltip("Select something to add!");
        tooltip.setTextAlignment(TextAlignment.CENTER);
        Tooltip.install(partSelectorBox, tooltip);
        
        //TODO: use lambda for selectUnit/selectWave method?
        partSelectorBox.setPromptText("...");
        partSelectorBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue.equals("Unit"))
                selectUnit();
            else if(newValue.equals("Wave"))
                selectWave();
        });

        selector.getChildren().add(partSelectorBox);
        this.getChildren().add(selector);

        VBox arrow = createArrowAndDelayVisuals();
        this.getChildren().add(arrow);
        this.setPrefHeight(myHeight);
    }
    
    private void showArrowAnimation(ImageView arrow, TextField field){
        ScaleTransition showArrow = new ScaleTransition(Duration.millis(500), arrow);
        showArrow.setFromX(0.0);
        showArrow.setToX(1.0);
        
        FadeTransition showField = new FadeTransition(Duration.millis(250), field);
        showField.setFromValue(0.0);
        showField.setToValue(1.0);
        
        SequentialTransition animation = new SequentialTransition(showArrow, showField);
        animation.play();
    }

    private VBox createArrowAndDelayVisuals() {
        ImageView arrowImage = new ImageView(new Image("images/arrow_icon.png"));
        ScaleImage.scaleByWidth(arrowImage, 120);
        delayTextField = new TextField();
        delayTextField.setPromptText("(sec)");
        delayTextField.setAlignment(Pos.CENTER);
        delayTextField.setMaxWidth(50);
        
        VBox arrow = new VBox(0.5*PADDING);
        arrow.getChildren().addAll(delayTextField, arrowImage);
        arrow.setAlignment(Pos.CENTER);
        
        showArrowAnimation(arrowImage, delayTextField);
        return arrow;
    }

    private void selectUnit() {
        SpriteSetting chooseUnit = new SpriteSetting(ENEMIES, ENEMIES);
        chooseUnit.getChildren().remove(0);
        chooseUnit.setTextColor(Color.BLACK);
        insertElement(chooseUnit);

        List<Double> unitDelay = new ArrayList<Double>();
        List<String> fileNames = new ArrayList<String>();
        try {
            unitDelay.add(Double.parseDouble(delayTextField.getText()));
            delays = unitDelay;
        } catch (NumberFormatException e) {

        }
    }

    private void selectWave() {
        File file = fileChooser.showOpenDialog(null);
        Text waveNameDisplay = new Text(file.getName());
        insertElement(waveNameDisplay);

        Map<String, Object> waveInfo = myController.loadPart(file
                                                             .getAbsolutePath());
        try {
            delays = (List<Double>) waveInfo.get(TIMES);
            partFileNames = (List<String>) waveInfo.get(ENEMIES);
        } catch (NullPointerException e) {

        }
    }

    private void insertElement(Node node) {
        if(selector.getChildren().size() > 1){
            selector.getChildren().remove(1);
        }
        selector.getChildren().add(1, node);
    }

    /**
     * Gets the file names of units/other waves in the wave.
     * 
     * @return List<String> of file names
     */
    public List<String> getFileNames() {
        return partFileNames;
    }

    /**
     * Gets the doubles corresponding to delay times between units in the wave.
     * 
     * @return List<Double> of delay times between units in the wave
     */
    public List<Double> getDelays() {
        return delays;
    }

}
