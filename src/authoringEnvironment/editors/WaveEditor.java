package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
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
		myRoot.getChildren().add(new FlowView(100, 100));
		return myRoot;
	}

	public ArrayList<UnitView> getWaves() {
		return new ArrayList<>();
	}

	@Override
	protected void createMap() {
	}

}
