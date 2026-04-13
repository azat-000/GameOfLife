package am.aua.gameoflife.core;

import am.aua.gameoflife.exceptions.PatternFormatException;

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
    public PackedWorld(String format) throws PatternFormatException {
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
    public final Cell getCell(int col, int row) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth())
            return Cell.DEAD;
        if(((world >>> (row * getWidth() + col)) & 1L) == 1L)
            return Cell.ALIVE;
        else
            return Cell.DEAD;
    }
    /**
     * Changes the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @param value The cell condition.
     */
    @Override
    final void setCell(int col, int row, Cell value) {
        if (row >= 0 && row < getHeight() && col >= 0 && col < getWidth()) {
            if (value == Cell.ALIVE)
                world |= 1L << (row * getWidth() + col);
            else
                world &= ~(1L << (row * getWidth() + col));
        }
    }
    /**
     * Used in nextGeneration to change the board to the next generation.
     */
    @Override
    protected final void nextGenerationImpl() {
        long nextWorld = 0L;
        for (int row = 0; row < getHeight(); row++)
            for (int col = 0; col < getWidth(); col++)
                if (computeCell(col, row) == Cell.ALIVE)
                    nextWorld |= 1L << (row * getWidth() + col);
        world = nextWorld;
    }

    /**
     *
     * @param o   the reference object with which to compare.
     * @return True if equal. Otherwise, false.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PackedWorld aw = (PackedWorld) o;
        return super.equals(o) && world == aw.world;
    }
}
