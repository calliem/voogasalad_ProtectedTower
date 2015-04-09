package pathsearch.pathalgorithms;

import java.util.List;
import java.util.stream.Collectors;

import pathsearch.graph.GraphNode;
import pathsearch.graph.PathCell;
import pathsearch.graph.GridIDTransform;

/**
 * @author Kaighn
 */
public class HeuristicGrid implements Heuristic{
	List<PathCell> endNodeCoords;
	GridIDTransform idTransform;
	
	public HeuristicGrid(List<Integer> endNodesID, GridIDTransform idTrans){
		endNodeCoords = endNodesID.stream().map(id -> idTrans.getCell(id)).collect(Collectors.toList());
		idTransform = idTrans;
	}
	
	@Override
	public double calculateHeuristic(GraphNode node) {
		PathCell cell = idTransform.getCell(node.getID());
		return endNodeCoords.stream().map(gcell -> gcell.distance(cell)).min((a,b) -> Integer.compare(a, b)).get();
	}

}