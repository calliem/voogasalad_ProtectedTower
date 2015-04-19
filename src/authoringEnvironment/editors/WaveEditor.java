package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
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
import authoringEnvironment.ProjectReader;
import authoringEnvironment.objects.FlowView;


/**
 * Creates the Wave Editor that allows the user to create and edit waves made
 * out of units or other waves.
 * 
 * @author Megan Gutter
 *
 */

public class WaveEditor extends MainEditor {
    private Map<String, ArrayList<FlowView>> myWaves;
    private final String WAVE = "Wave";
    private static final int PADDING = 10;
    private static final String NO_WAVES = "No waves yet...";
    private static final int BUTTON_HEIGHT = 24;
    private static final int WAVE_PANEL_HEIGHT = 105;
    private static final int INFO_PANEL_WIDTH = 170;
    private Text empty;
    private VBox editorLayout;
    private ScrollPane contentScrollPane;

    private int numWaves = 0;
    private static final Color EDITOR_BACKGROUND_COLOR = Color.GRAY;
    private static final Color DISPLAY_BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final Color INFO_BACKGROUND_COLOR = Color.web("#1D2951");
    private static final Color WAVE_NAME_COLOR = Color.GOLDENROD;

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

    public WaveEditor (Controller c, String name) {
        super(c, name);
        myWaves = new HashMap<String, ArrayList<FlowView>>();
    }

    /**
     * Overrides the configureUI() method in Editor and to be called in the
     * Editor superclass constructor. Sets up content for the WaveEditor tab
     * 
     * @return Group object that adds all visual elements
     */
    @Override
    public Group configureUI () {
        Group visuals = new Group();

        StackPane editor = new StackPane();
        Rectangle editorBackground =
                new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, EDITOR_BACKGROUND_COLOR);

        editorLayout = new VBox(PADDING);
        empty = new Text(NO_WAVES);
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);

        StackPane wavesDisplay = new StackPane();
        Rectangle wavesDisplayBackground =
                new Rectangle(CONTENT_WIDTH, WAVE_PANEL_HEIGHT + 2 * PADDING,
                              DISPLAY_BACKGROUND_COLOR);

        VBox contents = new VBox(PADDING);
        contents.setAlignment(Pos.CENTER_LEFT);
        contentScrollPane = new ScrollPane();
        contentScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setMaxHeight(CONTENT_HEIGHT - (BUTTON_HEIGHT + 2 * PADDING));
        contentScrollPane.setMaxWidth(CONTENT_WIDTH);

        Button makeNewWave = new Button("Create New Wave");
        makeNewWave.setMaxHeight(BUTTON_HEIGHT);
        makeNewWave.setOnAction(e -> {
            promptNewWaveName(editor, contents, wavesDisplayBackground).requestFocus();
        });

        wavesDisplay.getChildren().addAll(wavesDisplayBackground, contents);
        contentScrollPane.setContent(wavesDisplay);

        editorLayout.getChildren().addAll(makeNewWave);
        editorLayout.setAlignment(Pos.TOP_CENTER);
        editorLayout.setTranslateY(PADDING);
        StackPane.setAlignment(makeNewWave, Pos.TOP_RIGHT);

        editor.getChildren().addAll(editorBackground, editorLayout, empty);

        visuals.getChildren().add(editor);
        return visuals;
    }

    private TextField promptNewWaveName (StackPane editor, VBox contents, Rectangle background) {
        // TODO remove duplicated code from Kevin lol
        StackPane promptDisplay = new StackPane();
        Rectangle promptBackground = new Rectangle(300, 200);
        promptBackground.setOpacity(0.8);

        VBox promptContent = new VBox(20);
        promptContent.setAlignment(Pos.CENTER);
        Text prompt = new Text("Creating a new wave...");
        prompt.setFill(Color.WHITE);
        TextField promptField = new TextField();
        promptField.setMaxWidth(225);
        promptField.setPromptText("Enter a name...");

        HBox buttons = new HBox(10);
        Button create = new Button("Create");
        create.setOnAction( (e) -> {
            setupNewWave(editor, contents, background, promptDisplay, promptField);
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction( (e) -> {
            editor.getChildren().remove(promptDisplay);
        });

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(create, cancel);
        promptContent.getChildren().addAll(prompt, promptField, buttons);

        promptDisplay.getChildren().addAll(promptBackground, promptContent);

        editor.getChildren().add(promptDisplay);
        return promptField;
    }

    private void setupNewWave (StackPane editor,
                               VBox contents,
                               Rectangle background,
                               StackPane promptDisplay,
                               TextField promptField) {
        makeNewWave(contents, promptField.getText());
        if (myWaves.size() == 0) {
            editor.getChildren().remove(empty);
            editorLayout.getChildren().add(contentScrollPane);
        }
        myWaves.put(promptField.getText(), new ArrayList<FlowView>());

        numWaves++;
        background.setHeight(numWaves * WAVE_PANEL_HEIGHT + (numWaves - 1) * PADDING + 2 * PADDING);
        editor.getChildren().remove(promptDisplay);
    }

    private void makeNewWave (VBox contents, String waveName) {
        ScrollPane newWave = new ScrollPane();
        newWave.setHbarPolicy(ScrollBarPolicy.NEVER);
        newWave.setVbarPolicy(ScrollBarPolicy.NEVER);
        newWave.setMinHeight(WAVE_PANEL_HEIGHT);
        newWave.setMaxHeight(WAVE_PANEL_HEIGHT);
        // newWave.setMaxWidth(AuthoringEnvironment.getEnvironmentWidth());
        newWave.setPrefWidth(AuthoringEnvironment.getEnvironmentWidth() -
                             (INFO_PANEL_WIDTH + 3 * PADDING));
        newWave.setMaxWidth(AuthoringEnvironment.getEnvironmentWidth() -
                            (INFO_PANEL_WIDTH + 3 * PADDING));

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

        for (FlowView unit : myWaves.get(waveName)) {
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
        myController.addPartToGame(WAVE, waveName,
                                   ProjectReader.getParamsNoTypeOrName(WAVE), data);
    }

    private void addUnitToWave (ScrollPane displayPane, HBox wave, String waveName) {
        FlowView unit = new FlowView(100, myController);
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
    public Map<String, ArrayList<FlowView>> getWaves () {
        return myWaves;
    }
}
