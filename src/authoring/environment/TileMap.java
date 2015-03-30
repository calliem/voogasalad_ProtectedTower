//current not sure if this class is necessary. ideally yes, since it will help to extend in the future.

package authoring.environment;
import javafx.scene.Group;

//potentially make abstract?
public class TileMap {
	
	private Group myMap;
	private Tile[][] myTiles;
	private double myTileSize;
	
	//allowing both width and height gives greater flexibility in map creation
	private double myMapWidth;
	private double myMapHeight;
	
	//user specifies rectangle or square dimensions...allow this flexibility
	public TileMap(double mapWidth, double mapHeight, double tileSize) {
		myMap = new Group();
		myMapWidth = mapWidth;
		myMapHeight = mapHeight;
		myTileSize = tileSize;
		createMap();
	//	displayMap(myTiles);
		//myMap.getChildren().add(myTIles);
	//	displayGrid(myTile);
		
	}
	
	public Group getMap() {
		return myMap;
	}
	
/*	public void setActiveTiles{int startX, int startY, int endX, int endY
		//add if it is possible to drag and select multiple squares
	}*/
	
	//is this necessary if it is already written into the tile itself?
	private void setActiveTile(int x, int y) {
		myTiles[x][y].setActiveTile();
	}
	
	public Tile getTile(int x, int y){
		return myTiles[x][y];
	}
	
	//protected abstract Tile[][] populateGrid(double gridSize, Tile[][] cells);
	//where to calculate tile size? 
	private void createMap(){
		int numTileRows = (int) (myMapHeight / myTileSize);
		int numTileCols = (int) (myMapWidth / myTileSize);
		
		System.out.println(numTileRows);
		System.out.println(numTileCols);
		
		myTiles = new Tile[numTileRows][numTileCols];
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j] = new Tile(myTileSize, i, j);
				myMap.getChildren().add(myTiles[i][j]); //to speed up efficiency. will these be updated dynamically or do we need to call displaymap each time?
			}
		}
	}
	
	/*private void displayMap(Tile[][] tiles){
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				myMap.getChildren().add(tiles[i][j]);
			}
		}
	}*/

}
