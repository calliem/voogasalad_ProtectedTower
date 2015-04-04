package authoringEnvironment;

import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
public class Sidebar extends VBox {

	private static final int HBOX_SPACING = 10; //maybe set the spacing dynamically instead
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/display/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "sidebar_english");

	public Sidebar() {
		setSpacing(HBOX_SPACING);
		setDimensionRestrictions();
		createMapSettings();
	}
	
	private void createMapSettings(){
		System.out.println(myResources);
		//TODO: make a main tab to display the stuff here
		createTitleText(myResources.getString("GameSettings"));
		//setLives();
		updateTextField(myResources.getString("Lives"), 50); //TODO: remove hardcoded values
		
		createTitleText(myResources.getString("MapSettings"));
		setGridDimensions();
		updateTextField(myResources.getString("TileSize"), 50);
		
		createTitleText(myResources.getString("SetTiles"));
		
		//createHSelection(myResources.getString("GridDimensions"), gridDimensionsText);
		//TODO: event handlers
	}
	
	//TODO: remove duplicated code
	private void setGridDimensions(){
		HBox selection = new HBox();
		selection.setSpacing(HBOX_SPACING); 
		Text text = new Text(myResources.getString("GridDimensions"));
		
		HBox textFields = new HBox();
		TextField x = new TextField();
		x.setPrefWidth(50); //TODO: remove hardcoded stuff
		Text xSeparator = new Text(" x ");
		TextField y = new TextField();
		textFields.getChildren().addAll(x, xSeparator, y);
		
		y.setPrefWidth(50);
		Button setGridDimButton = new Button(myResources.getString("Update"));
		setGridDimButton.setOnMouseClicked(e -> updateGridDim());
		selection.getChildren().addAll(text, textFields,setGridDimButton);
		getChildren().add(selection);
	}
	
	private void updateTextField(String s, int boxWidth){
		HBox selection = new HBox();
		selection.setSpacing(10);
		Text lives = new Text(myResources.getString(s));
		TextField textField = new TextField();
		textField.setPrefWidth(boxWidth);
		Button livesButton = new Button(myResources.getString("Update"));
		selection.getChildren().addAll(lives, textField, livesButton);
		getChildren().add(selection);
	}
	
	
	//tile view
	
	//path view
	
	

	private void setDimensionRestrictions() {
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}

	private void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(15));
		title.setUnderline(true);
		getChildren().add(title);
	}

	private ListView<String> createListView(ObservableList<String> items, int height) {
		ListView<String> list = new ListView<String>();
		list.setItems(items);
		list.setMaxWidth(Double.MAX_VALUE);
		list.setPrefHeight(130);
		return list;
	}
	
	private void updateGridDim(){
		System.out.println("updateGridDim");
	}
}

// set grid size
// slider to set tile size

// 