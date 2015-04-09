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
public class TileMap extends Group{

	//TODO: figure out why gridlines are so weird
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
		
    	setOnDragDetected(e -> startFullDrag());
		myMapRows = mapRows;
		myMapCols = mapCols;
		myTileSize = tileSize;
		myGridLines = new Group();
		myActiveColor = DEFAULT_TILE_COLOR;
		//TODO: sethover x, y coordinate, tile size, etc.
		createMap();
		createGridLines();
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
	
	private void attachTileListener(Tile tile){
		tile.setOnMousePressed(e -> {tile.setFill(myActiveColor); System.out.println("I have been clicked!" + tile.getFill().toString()) ;});//myTiles[x][y].setFill(myActiveColor));
		tile.setOnMouseDragEntered(e -> tile.setFill(myActiveColor)); //TODO: fix dragging errors

	}
			
	public void changeTileSize(int tileSize) {
		myTileSize = tileSize;
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j].setTileSizeDynamically(tileSize);
			}
		}
		updateGridLines();

	//	myMap.getChildren().remove(myGridLines);
	//	createGridLines();
	}

	public Tile getTile(int x, int y) {
		return myTiles[x][y];
	}
	
	public void setActiveColor(Color color){
		myActiveColor = color;
	}

	private void createMap() {
		myTiles = new Tile[myMapRows][myMapCols];
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j] = new Tile(myTileSize, i, j);
				getChildren().add(myTiles[i][j]); // to speed up	
				attachTileListener(myTiles[i][j]);// time?
				//System.out.println("tile listener has been attached!");
			}
		}
	}
	
	/**
	 * Sets dimensions of the map to the number of rows and columns specified in the parameter. Directly sets the tile dimensions, while pixel adjustment is determined upon separate methods changing tile size. 
	 * @param newMapRows integer representing the number of rows the new map dimensions should have
	 * @param newMapCols integer representing the number of columns the new map dimensions should have
	 */
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
				getChildren().add(newTiles[i][j]);	
			}
		}

		myMapCols = newMapCols;
		myMapRows = newMapRows;
		
		myTiles = newTiles;
		updateGridLines();
	}
	
	/**
	 * Clears all tiles from the current active tile map by iterating through and removing all tiles of the 2D array individually
	 */
	private void clearTiles(){
		for (int i = 0; i < myMapRows; i++) {
			for (int j = 0; j < myMapCols; j++) {
				getChildren().remove(myTiles[i][j]);
			}
		}
		getChildren().remove(myGridLines);
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
	
	private void createGridLines() {
		int mapWidth = myMapCols * myTileSize;
		int mapHeight = myMapRows * myTileSize; 
		
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
		getChildren().add(myGridLines);
	}
	
	private void removeGridLines(){
		myGridLines.getChildren().clear();
	}
	
	private void updateGridLines(){
		removeGridLines();
		createGridLines();
	}
}
