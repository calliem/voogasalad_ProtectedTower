package voogasalad.util.pathsearch.pathalgorithms;

import voogasalad.util.pathsearch.graph.GraphNode;

/**
 * @author Kaighn
 */
public interface Heuristic {
	public double calculateHeuristic(GraphNode node);
}
