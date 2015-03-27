package engine;

import java.util.List;

public class Modifier extends Sprite {
	public Modifier() throws InsufficientParametersException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void modify(GameSprite s){}

	@Override
	public List<String> getParameters() {
		// TODO Auto-generated method stub
		return null;
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

}
