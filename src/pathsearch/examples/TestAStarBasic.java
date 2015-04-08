
package pathsearch.examples;

import java.util.Arrays;
import java.util.List;

import pathsearch.graph.GridCell;
import pathsearch.pathalgorithms.NoPathExistsException;
import pathsearch.wrappers.GridWrapper;
/**
 * A basic example of using the grid wrapper to run AStar. There are only three
 * lines of wrapper code. They are: (1) construct the wrapper, (2) initialize
 * graph in the wrapper, (3) run shortest path. These lines are labeled in the
 * code as 1, 2, and 3.
 * 
 * @author Kaighn
 *
 */
public class TestAStarBasic {
	public static void main(String[] args) throws NoPathExistsException {
		final Object[][] grid = { 
				{ 0, 7, 0, 0, 0, 0, 7, 0 },
				{ 0, 7, 0, 0, 0, 0, 7, 7 }, 
				{ 0, 7, 0, 0, 0, 0, 0, 0 },
				{ 0, 7, 0, 0, 0, 0, 0, 7 }, 
				{ 0, 0, 0, 7, 7, 0, 7, 0 },
				{ 0, 0, 0, 7, 0, 0, 7, 0 }, 
				{ 0, 0, 0, 7, 0, 7, 0, 0 },
				{ 0, 0, 0, 7, 0, 0, 0, 0 } };
		/**
		 * (1) Construct the wrapper.
		 */
		GridWrapper wrap = new GridWrapper();
		
		/**
		 * (2) Initialize graph. Pass in an obstacle function as a lambda. In this case, we want 7's to be obstacles.
		 * Be careful
		 */
		wrap.initializeGraph(grid, i -> (Integer) i == 7);
	
		List<GridCell> cellPath;
		
		//Example 1: (0,0) -> (7,7). Top left cell to bottom right cell.
		cellPath = wrap.shortestPath(0, 0, 7, 7); // line 3
		TestAStar.printCells(grid, cellPath);
		
		//Example 2: (0,0) -> {(0,7), (7,7)}. The path will go to the closer of the two end points.
		int[] ends = { 0,7, 7,0 };
		cellPath = wrap.shortestPath(0, 0 ); // line 3
		TestAStar.printCells(grid, cellPath);
		
		//Example 3: (0,0) -> 
		cellPath = wrap.shortestPath(0, 0, 7, 7); // line 3
		TestAStar.printCells(grid, cellPath);
		

	}
	
}
