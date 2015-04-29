package authoringEnvironment.objects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * Superclass component of FlowEditors, handles all the data for one round/wave
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public abstract class FlowStrip extends HBox {

    private static final int PADDING = 10;
    private static final int STRIP_PANEL_HEIGHT = 105;
    private static final int INFO_PANEL_WIDTH = 170;
    private static final Color INFO_BACKGROUND_COLOR = Color.web("#1D2951");
    private static final Color STRIP_NAME_COLOR = Color.GOLDENROD;
    private static final String AUTHORING_OBJECTS_PACKAGE = "authoringEnvironment.objects.";

    private Controller myController;
    protected String myKey;
    protected List<FlowView> myComponents;
    private String editorType;

    public FlowStrip (String type, String componentName, Controller c) {
        super(PADDING);
        myController = c;
        myComponents = new ArrayList<FlowView>();
        editorType = type;
        myKey = Controller.KEY_BEFORE_CREATION;
        setUpStrip(componentName);
    }

    private void setUpStrip (String componentName) {
        ScrollPane newRow = new ScrollPane();
        newRow.setHbarPolicy(ScrollBarPolicy.NEVER);
        newRow.setVbarPolicy(ScrollBarPolicy.NEVER);
        newRow.setPrefHeight(STRIP_PANEL_HEIGHT);
        newRow.setPrefWidth(AuthoringEnvironment.getEnvironmentWidth() -
                            (INFO_PANEL_WIDTH + 3 * PADDING));

        setAlignment(Pos.CENTER);

        HBox content = new HBox(PADDING);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setTranslateX(PADDING);

        VBox info = new VBox(2 * PADDING);
        info.setAlignment(Pos.CENTER);

        Button addUnit = new Button("Add To " + editorType);
        addUnit.setOnAction(e -> addComponentToRow(newRow, content, componentName));

        Button save = new Button("Save");
        save.setOnAction(e -> saveData(componentName));

        HBox buttons = new HBox(PADDING);
        buttons.setAlignment(Pos.CENTER);

        Text componentNameDisplay = new Text(componentName);
        componentNameDisplay.setFill(STRIP_NAME_COLOR);
        componentNameDisplay.setWrappingWidth(INFO_PANEL_WIDTH - 2 * PADDING);
        componentNameDisplay.setTextAlignment(TextAlignment.CENTER);
        buttons.getChildren().addAll(addUnit, save);
        info.getChildren().addAll(componentNameDisplay, buttons);

        StackPane buttonDisplay = new StackPane();
        Rectangle buttonBackground =
                new Rectangle(INFO_PANEL_WIDTH, STRIP_PANEL_HEIGHT, INFO_BACKGROUND_COLOR);

        buttonDisplay.getChildren().addAll(buttonBackground, info);
        // waveContent.getChildren().add(buttonDisplay);
        newRow.setContent(content);

        addAtLeftOfRow();
        getChildren().addAll(buttonDisplay, newRow);
    }

    protected abstract void addAtLeftOfRow ();

    protected abstract void saveData (String componentName);

    private void addComponentToRow (ScrollPane displayPane, HBox content, String name) {
        String toCreate = AUTHORING_OBJECTS_PACKAGE + editorType + "FlowView";
        FlowView flow = null;
        try {
            flow =
                    (FlowView) Class.forName(toCreate)
                            .getConstructor(int.class, Controller.class)
                            .newInstance(100, myController);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            e.printStackTrace();
        }
        content.getChildren().add(flow);
        myComponents.add(flow);

        displayPane.setHvalue(2.0);
    }
}
