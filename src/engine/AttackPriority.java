package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import engine.element.sprites.Enemy;
import engine.element.sprites.GameElement;

/**
 * Class for different attack priorities of towers 
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
	 * @return
	 */
	
	public GameElement getTarget(String priority, List<GameElement> myTargets){
		this.myTargets = myTargets;
		// TODO standardize the priority strings
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
	 * Returns the closest Enemy from the tower
	 * @return GameElement closest to the tower
	 */
	
    private GameElement closest(){
    	return closestFrom(location);
    }
    
    /**
     * Returns the farthest Enemy from the tower
     * @return
     */
    
    private GameElement farthest(){
    	return farthestFrom(location);
    }
    
	private GameElement closestFrom(Point2D source){
		ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceFrom(g.getLocation(), source));
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
    private GameElement farthestFrom(Point2D source){
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceFrom(g.getLocation(), source));
    	}
    	return myTargets.get(getLargestIndex(list));
    }
    
    private double distanceFrom(Point2D g1, Point2D g2){
    	return Math.sqrt(Math.pow(Math.abs(g1.getX()-g2.getX()),2)
				+ Math.pow(Math.abs(g1.getY()-g2.getY()),2));
    }
    
    private GameElement first(){
    	//get smallest distance from goal
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceFromGoal(g));
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
    private GameElement last(){
    	//get largest distance from goal
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), distanceFromGoal(g));
    	}
    	return myTargets.get(getLargestIndex(list));
    }
    
    private double distanceFromGoal(GameElement g) {
    	return distanceFrom(g.getLocation(), ((Enemy) g).getGoal().getLocation());
	}
    
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

	private GameElement weakest(){
		ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getHealth().doubleValue());
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
    
    private GameElement healthiest(){
		ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getHealth().doubleValue());
    	}
    	return myTargets.get(getLargestIndex(list));
    }
    
    private GameElement fastest(){
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getSpeed());
    	}
    	return myTargets.get(getLargestIndex(list));
    }

    private GameElement slowest(){
    	ArrayList<Double> list = new ArrayList<>();
    	for (GameElement g: myTargets){
    		list.add(myTargets.indexOf(g), ((Enemy) g).getSpeed());
    	}
    	return myTargets.get(getSmallestIndex(list));
    }
}
