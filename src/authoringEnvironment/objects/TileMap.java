package authoringEnvironment.objects;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Holds a 2D array of tiles, attaches listeners, and draws lines to display on the editor
 * @author Callie Mao
 *
 */


//potentially make abstract?
public class TileMap {

	private Group myMap;
	private Tile[][] myTiles;
	private double myTileSize;

	// allowing both width and height gives greater flexibility in map creation
	private double myMapWidth;
	private double myMapHeight;
	private Group myGridLines;

	// user specifies rectangle or square dimensions...allow this flexibility
	public TileMap(double mapWidth, double mapHeight, double tileSize) {
		myMap = new Group();
		myMapWidth = mapWidth;
		myMapHeight = mapHeight;
		myTileSize = tileSize;
		myGridLines = new Group();
		System.out.println("===============" + myMapWidth);
		System.out.println("===============" + myMapHeight);
		System.out.println("===============" + myTileSize);
		createMap();
		createGridLines();
		attachTileListeners();
	}

	public Group getMap() {
		return myMap;
	}

    private void attachTileListeners(){
    	myMap.setOnDragDetected(e -> myMap.startFullDrag());
    	for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				int x = i;
				int y = j;
				myTiles[i][j].setOnMousePressed(e -> myTiles[x][y].select());
				myTiles[i][j].setOnMouseDragEntered(e -> myTiles[x][y].dragSelect());
			}
    	}
    }
			
	/*
	 * public void setActiveTiles{int startX, int startY, int endX, int endY
	 * //add if it is possible to drag and select multiple squares }
	 */

	// is this necessary if it is already written into the tile itself?
	/*private void setActiveTile(int x, int y) {
		myTiles[x][y].setActiveTile();
	}*/
	
	//TODO: instead of letting user change 
	public void changeTileSize(double tileSize) {
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j].setTileSizeDynamically(tileSize);
			}
		}
	}

	public Tile getTile(int x, int y) {
		return myTiles[x][y];
	}

	// protected abstract Tile[][] populateGrid(double gridSize, Tile[][]
	// cells);
	// where to calculate tile size?
	private void createMap() {
		int numTileRows = (int) (myMapHeight / myTileSize);
		int numTileCols = (int) (myMapWidth / myTileSize);
		
		System.out.println("numtilerows" + numTileRows);
		System.out.println("num tile cols" + numTileCols);
		
		myTiles = new Tile[numTileRows][numTileCols];
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j] = new Tile(myTileSize, i, j);
				myMap.getChildren().add(myTiles[i][j]); // to speed up
															// efficiency. will
															// these be updated
															// dynamically or do
															// we need to call
															// displaymap each
															// time?
				System.out.println("add rectangle================================");
			}
		}
	}

	// might be more efficient if in the above for loop
	private void createGridLines() {
		// vertical lines
		for (int i = 0; i < myMapWidth; i += myTileSize) {
			System.out.println(myMapHeight);
			// super monkey generator
			myGridLines.getChildren().add(new Line(i, 0, i, myMapHeight));
		}
		// horizontal lines
		for (int i = 0; i < myMapHeight; i += myTileSize) {
			myGridLines.getChildren().add(new Line(0, i, myMapWidth, i));
		}
		myMap.getChildren().add(myGridLines);
	}

	/*
	 * private void displayMap(Tile[][] tiles){ for (int i = 0; i <
	 * tiles.length; i++) { for (int j = 0; j < tiles[0].length; j++) {
	 * myMap.getChildren().add(tiles[i][j]); } } }
	 */

}
