package voogasalad.util.pathsearch.wrappers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import voogasalad.util.pathsearch.graph.Graph;
import voogasalad.util.pathsearch.graph.GraphMakerGrid;
import voogasalad.util.pathsearch.graph.GraphNode;
import voogasalad.util.pathsearch.graph.GridCell;
import voogasalad.util.pathsearch.graph.GridIDTransform;
import voogasalad.util.pathsearch.pathalgorithms.HeuristicGrid;
import voogasalad.util.pathsearch.pathalgorithms.NoPathExistsException;
import voogasalad.util.pathsearch.pathalgorithms.ObstacleFunction;

public class GridWrapper {
	Graph myGraph;
	GridIDTransform idTransform;

	public GridWrapper() {
		myGraph = new Graph();
	}

	public void initializeGraph(Object[][] grid, ObstacleFunction obstacleFunc) {
		if (grid == null) {
			return;
		}
		idTransform = new GridIDTransform(grid[0].length);
		myGraph = GraphMakerGrid.makeGraph(grid, obstacleFunc, idTransform);
		idTransform = new GridIDTransform(grid[0].length);
	}

	public List<GridCell> shortestPath(int startRow, int startCol,
			int... endsCoords) throws NoPathExistsException {
		List<Integer> ends = new LinkedList<>();
		for (int i = 0; i < endsCoords.length; i += 2) {
			int a = endsCoords[i];
			a = endsCoords[i+1];
			idTransform.getID(7, 7);
			ends.add(idTransform.getID(endsCoords[i], endsCoords[i + 1]));
		}
		List<GraphNode> list = myGraph.shortestPath(idTransform.getID(startRow,
				startCol), ends, new HeuristicGrid(ends, idTransform));
		return list.stream().map(gnode -> idTransform.getCell(gnode.getID()))
				.collect(Collectors.toList());
	}
}
