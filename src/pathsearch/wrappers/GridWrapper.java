package pathsearch.wrappers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import pathsearch.graph.Graph;
import pathsearch.graph.GraphMakerGrid;
import pathsearch.graph.GraphNode;
import pathsearch.graph.PathCell;
import pathsearch.graph.GridIDTransform;
import pathsearch.pathalgorithms.HeuristicGrid;
import pathsearch.pathalgorithms.NoPathExistsException;
import pathsearch.pathalgorithms.ObstacleFunction;

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

	public List<PathCell> shortestPath(int startRow, int startCol,
			int... endsCoords) throws NoPathExistsException {
		List<Integer> ends = new LinkedList<>();
		for (int i = 0; i < endsCoords.length; i += 2) {
			idTransform.getID(7, 7);
			ends.add(idTransform.getID(endsCoords[i], endsCoords[i + 1]));
		}
		List<GraphNode> list = myGraph.shortestPath(idTransform.getID(startRow,
				startCol), ends, new HeuristicGrid(ends, idTransform));
		return list.stream().map(gnode -> idTransform.getCell(gnode.getID()))
				.collect(Collectors.toList());
	}
}
