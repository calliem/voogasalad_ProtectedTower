package authoringEnvironment.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.FlowStrip;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.util.NamePrompt;


/**
 * Superclass for WaveEditor and RoundEditor, sets up a row editing configuration
 * that consists of FlowStrips
 * 
 * @author Megan Gutter
 *
 */
public abstract class FlowEditor extends Editor {

    private static final int PADDING = 10;
    private static final int BUTTON_HEIGHT = 24;
    public static final int STRIP_PANEL_HEIGHT = 205;
    private static final Color EDITOR_BACKGROUND_COLOR = Color.GRAY;
    private static final Color DISPLAY_BACKGROUND_COLOR = Color.LIGHTBLUE;
    public static final String AUTHORING_OBJECTS_PACKAGE = "authoringEnvironment.objects.";

    private Map<String, ArrayList<FlowView>> myComponents;
    private String NOTHING_CREATED;
    private Text empty;
    private VBox editorLayout;
    private ScrollPane contentScrollPane;
    private StackPane editor;
    private int rows = 0;
    private NamePrompt prompt = new NamePrompt(partNames.getString(editorType).toLowerCase());

    public FlowEditor (Controller controller, String name) {
        super(controller, name);
        this.setStyle(MainEditor.DARK_TAB_CSS);
        myComponents = new HashMap<String, ArrayList<FlowView>>();
    }

    /**
     * Overrides the configureUI() method in Editor. Sets up visual content and data storage for
     * any class that extends FlowEditor
     * 
     * @return Group object that adds all visual elements
     */
    @Override
    public Group configureUI () {
        Group visuals = new Group();
        // TODO remove dependency
        NOTHING_CREATED = "No " + partNames.getString(editorType).toLowerCase() + "s yet...";
        editor = new StackPane();
        Rectangle editorBackground =
                new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, EDITOR_BACKGROUND_COLOR);

        editorLayout = new VBox(PADDING);
        empty = new Text(NOTHING_CREATED);
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);

        StackPane wavesDisplay = new StackPane();
        Rectangle wavesDisplayBackground =
                new Rectangle(CONTENT_WIDTH, STRIP_PANEL_HEIGHT + 2 * PADDING,
                              DISPLAY_BACKGROUND_COLOR);

        VBox contents = new VBox(PADDING);
        contents.setAlignment(Pos.CENTER_LEFT);
        contentScrollPane = new ScrollPane();
        contentScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setMaxHeight(CONTENT_HEIGHT - (BUTTON_HEIGHT + 2 * PADDING));
        contentScrollPane.setMaxWidth(CONTENT_WIDTH);

        // System.out.println("Editor Name = " + partNames.getString(editorType));
        Button makeNewRow = new Button("Create New " + partNames.getString(editorType));
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

    private void setupNewWave (VBox contents, Rectangle background, String componentName) {
        makeNewRow(contents, componentName);
        if (myComponents.size() == 0) {
            editor.getChildren().remove(empty);
            editorLayout.getChildren().add(contentScrollPane);
        }
        myComponents.put(componentName, new ArrayList<FlowView>());

        rows++;
        background.setHeight(rows * STRIP_PANEL_HEIGHT + (rows - 1) * PADDING + 2 * PADDING);

        hideOverlay();
    }

    private void makeNewRow (VBox contents, String componentName) {
        String toCreate = AUTHORING_OBJECTS_PACKAGE + partNames.getString(editorType) + "Strip";
        FlowStrip display = null;
        try {
            display = (FlowStrip) Class.forName(toCreate)
                    .getConstructor(String.class, String.class, Controller.class)
                    .newInstance(partNames.getString(editorType), componentName, myController);
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

    @Override
    public void hideOverlay () {
        if (isOverlayActive) {
            prompt.playHidePromptAnimation().setOnFinished(e -> {
                isOverlayActive = false;
                editor.getChildren().remove(prompt);
            });
        }
    }

}
