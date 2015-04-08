package voogasalad.util.pathsearch.pathalgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import voogasalad.util.pathsearch.graph.GraphNode;
import voogasalad.util.pathsearch.graph.GridCell;
import voogasalad.util.pathsearch.graph.GridIDTransform;

/**
 * @author Kaighn
 */
public class HeuristicGrid implements Heuristic{
	List<GridCell> endNodeCoords;
	GridIDTransform idTransform;
	
	public HeuristicGrid(List<Integer> endNodesID, GridIDTransform idTrans){
		endNodeCoords = endNodesID.stream().map(id -> idTrans.getCell(id)).collect(Collectors.toList());
		idTransform = idTrans;
	}
	
	@Override
	public double calculateHeuristic(GraphNode node) {
		GridCell cell = idTransform.getCell(node.getID());
		return endNodeCoords.stream().map(gcell -> gcell.distance(cell)).min((a,b) -> Integer.compare(a, b)).get();
	}

}
