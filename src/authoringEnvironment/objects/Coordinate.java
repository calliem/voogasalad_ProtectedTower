package authoringEnvironment.objects;

/**
 * Data structure that stores x- and y-coordinates on a Cartesian plane.
 * 
 * @author Callie Mao
 *
 */

public class Coordinate {
	
	private int myX;
	private int myY;
	
	public Coordinate(int x, int y){
		myX = x;
		myY = y;
	}
	
	private int getX(){
		return myX;
	}
	
	private int getY(){
		return myY;
	}

}
