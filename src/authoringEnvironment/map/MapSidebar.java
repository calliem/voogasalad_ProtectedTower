package authoringEnvironment.map;

import imageselectorTEMP.GraphicFileChooser;
import imageselectorTEMP.ImageSelector;

import java.util.List;
import java.util.ResourceBundle;

import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Scaler;
import authoringEnvironment.Sidebar;
import authoringEnvironment.UpdatableDisplay;
import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.TileMap;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Right sidebar on the map editor containing display properties that allows a
 * user to interactively set grid size, tile size, specific tiles / tile colors,
 * paths, and other visual elements of the map.
 * 
 * @author Callie Mao
 *
 */

// TODO: abstract further
public class MapSidebar extends Sidebar { // add a gridpane later on. but a
											// gridpane is hard to edit to set
											// things in between other things...

	// TODO: display sidebar with a gridpane not an HBox to keep everything
	// aligned and beautiful

	private static final double PADDING = AuthoringEnvironment.getEnvironmentWidth() / 128; // maybe
																							// set
																							// the
																							// spacing
																							// dynamically
																							// instead

	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private static final Color DEFAULT_TILE_DISPLAY_COLOR = Color.TRANSPARENT;
	private static final double DEFAULT_TILE_DISPLAY_SIZE = AuthoringEnvironment
			.getEnvironmentWidth() / 32;
	// TODO: is importing the main environment bad design? is this an added
	// dependency?
	private static final double TEXT_FIELD_WIDTH = AuthoringEnvironment
			.getEnvironmentWidth() / 32;
	private int myLives;
	private MapWorkspace myMapWorkspace;
	private static final int DEFAULT_LIVES = 20; // TODO: how to get this number
													// from Johnny
	private Color myActiveColor;

	private TextField tileRowDisplay;
	private TextField tileColDisplay;
	private TextField tileSizeDisplay;
	private VBox mapSettings;
	private Scaler myScaler;

	public MapSidebar(ResourceBundle resources, ObservableList<Node> maps,
			MapWorkspace mapWorkspace) { // active map may not yet be saved and
											// thus we cannot simply pull it out
											// of the observable list
		super(resources, maps, mapWorkspace);
		// mySelectedMap = activeMap;
		myMapWorkspace = mapWorkspace;
		myLives = DEFAULT_LIVES;
		myScaler = new Scaler(); //TODO: might be able to move this to superclass
		/*
		 * ObservableList<PathView> pathList =
		 * FXCollections.observableArrayList();
		 * getChildren().add(createListView(pathList, 300));
		 */
		createMapSettings();
	}

	protected void createMapSettings() {
		// TODO: make a main tab to display the stuff here
		// createTitleText(getResources().getString("GameSettings"));
		// setLives();
		mapSettings = createTitleText(getResources().getString("MapSettings"));
	//	createTitleText(getResources().getString("SetTiles"));
		selectTile();
		createSettings();

		setEditMapButtons();
		displayMaps();
	}

	private void displayMaps() {
		UpdatableDisplay mapDisplay = new UpdatableDisplay(getMaps());
		mapSettings.getChildren().add(mapDisplay);
	}

	// Lots of duplication below
	private void setLives() {
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		Text lives = new Text(getResources().getString("Lives"));
		TextField textField = new TextField(Integer.toString(myLives));

		Button button = new Button(getResources().getString("Update"));
		button.setOnMouseClicked(e -> System.out.println(textField.getText())); // TODO:
																				// add
																				// to
																				// properties
																				// file
																				// to
																				// be
																				// saved

		textField.setPrefWidth(TEXT_FIELD_WIDTH);
		selection.getChildren().addAll(lives, textField, button);
		mapSettings.getChildren().add(selection);
	}

