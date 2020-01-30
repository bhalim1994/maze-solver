package main.java.maze.solver;

/**
 * Coordinate.
 * 
 * Tracks the coordinate of the maze solver.
 * 
 * @author Benedict Halim
 * @version 2020
 */
public class Coordinate {
    /**
     * The x coordinate of the maze solver.
     */
    int x;
    /**
     * The y coordinate of the maze solver.
     */
    int y;
    /**
     * The previous coordinate of the maze solver.
     */
    Coordinate parent;

    /**
     * Constructs a Coordinate with no previous coordinate.
     * 
     * @param x an integer as the x coordinate.
     * @param y an integer as the y coordinate.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;
    }

    /**
     * Constructs a Coordinate with a previous coordinate.
     * 
     * @param x      an integer as the x coordinate.
     * @param y      an integer as the y coordinate.
     * @param parent a Coordinate as the previous coordinate.
     */
    public Coordinate(int x, int y, Coordinate parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    /**
     * Returns the x coordinate.
     * 
     * @return the x coordinate as an integer.
     */
    int getX() {
        return x;
    }

    /**
     * Returns the y coordinate.
     * 
     * @return the y coordinate as an integer.
     */
    int getY() {
        return y;
    }

    /**
     * Returns the previous coordinate.
     * 
     * @return the previous coordinate as a Coordinate.
     */
    Coordinate getParent() {
        return parent;
    }
}
