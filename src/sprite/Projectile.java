package sprite;

import java.util.List;

import voogasaladTEST.Moveable;


public class Projectile extends Sprite implements Moveable{

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