	private void setTileSize() {
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		Text lives = new Text(getResources().getString("TileSize"));
		tileSizeDisplay = new TextField(Integer.toString(myMapWorkspace.getActiveMap()
				.getTileSize()));
		Button button = new Button(getResources().getString("Update"));
		button.setOnMouseClicked(e -> myMapWorkspace.getActiveMap().changeTileSize(
				Integer.parseInt(tileSizeDisplay.getText())));
		tileSizeDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
		Text px = new Text(getResources().getString("PxSuffix"));
		selection.getChildren().addAll(lives, tileSizeDisplay, px, button);
		mapSettings.getChildren().add(selection);

	}

	private void selectTile() {
		HBox selectTile = new HBox();
		selectTile.setSpacing(30); // remove hardcoding

		VBox selection = new VBox();
		Text selectTileColor = new Text(getResources().getString("SelectTileColor")); // TODO:
																						// fix
		ColorPicker picker = new ColorPicker();
		selection.getChildren().addAll(selectTileColor, picker);

		Rectangle rectangleDisplay = new Rectangle(DEFAULT_TILE_DISPLAY_SIZE,
				DEFAULT_TILE_DISPLAY_SIZE, DEFAULT_TILE_DISPLAY_COLOR); // remove
																		// hardcoded
																		// including
																		// the
																		// color;

		selectTile.getChildren().addAll(selection, rectangleDisplay);

		mapSettings.getChildren().add(selectTile);
		picker.setOnAction(e -> changeActiveTileColor(picker.getValue(), rectangleDisplay));
	}

	private void changeActiveTileColor(Color color, Rectangle display) {
		myActiveColor = color;
		display.setFill(color);
		myMapWorkspace.getActiveMap().setActiveColor(color);
	}

	/*
	 * private void changeActiveTileColor(Color color, Rectangle display){
	 * //TODO: make this do the right thing display.setFill(color);
	 * myMapWorkspace.getActiveMap().setActiveColor(color);
	 * 
	 * 
	 * //TODO: make it update the color in the actual grid }
	 */

	// TODO: remove duplicated code
	private void createSettings() {
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		
		setImage();

		Text text = new Text(getResources().getString("MapDimensions"));
		HBox textFields = new HBox();
		tileRowDisplay = new TextField(Integer.toString(myMapWorkspace.getActiveMap()
				.getNumRows()));
		tileRowDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
		Text xSeparator = new Text(getResources().getString("DimensionXSeparation"));
		tileColDisplay = new TextField(Integer.toString(myMapWorkspace.getActiveMap()
				.getNumCols()));
		textFields.getChildren().addAll(tileRowDisplay, xSeparator, tileColDisplay);

		tileColDisplay.setPrefWidth(TEXT_FIELD_WIDTH);
		Button setGridDimButton = new Button(getResources().getString("Update"));
		setGridDimButton.setOnMouseClicked(e -> updateMapDim(tileRowDisplay.getText(),
				tileColDisplay.getText()));
		selection.getChildren().addAll(text, textFields, setGridDimButton);
		
		setTileSize();

		
		mapSettings.getChildren().add(selection);

	}

	private void setImage() {
		HBox selection = new HBox();
		selection.setSpacing(PADDING);

		// Text text = new Text("Select Background Image");
		// getResources().getString("Select Background Image")

		// TODO: this is duplicated from Kevin's class
		GraphicFileChooser imgSelector = new GraphicFileChooser("Select background");
		imgSelector.addExtensionFilter("png");
		imgSelector.addExtensionFilter("jpg");

		StringProperty imgFile = imgSelector.getSelectedFileNameProperty();
		imgFile.addListener((obs, oldValue, newValue) -> {
			System.out.println(newValue);
			myMapWorkspace.getActiveMap().setBackground(newValue);
		});

		mapSettings.getChildren().add(imgSelector);
	}

	// TODO: find a way to make this work
	/*
	 * private Button updateTextField(String s, int boxWidth){ HBox selection =
	 * new HBox(); selection.setSpacing(10); Text lives = new
	 * Text(getResources().getString(s)); TextField textField = new TextField();
	 * Button button = new Button(getResources().getString("Update"));
	 * textField.setPrefWidth(boxWidth); selection.getChildren().addAll(lives,
	 * textField, button); getChildren().add(selection);
	 * 
	 * }
	 */

