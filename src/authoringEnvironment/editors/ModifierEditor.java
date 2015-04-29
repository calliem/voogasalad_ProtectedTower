package authoringEnvironment.editors;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import authoringEnvironment.Controller;


public class ModifierEditor extends Editor {

    private static final int PADDING = 10;
    private static final int BUTTON_HEIGHT = 24;
    private static final int STRIP_PANEL_HEIGHT = 105;
    private static final Color EDITOR_BACKGROUND_COLOR = Color.GRAY;
    private static final Color DISPLAY_BACKGROUND_COLOR = Color.LIGHTBLUE;
    public static final String AUTHORING_OBJECTS_PACKAGE = "authoringEnvironment.objects.";
    private String NOTHING_CREATED;
    private Pane editor;
    private VBox editorLayout;
    private ScrollPane contentScrollPane;
    private Text empty;
    private Pane modifiersDisplay;
    private Button makeNewRow;

    public ModifierEditor (Controller controller, String name, String nameWithoutEditor) {
        super(controller, name, nameWithoutEditor);
    }

    @Override
    protected Group configureUI () {
        Group visuals = new Group();
        NOTHING_CREATED = "No " + editorName.toLowerCase() + "s yet...";
        modifiersDisplay = new StackPane();
        editor = new StackPane();
        Rectangle editorBackground =
                new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, EDITOR_BACKGROUND_COLOR);

        empty = new Text(NOTHING_CREATED);
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);

        VBox contents = new VBox(PADDING);
        contents.setAlignment(Pos.CENTER_LEFT);
        contentScrollPane = new ScrollPane();
        contentScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setMaxHeight(CONTENT_HEIGHT - (BUTTON_HEIGHT + 2 * PADDING));
        contentScrollPane.setMaxWidth(CONTENT_WIDTH);

        makeNewRow = new Button("Create New " + editorName);
        makeNewRow.setMaxHeight(BUTTON_HEIGHT);
        makeNewRow.setOnAction(e -> {
            addNewRow(contents);
        });

        contentScrollPane.setContent(modifiersDisplay);

        editorLayout = new VBox(PADDING);
        editorLayout.getChildren().addAll(makeNewRow);
        editorLayout.setAlignment(Pos.TOP_CENTER);
        editorLayout.setTranslateY(PADDING);
        StackPane.setAlignment(makeNewRow, Pos.TOP_RIGHT);
        editor.getChildren().addAll(editorBackground, editorLayout, empty);
        visuals.getChildren().add(editor);
        return visuals;
    }

    private void addNewRow (VBox contents) {
        // TODO Auto-generated method stub
        if (editorLayout.getChildren().size() == 1) {
            editor.getChildren().remove(empty);
        }
        HBox row = new HBox(PADDING);
        ChoiceBox<String> type = new ChoiceBox<>();
        ChoiceBox<String> authoringObjects =
                new ChoiceBox<>();
            authoringObjects.setItems(myController.getKeysForPartType("Tower"));
        
        row.getChildren().add(authoringObjects);
        editorLayout.getChildren().add(0, row);
        // myController.

    }

}
