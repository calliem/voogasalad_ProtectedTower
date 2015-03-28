package engine;


/*
 * This object holds the current balance of a player's credits.  The value can be increased or decreased on demand,
 * and includes methods to check if a withdrawal will cause a player's balance to go negative.
 */
public class Bank {
	private int balance;
	
	public Bank() {
		balance = 0;
	}

	public boolean checkSufficientFunds(int amount){
		return (balance - amount) >= 0;
	}
	public int getBalance(){
		return balance;
	}
	public void deposit(int amount){
		balance += amount;
	}
	public boolean withdraw(int amount){
		if (checkSufficientFunds(amount)) {
			balance -= amount;
			return true;
		} else {
			return false;
		}
	}
	public void forceWithdraw(int amount){
		balance -= amount;
	}
	
}
