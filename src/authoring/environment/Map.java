//current not sure if this class is necessary. ideally yes, since it will help to extend in the future.

package authoring.environment;

import javafx.scene.Group;

public abstract class Map {
	
	private Group myMap;
	private Tile[][] myTiles;
	
	public Map(double width, double height, double gridSize) {
		myMap = new Group();
		//myTiles = populateGrid(gridSize);
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
	
	protected abstract Tile[][] populateGrid(double gridSize, Tile[][] cells);

}
