/**
 * Sets up the unit editor that allows the user to create units as well as update their parameters/properties 
 * @author Callie Mao
 */

package authoring.environment;
import java.util.ArrayList;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import authoring.environment.objects.Unit;

public class UnitEditor extends PropertyEditor {
   
	public UnitEditor(Dimension2D dim) {
		super(dim);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Unit> getUnits(){
        return new ArrayList<>();
    }

	@Override
	protected Group configureUI() {
		// TODO Auto-generated method stub
		return null;
	}
}
