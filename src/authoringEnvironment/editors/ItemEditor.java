package authoringEnvironment.editors;

import java.util.List;

import authoringEnvironment.Controller;
import javafx.scene.Group;
import javafx.scene.Node;

public class ItemEditor extends SpriteEditor{
	
	private List<Node> myItems;

	public ItemEditor (Controller c, String name){
		super(c, name);
	}

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }
	

//	@Override
//	protected Group configureUI() {
//		// TODO Auto-generated method stub
//	    return new Group();
//	}

//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//		System.out.println("itemeditor updated");
//		
//	}

	
}
