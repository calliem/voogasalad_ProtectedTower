package authoringEnvironment;

import java.util.List;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Right sidebar on the map editor containing display properties that allows a user to
 * interactively set grid size, tile size, specific tiles / tile colors, paths,
 * and other visual elements of the map.
 * 
 * @author Callie Mao
 *
 */

//TODO: abstract further
public class MapSidebar extends Sidebar { //add a gridpane later on. but a gridpane is hard to edit to set things in between other things...

	//TODO: display sidebar with a gridpane not an HBox to keep everything aligned and beautiful
	
	private static final double PADDING = MainEnvironment.getEnvironmentWidth()/128; //maybe set the spacing dynamically instead
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private static final Color DEFAULT_TILE_DISPLAY_COLOR = Color.WHITE;
	private static final double DEFAULT_TILE_DISPLAY_SIZE = MainEnvironment.getEnvironmentWidth()/32;
	//TODO: is importing the main environment bad design? is this an added dependency?
	private static final double TEXT_FIELD_WIDTH = MainEnvironment.getEnvironmentWidth()/32;
	private int myLives;
	private TileMap myActiveMap;
//	private TileMap mySelectedMap;
	private static final int DEFAULT_LIVES = 20; //TODO: how to get this number from Johnny

	public MapSidebar(ResourceBundle resources,List<Node> maps, MapWorkspace mapWorkspace, TileMap activeMap) { //active map may not yet be saved and thus we cannot simply pull it out of the observable list
		super(resources, maps, mapWorkspace);
		myActiveMap = activeMap;
		//mySelectedMap = activeMap;
		myLives = DEFAULT_LIVES;		
		/*ObservableList<PathView> pathList = FXCollections.observableArrayList();
		getChildren().add(createListView(pathList, 300));*/
		createMapSettings();
		
	}


	protected void createMapSettings(){
		//TODO: make a main tab to display the stuff here
		createTitleText(getResources().getString("GameSettings"));
		setLives();
		createTitleText(getResources().getString("MapSettings"));
		setGridDimensions();
		setTileSize();
		createTitleText(getResources().getString("SetTiles"));
		selectTile();
		
		setEditMapButtons();

	}
	
	//Lots of duplication below
	private void setLives(){
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		Text lives = new Text(getResources().getString("Lives"));
		TextField textField = new TextField(Integer.toString(myLives));
		Button button = new Button(getResources().getString("Update"));
		button.setOnMouseClicked(e -> System.out.println(textField.getText())); //TODO: add to properties file to be saved
		textField.setPrefWidth(TEXT_FIELD_WIDTH);
		selection.getChildren().addAll(lives, textField, button);
		getChildren().add(selection);
	}
	
	private void setTileSize(){
		HBox selection = new HBox();
		selection.setSpacing(PADDING);
		Text lives = new Text(getResources().getString("TileSize"));
		TextField textField = new TextField(Integer.toString(myActiveMap.getTileSize()));
		Button button = new Button(getResources().getString("Update"));
		button.setOnMouseClicked(e -> myActiveMap.changeTileSize(Integer.parseInt(textField.getText())));
		textField.setPrefWidth(TEXT_FIELD_WIDTH);
		Text px = new Text(getResources().getString("PxSuffix"));
		selection.getChildren().addAll(lives, textField, px, button);
		getChildren().add(selection);
		
	}
	
	private void selectTile(){
		HBox selectTile = new HBox();
		selectTile.setSpacing(30); //remove hardcoding
		
		VBox selection = new VBox();
		Text selectTileColor = new Text(getResources().getString("SelectTileColor")); //TODO: fix
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
	/*	if (myActiveMap == null)
			System.out.println("LE MAP IS NULLL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			else
				System.out.println(myActiveMap.getNumRows());
		
		System.out.println("setting grid dimensions");*/
		HBox selection = new HBox();
		selection.setSpacing(PADDING); 
		Text text = new Text(getResources().getString("GridDimensions"));
		
		HBox textFields = new HBox();
		TextField x = new TextField(Integer.toString(myActiveMap.getNumRows()));
		x.setPrefWidth(TEXT_FIELD_WIDTH); 
		Text xSeparator = new Text(getResources().getString("DimensionXSeparation"));
		TextField y = new TextField(Integer.toString(myActiveMap.getNumCols()));
		textFields.getChildren().addAll(x, xSeparator, y);
		
		y.setPrefWidth(TEXT_FIELD_WIDTH);
		Button setGridDimButton = new Button(getResources().getString("Update"));
		setGridDimButton.setOnMouseClicked(e -> updateMapDim(x.getText(), y.getText()));
		selection.getChildren().addAll(text, textFields,setGridDimButton);
		getChildren().add(selection);
	}

	
	
	//TODO: find a way to make this work
/*	private Button updateTextField(String s, int boxWidth){
		HBox selection = new HBox();
		selection.setSpacing(10);
		Text lives = new Text(getResources().getString(s));
		TextField textField = new TextField();
		Button button = new Button(getResources().getString("Update"));
		textField.setPrefWidth(boxWidth);
		selection.getChildren().addAll(lives, textField, button);
		getChildren().add(selection);
		
	}*/
	
	
//TODO: path view

	private void setEditMapButtons(){
		Button createMapButton = new Button(getResources().getString("CreateMap"));
		createMapButton.setOnMouseClicked(e -> createMap());
		Button saveMapButton = new Button(getResources().getString("SaveMap"));
		saveMapButton.setOnMouseClicked(e -> saveMap(myActiveMap));
		Button deleteMapButton = new Button(getResources().getString("DeleteMap"));
		deleteMapButton.setOnMouseClicked(e -> removeMap());
		getChildren().addAll(createMapButton, saveMapButton, deleteMapButton);	
	}
	
	private void removeMap(){
		System.out.println(getMaps());
			getMaps().remove(myActiveMap);
			getMapWorkspace().getChildren().remove(myActiveMap);
			System.out.println(getMaps());
	}
	
	/*//TODO: remove duplication since this is the exact code written in mapeditor
	protected void createMap() {
        myActiveMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);	
        setDefaultTextFieldValues(); //TODO: ______
        getMapWorkspace().getChildren().add(myActiveMap);
    }*/
	
	protected void createMap(){
		getMapWorkspace().createDefaultMap();
	}
	
	/**
	 * Saves the current active map into the map arraylist. The ArrayList is ordered by most recently saved maps, which will contain the highest index. Saving a currently existing map will update its index to the highest possible one. This allows for easy searching and also for getting the most recently used active map from other methods/classes.
	 * @param activeMap
	 */
	private void saveMap(TileMap activeMap){
		if (!getMaps().contains(activeMap)){ //the edited and stored activemaps technically should map to the same address right?
			getMaps().add(activeMap);
			System.out.println(getMaps());
		}
		else{
			getMaps().remove(activeMap);
			getMaps().add(activeMap);
			System.out.println(getMaps());
		}
	}
	
	private void updateMapDim(String numRows, String numCols){
		myActiveMap.setMapDimensions(Integer.parseInt(numRows), Integer.parseInt(numCols));
	}
}
