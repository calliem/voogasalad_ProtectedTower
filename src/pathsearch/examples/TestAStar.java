
package pathsearch.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import pathsearch.graph.Graph;
import pathsearch.graph.GraphMakerGrid;
import pathsearch.graph.GraphNode;
import pathsearch.graph.GridCell;
import pathsearch.graph.GridIDTransform;
import pathsearch.pathalgorithms.HeuristicGrid;
import pathsearch.pathalgorithms.NoPathExistsException;

/**
 * @author Kaighn
 */
public class TestAStar {
	public static void main(String[] args) {
		// set up grid, with 0's as free spaces, 7's as obstacles, 4's as end
		// points, and 8 as the start.
		// In this setup, the top right 4 is blocked by 7's. The path settles
		// for the bottom right 4.
		// Play around with the grid to see different
		final Integer[][] grid = { { 8, 7, 0, 0, 0, 0, 7, 4 },
				{ 0, 7, 0, 0, 0, 0, 0, 7 }, { 0, 7, 0, 0, 0, 0, 0, 0 },
				{ 0, 7, 0, 0, 0, 0, 0, 7 }, { 0, 0, 0, 7, 7, 0, 7, 0 },
				{ 0, 0, 0, 7, 0, 0, 7, 0 }, { 0, 0, 0, 7, 0, 7, 0, 4 },
				{ 0, 0, 0, 7, 0, 0, 0, 0 } };
		GridIDTransform idTransform = new GridIDTransform(grid.length);
		Graph g = GraphMakerGrid.makeGraph(grid, o -> {
			Integer i = (Integer) o;
			return i == 7;
		}, idTransform);
		List<Integer> ends = new LinkedList<>();
		int start = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 4) {
					ends.add(idTransform.getID(i, j));
				}
				if (grid[i][j] == 8) {
					start = idTransform.getID(i, j);
				}
			}
		}
		List<GraphNode> list;
		try {
			list = g.shortestPath(start, ends, new HeuristicGrid(ends,
					idTransform));
			List<GridCell> cells = list.stream()
					.map(node -> idTransform.getCell(node.getID()))
					.collect(Collectors.toList());
			printCells(grid, cells);
		} catch (NoPathExistsException e) {
			e.printStackTrace();
		}
	}

	public static void printCells(Object[][] grid, List<GridCell> list) {
		Object[][] dest = new Object[grid[0].length][grid.length];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				dest[i][j] = grid[i][j];
			}
		}

		for (GridCell cell : list) {
			dest[cell.getRow()][cell.getCol()] = 1;
		}

		// print resulting grid, with the path labeled as 1's
		for (Object[] row : dest) {
			for (Object i : row) {
				System.out.printf("%d  ", i);
			}
			System.out.println();
		}
	}
}
