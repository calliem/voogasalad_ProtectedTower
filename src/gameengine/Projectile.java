package gameengine;

import java.util.List;

import otherstuff.Moveable;


public class Projectile extends Sprite implements Moveable{

	public Projectile() throws InsufficientParametersException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTargetable(String type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCollidable(String type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void target(Sprite s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
