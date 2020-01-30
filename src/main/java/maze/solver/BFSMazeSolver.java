package main.java.maze.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * BFSMazeSolver.
 * 
 * Solves the maze through breadth-first search method.
 * 
 * @author Benedict Halim
 * @version 2020
 */
public class BFSMazeSolver {
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
        LinkedList<Coordinate> nextVisit = new LinkedList<>();

        // Starts at the entry.
        Coordinate start = maze.getEntry();
        nextVisit.add(start);

        // If the list is not empty, retrieve and remove the first tile from the list.
        while (!nextVisit.isEmpty()) {
            Coordinate current = nextVisit.remove();

            // If it's not a valid location or is already explored, skip to the next tile.
            if (!maze.isValidLocation(current.getX(), current.getY())
                    || maze.isExplored(current.getX(), current.getY())) {
                continue;
            }

            // If it's a wall, set it to visited, and skip to the next tile.
            if (maze.isWall(current.getX(), current.getY())) {
                maze.setVisited(current.getX(), current.getY(), true);
                continue;
            }

            // Once exit node is found, backtrack from the current node to the start node to
            // find the shortest path.
            if (maze.isExit(current.getX(), current.getY())) {
                return trackPath(current);
            }

            // Add all immediate neighbours in their four directions to the queue.
            for (int[] direction : DIRECTIONS) {
                Coordinate coordinate = new Coordinate(current.getX() + direction[0], current.getY() + direction[1],
                        current);
                nextVisit.add(coordinate);
                // Set current one to be visited.
                maze.setVisited(current.getX(), current.getY(), true);
            }
        }
        // Otherwise, return an empty list.
        return Collections.emptyList();
    }

    /**
     * Backtracks through the path.
     * 
     * @param current a Coordinate object showing the current position.
     * @return The path as a list of coordinates.
     */
    private List<Coordinate> trackPath(Coordinate current) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate iterator = current;

        // Backtracks through the path from current to its parents until it hits a null parent (Starting tile)
        while (iterator != null) {
            path.add(iterator);
            iterator = iterator.parent;
        }

        // Return the path created from the while loop
        return path;
    }
}
