package util.pathsearch.pathalgorithms;

import java.util.LinkedList;
import java.util.List;
import util.pathsearch.graph.Edge;
import util.pathsearch.graph.GraphNode;

/**
 * @author Kaighn
 */
public class TreeNode {
	private double myCost;
	private double myHeuristic;
	private GraphNode myNode;
	private List<Integer> myPathHistory;
	private static Heuristic myHeuristicCalculator;
	
	public TreeNode(GraphNode start, Heuristic hCalc){
		myCost = 0;
		myNode = start;
		myPathHistory = new LinkedList<>();
		myPathHistory.add(start.getID());
		myHeuristicCalculator = hCalc;
		myHeuristic = hCalc.calculateHeuristic(start);
	}
	private TreeNode(TreeNode parent, Edge traversedEdge) {
		myCost = parent.getCost() + traversedEdge.getCost();
		myNode = traversedEdge.getNeighbor();
		myPathHistory = new LinkedList<>(parent.getPathHistory());
		myPathHistory.add(myNode.getID());
		myHeuristic = myHeuristicCalculator.calculateHeuristic(traversedEdge.getNeighbor());
	}
	
	
	public List<Integer> getPathHistory() {
		return myPathHistory;
	}
	
	public double getCost() {
		return myCost;
	}

	public double getHeuristic() {
		return myHeuristic;
	}
	
	public GraphNode getGraphNode(){
		return myNode;
	}
	
	public List<TreeNode> calculateNeighbors(){
		List<TreeNode> ret = new LinkedList<>();
		for(Edge e : myNode.getEdges()){
			ret.add(new TreeNode(this, e));
		}
		return ret;
	}
}