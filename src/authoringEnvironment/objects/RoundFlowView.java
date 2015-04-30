package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.Controller;

/**
 * Creates the visual selector for adding a unit/wave, select a path, and the time delay. Stores
 * the information for a RoundEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public class RoundFlowView extends FlowView {
    private Controller myController;
    private List<String> pathKeys;

    public RoundFlowView (int height, Controller c) {
        super(height, c);
        myController = c;
    }

    @Override
    protected List<Node> createOptionSelector () {
        List<Node> options = new ArrayList<Node>();
        Button waveButton = new Button("Choose Wave");
        waveButton.setOnAction(e -> selectWave());
        options.add(waveButton);
        
        List<String> comboSelections = new ArrayList();
        ObservableList<String> optionsList = FXCollections.observableArrayList(comboSelections);//TODO: Get path selections for map selection
        final ComboBox<String> pathSelectorBox = new ComboBox<>(optionsList);
        Tooltip tooltip = new Tooltip("Select something to add!");
        tooltip.setTextAlignment(TextAlignment.CENTER);
        Tooltip.install(pathSelectorBox, tooltip);

        pathSelectorBox.setPromptText("...");
        pathSelectorBox.valueProperty().addListener( (obs, oldValue, newValue) -> {
            pathKeys = new ArrayList<String>();
            pathKeys.add("pathstartnameidk " + newValue); //TODO: get the keys
        });
        options.add(pathSelectorBox);
        
        return options;
    }
    
    public List<String> getPaths() {
        return pathKeys;
    }
}
