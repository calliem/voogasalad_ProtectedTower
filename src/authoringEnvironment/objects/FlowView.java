package authoringEnvironment.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import authoringEnvironment.Controller;
import authoringEnvironment.setting.SpriteSetting;
import imageselectorTEMP.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


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
    private final static String WAVE = "Wave";
    private final static String enemies = "Enemies";
    private static final int paddingSize = 10;
    private List<String> partFileNames;
    private List<Double> delays;

    /**
     * Creates the visual and input elements for the "timeline" in the
     * WaveEditor. Contains buttons to add a unit or wave and the delay time
     * between units/waves in a wave.
     * 
     * @param height
     *        Height of the hbox display
     */
    public FlowView (int height, Controller c) {
        super(paddingSize);
        c = myController;
        myHeight = height;
        fileChooser = new FileChooser();
        partFileNames = new ArrayList<String>();
        delays = new ArrayList<Double>();

        VBox partSelector = new VBox(paddingSize);

        // TODO remove duplication when figure out how to do the lambda thing
        Button selectUnitButton = new Button("Select Unit");
        selectUnitButton.setOnAction(e -> selectUnit());
        partSelector.getChildren().add(selectUnitButton);

        Button selectWaveButton = new Button("Select Wave");
        selectWaveButton.setOnAction(e -> selectWave());
        partSelector.getChildren().add(selectWaveButton);

        this.getChildren().add(partSelector);
        this.getChildren().add(new Rectangle());

        VBox arrow = createArrowAndDelayVisuals();
        this.getChildren().add(arrow);
        this.setPrefHeight(myHeight);
    }

    private VBox createArrowAndDelayVisuals () {
        ImageView arrowImage = new ImageView(new Image("images/arrow_icon.png"));
        ScaleImage.scaleByWidth(arrowImage, 120);
        delayTextField = new TextField();
        delayTextField.setMaxWidth(50);
        VBox arrow = new VBox(paddingSize);
        HBox timeInput = new HBox(paddingSize);
        timeInput.getChildren().add(delayTextField);
        timeInput.getChildren().add(new Text("s"));
        timeInput.setAlignment(Pos.CENTER);
        arrow.getChildren().add(timeInput);
        arrow.getChildren().add(arrowImage);
        arrow.setAlignment(Pos.CENTER);
        return arrow;
    }

    private void selectUnit () {
        SpriteSetting chooseUnit = new SpriteSetting(enemies, enemies);
        insertElement(chooseUnit);

        List<Double> unitDelay = new ArrayList<Double>();
        List<String> fileNames = new ArrayList<String>();
        try {
            unitDelay.add(Double.parseDouble(delayTextField.getText()));
            delays = unitDelay;
        }
        catch (NumberFormatException e) {

        }
    }

    private void selectWave () {
        File file = fileChooser.showOpenDialog(null);
        Text waveNameDisplay = new Text(file.getName());
        insertElement(waveNameDisplay);

        Map<String, Object> waveInfo = myController.loadPart(file
                .getAbsolutePath());
        try {
            String times = "Times";
            delays = (List<Double>) waveInfo.get(times);
            partFileNames = (List<String>) waveInfo.get(enemies);
        }
        catch (NullPointerException e) {

        }
    }

    private void insertElement (Node node) {
        this.getChildren().remove(1);
        this.getChildren().add(1, node);
    }

    /**
     * Gets the file names of units/other waves in the wave.
     * 
     * @return List<String> of file names
     */
    public List<String> getFileNames () {
        return partFileNames;
    }

    /**
     * Gets the doubles corresponding to delay times between units in the wave.
     * 
     * @return List<Double> of delay times between units in the wave
     */
    public List<Double> getDelays () {
        return delays;
    }

}
