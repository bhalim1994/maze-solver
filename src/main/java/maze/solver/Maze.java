package main.java.maze.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Maze.
 * 
 * The maze to solve.
 * 
 * @author Benedict Halim
 * @version 2020
 */
public class Maze {
    /**
     * The tiles that the maze solver can go on.
     */
    private static final int VALID = 0;
    /**
     * The tiles that the maze solver cannot go on.
     */
    private static final int WALL = 1;
    /**
     * The starting tile of the maze solver.
     */
    private static final int START = 2;
    /**
     * The endng tile of the maze solver.
     */
    private static final int EXIT = 3;
    /**
     * The tiles that the maze solver has explored.
     */
    private static final int PATH = 4;
    /**
     * The maze to solve as a 2D integer array.
     */
    private int[][] maze;
    /**
     * The visited maze as a 2D boolean array.
     */
    private boolean[][] visited;
    /**
     * The staring coordinates.
     */
    private Coordinate start;
    /**
     * The ending coordinates.
     */
    private Coordinate end;

    /**
     * Constructs a Maze from a text file.
     * 
     * @param maze a text file with the maze.
     */
    public Maze(File maze) throws FileNotFoundException {
        String fileText = "";
        // Converts the maze text file into a String
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine() + "\n";
            }
        }
        // Initializes the maze from the String.
        initializeMaze(fileText);
    }

    /**
     * Initializes the maze.
     * 
     * @param text a String that contains the maze of interest.
     */
    private void initializeMaze(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("This is an empty maze!");
        }

        // Uses regex to split a String by new lines.
        String[] lines = text.split("\\r?\\n");
        // Initializes the maze and visited maze.
        maze = new int[lines.length][lines[0].length()];
        visited = new boolean[lines.length][lines[0].length()];

        // Check if the width of the maze is valid.
        for (int row = 0; row < getHeight(); row++) {
            // Checks each String length with the width it's supposed to be.
            if (lines[row].length() != getWidth()) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length()
                        + " but should be " + getWidth() + ")");
            }

            // Sets the tiles to its corresponding integers.
            for (int col = 0; col < getWidth(); col++) {
                if (lines[row].charAt(col) == '#')
                    maze[row][col] = WALL;
                else if (lines[row].charAt(col) == 'S') {
                    maze[row][col] = START;
                    start = new Coordinate(row, col);
                } else if (lines[row].charAt(col) == 'E') {
                    maze[row][col] = EXIT;
                    end = new Coordinate(row, col);
                } else
                    maze[row][col] = VALID;
            }
        }
    }

    /**
     * Gets the height of the maze.
     * 
     * @return The height of the maze as an integer.
     */
    public int getHeight() {
        return maze.length;
    }

    /**
     * Gets the width of the maze.
     * 
     * @return The width of the maze as an integer.
     */
    public int getWidth() {
        return maze[0].length;
    }

    /**
     * Gets the entry point of the maze.
     * 
     * @return The entry point of the maze as a Coordinate.
     */
    public Coordinate getEntry() {
        return start;
    }

    /**
     * Gets the exit point of the maze.
     * 
     * @return The exit point of the maze as a Coordinate.
     */
    public Coordinate getExit() {
        return end;
    }

    /**
     * Checks if the point is an exit point.
     * 
     * @param x an integer for the exit's x coordinate.
     * @param y an integer for the exit's y coordinate.
     * @return True if it's the exit point, false otherwise.
     */
    public boolean isExit(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    /**
     * Checks if the point is a start point.
     * 
     * @param x an integer for the start's x coordinate.
     * @param y an integer for the start's y coordinate.
     * @return True if it's the start point, false otherwise.
     */
    public boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    /**
     * Checks if the point is a visited point.
     * 
     * @param row an integer for the visted point's x coordinate.
     * @param col an integer for the visited point's y coordinate.
     * @return True if the point has been visited, false otherwise.
     */
    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    /**
     * Checks if the point is a wall.
     * 
     * @param row an integer for the wall's x coordinate.
     * @param col an integer for the wall's y coordinate.
     * @return True if it's a wall, false otherwise.
     */
    public boolean isWall(int row, int col) {
        return maze[row][col] == WALL;
    }

    /**
     * Sets the point of interest to be whether it's been visited or not.
     * 
     * @param row   an integer for the point of interest's x coordinate.
     * @param col   an integer for the point of interest's y coordinate.
     * @param value a boolean to set whether it's visited (True) or not (False).
     */
    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    /**
     * Checks whether the current location is a valid location.
     * 
     * @param row an integer for the location's x coordinate.
     * @param col an integer for the location's y coordinate.
     * @return True if it's the a valid location, false otherwise.
     */
    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            return false;
        }
        return true;
    }

    /**
     * Outputs the maze's solution path.
     * 
     * @param path A list of coordinates as the solution path.
     */
    public void printPath(List<Coordinate> path) {
        // Create a 2D array of a temporary maze
        int[][] tempMaze = Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new);
        // If it's a start or exit point, don't include that since not part of path
        for (Coordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isExit(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            // Set the tiles of interest to the PATH tile
            tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
        }
        System.out.println(toString(tempMaze));
    }

    /**
     * Converts the maze to a String.
     * 
     * @param maze a 2D integer array of the maze.
     * @return The maze of interest as a String.
     */
    public String toString(int[][] maze) {
        // Add one to height for spacing in between mazes
        StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
        // Convert each integer tile to its corresponding character
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (maze[row][col] == VALID) {
                    result.append(' ');
                } else if (maze[row][col] == WALL) {
                    result.append('#');
                } else if (maze[row][col] == START) {
                    result.append('S');
                } else if (maze[row][col] == EXIT) {
                    result.append('E');
                } else {
                    result.append('.');
                }
            }
            result.append('\n');
        }
        // Convert StringBuilder to String object
        return result.toString();
    }

    /**
     * Resets the maze to being completely unvisited.
     */
    public void reset() {
        // Makes every tile non-visited
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }
}
