package authoringEnvironment.objects;

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

	// allowing both width and height gives greater flexibility in map creation
	private int myMapRows;
	private int myMapCols;
	private Group myGridLines;
	
	private static final Color DEFAULT_TILE_COLOR = Color.WHITE;

	// user specifies rectangle or square dimensions...allow this flexibility
	public TileMap(int mapRows, int mapCols, int tileSize) {
		myMap = new Group();
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
	
/*	public void changeTile(int row, int col, Color color){
		myTiles[row][col].setFill(color);
	}*/
	
	//need to have different selectors for setting path and for changing tile color

    private void attachTileListeners(){
    	myMap.setOnDragDetected(e -> myMap.startFullDrag());
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
    }
			
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
				myMap.getChildren().add(myTiles[i][j]); // to speed up												// time?
			}
		}
		attachTileListeners();
	}
	
	public void setMapRows(int numRows){
		myMapRows = numRows;
		updateMap();
		//TODO: remake the map each time
	}
	
	public void setMapCols(int numCols){
		myMapCols = numCols;
		updateMap();
		//TODO: remake the map each time
	}
	
	
	//will remove all extra tiles on the right and bottom. TODO: make it delete for everything but the center tiles
	private void updateMap(){
		//myMap.getChildren().clear();
		
		//set proper x and y coordinates
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j].setTranslateX(i*myTileSize);
				myTiles[i][j].setTranslateY(j*myTileSize);
			}
		}
		
		//delete all extra tiles
	}

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
