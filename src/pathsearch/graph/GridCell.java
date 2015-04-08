
package pathsearch.graph;

/**
 * @author Kaighn
 */
public class GridCell {
	private int myRow, myCol;
	
	public GridCell(int row, int col){
		myRow = row;
		myCol = col;
	}
	
	public int getRow(){
		return myRow;
	}
	public int getCol(){
		return myCol;
	}
	public int distance(GridCell c){
		return Math.abs(myRow - c.getRow()) + Math.abs(myCol - c.getCol());
	}
}
