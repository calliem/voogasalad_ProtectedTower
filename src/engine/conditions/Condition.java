package engine.conditions;

import java.util.function.Consumer;
import java.util.function.Predicate;

import engine.element.Game;

/**
 * This class holds user-defined conditions and responses to be checked every iteration of the game loop
 * 
 * @author Sean Scott
 *
 */
public class Condition {

	private Game myGame;
	private Predicate<Game> myCondition;
	private Consumer<Game> myResponse;
	
	public Condition(Game game, Predicate<Game> condition, Consumer<Game> response){
		myGame = game;
		myCondition = condition;
		myResponse = response;
	}

    /**
     * checks whether the condition has been satisfied and performs action
     */
    public void check(){
    	if (myCondition.test(myGame)){
    		myResponse.accept(myGame);
    	}
    }
}
