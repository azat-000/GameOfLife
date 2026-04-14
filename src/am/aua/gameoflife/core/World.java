package am.aua.gameoflife.core;

import am.aua.gameoflife.exceptions.PatternFormatException;

import java.util.Scanner;

/**
 * The Board og the game.
 */
public abstract class World {
    private Pattern pattern;
    private int generation;
    enum Cell {ALIVE, DEAD}
    //constructors
    /**
     *
     * @param format The format of the game/board.
     */
    protected World(String format) throws PatternFormatException {
        pattern = new Pattern(format);
        generation = 0;
    }

    /**
     * Copy constructor.
     * @param world The object to be copied.
     */
    protected World(World world){
        pattern = world.pattern;
        generation = world.generation;

    }

    /**
     *
     * @param pattern -- the pattern of the game board.
     */
    protected World(Pattern pattern){
        this.pattern = pattern;
        generation = 0;
    }
    //methods

    /**
     * Returns the width of the board.
     * @return the width of the board.
     */
    public final int getWidth(){
        return pattern.getWidth();
    }

    /**
     * Returns the height of the board.
     * @return the height of the board.
     */
    public final int getHeight(){
        return pattern.getHeight();
    }

    /**
     * Returns the generation count of the game.
     * @return the generation count of the game.
     */
    public final int getGenerationCount(){
        return generation;
    }

    /**
     * Returns the pattern of the game/board.
     * @return the pattern of the game/board.
     */
    public final Pattern getPattern(){
        return pattern;
    }

    /**
     * Changes the board to the next generation
     */
    public final void nextGeneration(){
        nextGenerationImpl();
        generation++;
    }

    /**
     * Used in nextGeneration to change the board to the next generation.
     */
    protected abstract void nextGenerationImpl();

    /**
     * Returns the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return The cell condition.
     */
    public abstract Cell getCell(int col, int row);

    /**
     * Changes the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @param value The cell condition.
     */
    abstract void setCell(int col, int row, Cell value);

    private final int countNeighbours(int col, int row) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (!(i == 0 && j == 0) && getCell(col + j, row + i) ==  Cell.ALIVE)
                    count++;
        return count;
    }

    /**
     * Computes the cell for the next generation.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return the condition of the cell for the next generation.
     */
    protected final Cell computeCell(int col, int row) {
        int neighbours = countNeighbours(col, row);
        if (neighbours == 3 || ((getCell(col, row) == Cell.ALIVE) && neighbours == 2))
            return Cell.ALIVE;
        return Cell.DEAD;
    }

    /**
     *
     * @param o   the reference object with which to compare.
     * @return True if equals. Otherwise, false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        World otherWorld = (World) o;
        return generation == otherWorld.generation
                && pattern.equals(otherWorld.pattern);
    }

    /**
     * Returns the string representation of the board.
     * @return The string representation of the board.
     */
    @Override
    public String toString() {
        final char ALIVE = '\u25AE';
        final char DEAD = '\u25AF';
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++)
                if (getCell(col, row) == Cell.ALIVE)
                    result.append(ALIVE);
                else
                    result.append(DEAD);
            result.append("\n");
        }
        return result.toString();
    }
}

