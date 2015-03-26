package engine;

import java.util.List;

public class Enemy extends MoveableSprite{

	public Enemy() throws InsufficientParametersException {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void target(Sprite s) {
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
	public List<String> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
}