	// TODO: path view

	private void setEditMapButtons() {
		HBox mapButtons = new HBox();
		mapButtons.setSpacing(20);
		Button createMapButton = new Button(getResources().getString("CreateMap"));
		createMapButton.setOnMouseClicked(e -> createMap());
		Button saveMapButton = new Button(getResources().getString("SaveMap"));
		saveMapButton.setStyle("-fx-background-color: #6e8b3d; -fx-text-fill: white;");
		saveMapButton.setOnMouseClicked(e -> saveMap(myMapWorkspace.getActiveMap()));
		Button deleteMapButton = new Button(getResources().getString("DeleteMap"));
		deleteMapButton.setStyle("-fx-background-color: #cd3333; -fx-text-fill: white;");
		deleteMapButton.setOnMouseClicked(e -> removeMap());
		mapButtons.getChildren().addAll(createMapButton, saveMapButton, deleteMapButton);
		mapSettings.getChildren().add(mapButtons);
	}

	private void removeMap() {
		System.out.println("removing map from map sidebar");
		System.out.println("Maps before remove: " + getMaps());
		
		ScaleTransition scale = myScaler.scaleEditScreen(1.0, 0.0, myMapWorkspace.getActiveMap());
		scale.setOnFinished((e) -> {
			getMaps().remove(myMapWorkspace.getActiveMap());
			getMapWorkspace().removeMap();
		});
		
		
		//PauseTransition wait = new PauseTransition(Duration.millis(200));
		//wait.setOnFinished((e) -> getMapWorkspace().removeMap());
		
//		ScaleTransition scale = myScaler.scaleEditScreen(1.0, 0.0, overlay);
//		scale.setOnFinished((e) -> {
//			myContent.getChildren().remove(overlay);
//			overlayActive = false;
//		});
		
		//wait.play();
		
		
		System.out.println("Maps after remove: " + getMaps());
	}

	/*
	 * //TODO: remove duplication since this is the exact code written in
	 * mapeditor protected void createMap() { myActiveMap = new
	 * TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
	 * setDefaultTextFieldValues(); //TODO: ______
	 * getMapWorkspace().getChildren().add(myActiveMap); }
	 */

	protected void createMap() {
		
		TileMap newMap = getMapWorkspace().createDefaultMap();
		ScaleTransition scale = myScaler.scaleEditScreen(0.0, 1.0, newMap);
		scale.setOnFinished((e) -> {
			getMaps().remove(myMapWorkspace.getActiveMap());
			//getMapWorkspace().getChildren().add(newMap);
			tileRowDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
					.getNumRows()));
			tileColDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
					.getNumCols()));
			tileSizeDisplay.setText(Integer.toString(getMapWorkspace().getActiveMap()
					.getTileSize()));
			myMapWorkspace.getActiveMap().setActiveColor(myActiveColor);
		});
		
		
		
		// TODO: textField.setText to update it
	}

	/**
	 * Saves the current active map into the map arraylist. The ArrayList is
	 * ordered by most recently saved maps, which will contain the highest
	 * index. Saving a currently existing map will update its index to the
	 * highest possible one. This allows for easy searching and also for getting
	 * the most recently used active map from other methods/classes.
	 * 
	 * @param activeMap
	 */
	private void saveMap(TileMap activeMap) {
		if (!getMaps().contains(activeMap)) { // the edited and stored
												// activemaps technically should
												// map to the same address
												// right?
			getMaps().add(activeMap);
			System.out.println("Maps after save: " + getMaps());
		} else {
			getMaps().remove(activeMap);
			getMaps().add(activeMap);
			System.out.println("Maps after save: " + getMaps());
		}
	}

	private void updateMapDim(String numRows, String numCols) {
		myMapWorkspace.getActiveMap().setMapDimensions(Integer.parseInt(numRows),
				Integer.parseInt(numCols));
	}

	// Con: myMapWorkspace.getActiveMap(). instead of myActiveMap introduces a
	// dependency on my workspace. if something is changed there without this
	// class knowing, the code is easy to break
}
