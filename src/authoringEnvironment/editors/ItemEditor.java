package authoringEnvironment.editors;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;

public class ItemEditor extends PropertyEditor{
	
	private List<Node> myItems;

	public ItemEditor (){
		super();
	}
	

	@Override
	protected Group configureUI() {
		// TODO Auto-generated method stub
	    return new Group();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("itemeditor updated");
		
	}

	@Override
	public List<Node> getObjects() {
		// TODO Auto-generated method stub
		return myItems;
	}
	
}
