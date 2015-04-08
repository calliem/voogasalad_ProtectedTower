package authoringEnvironment;

import java.util.ResourceBundle;

import authoringEnvironment.objects.PathView;
import authoringEnvironment.objects.TileMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Right sidebar containing display properties that allows a user to
 * interactively set grid size, tile size, specific tiles / tile colors, paths,
 * and other visual elements of the map.
 * 
 * @author Callie Mao
 *
 */

//TODO: abstract further
public class Sidebar extends VBox { //add a gridpane later on. but a gridpane is hard to edit to set things in between other things...

	//TODO: display sidebar with a gridpane not an HBox to keep everything aligned and beautiful
	
	private static final double PADDING = MainEnvironment.getEnvironmentWidth()/128; //maybe set the spacing dynamically instead
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private static final Color DEFAULT_TILE_DISPLAY_COLOR = Color.WHITE;
	private static final double DEFAULT_TILE_DISPLAY_SIZE = MainEnvironment.getEnvironmentWidth()/32;
	//TODO: is importing the main environment bad design? is this an added dependency?
	private static final double TEXT_FIELD_WIDTH = MainEnvironment.getEnvironmentWidth()/32;
	private static final double LISTVIEW_HEIGHT = MainEnvironment.getEnvironmentHeight()/6;
	private static final double TITLE_FONT_SIZE = MainEnvironment.getEnvironmentWidth()/85;
	private ResourceBundle myResources;
	private TileMap myActiveMap;
	private int myLives;
	ObservableList<TileMap> myMaps;
	private static final int DEFAULT_LIVES = 20;

	public Sidebar(ResourceBundle resources, TileMap activeMap, ObservableList<TileMap> maps) { //active map may not yet be saved and thus we cannot simply pull it out of the observable list
		System.out.println(MainEnvironment.getEnvironmentHeight());
		myResources = resources;
		myActiveMap = activeMap;
		myLives = DEFAULT_LIVES;
		//setSpacing(HBOX_SPACING);
		setDimensionRestrictions();
		myMaps = maps;
		createMapSettings();
		
		/*ObservableList<PathView> pathList = FXCollections.observableArrayList();
		getChildren().add(createListView(pathList, 300));*/
		
	}
	
	private void createMapSettings(){
		//TODO: make a main tab to display the stuff here
		createTitleText(myResources.getString("GameSettings"));
		setLives();
		createTitleText(myResources.getString("MapSettings"));
		setGridDimensions();
		setTileSize();
		createTitleText(myResources.getString("SetTiles"));
		selectTile();
		
		setEditMapButtons();

	}
	
	//Lots of duplication below
	private void setLives(){
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		Text lives = new Text(myResources.getString("Lives"));
		TextField textField = new TextField(Integer.toString(myLives));
		Button button = new Button(myResources.getString("Update"));
		button.setOnMouseClicked(e -> System.out.println(textField.getText())); //TODO: add to properties file to be saved
		textField.setPrefWidth(TEXT_FIELD_WIDTH);
		selection.getChildren().addAll(lives, textField, button);
		getChildren().add(selection);
	}
	
	private void setTileSize(){
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		Text lives = new Text(myResources.getString("TileSize"));
		TextField textField = new TextField(Integer.toString(myActiveMap.getTileSize()));
		Button button = new Button(myResources.getString("Update"));
		button.setOnMouseClicked(e -> myActiveMap.changeTileSize(Integer.parseInt(textField.getText())));
		textField.setPrefWidth(TEXT_FIELD_WIDTH);
		Text px = new Text(myResources.getString("PxSuffix"));
		selection.getChildren().addAll(lives, textField, px, button);
		getChildren().add(selection);
	}
	
