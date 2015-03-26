package engine;

public class Bank {

	public boolean checkSufficientFunds(){
		return false;
	}
	public int getBalance(){
		return 0;
	}
	public void deposit(int amount){
		
	}
	public boolean withdraw(int amount){
		return checkSufficientFunds();
	}
	public void forceWithdraw(int amount){
		
	}
	
}
