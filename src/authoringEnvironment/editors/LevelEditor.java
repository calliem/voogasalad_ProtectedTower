

package authoringEnvironment.editors;

import authoringEnvironment.Controller;
import javafx.geometry.Dimension2D;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Sets up the wave editor that allows the user to specify a specific map and the specific waves that will appear at specific spawn points. The user will also be able to customize delay in timing before each wave is sent out 
 * @author Callie Mao
 */

public class LevelEditor extends MainEditor{
    public LevelEditor(Dimension2D dim, Stage s) {
		super(dim, s);
	}

    @Override
    protected void createMap() {
    	
    	
    }

	
	
/*	@Override
	public void update() {
		System.out.println("level editor updated");		// TODO Auto-generated method stub		
		MapEditor mapEditor = (MapEditor) Controller.getEditor("Maps");  //TODO: find a way to get sthe same resource file but to also use myResources.getString() on the proper resource file //how to avoid this issue of more dependencies on this string name. lots of code will have to change in order to change this; maybe use indexes instead?
    	getMapWorkspace().getChildren().add(mapEditor.getActiveMap().getMap());
	}*/
}
