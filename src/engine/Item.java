package engine;

import engine.element.Modifier;

/**
 * an item that the player can use in-game
 * 
 * @author Sean Scott
 *
 */
public class Item {

	private Modifier myModifier;
	
	public Item(Modifier mod){
		myModifier = mod;
	}
	
}
