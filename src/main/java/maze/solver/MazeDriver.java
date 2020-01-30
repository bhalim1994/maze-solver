package main.java.maze.solver;

import java.io.File;
import java.util.List;

/**
 * MazeDriver.
 * 
 * Drives the MazeSolver.
 * 
 * @author Benedict Halim
 * @version 2020
 */
public class MazeDriver {

    /**
     * Loads the mazes and runs them hrough DFS and BFS.
     * 
     * @param args unused
     */
    public static void main(String[] args) throws Exception {
        File maze1 = new File("src/resources/mazes/maze1.txt");
        File maze2 = new File("src/resources/mazes/maze2.txt");

        run(maze1);
        run(maze2);
    }

    /**
     * Helper method to run the mazes.
     * 
     * @param file The maze files.
     */
    private static void run(File file) throws Exception {
        Maze maze = new Maze(file);
        dfs(maze);
        bfs(maze);
    }

    /**
     * Solves the maze through BFS.
     * 
     * @param maze a Maze object as the maze of interest.
     */
    private static void bfs(Maze maze) {
        BFSMazeSolver bfs = new BFSMazeSolver();
        List<Coordinate> path = bfs.solve(maze);
        maze.printPath(path);
        maze.reset();
    }

    /**
     * Solves the maze through DFS.
     * 
     * @param maze a Maze object as the maze of interest.
     */
    private static void dfs(Maze maze) {
        DFSMazeSolver dfs = new DFSMazeSolver();
        List<Coordinate> path = dfs.solve(maze);
        maze.printPath(path);
        maze.reset();
    }
}
