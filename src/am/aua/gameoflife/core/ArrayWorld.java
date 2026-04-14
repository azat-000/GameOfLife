package am.aua.gameoflife.core;

import am.aua.gameoflife.exceptions.PatternFormatException;

/**
 * The board of the game represented as 2D arrays. Works for any size.
 */
public class ArrayWorld extends World {
    private Cell[][] world;
    //constructors

    /**
     * Constructs the board given the format.
     * @param format the format of the board.
     */
    public ArrayWorld(String format) throws PatternFormatException {
        super(format);
        world = new Cell[getHeight()][getWidth()];
        //optional: false is the default value
        for (int row = 0; row < world.length; row++)
            for (int col = 0; col < world[row].length; col++)
                world[row][col] = Cell.DEAD;
        getPattern().initialise(this);
    }

    /**
     * Copy constructor.
     * @param aw The object to be copied.
     */
    public ArrayWorld(ArrayWorld aw) {
        super(aw);
        world = new Cell[getHeight()][getWidth()];
        for (int row = 0; row < world.length; row++)
            for (int col = 0; col < world[row].length; col++)
                world[row][col] = aw.world[row][col];
    }

    /**
     *
     * @param pattern -- the pattern of the game board.
     * @throws PatternFormatException
     */
    public ArrayWorld(Pattern pattern) throws PatternFormatException{
        super(pattern);
        world = new Cell[getHeight()][getWidth()];
        getPattern().initialise(this);
    }
    //methods

    /**
     * Returns the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return the cell condition.
     */
    @Override
    public final Cell getCell(int col, int row) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth())
            return Cell.DEAD;
        return world[row][col];
    }
    /**
     * Changes the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @param value The cell condition.
     */
    @Override
    final void setCell(int col, int row, Cell value) {
        if (row >= 0 && row < getHeight() && col >= 0 && col < getWidth())
            world[row][col] = value;
    }
    /**
     * Used in nextGeneration to change the board to the next generation.
     */
    @Override
    protected final void nextGenerationImpl() {
        Cell[][] nextWorld = new Cell[world.length][];
        for (int row = 0; row < nextWorld.length; row++) {
            nextWorld[row] = new Cell[world[row].length];
            for (int col = 0; col < nextWorld[row].length; col++)
                nextWorld[row][col] = computeCell(col, row);
        }
        world = nextWorld;
    }

    /**
     *
     * @param o   the reference object with which to compare.
     * @return True if equals. Otherwise, false.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        ArrayWorld aw = (ArrayWorld) o;
        for (int row = 0; row < getHeight(); row++)
            for (int col = 0; col < getWidth(); col++)
                if (world[row][col] != aw.world[row][col])
                    return false;
        return true;
    }
}
