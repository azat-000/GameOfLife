package am.aua.gameoflife.core;

import java.util.Scanner;

/**
 * The <code>PackedLife</code> class represents a Game of Life where the
 * underlying board is represented by a single <code>long</code> value. This
 * representation is only applicable of boards with up to 64 cells.
 */
public class PackedLife {
    // instance variables
    private int width;
    private int height;
    private long world;
    private Pattern pattern;

    // constructors
    /**
     * Constructs a newly allocated <code>PackedLife</code> object as specified by
     * the argument pattern.
     *
     * @param format	a pattern specified in <code>String</code> format
     */
    public PackedLife(String format) {
        pattern = new Pattern(format);
        width = pattern.getWidth();
        height = pattern.getHeight();
        world = pattern.initialise();
    }

    // methods
    private boolean getCell(int col, int row) {
        if (row < 0 || row >= height || col < 0 || col >= width)
            return false;
        return ((world >>> (row * width + col)) & 1L) == 1L;
    }
    private void setCell(int col, int row, boolean value) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            if (value)
                world |= 1L << (row * width + col);
            else
                world &= ~(1L << (row * width + col));
        }
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
        long nextWorld = 0L;
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                if (computeCell(col, row))
                    nextWorld |= 1L << (row * width + col);
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
    
    /*
    // testing
    public static void main(String[] args) {
        PackedLife pl = new PackedLife(args[0]);
        pl.play();
    }
    */
}
