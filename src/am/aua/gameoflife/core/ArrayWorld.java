package am.aua.gameoflife.core;

/**
 * The board of the game represented as 2D arrays. Works for any size.
 */
public class ArrayWorld extends World {
    private boolean[][] world;
    //constructors

    /**
     * Constructs the board given the format.
     * @param format the format of the board.
     */
    public ArrayWorld(String format) {
        super(format);
        world = new boolean[getHeight()][getWidth()];
        //optional: false is the default value
        for (int row = 0; row < world.length; row++)
            for (int col = 0; col < world[row].length; col++)
                world[row][col] = false;
        getPattern().initialise(this);
    }

    /**
     * Copy constructor.
     * @param aw The object to be copied.
     */
    public ArrayWorld(ArrayWorld aw) {
        super(aw);
        world = new boolean[getHeight()][getWidth()];
        for (int row = 0; row < world.length; row++)
            for (int col = 0; col < world[row].length; col++)
                world[row][col] = aw.world[row][col];
    }
    //methods

    /**
     * Returns the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return the cell condition.
     */
    @Override
    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth())
            return false;
        return world[row][col];
    }
    /**
     * Changes the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @param value The cell condition.
     */
    @Override
    void setCell(int col, int row, boolean value) {
        if (row >= 0 && row < getHeight() && col >= 0 && col < getWidth())
            world[row][col] = value;
    }
    /**
     * Used in nextGeneration to change the board to the next generation.
     */
    @Override
    protected void nextGenerationImpl() {
        boolean[][] nextWorld = new boolean[world.length][];
        for (int row = 0; row < nextWorld.length; row++) {
            nextWorld[row] = new boolean[world[row].length];
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
    public boolean equals(Object o) {
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
