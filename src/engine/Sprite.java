package engine;

import java.util.List;

public abstract class Sprite {
	public Sprite() throws InsufficientParametersException{
		
	}
	public abstract List<String> getParameters();
	public double[] getCoordinates(Sprite s){
		return null;
	}
	public double getSize(){
		return 0;
	}
	public abstract boolean isTargetable(String type);
	public abstract boolean isCollidable(String type);
	public abstract void target(Sprite s);
	public boolean collide(Sprite s){
		return false;
	}
}
