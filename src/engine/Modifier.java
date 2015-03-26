package engine;

public abstract class Modifier extends Sprite {
	public Modifier() throws InsufficientParametersException {
		super();
		// TODO Auto-generated constructor stub
	}

	public abstract void modify(Sprite s);

}
