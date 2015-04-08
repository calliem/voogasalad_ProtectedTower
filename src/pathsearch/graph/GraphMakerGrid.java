
package pathsearch.graph;

import java.util.Arrays;

import pathsearch.pathalgorithms.ObstacleFunction;

/**
 * @author Kaighn
 */
public class GraphMakerGrid {
	public static Graph makeGraph(Object[][] grid, ObstacleFunction oFunc, GridIDTransform idTransform){
		Graph g = new Graph();
		NeighborCalculator neighborCalc = new NeighborCalculatorSquare();
		GraphNode[][] nodeGrid = new GraphNode[grid.length][grid[0].length];
		
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				nodeGrid[i][j] = new GraphNode(grid.length*i+j);
				g.addNode(nodeGrid[i][j]);
			}
		}
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				if(oFunc.isObstacle(grid[i][j])){
					continue;
					
				}
				GraphNode n = nodeGrid[i][j];
				for(int x = 0; x < grid.length; x++){
					for(int y = 0; y < grid.length; y++){
						if(neighborCalc.isNeighbor(i, j, x, y) && !oFunc.isObstacle(grid[x][y])){
							n.addEdge(new Edge(nodeGrid[x][y],1));
						}
					}
				}
			}
		} //TODO these looops are atrocious
		
		return g;
	}
}
