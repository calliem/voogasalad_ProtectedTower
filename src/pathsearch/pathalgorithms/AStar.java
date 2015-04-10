package pathsearch.pathalgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import pathsearch.graph.GraphNode;

/**
 * @author Kaighn
 */
public class AStar {
	public static List<Integer> run(GraphNode start, List<GraphNode> ends,
			Heuristic heuristicCalculator) throws NoPathExistsException {
		PriorityQueue<TreeNode> fringe = new PriorityQueue<TreeNode>(100,
				(a, b) -> a.getCost() + a.getHeuristic() >= b.getCost()
						+ b.getHeuristic() ? 1 : -1);
		List<Integer> finalPath;
		TreeNode treeStart = new TreeNode(start, heuristicCalculator);
		fringe.add(treeStart);
		while (true) {
			TreeNode current = fringe.poll();
			if (current == null) {
				throw new NoPathExistsException();
			}
			current.getGraphNode().setVisited(true);
			if (isSolutionNode(current.getGraphNode(),ends)) {
				finalPath = current.getPathHistory();
				break;
			}
			for (TreeNode neighbor : current.calculateNeighbors()) {
				if (!neighbor.getGraphNode().visited()) {
					fringe.add(neighbor);
				}
			}
		}
		return finalPath;
	}

	private static boolean isSolutionNode(GraphNode current, List<GraphNode> ends) {
		return ends.contains(current);
	}
}
