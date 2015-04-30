package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import authoringEnvironment.Controller;


public class ModifierEditor extends Editor {

   protected static final int PADDING = 10;
    private static final int BUTTON_HEIGHT = 24;
    private static final Color EDITOR_BACKGROUND_COLOR = Color.GRAY;
    public static final String AUTHORING_OBJECTS_PACKAGE = "authoringEnvironment.objects.";
    private String NOTHING_CREATED;
    private Pane editor;
    private VBox editorLayout;
    private ScrollPane contentScrollPane;
    private Text empty;
    private Button makeNewRow;
    private StackPane modifiersDisplay;
    private Rectangle editorBackground;
    private List<VBox> rows;



    public ModifierEditor (Controller controller, String name) {
        super(controller, name);
    }

    @Override
    protected Group configureUI () {
        Group visuals = new Group();
        modifiersDisplay = new StackPane();
        NOTHING_CREATED =
                "No " + editorType.substring(0, editorType.indexOf("Editor")).toLowerCase() +
                        "s yet...";
        editor = new StackPane();
        editorBackground =
                new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, EDITOR_BACKGROUND_COLOR);

        empty = new Text(NOTHING_CREATED);
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);

        VBox contents = new VBox(PADDING);
        contents.setAlignment(Pos.CENTER);
        contents.setPrefWidth(CONTENT_WIDTH);
        contentScrollPane = new ScrollPane();
        contentScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        contentScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        contentScrollPane.setMaxHeight(CONTENT_HEIGHT - (BUTTON_HEIGHT + 2 * PADDING));
        contentScrollPane.setPrefWidth(CONTENT_WIDTH);
        contentScrollPane.setTranslateY(PADDING);

        makeNewRow =
                new Button("Create New " + editorType.substring(0, editorType.indexOf("Editor")));
        makeNewRow.setMaxHeight(BUTTON_HEIGHT);
        makeNewRow.setOnAction(e -> {
            addNewRow(contents);
        });

        modifiersDisplay.getChildren().addAll(contents);
        contentScrollPane.setContent(modifiersDisplay);
        editorLayout = new VBox(PADDING);
        contents.getChildren().addAll(editorBackground, makeNewRow);
        editorLayout.setAlignment(Pos.TOP_CENTER);
        editorLayout.setTranslateY(PADDING);
        StackPane.setAlignment(makeNewRow, Pos.TOP_RIGHT);
        editor.getChildren().addAll(editorBackground, editorLayout, empty);
        visuals.getChildren().add(editor);
        visuals.getChildren().add(contentScrollPane);
       rows = new ArrayList<>();
        return visuals;
    }
    
    private void addNewRow (VBox contents) {
        editor.getChildren().remove(empty);
        VBox toAdd = new ModifierStrip(myController,CONTENT_WIDTH);
        contents.getChildren().add(contents.getChildren().size() - 1, toAdd);
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }


}
