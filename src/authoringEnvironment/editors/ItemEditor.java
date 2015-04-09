package authoringEnvironment.editors;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ItemEditor extends PropertyEditor{
	
	private List<Node> myItems;

	public ItemEditor (Dimension2D dim, Stage s){
		super(dim);
	}
	

	@Override
	protected void configureUI() {
		// TODO Auto-generated method stub
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
