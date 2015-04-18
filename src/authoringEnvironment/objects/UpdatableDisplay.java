package authoringEnvironment.objects;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class UpdatableDisplay extends VBox {

	List<Node> myObjects;

    public UpdatableDisplay (List<Node> objects) {
    	myObjects = objects;
    	displayValues();
    }
    
    private void displayValues() {

        ScrollPane mapDisplay = new ScrollPane();
        VBox container = new VBox();
        
        if (myObjects.isEmpty()){
        	Text isEmpty = new Text("This list is empty.");
        }
        
        for (Node object : myObjects) {
            HBox mapInfo = new HBox();
            mapInfo.setSpacing(10);
            Text mapName = new Text();
            ImageView image = new ImageView();
            mapInfo.getChildren().addAll(mapName, image);
            container.getChildren().add(mapInfo);
        }
        
        mapDisplay.setContent(container);
        getChildren().add(mapDisplay);
    }
    
    private void updateDisplay(List<Node> updatedOjbects){
    	
    }
}
