

package authoringEnvironment.editors;

import authoringEnvironment.Controller;
import javafx.geometry.Dimension2D;
import javafx.stage.Stage;

/**
 * General abstract class for editors that allow user interaction in sprite/property creation and editing
 * @author Callie Mao
 */
public abstract class PropertyEditor extends Editor {

	public PropertyEditor(Controller c, String name) {
		super(c, name);
		// TODO Auto-generated constructor stub
	} // abstract class?

	public void update(){
		System.out.println("hi");
	}
}
