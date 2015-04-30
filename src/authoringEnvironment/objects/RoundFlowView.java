package authoringEnvironment.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;


/**
 * Creates the visual selector for adding a unit/wave, select a path, and the time delay. Stores
 * the information for a RoundEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public class RoundFlowView extends FlowView {
    private List<String> pathSelections;
    private List<String> pathKeys;
    private String waveSelectedKey;
    private String pathSelectedKey;
    

    public RoundFlowView (int height, Controller c) {
        super(height, c, new ArrayList<String>());
    }

    public RoundFlowView (int height, Controller c, List<String> paths) {
        super(height, c, paths);
    }

    @Override
    protected List<Node> createOptionSelector (List<String> pathKeys) {
        List<Node> options = new ArrayList<Node>();
        Button waveButton = new Button("Choose Wave");
        waveButton.setOnAction(e -> selectWave(options));
        options.add(waveButton);
        options.add(new Text(""));
        Map<String, String> pathNameToKey = new HashMap<String, String>();
        for (String key : pathKeys)
            pathNameToKey.put(myController.getNameForPart(key), key);

        pathSelections = new ArrayList<String>();
        for (String name : pathNameToKey.keySet())
            pathSelections.add(name);
        ObservableList<String> optionsList = FXCollections.observableArrayList(pathSelections);
        final ComboBox<String> pathSelectorBox = new ComboBox<>(optionsList);
        pathSelectorBox.valueProperty().addListener( (obs, oldValue, newValue) -> {
            pathSelectedKey = pathNameToKey.get(newValue);
        });
        Tooltip tooltip = new Tooltip("Select something to add!");
        tooltip.setTextAlignment(TextAlignment.CENTER);
        Tooltip.install(pathSelectorBox, tooltip);

        pathSelectorBox.setPromptText("...");
       
        options.add(pathSelectorBox);

        return options;
    }

    
    protected void selectWave (List<Node> options) {
        File file = fileChooser.showOpenDialog(null);
        Text waveNameDisplay = new Text(file.getName());
        insertElement(waveNameDisplay);

        Map<String, Object> waveInfo = myController.loadPart(file
                .getAbsolutePath());

        waveSelectedKey = (String) waveInfo.get(InstanceManager.PART_KEY_KEY);
    }

    public void changePathSelection (List<String> paths) {
        pathSelections = paths;
    }
    
    public String getPathKey(){
        return pathSelectedKey;
    }

    public String getWaveKey () {
        return waveSelectedKey;
    }

    public List<String> getPaths () {
        return pathKeys;
    }
}
