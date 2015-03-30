/**
 * Sets up the wave editor that allows the user to specify units within a wave and the time delay before each unit appears on the map 
 * @author Callie Mao
 */

package authoring.environment;

import java.util.ArrayList;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import authoring.environment.objects.Unit;

public class WaveEditor extends MainEditor{
    public WaveEditor(Dimension2D dim) {
		super(dim);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Unit> getWaves(){
        return new ArrayList<>();
    }

	@Override
	protected void createMap() {
		// TODO Auto-generated method stub
		
	}

/*    @Override
    protected Group configureUI() {
    	super.configureUI();
    	getGridPane();
        return null;
    }*/
}
