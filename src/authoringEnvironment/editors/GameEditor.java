package authoringEnvironment.editors;

import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameEditor extends Editor {

	public GameEditor() {
		super();
	}

	@Override
	protected void configureUI() {

		// TODO: put everything into a StringSetting object

		GridPane framework = new GridPane();
		framework.setVgap(10);
		framework.setHgap(10);
		framework.setPadding(new Insets(10));

		// setConstraints(framework);
		Text gameName = new Text("Game Name");
		TextField gameNameEntry = new TextField();
		gameNameEntry.setPromptText("Enter the name of your game");
		Text gameDescription = new Text("Game Description");
		TextArea gameDescriptionEntry = new TextArea();
		gameDescriptionEntry.setPromptText("Enter a description of your game");
		gameDescriptionEntry.setPrefHeight(300);

		Text totalLives = new Text("TotalLives");
		TextField totalLivesEntry = new TextField();
		totalLivesEntry
				.setPromptText("Enter the number of lives the player will begin the game with");

		framework.add(gameName, 0, 0);
		framework.add(gameNameEntry, 1, 0);
		framework.add(gameDescription, 0, 1);
		framework.add(gameDescriptionEntry, 1, 1);
		framework.add(totalLives, 0, 2);
		framework.add(totalLivesEntry, 1, 2);

		getChildren().add(framework);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Node> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
