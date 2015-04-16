package util.pathsearch.pathalgorithms;

import util.pathsearch.graph.GraphNode;

/**
 * @author Kaighn
 */
public interface Heuristic {
	public double calculateHeuristic(GraphNode node);
}
