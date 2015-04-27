package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.DataFormatException;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.objects.WaveFlowView;
import authoringEnvironment.util.NamePrompt;


/**
 * Creates the Wave Editor that allows the user to create and edit waves made
 * out of units or other waves.
 * 
 * @author Megan Gutter
 *
 */

public class WaveEditor extends FlowEditor {
    private Map<String, ArrayList<WaveFlowView>> myWaves;
    private static final String WAVE = "Wave";
    private static final int PADDING = 10;
    private static final String NO_WAVES = "No waves yet...";
    private static final int BUTTON_HEIGHT = 24;
    private static final int WAVE_PANEL_HEIGHT = 105;
    private static final int INFO_PANEL_WIDTH = 170;
    private Text empty;
    private VBox editorLayout;
    private ScrollPane contentScrollPane;
    private StackPane editor;

    private String myKey;

    private int numWaves = 0;
    private NamePrompt prompt = new NamePrompt("wave");
    private static final Color EDITOR_BACKGROUND_COLOR = Color.GRAY;
    private static final Color DISPLAY_BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final Color INFO_BACKGROUND_COLOR = Color.web("#1D2951");
    private static final Color WAVE_NAME_COLOR = Color.GOLDENROD;

    private Node activeOverlay;

    /**
     * WaveEditor constructor, calls MainEditor superclass and initializes a map
     * of string (wave name) to array list of FlowViews (wave information) to
     * store waves
     * 
     * @param c
     *        Instance of the controller
     * @param name
     *        Name of the tab
     */

    public WaveEditor (Controller c, String name, String nameWithoutEditor) {
        super(c, name, nameWithoutEditor);
        myKey = Controller.KEY_BEFORE_CREATION;
        myWaves = new HashMap<String, ArrayList<WaveFlowView>>();
    }

    /**
     * Overrides the configureUI() method in Editor and to be called in the
     * Editor superclass constructor. Sets up content for the WaveEditor tab
     * 
     * @return Group object that adds all visual elements
     */
//    @Override
//    public Group configureUI () {
//        Group visuals = new Group();
//
//        editor = new StackPane();
//        Rectangle editorBackground =
//                new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, EDITOR_BACKGROUND_COLOR);
//
//        editorLayout = new VBox(PADDING);
//        empty = new Text(NO_WAVES);
//        empty.setFont(new Font(30));
//        empty.setFill(Color.WHITE);
//
//        StackPane wavesDisplay = new StackPane();
//        Rectangle wavesDisplayBackground =
//                new Rectangle(CONTENT_WIDTH, WAVE_PANEL_HEIGHT + 2 * PADDING,
//                              DISPLAY_BACKGROUND_COLOR);
//
//        VBox contents = new VBox(PADDING);
//        contents.setAlignment(Pos.CENTER_LEFT);
//        contentScrollPane = new ScrollPane();
//        contentScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
//        contentScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
//        contentScrollPane.setMaxHeight(CONTENT_HEIGHT - (BUTTON_HEIGHT + 2 * PADDING));
//        contentScrollPane.setMaxWidth(CONTENT_WIDTH);
//
//        Button makeNewWave = new Button("Create New Wave");
//        makeNewWave.setMaxHeight(BUTTON_HEIGHT);
//        makeNewWave.setOnAction(e -> {
//            promptNewWaveName(contents, wavesDisplayBackground);
//        });
//
//        wavesDisplay.getChildren().addAll(wavesDisplayBackground, contents);
//        contentScrollPane.setContent(wavesDisplay);
//
//        editorLayout.getChildren().addAll(makeNewWave);
//        editorLayout.setAlignment(Pos.TOP_CENTER);
//        editorLayout.setTranslateY(PADDING);
//        StackPane.setAlignment(makeNewWave, Pos.TOP_RIGHT);
//
//        editor.getChildren().addAll(editorBackground, editorLayout, empty);
//
//        visuals.getChildren().add(editor);
//        return visuals;
//    }

//    private void promptNewWaveName (VBox contents, Rectangle background) {
//        Button create = prompt.getCreateButton();
//        create.setOnAction( (e) -> {
//            String waveName = prompt.getEnteredName();
//            setupNewWave(contents, background, waveName);
//        });
//
//        Button cancel = prompt.getCancelButton();
//        cancel.setOnAction( (e) -> {
//            hideOverlay();
//
//        });
//
//        showOverlay();
//    }

//    // TODO: duplicated code in sprite editor, lines 195-199
//    private void showOverlay () {
//        prompt.showPrompt(editor);
//        isOverlayActive = true;
//    }
//
//    @Override
//    public void hideOverlay () {
//        if (isOverlayActive) {
//            prompt.playHidePromptAnimation().setOnFinished(e -> {
//                isOverlayActive = false;
//                editor.getChildren().remove(prompt);
//            });
//        }
//    }

