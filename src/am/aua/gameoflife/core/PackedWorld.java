package am.aua.gameoflife.core;

/**
 * The board of the game represented as a long. Cannot have more than 64 cells. Saying this, height * width shouldn't be more than 64.
 */
public class PackedWorld extends World{
    private long world;
    //constructors

    /**
     * Constructs the board with the given format.
     * @param format The format of the board.
     */
    public PackedWorld(String format) {
        super(format);
        if (getHeight() * getWidth() > 64) {
            System.out.println("Board too large for PackedWorld. Aborting.");
            System.exit(0);
        }
        getPattern().initialise(this);
    }

    /**
     * Copy constructor.
     * @param pw The object to be copied.
     */
    public PackedWorld(PackedWorld pw) {
        super(pw);
        world = pw.world;
    }

    /**
     * Returns the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return The cell condition.
     */
    @Override
    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth())
            return false;
        return ((world >>> (row * getWidth() + col)) & 1L) == 1L;
    }
    /**
     * Changes the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @param value The cell condition.
     */
    @Override
    void setCell(int col, int row, boolean value) {
        if (row >= 0 && row < getHeight() && col >= 0 && col < getWidth()) {
            if (value)
                world |= 1L << (row * getWidth() + col);
            else
                world &= ~(1L << (row * getWidth() + col));
        }
    }
    /**
     * Used in nextGeneration to change the board to the next generation.
     */
    @Override
    protected void nextGenerationImpl() {
        long nextWorld = 0L;
        for (int row = 0; row < getHeight(); row++)
            for (int col = 0; col < getWidth(); col++)
                if (computeCell(col, row))
                    nextWorld |= 1L << (row * getWidth() + col);
        world = nextWorld;
    }

    /**
     *
     * @param o   the reference object with which to compare.
     * @return True if equal. Otherwise, false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PackedWorld aw = (PackedWorld) o;
        return super.equals(o) && world == aw.world;
    }
}
