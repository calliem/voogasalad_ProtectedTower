package util.pathsearch.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kaighn
 */
public class GraphNode {
	private List<Edge> myEdges;
	private int ID;
	private static int IDCounter;
	private boolean visited;
	
	public GraphNode(int id){
		myEdges = new LinkedList<>();
		ID = id;
		visited = false;
	}
	
	public void addEdge(Edge e){
		myEdges.add(e);
	}
	
	public int getID(){
		return ID;
	}
	
	public void setVisited(boolean b){
		visited = b;
	}
	
	public boolean visited(){
		return visited;
	}
	
	public List<Edge> getEdges(){
		return Collections.unmodifiableList(myEdges);
	}
	
	public List<GraphNode> getNeighbors(){
		return Collections.unmodifiableList(myEdges.stream().map(e -> e.getNeighbor()).collect(Collectors.toList()));
	}
}
