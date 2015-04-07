package engine.element;

import java.util.List;
import engine.GameElement;
import engine.Updateable;
import engine.sprites.Enemy;
import engine.sprites.GridCell;
import engine.sprites.Projectile;
import engine.sprites.Tower;


/**
 * This class holds the layout of the game, including the locations of the game elements like grid
 * cells and towers/enemies. It also call on these objects to update their location and behaviors,
 * while functioning as a controller to tell objects what to do, such as telling towers what enemies
 * are within range.
 * 
 * @author Qian Wang
 *
 */
public class Layout extends GameElement implements Updateable {

    private GridCell[][] myGrid;
    private List<Tower> myTowers;
    private List<Enemy> myEnemies;
    private List<Projectile> myProjectiles;

    @Override
    public void update (int counter) {
        // TODO Update all game elements

    }

}
