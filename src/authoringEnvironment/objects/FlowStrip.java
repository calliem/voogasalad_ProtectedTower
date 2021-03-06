package authoringEnvironment.objects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.DataFormatException;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.editors.FlowEditor;


/**
 * Superclass component of FlowEditors, handles all the data for one round/wave
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public abstract class FlowStrip extends HBox {

    private static final int PADDING = 10;
    private static final int STRIP_PANEL_HEIGHT = FlowEditor.STRIP_PANEL_HEIGHT;
    private static final int INFO_PANEL_WIDTH = 170;
    private static final Color INFO_BACKGROUND_COLOR = Color.web("#1D2951");
    private static final Color STRIP_NAME_COLOR = Color.GOLDENROD;
    private static final String AUTHORING_OBJECTS_PACKAGE = "authoringEnvironment.objects.";

    protected Controller myController;
    private String myKey;
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

        VBox buttons = new VBox(PADDING);
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

        addAtLeftOfRow(content);
        newRow.setContent(content);
        getChildren().addAll(buttonDisplay, newRow);
    }

    protected abstract void addAtLeftOfRow (HBox content);

    protected abstract void saveData (String componentName);

    protected void addComponentToRow (ScrollPane displayPane, HBox content, String name) {
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
    
    protected List<Double> getTimesFromZero (List<String> partFileNames, List<Double> delays) {
        List<Double> times = new ArrayList<Double>();
        times.add(0.0);
        for (Double d : delays) {
            Double all = 0.0;
            for (Double t : times)
                all += t;
            times.add(all + d);
        }
        
        // Get rid of potential last element due to extra arrow/input space
        if (partFileNames.size() != times.size()) {
            times.remove(times.size() - 1);
        }
        return times;
    }
    
    protected void saveToGame (String partType, String componentName, List<String> params, List<Object> data) {
        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION)) {
                myKey = myController.addPartToGame(partType, componentName,
                                                   params, data);
            }
            else {
                myKey = myController.addPartToGame(myKey, partType, componentName,
                                                   params, data);
            }
        }
        catch (MissingInformationException | DataFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
