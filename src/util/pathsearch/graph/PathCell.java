package util.pathsearch.graph;

/**
 * @author Kaighn
 */
public class PathCell {
	private int myRow, myCol;
	
	public PathCell(int row, int col){
		myRow = row;
		myCol = col;
	}
	
	public int getRow(){
		return myRow;
	}
	public int getCol(){
		return myCol;
	}
	public int distance(PathCell c){
		return Math.abs(myRow - c.getRow()) + Math.abs(myCol - c.getCol());
	}
}
