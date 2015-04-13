package authoringEnvironment.editors;

import imageselectorTEMP.ImageSelector;

import java.util.List;

import authoringEnvironment.Controller;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 * This class extends SpriteEditor and allows the user to create a tile object. Because of slow runtimes from large image sizes, users are currently restricted to only being able to utilize rectanglular tiles with customized colors. If applicable, the tiles can be given tags that can later be utilized in the MapEditor to set specific terrain tags, and the tags can later be matched by the user when placing towers. 
 * @author Callie Mao
 *
 */
public class TileEditor extends SpriteEditor{

    public TileEditor(Controller c, String name) {
        super(c, name);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void promptSpriteCreation() {
		StackPane promptDisplay = new StackPane();
		Rectangle promptBackground = new Rectangle(300, 400);
		promptBackground.setOpacity(0.8);

		VBox promptContent = new VBox(20);
		promptContent.setAlignment(Pos.CENTER);
		Text prompt = new Text("Creating a new "
				+ tabName.toLowerCase().substring(0, tabName.length() - 1)
				+ "...");
		prompt.setFill(Color.WHITE);
		TextField promptField = new TextField();
		promptField.setMaxWidth(225);
		promptField.setPromptText("Enter a name...");

		/*ImageSelector imgSelector = new ImageSelector();
		imgSelector.addExtensionFilter("png");
		imgSelector.addExtensionFilter("jpg");
		imgSelector.addExtensionFilter("gif");
		imgSelector.setPreviewImageSize(225, 150);

		HBox buttons = new HBox(10);
		Button create = new Button("Create");
		create.setOnAction((e) -> {
			addSprite(promptField.getText(),
					imgSelector.getSelectedImageFile(), currentRow);
			hideEditScreen(promptDisplay);
		});

		Button cancel = new Button("Cancel");
		cancel.setOnAction((e) -> {
			hideEditScreen(promptDisplay);
		});*/

    }
}
