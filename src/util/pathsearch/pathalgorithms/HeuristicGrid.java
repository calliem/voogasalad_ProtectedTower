package util.pathsearch.pathalgorithms;

import java.util.List;
import java.util.stream.Collectors;
import util.pathsearch.graph.GraphNode;
import util.pathsearch.graph.GridIDTransform;
import util.pathsearch.graph.PathCell;

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