    private void setupNewWave (VBox contents, Rectangle background, String waveName) {
        makeNewWave(contents, waveName);
        if (myWaves.size() == 0) {
            editor.getChildren().remove(empty);
            editorLayout.getChildren().add(contentScrollPane);
        }
        myWaves.put(waveName, new ArrayList<WaveFlowView>());

        numWaves++;
        background.setHeight(numWaves * WAVE_PANEL_HEIGHT + (numWaves - 1) * PADDING + 2 * PADDING);

        hideOverlay();

    }

    private void makeNewWave (VBox contents, String waveName) {
        ScrollPane newWave = new ScrollPane();
        newWave.setHbarPolicy(ScrollBarPolicy.NEVER);
        newWave.setVbarPolicy(ScrollBarPolicy.NEVER);
        newWave.setPrefHeight(WAVE_PANEL_HEIGHT);
        newWave.setPrefWidth(AuthoringEnvironment.getEnvironmentWidth() -
                             (INFO_PANEL_WIDTH + 3 * PADDING));
        // newWave.setMaxWidth(AuthoringEnvironment.getEnvironmentWidth() - (INFO_PANEL_WIDTH +
        // 3*PADDING));

        HBox waveDisplay = new HBox(PADDING);
        waveDisplay.setAlignment(Pos.CENTER);

        HBox waveContent = new HBox(PADDING);
        waveContent.setAlignment(Pos.CENTER_LEFT);
        waveContent.setTranslateX(PADDING);

        VBox info = new VBox(2 * PADDING);
        info.setAlignment(Pos.CENTER);

        Button addUnit = new Button("Add Unit");
        addUnit.setOnAction(e -> addUnitToWave(newWave, waveContent, waveName));

        Button save = new Button("Save");
        save.setOnAction(e -> saveWaveData(waveName));

        HBox buttons = new HBox(PADDING);
        buttons.setAlignment(Pos.CENTER);

        Text waveNameDisplay = new Text(waveName);
        waveNameDisplay.setFill(WAVE_NAME_COLOR);
        waveNameDisplay.setWrappingWidth(INFO_PANEL_WIDTH - 2 * PADDING);
        waveNameDisplay.setTextAlignment(TextAlignment.CENTER);
        buttons.getChildren().addAll(addUnit, save);
        info.getChildren().addAll(waveNameDisplay, buttons);

        StackPane buttonDisplay = new StackPane();
        Rectangle buttonBackground =
                new Rectangle(INFO_PANEL_WIDTH, WAVE_PANEL_HEIGHT, INFO_BACKGROUND_COLOR);

        buttonDisplay.getChildren().addAll(buttonBackground, info);
        // waveContent.getChildren().add(buttonDisplay);
        newWave.setContent(waveContent);

        waveDisplay.getChildren().addAll(buttonDisplay, newWave);
        contents.getChildren().add(waveDisplay);
    }

    private void saveWaveData (String waveName) {
        List<String> partFileNames = new ArrayList<String>();
        List<Double> delays = new ArrayList<Double>();
        List<Double> times = new ArrayList<Double>();
        times.add(0.0);

        for (WaveFlowView unit : myWaves.get(waveName)) {
            partFileNames.addAll(unit.getFileNames());
            delays.addAll(unit.getDelays());
        }

        for (Double d : delays) {
            Double all = 0.0;
            for (Double t : times)
                all += t;
            times.add(all + d);
        }

        List<Object> data = new ArrayList<Object>();
        data.add(partFileNames);
        data.add(times);
        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION))
                myKey = myController.addPartToGame(WAVE, waveName,
                                                   ProjectReader.getParamsNoTypeOrName(WAVE), data);
            else
                myKey =
                        myController.addPartToGame(myKey, WAVE, waveName,
                                                   ProjectReader.getParamsNoTypeOrName(WAVE), data);
        }
        catch (MissingInformationException | DataFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addUnitToWave (ScrollPane displayPane, HBox wave, String waveName) {
        WaveFlowView unit = new WaveFlowView(100, myController);
        wave.getChildren().add(unit);
        myWaves.get(waveName).add(unit);

        displayPane.setHvalue(2.0);
    }

    /**
     * Gets wave information
     * 
     * @return Map of wave information, where the name of the wave is the key
     *         and the value is its corresponding information stored in an array
     *         list of FlowViews.
     */
    public Map<String, ArrayList<WaveFlowView>> getWaves () {
        return myWaves;
    }

    @Override
    protected String returnEditorTypeName () {
        return "Wave";
    }
}
