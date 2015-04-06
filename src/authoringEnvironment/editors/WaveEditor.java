package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.objects.UnitView;

public class WaveEditor extends MainEditor {
	private Group myRoot;

	public WaveEditor(Dimension2D dim, ResourceBundle resources, Stage s) {
		super(dim, resources, s);
	}
	
	@Override
	public Group configureUI () {
		myRoot = new Group();		
		
		HBox wave1 = makeNewWave();
		myRoot.getChildren().add(wave1);
		return myRoot;
	}
	
	private HBox makeNewWave() {
		HBox newWave = new HBox(10);
		Button addUnit = new Button("Add Unit");
		addUnit.setOnAction(e -> {
			addUnitToWave(newWave);
		});
		return newWave;
	}
	
	private void addUnitToWave(HBox wave) {
		wave.getChildren().add(new FlowView(100, 100));
	}

	public ArrayList<UnitView> getWaves() {
		return new ArrayList<>();
	}

	@Override
	protected void createMap() {
	}

}
