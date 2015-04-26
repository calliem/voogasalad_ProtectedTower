package authoringEnvironment.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import authoringEnvironment.objects.FlowStrip;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.util.NamePrompt;


public abstract class FlowEditor extends Editor {

    private Map<String, ArrayList<FlowView>> myComponents;
    // private final String WAVE = "Wave";
    private static final int PADDING = 10;
    // private static final String NO_WAVES = "No waves yet...";
    private final String NOTHING_CREATED = "No " + editorType.toLowerCase() + "s yet...";
    private static final int BUTTON_HEIGHT = 24;
    private static final int WAVE_PANEL_HEIGHT = 105;
    private static final int INFO_PANEL_WIDTH = 170;
    private Text empty;
    private VBox editorLayout;
    private ScrollPane contentScrollPane;
    private StackPane editor;

    private String myKey;

    private int rows = 0;
    private NamePrompt prompt = new NamePrompt(editorType.toLowerCase());
    private static final Color EDITOR_BACKGROUND_COLOR = Color.GRAY;
    private static final Color DISPLAY_BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final Color INFO_BACKGROUND_COLOR = Color.web("#1D2951");
    private static final Color WAVE_NAME_COLOR = Color.GOLDENROD;
    public static final String AUTHORING_OBJECTS_PACKAGE = "authoringEnvironment.objects.";

    public FlowEditor (Controller controller, String name) {
        super(controller, name);
        this.setStyle(MainEditor.DARK_TAB_CSS);
    }

    protected abstract String returnEditorTypeName ();

    @Override
    public Group configureUI () {

        Group visuals = new Group();

        editor = new StackPane();
        Rectangle editorBackground =
                new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, EDITOR_BACKGROUND_COLOR);

        editorLayout = new VBox(PADDING);
        empty = new Text(NOTHING_CREATED);
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

        Button makeNewRow = new Button("Create New " + editorType);
        makeNewRow.setMaxHeight(BUTTON_HEIGHT);
        makeNewRow.setOnAction(e -> {
            promptName(contents, wavesDisplayBackground);
        });

        wavesDisplay.getChildren().addAll(wavesDisplayBackground, contents);
        contentScrollPane.setContent(wavesDisplay);

        editorLayout.getChildren().addAll(makeNewRow);
        editorLayout.setAlignment(Pos.TOP_CENTER);
        editorLayout.setTranslateY(PADDING);
        StackPane.setAlignment(makeNewRow, Pos.TOP_RIGHT);

        editor.getChildren().addAll(editorBackground, editorLayout, empty);

        visuals.getChildren().add(editor);
        return visuals;
    }

    private void promptName (VBox contents, Rectangle background) {
        Button create = prompt.getCreateButton();
        create.setOnAction( (e) -> {
            String partName = prompt.getEnteredName();
            setupNewWave(contents, background, partName);
        });

        Button cancel = prompt.getCancelButton();
        cancel.setOnAction( (e) -> {
            hideOverlay();

        });

        showOverlay();
    }

    private void setupNewWave (VBox contents, Rectangle background, String waveName) {
        makeNewRow(contents, waveName);
        if (myComponents.size() == 0) {
            editor.getChildren().remove(empty);
            editorLayout.getChildren().add(contentScrollPane);
        }
        myComponents.put(waveName, new ArrayList<FlowView>());

        rows++;
        background.setHeight(rows * WAVE_PANEL_HEIGHT + (rows - 1) * PADDING + 2 * PADDING);

        hideOverlay();
    }

    private void makeNewRow (VBox contents, String componentName) {
        String toCreate = AUTHORING_OBJECTS_PACKAGE + editorType + "Strip";
        FlowStrip display = null;
        try {
            display = (FlowStrip) Class.forName(toCreate)
            .getConstructor(String.class, String.class, Controller.class)
            .newInstance(editorType, componentName, myController);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            e.printStackTrace();
        }
        contents.getChildren().add(display);
    }

    // TODO: duplicated code in sprite editor, lines 195-199
    private void showOverlay () {
        prompt.showPrompt(editor);
        isOverlayActive = true;
    }

}
