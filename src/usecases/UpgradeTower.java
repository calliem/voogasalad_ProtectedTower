package usecases;

import java.util.List;

import engine.Tower;

public class UpgradeTower {

    /*
* This method finds a specific tower and, for all of its string upgrade references, adds the actual tower      * object to its upgrade list instead of just the string reference
     */
    public void addUpgradeTowers(List<Tower> allTowers, Tower towerToAdd){
            for(Tower t:allTowers){
                if(towerToAdd.getUpgrades().contains(t.getName())){
                    towerToAdd.addUpgrade(t);
                }
            }
    }
}


