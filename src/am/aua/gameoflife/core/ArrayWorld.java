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
    public ArrayWorld(String format){
        super(format);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
    }

    /**
     * Copy constructor.
     * @param arrayWorld The object to be copied.
     */
    public ArrayWorld(ArrayWorld arrayWorld){
        super(arrayWorld);
        world = new boolean[getHeight()][getWidth()];
        for(int i = 0; i < getHeight(); i++){
            for(int j = 0; j < getWidth(); j++){
                world[i][j] = arrayWorld.world[i][j];
            }
        }
    }
    //methods

    /**
     * Returns the cell condition.
     * @param col The column of the cell.
     * @param row The row of the cell.
     * @return the cell condition.
     */
    @Override
    public boolean getCell(int col, int row){
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
    void setCell(int col, int row, boolean value){
        if (row >= 0 && row < getHeight() && col >= 0 && col < getWidth())
            world[row][col] = value;
    }
    /**
     * Used in nextGeneration to change the board to the next generation.
     */
    @Override
    protected void nextGenerationImpl(){
        boolean[][] nextWorld = new boolean[getHeight()][getWidth()];
        for (int row = 0; row < getHeight(); row++)
            for (int col = 0; col < getWidth(); col++)
                nextWorld[row][col] = computeCell(col, row);
        world = nextWorld;
    }

    /**
     *
     * @param otherObject   the reference object with which to compare.
     * @return True if equals. Otherwise, false.
     */
    @Override
    public boolean equals(Object otherObject){
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        ArrayWorld otherArrayWorld = (ArrayWorld)otherObject;
        //checking if the worlds are equal
        if(!super.equals(otherArrayWorld)) return false;
        for(int row = 0; row < getHeight(); row++){
            for(int col = 0; col < getWidth(); col++){
                if(world[row][col] != otherArrayWorld.world[row][col]) return false;
            }
        }
        return true;
    }
}
