package am.aua.gameoflife.core;

import java.util.Scanner;

/**
 * The Board og the game.
 */
public abstract class World {
    private Pattern pattern;
    private int generation;
    //constructors
    /**
     *
     * @param format The format of the game/board.
     */
    protected World(String format){
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
    //methods

    /**
     * Returns the width of the board.
     * @return the width of the board.
     */
    public int getWidth(){
        return pattern.getWidth();
    }

    /**
     * Returns the height of the board.
     * @return the height of the board.
     */
    public int getHeight(){
        return pattern.getHeight();
    }

    /**
     * Returns the generation count of the game.
     * @return the generation count of the game.
     */
    public int getGenerationCount(){
        return generation;
    }

    /**
     * Returns the pattern of the game/board.
     * @return the pattern of the game/board.
     */
    public Pattern getPattern(){
        return pattern;
    }

    /**
     * Changes the board to the next generation
     */
    public void nextGeneration(){
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
    public abstract boolean getCell(int col, int row);

    /**
     * Changes the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @param value The cell condition.
     */
    abstract void setCell(int col, int row, boolean value);

    private int countNeighbours(int col, int row) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (!(i == 0 && j == 0) && getCell(col + j, row + i))
                    count++;
        return count;
    }

    /**
     * Computes the cell for the next generation.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return the condition of the cell for the next generation.
     */
    protected boolean computeCell(int col, int row) {
        int neighbours = countNeighbours(col, row);
        return neighbours == 3 || (getCell(col, row) && neighbours == 2);
    }

    /**
     *
     * @param other   the reference object with which to compare.
     * @return True if equals. Otherwise, false.
     */
    @Override
    public boolean equals(Object other){
        if(other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        World otherWorld = (World)other;
        //error code still
        return generation == otherWorld.generation && pattern.equals(otherWorld.pattern);
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
        for (int row = 0; row < pattern.getHeight(); row++) {
            for (int col = 0; col < pattern.getWidth(); col++)
                if (getCell(col, row))
                    result.append(ALIVE);
                else
                    result.append(DEAD);
            result.append("\n");
        }
        return result.toString();
    }
}

