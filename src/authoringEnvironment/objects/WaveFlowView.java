package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.Controller;
import authoringEnvironment.Variables;
import authoringEnvironment.setting.SpriteSetting;

public class WaveFlowView extends FlowView {

    public WaveFlowView (int height, Controller c) {
        super(height, c);
    }
    
    private void selectUnit () {
        SpriteSetting chooseUnit =
                new SpriteSetting(myController, "Wave", Variables.PARTNAME_ENEMIES,
                                  Variables.PARTNAME_ENEMIES);
        chooseUnit.getChildren().remove(0);
        chooseUnit.setTextColor(Color.BLACK);
        insertElement(chooseUnit);

        List<Double> unitDelay = new ArrayList<Double>();
        List<String> fileNames = new ArrayList<String>();
        try {
            unitDelay.add(Double.parseDouble(delayTextField.getText()));
            delays = unitDelay;
        }
        catch (NumberFormatException e) {

        }
    }
    
    @Override
    protected ComboBox<String> createOptionSelector () {
        ArrayList<String> options = new ArrayList<>();
        options.add("Unit");
        options.add("Wave");
        ObservableList<String> optionsList = FXCollections.observableArrayList(options);
        final ComboBox<String> partSelectorBox = new ComboBox<>(optionsList);
        Tooltip tooltip = new Tooltip("Select something to add!");
        tooltip.setTextAlignment(TextAlignment.CENTER);
        Tooltip.install(partSelectorBox, tooltip);

        // TODO: use lambda for selectUnit/selectWave method?
        partSelectorBox.setPromptText("...");
        partSelectorBox.valueProperty().addListener( (obs, oldValue, newValue) -> {
            if (newValue.equals("Unit"))
                selectUnit();
                                                    else if (newValue.equals("Wave"))
                                                        selectWave();
                                                });
        return partSelectorBox;
    }

}
