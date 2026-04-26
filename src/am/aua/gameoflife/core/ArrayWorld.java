package am.aua.gameoflife.core;

import am.aua.gameoflife.exceptions.PatternFormatException;

/**
 * The board of the game represented as 2D arrays. Works for any size.
 */
public class ArrayWorld extends World {
    private Cell[][] world;
    private final Cell[] deadRow;
    //constructors

    /**
     * Constructs the board given the format.
     * @param format the format of the board.
     */
    public ArrayWorld(String format) throws PatternFormatException {
        super(format);
        world = new Cell[getHeight()][getWidth()];
        deadRow = new Cell[getWidth()];
        for(int i = 0; i<deadRow.length; i++)
            deadRow[i] = Cell.DEAD;
        for (int row = 0; row < world.length; row++)
            for (int col = 0; col < world[row].length; col++)
                world[row][col] = Cell.DEAD;
        getPattern().initialise(this);
        for(int row = 0; row<world.length; row ++)
            if(isRowDead(row)) world[row] = deadRow;
    }


    /**
     * Copy constructor.
     * @param aw The object to be copied.
     */
    public ArrayWorld(ArrayWorld aw) {
        super(aw);
        world = new Cell[getHeight()][getWidth()];
        deadRow = aw.deadRow;
        for (int row = 0; row < world.length; row++)
            if(aw.world[row] == deadRow)
                world[row] = deadRow;
            else
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
        deadRow = new Cell[getWidth()];
        for(int i = 0; i<deadRow.length; i++)
            deadRow[i] = Cell.DEAD;
        for (int row = 0; row < world.length; row++)
            for (int col = 0; col < world[row].length; col++)
                world[row][col] = Cell.DEAD;
        getPattern().initialise(this);
        for(int row = 0; row<world.length; row ++)
            if(isRowDead(row)) world[row] = deadRow;
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
    private boolean isRowDead(int row){
        for(Cell each : world[row])
            if(each == Cell.ALIVE) return false;
        return true;
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
    @Override
    public ArrayWorld clone(){
        ArrayWorld copy = (ArrayWorld)super.clone();
        Cell[][] worldCopy = new Cell[getHeight()][getWidth()];
        for (int row = 0; row < world.length; row++)
            if(world[row] == deadRow)
                worldCopy[row] = deadRow;
            else
                for (int col = 0; col < world[row].length; col++)
                    worldCopy[row][col] = world[row][col];
        copy.world = worldCopy;
        return copy;
    }
    //testing the copy constructor.
//    public static void main(String[] args) throws PatternFormatException {
//        ArrayWorld first = new ArrayWorld("nm:ss:2:2:0:0:10 10");
//        ArrayWorld second = new ArrayWorld(first);
//        System.out.println(first.equals(second));
//        second.setCell(0,0, Cell.DEAD);
//        System.out.println((first.equals(second)));
//    }
}
