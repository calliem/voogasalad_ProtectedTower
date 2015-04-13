package authoringEnvironment.editors;

import imageselectorTEMP.ImageSelector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import util.reflection.Reflection;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.ProjectileView;
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.objects.TowerView;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Dimension2D;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * General abstract class for editors that allow user interaction in
 * sprite/property creation and editing
 * 
 * @author Callie Mao
 */
public abstract class SpriteEditor extends Editor {
	private StackPane myContent;
	private HBox currentRow;
	private boolean overlayActive = false;
	private boolean editing = false;
	private Text empty;
	private List<Node> spritesCreated;
	private IntegerProperty numSprites;

	private static final double CONTENT_WIDTH = AuthoringEnvironment
			.getEnvironmentWidth();
	private static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment
			.getEnvironmentHeight();

	private static final int ROW_SIZE = 7;

	/**
	 * Creates a tower object.
	 * 
	 * @param dim
	 *            dimensions of the environment
	 * @param rb
	 *            the resource bundle containing displayed strings
	 * @param s
	 *            the stage on which the authoring environment is displayed
	 */
	public SpriteEditor(Controller c, String name) {
		super(c, name);
	}

	/**
	 * Sets up the editor UI.
	 */
	@Override
	protected Group configureUI() {
		// TODO Auto-generated method stub
		Group visuals = new Group();
		myContent = new StackPane();
		spritesCreated = new ArrayList<>();

		// TODO remove magic number
		Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT,
				Color.GRAY);

		VBox spriteDisplay = new VBox(20);
		spriteDisplay.setTranslateY(10);

		HBox editControls = setupEditControls();
		spriteDisplay.getChildren().add(editControls);

		ArrayList<HBox> rows = new ArrayList<>();

		// TODO remove magic numbers
		HBox row = new HBox(20);
		currentRow = row;
		spriteDisplay.getChildren().add(row);
		rows.add(row);

		numSprites = new SimpleIntegerProperty(0);
		numSprites.addListener((obs, oldValue, newValue) -> {
			if ((int) newValue == 0) {
				myContent.getChildren().add(empty);
			} else if ((int) newValue > 0
					&& myContent.getChildren().contains(empty)) {
				myContent.getChildren().remove(empty);
			}

			// if there's 2 on a row already
				else if (currentRow.getChildren().size() == ROW_SIZE) {
					HBox newRow = new HBox(20);
					newRow.setAlignment(Pos.TOP_CENTER);
					currentRow = newRow;
					rows.add(newRow);
					spriteDisplay.getChildren().add(newRow);
				}

				else if ((int) newValue < (int) oldValue) {
					System.out.println("rows: " + rows.size());
				}
			});

		empty = new Text("No " + tabName.toLowerCase() + " yet...");
		// myResources.getString("NoTowersCreated"));
		empty.setFont(new Font(30));
		empty.setFill(Color.WHITE);

		currentRow.setAlignment(Pos.TOP_CENTER);
		currentRow.setMaxHeight(100);

