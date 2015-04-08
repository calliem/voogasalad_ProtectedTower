
package pathsearch.pathalgorithms;

import pathsearch.graph.GraphNode;

/**
 * @author Kaighn
 */
public interface Heuristic {
	public double calculateHeuristic(GraphNode node);
}
