package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.Controller;
import authoringEnvironment.Variables;
import authoringEnvironment.setting.SpriteSetting;


/**
 * Creates the visual selector for adding a unit/wave and the time delay. Stores
 * the information for a WaveEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public class WaveFlowView extends FlowView {

    private static final String WAVE = "Wave";
    private static final String UNIT = "Unit";

    public WaveFlowView (int height, Controller c) {
        super(height, c);
    }

    @Override
    protected List<Node> createOptionSelector () {
        List<String> comboSelections = new ArrayList<String>();
        List<Node> options = new ArrayList<Node>();
        comboSelections.add(UNIT);
        comboSelections.add(WAVE);
        ObservableList<String> optionsList = FXCollections.observableArrayList(comboSelections);
        final ComboBox<String> partSelectorBox = new ComboBox<>(optionsList);
        Tooltip tooltip = new Tooltip("Select something to add!");
        tooltip.setTextAlignment(TextAlignment.CENTER);
        Tooltip.install(partSelectorBox, tooltip);

        // TODO: use lambda for selectUnit/selectWave method?
        partSelectorBox.setPromptText("...");
        partSelectorBox.valueProperty().addListener( (obs, oldValue, newValue) -> {
            if (newValue.equals(UNIT))
                selectUnit();
                                                    else if (newValue.equals(WAVE))
                                                        selectWave();
                                                });
        options.add(partSelectorBox);
        return options;
    }

    private void selectUnit () {
        SpriteSetting chooseUnit =
                new SpriteSetting(myController, WAVE, Variables.PARTNAME_ENEMIES,
                                  Variables.PARTNAME_ENEMIES);
        chooseUnit.setSingularChoice(true);
        chooseUnit.getChildren().remove(0);
        chooseUnit.setTextColor(Color.BLACK);
        insertElement(chooseUnit);

        List<String> fileNames = new ArrayList<String>();
        try {
            fileNames.add(chooseUnit.getDataAsString());
            partFileNames = fileNames;
        }
        catch (NumberFormatException e) {
        }
    }
}