	private void selectTile(){
		HBox selectTile = new HBox();
		selectTile.setSpacing(30); //remove hardcoding
		
		VBox selection = new VBox();
		Text selectTileColor = new Text(myResources.getString("SelectTileColor")); //TODO: fix
		ColorPicker picker = new ColorPicker();
		selection.getChildren().addAll(selectTileColor, picker);
		
		Rectangle rectangleDisplay = new Rectangle(DEFAULT_TILE_DISPLAY_SIZE, DEFAULT_TILE_DISPLAY_SIZE, DEFAULT_TILE_DISPLAY_COLOR); //remove hardcoded including the color;
		
		//Button placeTile = new Button("Place Tile"); //TODO:
		//placeTile.setPrefHeight(40);
		
		
		selectTile.getChildren().addAll(selection, rectangleDisplay);
	
		
		getChildren().add(selectTile);
		picker.setOnAction(e -> changeActiveTileColor(picker.getValue(), rectangleDisplay));
		
	//	placeTile.setOnMouseClicked(e -> {myMap.setActiveColor(picker.getValue());}); 
		
		//changeActiveTileColor(picker.getValue()));
		
		//Grid Dimensions will set the number of rows and cols (not the actual pixels) 
	}
	
	private void changeActiveTileColor(Color color, Rectangle display){
		//TODO: make this do the right thing
		display.setFill(color);
		myActiveMap.setActiveColor(color); 
		
		
		//TODO: make it update the color in the actual grid 
	}
	
	//TODO: remove duplicated code
	private void setGridDimensions(){
		HBox selection = new HBox();
		selection.setSpacing(PADDING); 
		Text text = new Text(myResources.getString("GridDimensions"));
		
		HBox textFields = new HBox();
		TextField x = new TextField(Integer.toString(myActiveMap.getNumRows()));
		x.setPrefWidth(TEXT_FIELD_WIDTH); 
		Text xSeparator = new Text(myResources.getString("DimensionXSeparation"));
		TextField y = new TextField(Integer.toString(myActiveMap.getNumCols()));
		textFields.getChildren().addAll(x, xSeparator, y);
		
		y.setPrefWidth(TEXT_FIELD_WIDTH);
		Button setGridDimButton = new Button(myResources.getString("Update"));
		setGridDimButton.setOnMouseClicked(e -> updateMapDim(x.getText(), y.getText()));
		selection.getChildren().addAll(text, textFields,setGridDimButton);
		getChildren().add(selection);
	}

	
	
	//TODO: find a way to make this work
/*	private Button updateTextField(String s, int boxWidth){
		HBox selection = new HBox();
		selection.setSpacing(10);
		Text lives = new Text(myResources.getString(s));
		TextField textField = new TextField();
		Button button = new Button(myResources.getString("Update"));
		textField.setPrefWidth(boxWidth);
		selection.getChildren().addAll(lives, textField, button);
		getChildren().add(selection);
		
	}*/
	
	
	
	//TODO: path view

	private void setDimensionRestrictions() {
		setPadding(new Insets(PADDING));
		//setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}

	private void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(TITLE_FONT_SIZE));
		title.setUnderline(true);
		getChildren().add(title);
	}

	private ListView<PathView> createListView(ObservableList<PathView> items, int height) {
		ListView<PathView> list = new ListView<PathView>();
		list.setItems(items);
		list.setMaxWidth(Double.MAX_VALUE);
		list.setPrefHeight(LISTVIEW_HEIGHT);
		return list;
	}
	
	
	
	private void setEditMapButtons(){
		Button saveMapButton = new Button(myResources.getString("SaveMap"));
		saveMapButton.setOnMouseClicked(e -> myMaps.add(myActiveMap));
		Button deleteMapButton = new Button(myResources.getString("DeleteMap"));
		deleteMapButton.setOnMouseClicked(e -> removeMap());
		getChildren().addAll(saveMapButton, deleteMapButton);
		
	}
	
	private void removeMap(){
		System.out.println(myMaps);
		if (myMaps.contains(myActiveMap)){
			myMaps.remove(myActiveMap);
			System.out.println(myMaps);
		}
		else{
			System.out.println("You cannot remove a map that has not been saved previously.");
			//TODO: show error
			//or only include delete map buttons for maps that have already been saved
		}
			
	}
	
	private void updateMapDim(String numRows, String numCols){
		myActiveMap.setMapDimensions(Integer.parseInt(numRows), Integer.parseInt(numCols));
	}
}
