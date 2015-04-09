

package authoringEnvironment.editors;

import java.util.ArrayList;

/**
 * Sets up the unit editor that allows the user to create units as well as update their parameters/properties 
 * @author Callie Mao
 */
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;
import authoringEnvironment.objects.UnitView;

public class UnitEditor extends PropertyEditor {
    public UnitEditor(Dimension2D dim, Stage s) {
        super(dim, s);
        // TODO Auto-generated constructor stub
    }

    public ArrayList<UnitView> getUnits(){
        return null;
    }

    @Override
    protected void configureUI() {
        // TODO Auto-generated method stub
     
    }

}
