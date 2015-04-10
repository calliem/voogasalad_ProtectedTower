package authoringEnvironment;

import authoringEnvironment.objects.SpriteView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UpdatableDisplay extends HBox{
	
	SpriteView mySprite;
	
	public UpdatableDisplay(List<Node> objects){
		mySprite = sprite;
		
	}
	
	private void displayValues(){
		
		ScrollPane mapDisplay = new ScrollPane();
		VBox container = new VBox(); 
		

		
		for (Node object: objects){
			HBox mapInfo = new HBox();
			mapInfo.setSpacing(10);
			Text mapName = new Text("hi");
			ImageView image = new ImageView();
			mapInfo.getChildren().addAll(mapName, image);
			container.getChildren().add(mapInfo);
		}
		mapDisplay.setContent(container);
		getChildren().add(mapDisplay);
	}
	
	
	
	
	
	
}
