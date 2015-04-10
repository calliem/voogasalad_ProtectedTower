package engine.element;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Point2D;
import authoringEnvironment.objects.TileMap;
import engine.Updateable;
import engine.element.sprites.GridCell;
import engine.element.sprites.Sprite;


/**
 * This class holds the layout of the game, including the locations of the game elements like grid
 * cells and towers/enemies. It also call on these objects to update their location and behaviors,
 * while functioning as a controller to tell objects what to do, such as telling towers what enemies
 * are within range.
 * 
 * @author Michael Yang
 * @author Qian Wang
 *
 */
public class Layout extends GameElement implements Updateable {

	private List<Sprite> towerList;
	private List<Sprite> enemyList;
	private List<Sprite> projectileList;
	private GridCell[][] terrainMap;
	// private List<List<GridCell>> spriteMap; not sure if neccessary yet
	private List<Sprite> spritesList;
	private double gridSize;

	public Layout () {

	}

	public Layout (TileMap map) {
		// take in either a tile map or a Tile[][]
		// create a 2D arraylist of gridcells
		/*
		 * Tile[][] tileMap = map.getTiles();
		 * gridSize = map.tileSize();
		 * for(int i = 0; i<tileMap.length; i++)
		 * for (int j = 0; j<tileMap[i].length; j++)
		 * terrainMap[i][j] = new GridCell(gridSize, tileMap[i][j].getTags()); //or some way of
		 * giving it the appropriate size and tags
		 */
	}

	@Override
	public void update (int counter) {
		// TODO Update all game elements

	}

	public List<Sprite> updateSprites () {
		for (Sprite s : projectileList) {
			// s.update();
		}
		for (Sprite t : towerList) {
			// give every tower the enemies within its range
			// t.enemiesInRange(getEnemiesInRange(t));
			// fire projectiles
		}
		for (Sprite s : enemyList) {
			// s.update();
			// check if dead or to be removed
		}
		// collision checking either before or after
		// probably before so that update can handle removing sprites too
		return getSprites();
	}

	public void placeTower (Sprite tower, Point2D loc) {
		// loc param can probably be removed because the tower can just hold its location to be
		// placed at
		if (canPlace(tower, loc))
			towerList.add(tower);
	}

	public boolean canPlace (Sprite tower, Point2D loc) {
		// collision checking and tag checking
		boolean collision = true;
		// if there are any collisions with other towers, then collision stays true
		// if no collisions then the tower can be placed and collision is false
		boolean place = true;
		/*
		 * for (GridCell c: occupiedGridCells(tower))
		 * if(!tagsInCommon(c.getTags(),tower.getTags())) //if the cell has none of the tags of the
		 * tower then can't place so place is false{
		 * place = false;
		 * break;
		 * }
		 */
		return place && !collision;
	}

	private boolean tagsInCommon (List<String> cellTags, List<String> towerTags) {
		boolean common = false;
		for (String tag : towerTags)
			if (cellTags.contains(tag))
				return true;
		return common;
	}

	private Set<Sprite> getEnemiesInRange (Sprite tower) {
		Set<Sprite> enemies = new HashSet<>();
		// find enemies in range (collision checking)
		return enemies;
	}

	public void spawnEnemy (Sprite enemy, Point2D loc) {
		enemyList.add(enemy);
	}

	private Set<GridCell> occupiedGridCells (Sprite sprite) {
		Set<GridCell> occupied = new HashSet<>();
		// radiate outwards from currentGridCell and check for collisions with GridCells
		checkAdjacent(currentGridCell(sprite), occupied, sprite);
		return occupied;
	}

	private void checkAdjacent (GridCell current, Set<GridCell> occupied, Sprite sprite) {
		int[] x = { 1, 1, 0, -1, -1, 0, 1, -1 };
		int[] y = { 0, 1, 1, 0, -1, -1, -1, 1 };
		// need to know how to check if the sprite collides with the gridcell
		/*
		 * if (sprite.intersects(current)){
		 * if(occupied.contains(current))
		 * return;
		 * occupied.add(current);
		 * for (int i = 0; i<x.length; i++){
		 * checkAdjacent(terrainMap[current.x+x[i]][current.y+y[i]], occupied, sprite);
		 * }
		 * }
		 */
	}

	private GridCell currentGridCell (Sprite sprite) {
		// return terrainMap[sprite.x/gridSize][sprite.y/gridSize]
		return null;
	}

	private void fireProjectiles () {
		// may not be necessary if update handles this
	}

	public List<Sprite> getSprites () {
		spritesList.addAll(towerList);
		spritesList.addAll(enemyList);
		spritesList.addAll(projectileList);
		return spritesList;
	}

}
