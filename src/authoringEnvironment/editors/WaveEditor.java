

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.stage.Stage;
import authoringEnvironment.objects.UnitView;

<<<<<<< HEAD
/**
 * Sets up the wave editor that allows the user to specify units within a wave and the time delay before each unit appears on the map 
 * @author Callie Mao
 */

public class WaveEditor extends MainEditor {
	public WaveEditor(Dimension2D dim, ResourceBundle resources) {
		super(dim, resources);
=======
public class WaveEditor extends MainEditor{
    public WaveEditor(Dimension2D dim, ResourceBundle resources, Stage s) {
		super(dim, resources, s);
>>>>>>> 8895d74c0cf256fc1f2bc1a4062df4283a1b093a
		// TODO Auto-generated constructor stub
	}

	public ArrayList<UnitView> getWaves() {
		return new ArrayList<>();
	}

	@Override
	protected void createMap() {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override protected Group configureUI() { super.configureUI();
	 * getGridPane(); return null; }
	 */
}
