package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoringEnvironment.Controller;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class builds the general editor structure for all individual editor tabs
 * viewable from the main environment
 * 
 * @author Callie Mao
 * @author Johnny Kumpf
 * @author Kevin He
 */

public abstract class Editor extends Tab {
	// or use a ArrayList<?> getObjects() method in this superclass?
	// TODO: have later methods get myDimensions from a closer class not
	// mainenvironment. THey are passed as parameters for a reason.

	protected String tabName;
	private Group contentRoot;
	protected List<String> partKeys;
	private Controller myController;

	public Editor(Controller controller, String name) {
		myController = controller;
		tabName = name;
		contentRoot = configureUI();
		partKeys = new ArrayList<String>();
		this.setContent(contentRoot);
		this.setText(tabName);
		this.setClosable(false);
	}

	protected abstract Group configureUI();

	protected void addPartToGame(String partType, String partName,
			List<String> params, List<Object> data) {
		partKeys.add(myController.addPartToGame(partType, partName, params,
				data));
	}

	protected void addPartToGame(String partType, Map<String, Object> part) {
		partKeys.add(myController.addPartToGame(partType, part));
	}

	protected List<String> getKeysForParts(String partTabName) {
		return myController.getKeysForParts(partTabName);
	}

	protected Map<String, Object> getPartCopy(String partKey) {
		return myController.getPartCopy(partKey);
	}

	protected Map<String, String> getSpriteFileMap(String partTabName) {
		return myController.getSpriteFileMap(partTabName);
	}

	public String getName() {
		return tabName;
	}

	public List<String> getPartKeys() {
		return new ArrayList<String>(partKeys);
	}

	// to be used by backend
	public void displayError(String s) {
		Stage stage = new Stage();
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		Text text = new Text(s);

		Button button = new Button("Ok");
		button.setOnMouseClicked(e -> stage.hide()); // this doesn't seem to
														// work.... also hide()
														// doesn't actually
														// close() it right..?
		root.getChildren().addAll(text, button);

		Scene scene = new Scene(root, 400, 200);// getWidth() / 4, getHeight() /
												// 6);

		stage.setTitle("Error"); // TODO: how to use this parameter?
									// myResources.getString("Error"). How to
									// add to the mainenvironment resources
									// without the parser freaking out?
		// MainStageTitle=protected Tower()
		stage.setScene(scene);
		stage.show();
	}

}
