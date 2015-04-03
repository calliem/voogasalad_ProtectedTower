/**
 * Sets up the wave editor that allows the user to specify a specific map and the specific waves that will appear at specific spawn points. The user will also be able to customize delay in timing before each wave is sent out 
 * @author Callie Mao
 */

package authoringEnvironment.editors;

import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.stage.Stage;

public class LevelEditor extends MainEditor{
    public LevelEditor(Dimension2D dim, ResourceBundle resources, Stage s) {
		super(dim, resources, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Group configureUI() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    protected void createMap () {
        // TODO Auto-generated method stub

    }
}
