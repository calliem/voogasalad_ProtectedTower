

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.stage.Stage;
import authoringEnvironment.objects.UnitView;

public class WaveEditor extends MainEditor{
    public WaveEditor(Dimension2D dim, Stage s) {
		super(dim, s);
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
