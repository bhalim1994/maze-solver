package main.java.maze.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DFSMazeSolver.
 * 
 * Solves the maze through depth-first search method.
 * 
 * @author Benedict Halim
 * @version 2020
 */
public class DFSMazeSolver {
    /**
     * Right, down, left, up
     */
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    /**
     * Solves the maze.
     * 
     * @param maze a Maze object as the maze of interest.
     * @return The list of coordinates outlining the path.
     */
    public List<Coordinate> solve(Maze maze) {
        // Create a new list of coordinates to signify the path.
        List<Coordinate> path = new ArrayList<>();

        int startRow = maze.getEntry().getX();
        int startCol = maze.getEntry().getY();
        // If there is a path from entry to exit, return the path.
        if (explore(maze, startRow, startCol, path)) {
            return path;
        }
        // Otherwise, return an empty list
        return Collections.emptyList();
    }

    /**
     * Explores possibilities of paths in the maze using a stack method.
     * 
     * @param maze a Maze object for the current maze of interest.
     * @param row  an integer for the current row.
     * @param col  an integer for the current column.
     * @param path a list of Coordinate objects as the path.
     * @return True if there is a path to be found, false otherwise.
     */
    private boolean explore(Maze maze, int row, int col, List<Coordinate> path) {
        // If we're at a non-valid location, a wall, or an already visited node, return
        // failure.
        if (!maze.isValidLocation(row, col) || maze.isWall(row, col) || maze.isExplored(row, col)) {
            return false;
        }

        // Add the tile into the path list and mark current tile as visited.
        path.add(new Coordinate(row, col));
        maze.setVisited(row, col, true);

        // If we've found the exit, return success.
        if (maze.isExit(row, col)) {
            return true;
        }

        // If the exit has not been found, then recursively travel in all directions.
        for (int[] direction : DIRECTIONS) {
            Coordinate coordinate = getNextCoordinate(row, col, direction[0], direction[1]);
            // Recursive function on if we've found the exit, just return true.
            if (explore(maze, coordinate.getX(), coordinate.getY(), path)) {
                return true;
            }
        }

        // Remove from path if it is not a valid path.
        path.remove(path.size() - 1);
        return false;
    }

    /**
     * Adds two coordinates together.
     * 
     * @param row an integer for the current row.
     * @param col an integer for the current column.
     * @param i   an integer to shift current row by.
     * @param j   an integer to shift current column by.
     * @return the new Coordinate as a Coordinate object.
     */
    private Coordinate getNextCoordinate(int row, int col, int i, int j) {
        return new Coordinate(row + i, col + j);
    }
}
