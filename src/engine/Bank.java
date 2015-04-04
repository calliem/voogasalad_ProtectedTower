package engine;

/*
 * This object holds the current balance of a player's credits.  The value can be increased or decreased on demand,
 * and includes methods to check if a withdrawal will cause a player's balance to go negative.
 */
public class Bank {

	public boolean checkSufficientFunds() {
		return false;
	}

	public int getBalance() {
		return 0;
	}

	public void deposit(int amount) {

	}

	public boolean withdraw(int amount) {
		return checkSufficientFunds();
	}

	public void forceWithdraw(int amount) {

	}

}
