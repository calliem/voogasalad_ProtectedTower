//current not sure if this class is necessary. ideally yes, since it will help to extend in the future.

package authoringEnvironment.objects;

import javafx.scene.Group;
import javafx.scene.shape.Line;

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
		createMap();
		// displayMap(myTiles);
		// myMap.getChildren().add(myTIles);
		// displayGrid(myTile);
		createGridLines();
		attachTileListeners();

	}

	public Group getMap() {
		return myMap;
	}

    private void attachTileListeners(){
    	for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				int x = i;
				int y = j;
				myTiles[i][j].setOnMouseClicked(e -> myTiles[x][y].select());
				//MouseEvent
				//EventHandler<MouseEvent> 
			}
    	}
    }
			

    	
    	
    /*    for(int i = 0; i < shapeGrid.size(); i++){
            for(int j = 0; j < shapeGrid.get(i).size(); j++){
                Cell cell = cellGrid.get(i).get(j);
                Shape shape = shapeGrid.get(i).get(j);
                shape.setFill(stateMap.get(mySim.getColorCode(cell)).getColor());

                //set actions for each cell
                BooleanProperty stateTracker = cell.getTracker();
                stateTracker.addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs,
                                        Boolean oldValue, Boolean newValue) {
                        shape.setFill(stateMap.get(mySim.getColorCode(cell)).getColor());

                        int oldCell = cellsToTrack.indexOf(cell.getOldProp()[0]);
                        int newCell = cellsToTrack.indexOf(cell.getAllProp()[0]);
                        int NOT_FOUND = -1;
                        if(oldCell != newCell){
                            if(newCell != NOT_FOUND){
                                populationCounts[newCell]++;
                            }
                            if(oldCell != NOT_FOUND){
                                populationCounts[oldCell]--;
                            }

                            updateBarGraph();
                        }
                    }
                });
                shape.setOnMousePressed(e -> handleMousePressed(e, cell, shape));
                shape.setOnMouseDragEntered(e -> handleDragEntered(e, cell, shape));
            
        }
    }*/

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
				myTiles[i][j].setTileSize(tileSize);
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

		System.out.println(numTileRows);
		System.out.println(numTileCols);

		myTiles = new Tile[numTileRows][numTileCols];
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				myTiles[i][j] = new Tile(myTileSize, i, j);
				// myGridLines.getChildren().add(new PolyLine());

				myMap.getChildren().addAll(myTiles[i][j]); // to speed up
															// efficiency. will
															// these be updated
															// dynamically or do
															// we need to call
															// displaymap each
															// time?
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
