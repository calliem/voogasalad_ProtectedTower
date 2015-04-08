package engine;

/**
 * This object holds the current balance of a player's credits. The value can be increased or
 * decreased on demand, and includes methods to check if a withdrawal will cause a player's balance
 * to go negative.
 * 
 * @author Bojia Chen
 * @author Qian Wang
 *
 */

public class Bank {
    private double myBalance;

    public Bank () {
        myBalance = 0;
    }

    /**
     * Checks if the bank has more than the specified amount
     * 
     * @param amount double of the value to check
     * @return true if the current balance is greater than the given amount
     */
    public boolean checkSufficientFunds (double amount) {
        return (myBalance >= amount);
    }

    /**
     * @return double of the current balance
     */
    public double getBalance () {
        return myBalance;
    }

    /**
     * Adds credits to the current balance
     * 
     * @param amount double of the value to add
     */
    public void deposit (double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        else {
            myBalance += amount;
        }
    }

    /**
     * Removes credit from the current balance if there are sufficient funds
     * 
     * @param amount double of the value to withdraw
     * @return true if the withdraw was successful, if the current balance was higher than the
     *         request amount
     */
    public boolean withdraw (double amount) {
        if (checkSufficientFunds(amount)) {
            myBalance -= amount;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Removes credit from the current balance always
     * 
     * @param amount double of the value to withdraw
     */
    public void forceWithdraw (double amount) {
        myBalance -= amount;
    }

}
