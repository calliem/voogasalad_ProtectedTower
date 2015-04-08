
package pathsearch.graph;

/**
 * @author Kaighn
 */
public class GridIDTransform {
	private final int myWidth;
	
	public GridIDTransform(int width){
		myWidth = width;
	}
	
	public int getID(int row, int col){
		return row*myWidth + col;
	}
	
	public GridCell getCell(int id){
		return new GridCell(id/myWidth, id%myWidth);
	}

}
