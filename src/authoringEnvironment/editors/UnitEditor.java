/**
 * Sets up the unit editor that allows the user to create units as well as update their parameters/properties 
 * @author Callie Mao
 */

package authoringEnvironment.editors;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import authoringEnvironment.objects.UnitView;

public class UnitEditor extends PropertyEditor {
    public UnitEditor(Dimension2D dim, ResourceBundle resources) {
        super(dim, resources);
        // TODO Auto-generated constructor stub
    }

    public ArrayList<UnitView> getUnits(){
        return null;
    }

    @Override
    public Group configureUI() {
        // TODO Auto-generated method stub
        return null;
    }
}
