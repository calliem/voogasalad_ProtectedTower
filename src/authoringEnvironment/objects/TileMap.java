package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Holds a 2D array of tiles, attaches listeners, and draws lines to display on the editor
 * @author Callie Mao
 *
 */

//potentially make abstract?
public class TileMap {

	//TODO: figure out why gridlines are so weird
	private Group myMap;
	private Tile[][] myTiles;
	private int myTileSize;
	private Color myActiveColor;
	
	private HashMap<String, Integer> myTags; //maps a string to the number of elements with that tag

	// allowing both width and height gives greater flexibility in map creation
	private int myMapRows;
	private int myMapCols;
	
	private Group myGridLines;
	
	private static final Color DEFAULT_TILE_COLOR = Color.WHITE;

	// user specifies rectangle or square dimensions...allow this flexibility
	public TileMap(int mapRows, int mapCols, int tileSize) {
		myMap = new Group();
    	myMap.setOnDragDetected(e -> myMap.startFullDrag());
		myMapRows = mapRows;
		myMapCols = mapCols;
		myTileSize = tileSize;
		myGridLines = new Group();
		myActiveColor = DEFAULT_TILE_COLOR;
		//TODO: sethover x, y coordinate, tile size, etc.
		createMap();
		//createGridLines();
	}

	public Group getMap() {
		return myMap;
	}
	
	public int addTag(int x, int y, String tag){
		myTiles[x][y].addTag(tag);
		int numTags = myTags.get(tag);
		return ++numTags;
	}
	
	public int removeTag(int x, int y, String tag){
		myTiles[x][y].removeTag(tag);
		int numTags = myTags.get(tag);
		return --numTags;
	}
	
/*	public void changeTile(int row, int col, Color color){
		myTiles[row][col].setFill(color);
	}*/
	
	//need to have different selectors for setting path and for changing tile color

	
	//TODO: attach tile listeners to new tiles but make it to add to specific one
	
	private void attachTileListener(Tile tile){
		tile.setOnMousePressed(e -> tile.setFill(myActiveColor));//myTiles[x][y].setFill(myActiveColor));
		tile.setOnMouseDragEntered(e -> tile.setFill(myActiveColor)); //TODO: fix dragging errors

	}
	
    /*private void attachTileListeners(){
    	for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				int x = i;
				int y = j;
				myTiles[i][j].setOnMousePressed(e -> myTiles[x][y].setFill(myActiveColor));//myTiles[x][y].setFill(myActiveColor));
				myTiles[i][j].setOnMouseDragEntered(e -> myTiles[x][y].setFill(myActiveColor)); //TODO: fix dragging errors

				
//				myTiles[i][j].setOnMousePressed(e -> myTiles[x][y].select());
//				myTiles[i][j].setOnMouseDragEntered(e -> myTiles[x][y].dragSelect());
//				these above lines are to set pathing
			
			}
    	}
    }*/
			
	public void changeTileSize(int tileSize) {
		myTileSize = tileSize;
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j].setTileSizeDynamically(tileSize);
			}
		}
	//	myMap.getChildren().remove(myGridLines);
	//	createGridLines();
	}

	public Tile getTile(int x, int y) {
		return myTiles[x][y];
	}
	
	public void setActiveColor(Color color){
		System.out.println("update color");
		myActiveColor = color;
	}

	private void createMap() {
		myTiles = new Tile[myMapRows][myMapCols];
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j] = new Tile(myTileSize, i, j);
				myMap.getChildren().add(myTiles[i][j]); // to speed up	
				attachTileListener(myTiles[i][j]);// time?
			}
		}
	}

	//@param myMapRows and myMapCols represent the previous/old rows and cols
	public void setMapDimensions(int newMapRows, int newMapCols){
		clearTiles();
		Tile[][] newTiles = new Tile[newMapRows][newMapCols];
		
		//TODO make newmethod to avoid duplication since this is similar to createMap

		for (int i = 0; i < newMapRows; i++) {
			for (int j = 0; j < newMapCols; j++) {
				if (i >= myMapRows || j >= myMapCols)
					newTiles[i][j] = new Tile(myTileSize, i, j);
				else{
					newTiles[i][j] = myTiles[i][j];
				}
				attachTileListener(newTiles[i][j]);
				myMap.getChildren().add(newTiles[i][j]);
				
			}
		}

		myMapCols = newMapCols;
		myMapRows = newMapRows;
		
		myTiles = newTiles;
	}
	
	//clears tiles from this current map
	private void clearTiles(){
		for (int i = 0; i < myMapRows; i++) {
			for (int j = 0; j < myMapCols; j++) {
				myMap.getChildren().remove(myTiles[i][j]);
			}
		}
	}
	
	public int getNumRows(){
		return myTiles.length;
	}
	
	public int getNumCols(){
		return myTiles[0].length;
	}
	
	public int getTileSize(){
		return myTileSize;
	}
		//for (int i = myTiles.length; )
		
		//delete all extra tiles

	// might be more efficient if in the above for loop
	/*private void createGridLines() {
		int mapWidth = myMapRows * myTileSize;
		int mapHeight = myMapCols * myTileSize; 
		
		//TODO: make an error display if mapwidth or mapheight is greater than allowed or create a scrollpane instead
		// vertical lines
		for (int i = 0; i < mapWidth; i += myTileSize) {
			Line verticalLine = new Line(i, 0, i, mapHeight);
			verticalLine.setStroke(Color.web("B2B2B2"));
			myGridLines.getChildren().add(verticalLine);
		}
		// horizontal lines
		for (int i = 0; i < mapHeight; i += myTileSize) {
			Line horizontalLine = new Line(0, i, mapWidth, i);
			horizontalLine.setStroke(Color.web("B2B2B2"));
			myGridLines.getChildren().add(horizontalLine);
		}
		myMap.getChildren().add(myGridLines);
	}*/
}
