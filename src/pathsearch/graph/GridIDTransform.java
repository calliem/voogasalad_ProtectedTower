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
	
	public PathCell getCell(int id){
		return new PathCell(id/myWidth, id%myWidth);
	}

}
