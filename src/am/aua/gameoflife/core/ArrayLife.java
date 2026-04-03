package am.aua.gameoflife.core;

import java.util.Scanner;

/**
 * The <code>ArrayLife</code> class represents a Game of Life where the
 * underlying board is represented by a 2D array of <code>boolean</code> values.
 */
public class ArrayLife {
    // instance variables
    private int width;
    private int height;
    private boolean[][] world;
    private Pattern pattern;

    // constructors
    /**
     * Constructs a newly allocated <code>ArrayLife</code> object as specified by
     * the argument pattern.
     *
     * @param format	a pattern specified in <code>String</code> format
     */
    public ArrayLife(String format) {
        pattern = new Pattern(format);
        width = pattern.getWidth();
        height = pattern.getHeight();
        world = new boolean[height][width];
        pattern.initialise(world);
    }

    // methods
    private boolean getCell(int col, int row) {
        if (row < 0 || row >= height || col < 0 || col >= width)
            return false;
        return world[row][col];
    }
    private void setCell(int col, int row, boolean value) {
        if (row >= 0 && row < height && col >= 0 && col < width)
            world[row][col] = value;
    }
    private int countNeighbours(int col, int row) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (!(i == 0 && j == 0) && getCell(col + j, row + i))
                    count++;
        return count;
    }
    private boolean computeCell(int col, int row) {
        int neighbours = countNeighbours(col, row);
        return neighbours == 3 || (getCell(col, row) && neighbours == 2);
    }
    private void nextGeneration() {
        boolean[][] nextWorld = new boolean[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                nextWorld[row][col] = computeCell(col, row);
//        for (int row = 0; row < height; row++)
//            for (int col = 0; col < width; col++)
//                setCell(col, row, nextWorld[row][col]);
        world = nextWorld;
    }
    private void print() {
        System.out.println(toString());
    }
    /**
     * Generates a <code>String</code> representation of the game board.
     *
     * @return		the <code>String</code> representation of the game board
     */
    public String toString() {
        final char ALIVE = '\u25AE';
        final char DEAD = '\u25AF';
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++)
                if (getCell(col, row))
                    result.append(ALIVE);
                else
                    result.append(DEAD);
            result.append("\n");
        }
        return result.toString();
    }
    /**
     * Prints the game board and advances to the next generation, while the user
     * inputs the character 's'; stops when the user inputs 'q' (or anything other
     * than 's').
     */
    public void play() {
        Scanner keyboard = new Scanner(System.in);
        while (keyboard.next().equals("s")) {
            print();
            nextGeneration();
        }
    }
}