		myContent.getChildren().addAll(background, spriteDisplay, empty);
		StackPane.setAlignment(spriteDisplay, Pos.TOP_CENTER);
		visuals.getChildren().add(myContent);
		return visuals;
	}

	private HBox setupEditControls() {
		HBox editControls = new HBox(10);
		editControls.setAlignment(Pos.CENTER_RIGHT);
		Button edit = new Button("Edit");
		fixButtonDimensions(edit);
		edit.setTranslateX(-10);

		Button add = new Button("+ "
				+ tabName.substring(0, tabName.length() - 1));
		add.setTranslateX(-10);
		fixButtonDimensions(add);
		add.setOnMousePressed((e) -> {
			promptSpriteCreation();
		});

		edit.setOnAction((e) -> {
			if (!editing) {
				startEditing(editControls, edit, add);
			} else {
				finishEditing(editControls, edit, add);
			}
			editing = !editing;
		});
		editControls.getChildren().add(edit);
		return editControls;
	}

	private void promptSpriteCreation() {
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

		ImageSelector imgSelector = new ImageSelector();
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
		});

		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(create, cancel);
		promptContent.getChildren().addAll(prompt, promptField, imgSelector,
				buttons);

		promptDisplay.getChildren().addAll(promptBackground, promptContent);
		showEditScreen(promptDisplay);
	}

	private void addSprite(String name, String imageFile, HBox row) {
		String className = "authoringEnvironment.objects."
				+ tabName.substring(0, tabName.length() - 1) + "View";
		SpriteView sprite = generateSpriteView(myController, name, imageFile, className);
		sprite.initiateEditableState();
		setupSpriteAction(sprite);
		BooleanProperty spriteExists = new SimpleBooleanProperty(true);
		spriteExists.bind(sprite.isExisting());
		spriteExists.addListener((obs, oldValue, newValue) -> {
			deleteSprite(row, sprite, newValue);
		});

		row.getChildren().add(sprite);
		spritesCreated.add(sprite);
		String key = myController.addPartToGame(
				tabName.substring(0, tabName.length() - 1),
				sprite.getParameterFields());
		myController.specifyPartImage(key, sprite.getImageFilePath());
		numSprites.setValue(spritesCreated.size());
	}

	private SpriteView generateSpriteView(Controller c, String name, String imageFile,
			String className) {
		SpriteView sprite = null;
		try {
			sprite = (SpriteView) Class.forName(className)
					.getConstructor(Controller.class, String.class, String.class)
					.newInstance(c, name, imageFile);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| ClassNotFoundException e1) {
			System.err
					.println("Class: "
							+ className
							+ "\nCouldn't be created with constructor (Controller, String, String)");
			e1.printStackTrace();
		}
		return sprite;
	}

	private void deleteSprite(HBox row, SpriteView sprite, Boolean newValue) {
		if (!newValue) {
			PauseTransition wait = new PauseTransition(Duration.millis(200));
			wait.setOnFinished((e) -> row.getChildren().remove(sprite));
			wait.play();
			spritesCreated.remove(sprite);
			numSprites.setValue(spritesCreated.size());
		}
	}

	private void setupSpriteAction(SpriteView sprite) {
		sprite.setOnMousePressed((e) -> {
			if (sprite.isExisting().getValue() && editing)
				showEditScreen(sprite.getEditorOverlay());
		});
		sprite.getCloseButton().setOnAction((e) -> {
			hideEditScreen(sprite.getEditorOverlay());
			sprite.discardUnsavedChanges();
			sprite.setupTooltipText(sprite.getSpriteInfo());
		});
	}

	private void showEditScreen(StackPane overlay) {
		if (!overlayActive) {
			myContent.getChildren().add(overlay);
			scaleEditScreen(0.0, 1.0, overlay);
			overlayActive = true;
		}
	}

	private void hideEditScreen(StackPane overlay) {
		if (overlayActive) {
			ScaleTransition scale = scaleEditScreen(1.0, 0.0, overlay);
			scale.setOnFinished((e) -> {
				myContent.getChildren().remove(overlay);
				overlayActive = false;
			});
		}
	}

	private ScaleTransition scaleEditScreen(double from, double to,
			StackPane overlay) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(200),
				overlay);
		scale.setFromX(from);
		scale.setFromY(from);
		scale.setToX(to);
		scale.setToY(to);
		scale.setCycleCount(1);
		scale.play();

		return scale;
	}

	private void finishEditing(HBox editControls, Button edit, Button add) {
		TranslateTransition move = transitionButton(add, -10, 90);
		move.setOnFinished(e -> editControls.getChildren().remove(0));
		edit.setText("Edit");
		for (Node sprite : spritesCreated) {
			((SpriteView) sprite).exitEditableState();
		}
	}

	private void startEditing(HBox editControls, Button edit, Button add) {
		editControls.getChildren().add(0, add);
		transitionButton(add, 90, -10);
		edit.setText("Done");
		for (Node sprite : spritesCreated) {
			((SpriteView) sprite).initiateEditableState();
		}
	}

	private TranslateTransition transitionButton(Button add, double from,
			double to) {
		TranslateTransition moveButton = new TranslateTransition(
				Duration.millis(100), add);
		moveButton.setFromX(from);
		moveButton.setToX(to);
		moveButton.setCycleCount(1);
		moveButton.play();

		return moveButton;
	}

	private void fixButtonDimensions(Button button) {
		button.setMinWidth(100);
		button.setMaxWidth(100);
	}
	//
	// @Override
	// public List<Node> getObjects() {
	// // TODO Auto-generated method stub
	// return spritesCreated;
	// }

	// @Override
	// public void update() {
	// // TODO Auto-generated method stub
	//
	// }
}
