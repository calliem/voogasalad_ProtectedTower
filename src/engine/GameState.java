package engine;

/**
 * This class holds the state of the game and the methods to implement the state, such as increment
 * scores, keeping track of money/experience, and number of lives remaining.
 * 
 * @author Qian Wang
 *
 */

@Deprecated
public class GameState {

    private Bank myBank;
    private int myPoints;

    public GameState () {
        myBank = new Bank(0);
        myPoints = 0;
    }

    public void addFunds (double amount) {
        myBank.deposit(amount);
    }

    public void withdrawFunds (double amount) {
        myBank.withdraw(amount);
    }
}
