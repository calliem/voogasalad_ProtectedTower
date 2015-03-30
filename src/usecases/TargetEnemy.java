package usecases;

import engine.sprites.Enemy;
import engine.sprites.Tower;

public class TargetEnemy{
    public Tower detectingTower = new Tower();
    public Enemy targetEnemy = new Enemy();
    targetEnemy.move();

    //targetEnemy.move() places enemy within range of detectingTower
    detectingTower.target(targetEnemy);

    //detectingTower.target() runs targetEnemy.isTargetable(detectingTower), verifies that it is in fact targetable and in range, and runs method to create new projectile object

}

