

package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
/**
 * Sets up the unit editor that allows the user to create units as well as update their parameters/properties 
 * @author Callie Mao
 */
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.UnitView;

public class UnitEditor extends PropertyEditor {
    public UnitEditor(Controller c, String name) {
        super(c, name);
        // TODO Auto-generated constructor stub
    }

    public ArrayList<UnitView> getUnits(){
        return null;
    }

//    @Override
//    protected Group configureUI() {
//        // TODO Auto-generated method stub
//        return new Group();
//    }


}
