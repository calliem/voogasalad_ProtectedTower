package authoringEnvironment.editors;

import java.awt.ScrollPane;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RowEditor extends Editor {

	public RowEditor(Dimension2D dim, Stage s) {
		super(dim, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Node configureUI() {
		ScrollPane foundation = new ScrollPane();
		Rectangle background = new Rectangle(myDimensions.getWidth()*MAP_WIDTH_MULTIPLIER, 0.9 * myDimensions.getHeight(), Color.web("2A2A29"));
		
		// TODO Auto-generated method stub
		return ;
	}

}
