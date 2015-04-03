package authoringEnvironment.editors;

import java.util.ResourceBundle;

/**
 * Sets up the projectile editor that allows the user to create projectiles as well as specify their parameters and properties 
 * @author Callie Mao
 */

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.stage.Stage;

public class ProjectileEditor extends PropertyEditor {
<<<<<<< HEAD
	public ProjectileEditor(Dimension2D dim, ResourceBundle resources) {
		super(dim, resources);
		// TODO Auto-generated constructor stub
	}
=======
    public ProjectileEditor(Dimension2D dim, ResourceBundle resources, Stage s) {
        super(dim, resources, s);
        // TODO Auto-generated constructor stub
    }
>>>>>>> 8895d74c0cf256fc1f2bc1a4062df4283a1b093a

    @Override
    public Group configureUI() {
        // TODO Auto-generated method stub
        return null;
    }
}
