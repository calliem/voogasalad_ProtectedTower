//current not sure if this class is necessary. ideally yes, since it will help to extend in the future.

package authoring.environment;

import javafx.scene.Group;

public abstract class TileMap {
	
	private Group myMap;
	private Tile[][] myTiles;
	private double myTileSize;
	private double myMapSize;
	
	//width and height should be the same for square tile sizes
	public TileMap(double mapSize, double tileSize) {
		myMap = new Group();
		myMapSize = mapSize;
		myTileSize = tileSize;
		myTiles = createMap();
	//	displayGrid(myTile);
	}
	
	public Group getGrid() {
		return myMap;
	}
	
/*	public void setActiveTiles{int startX, int startY, int endX, int endY
		//add if it is possible to drag and select multiple squares
	}*/
	
	private void setActiveTile(int x, int y) {
			myTiles[x][y].setActivePath();
	}
	
/*	private void displayGrid(Tile[][] shapes) {
		myMap.getChildren().clear();
		for (int i = 0; i < shapes.length; i++) {
			for (int j = 0; j < shapes[0].length; j++) {
				myMap.getChildren().add(shapes[i][j]);
			}
		}
	}*/
	
	//protected abstract Tile[][] populateGrid(double gridSize, Tile[][] cells);
	//where to calculate tile size? 
	public Tile[][] createMap(){
		Tile[][] tiles = new Tile[myTileSize][myTileSize];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				Tile tile = new Tile(myTileSize);
		}
		return tiles;
	}
}
