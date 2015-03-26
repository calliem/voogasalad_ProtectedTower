package engine;

import java.util.List;

public class Enemy extends Sprite implements Moveable{

	public Enemy() throws InsufficientParametersException {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void target(Sprite s) {
	}
	@Override
	public void move() {
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
}
