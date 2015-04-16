package util.pathsearch.graph;


/**
 * @author Kaighn
 */
public class Edge {
	private GraphNode myNeighbor;
	private double myCost;
	
	public Edge(GraphNode node, double cost){
		myNeighbor = node;
		myCost = cost;
	}
	
	public double getCost(){
		return myCost;
	}
	public GraphNode getNeighbor(){
		return myNeighbor;
	}
	
	
}
