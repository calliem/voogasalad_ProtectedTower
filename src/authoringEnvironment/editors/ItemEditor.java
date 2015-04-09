package authoringEnvironment.editors;

import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ItemEditor extends PropertyEditor{
	
	private ObservableList<ItemView> myItems;

	public ItemEditor (Dimension2D dim, Stage s){
		super(dim, s);
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
	public ObservableList<Node> getObjects() {
		// TODO Auto-generated method stub
		return myItems;
	}
	
}
