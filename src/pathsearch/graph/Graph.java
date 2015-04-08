package voogasalad.util.pathsearch.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import voogasalad.util.pathsearch.pathalgorithms.AStar;
import voogasalad.util.pathsearch.pathalgorithms.BFS;
import voogasalad.util.pathsearch.pathalgorithms.Heuristic;
import voogasalad.util.pathsearch.pathalgorithms.NoPathExistsException;

/**
 * @author Kaighn
 */
public class Graph {
	private List<GraphNode> myNodes;
	private Map<Integer,GraphNode> myNodeMap;
	
	public Graph(){
		myNodes = new ArrayList<>();
		myNodeMap = new HashMap<>();
	}
	
	public void addNode(GraphNode node){
		myNodes.add(node);
		myNodeMap.put(node.getID(), node);
	}
	
	public List<GraphNode> shortestPath(int start, List<Integer> endNodes, Heuristic h) throws NoPathExistsException{
		List<Integer> path = AStar.run(myNodeMap.get(start), endNodes.stream().map(n -> myNodeMap.get(n)).collect(Collectors.toList()), h);
		resetVisited();
		return toNodes(path);
	}
	
	public Map<GraphNode,GraphNode> allShortestPaths(List<Integer> endNodes){
		resetVisited();
		return BFS.completeBFS(toNodes(endNodes));
	}
	
	private List<GraphNode> toNodes(List<Integer> ids){
		return Collections.unmodifiableList(ids.stream().map(id -> myNodeMap.get(id)).collect(Collectors.toList()));
	}
	
	private void resetVisited(){
		myNodes.forEach(n -> n.setVisited(false));
	}
	
//	public String toString(){
//		StringBuilder b = new StringBuilder();
//		
//	}
}
