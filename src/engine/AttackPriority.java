package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import engine.element.sprites.Enemy;
import engine.element.sprites.GameElement;

/**
 * Class for to return the GameElement a tower should target based on its attack priority
 * @author Michael
 *
 */

public class AttackPriority {

	private Point2D location;
	private List<GameElement> myTargets;
	
	public AttackPriority(Point2D loc){
		location = loc;
	}
	
	/**
	 * Returns the target from the list based on the priority 
	 * @param priority Attack priority to select target
	 * @param myTargets List of targets to choose from
	 * @return GameElement the tower should target
	 */
	
	public GameElement getTarget(String priority, List<GameElement> myTargets){
		this.myTargets = myTargets;
		// TODO standardize the attack priority strings
		switch (priority) {
		case "close" : return closest();
		case "farthest" : return farthest();
		case "first" : return first();
		case "last" : return last();
		case "fastest" : return fastest();
		case "slowest" : return slowest();
		case "weakest" : return weakest();
		case "strongest" : return healthiest();
		default : return first();
		}
	}
	
	/**
	 * Returns the closest GameElement from the tower
	 * @return GameElement closest to the tower
	 */
	
    private GameElement closest(){
    	return closestFrom(location);
    }
    
    /**
     * Returns the farthest GameElement from the tower
     * @return GameElement farthest from the tower
     */
    
    private GameElement farthest(){
    	return farthestFrom(location);
    }
    
    /**
     * Returns the GameElement closest to its goal
     * @return GameElement closest to its goal
     */
    
    private GameElement first(){
    	//get smallest distance from goal
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceFromGoal(g));
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
    /**
     * Returns the GameElement farthest from its goal
     * @return GameElement farthest from its goal
     */
    
    private GameElement last(){
    	//get largest distance from goal
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceFromGoal(g));
    	}
    	return myTargets.get(getLargestIndex(list));
    }
    
    /**
     * Returns the GameElement with the least health
     * @return GameElement with the least health
     */
    
	private GameElement weakest(){
		ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getHealth().doubleValue());
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
	/**
	 * Returns the GameElement with the most health
	 * @return GameElement with the most health
	 */
	
    private GameElement healthiest(){
		ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getHealth().doubleValue());
    	}
    	return myTargets.get(getLargestIndex(list));
    }
    
    /**
     * Returns the GameElement with the greatest speed
     * @return GameElement with the greatest speed 
     */
    
    private GameElement fastest(){
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getSpeed());
    	}
    	return myTargets.get(getLargestIndex(list));
    }

    /**
     * Returns the GameELement with the least speed
     * @return GameElement with the least speed
     */
    
    private GameElement slowest(){
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getSpeed());
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
    /**
     * Returns the GameElement closest to the source location
     * @param source Source location the targets are compared to
     * @return GameElement closest to the source location
     */
    
	private GameElement closestFrom(Point2D source){
		ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceBetweenLocs(g.getLocation(), source));
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
	/**
	 * Returns the GameElement farthest from the source location
	 * @param source Source location the targets are compared to
	 * @return GameElement farthest from the source location
	 */
	
    private GameElement farthestFrom(Point2D source){
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceBetweenLocs(g.getLocation(), source));
    	}
    	return myTargets.get(getLargestIndex(list));
    }
    
    /**
     * Calculates the pixel distance between two locations.
     * @param loc1 first location
     * @param loc2 second location
     * @return distance between the two locations
     */
    
    private double distanceBetweenLocs(Point2D loc1, Point2D loc2){
    	return loc1.distance(loc2);
    }
    
    /**
     * Calculates the pixel distance between two GameElements
     * @param sprite1 first GameElement
     * @param sprite2 second GameElement
     * @return
     */
    
    private double distanceBetweenSprites(GameElement sprite1, GameElement sprite2){
    	return sprite1.getLocation().distance(sprite2.getLocation());
    }
    
    /**
     * Calculates the distance between a GameElement and its goal
     * @param g GameElement to calculate for
     * @return The distance between the GameElement and its goal
     */
    
    private double distanceFromGoal(GameElement g) {
    	return distanceBetweenSprites(g, ((Enemy) g).getGoal());
	}
    
    /**
     * Returns the index of the largest value in the list
     * @param list List of doubles
     * @return index of the largest double in the list
     */
    
    private int getLargestIndex(List<Double> list){
    	double max = Double.MIN_VALUE;
    	int index = -1;
    	for (int i = 0; i<list.size(); i++){
    		if (list.get(i) > max){
    			max = list.get(i);
    			index = i;
    		}
    	}
    	return index;
    }
    
    /**
     * Returns the index of the smallest value in the list
     * @param list List of doubles
     * @return index of the smallest double in the list
     */
    
    private int getSmallestIndex(List<Double> list){
    	double min = Double.MAX_VALUE;
    	int index = -1;
    	for (int i = 0; i<list.size(); i++){
    		if (list.get(i) < min){
    			min = list.get(i);
    			index = i;
    		}
    	}
    	return index;
    }

}
