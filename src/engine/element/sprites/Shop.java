package engine.element.sprites;

import java.util.List;

import engine.Bank;
import engine.Item;
import engine.element.Layout;

/**
 * a shop for buying towers and special items
 * 
 * @author Sean Scott
 *
 */
public class Shop {
	
	private Layout myLayout;
	private Bank myBank;
	private List<Tower> myTowers;
	//private List<Item> myItems;
	
	public Shop (Layout layout, Bank bank, List<Tower> towers){
		myLayout = layout;
		myBank = bank;
		myTowers = towers;
		//myItems = items;
	}
	
	public void buyTower (Tower tower){
		double cost = tower.getCost();
		if (myBank.checkSufficientFunds(cost)){
			myBank.withdraw(cost);
			myLayout.pickUpTower(tower);
		}
	}
	

}
