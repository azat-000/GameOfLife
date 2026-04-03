package am.aua.gameoflife.core;

/**
 * The <code>Pattern</code> class represents a Game of Life pattern that
 * supports a <code>String</code> specification format like:
 * NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS
 * For example, "Glider:Richard Guy:20:20:1:1:01 001 111"
 */
public class Pattern {
    // instance variables
    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;

    // constructors
    /**
     * Constructs a newly allocated <code>Pattern</code> object as specified by
     * the argument pattern.
     *
     * @param format	a pattern specified in <code>String</code> format
     */
    public Pattern(String format) {
        String[] tokens = format.split(":");
        name = tokens[0];
        author = tokens[1];
        width = Integer.parseInt(tokens[2]);
        height = Integer.parseInt(tokens[3]);
        startCol = Integer.parseInt(tokens[4]);
        startRow = Integer.parseInt(tokens[5]);
        cells = tokens[6];
    }

    // methods
    /**
     * Returns the name of the pattern.
     *
     * @return		the <code>String</code> name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the author of the pattern.
     *
     * @return		the <code>String</code> author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * Returns the width of the pattern board.
     *
     * @return		the <code>int</code> width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Returns the height of the pattern board.
     *
     * @return		the <code>int</code> height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Returns the start column of the pattern.
     *
     * @return		the <code>int</code> start column
     */
    public int getStartCol() {
        return startCol;
    }
    /**
     * Returns the start row of the pattern.
     *
     * @return		the <code>int</code> start row
     */
    public int getStartRow() {
        return startRow;
    }
    /**
     * Returns the cells of the pattern.
     *
     * @return		the <code>String</code> cells
     */
    public String getCells() {
        return cells;
    }
    /**
     * Updates the values in the 2D array representing the state of 'world' as
     * expressed by the contents of the field 'cells'
     *
     * @param world	the <code>boolean[][]</code> representation of the 'world'
     */
    public void initialise(boolean[][] world) {
        String[] rows = cells.split(" ");
        for (int i = 0; i < rows.length; i++)
            for (int j = 0; j < rows[i].length(); j++)
                if (rows[i].charAt(j) == '1')
                    world[startRow + i][startCol + j] = true;
    }
    /**
     * Generates the state of 'world', as expressed by the contents of the field
     * 'cells', in the form of a long integer
     *
     * @return		the <code>long</code> representation of the 'world'
     */
    public long initialise() {
        long world = 0L;
        String[] rows = cells.split(" ");
        for (int i = 0; i < rows.length; i++)
            for (int j = 0; j < rows[i].length(); j++)
                if (rows[i].charAt(j) == '1')
                    world |= 1L << ((startRow + i) * width + (startCol + j));
        return world;
    }

    /*
    // testing
    public static void main(String[] args) {
        Pattern pattern = new Pattern(args[0]);
        System.out.println("Name:  " + pattern.getName());
        System.out.println("Author:  " + pattern.getAuthor());
        System.out.println("Width:  " + pattern.getWidth());
        System.out.println("Height:  " + pattern.getHeight());
        System.out.println("StartCol:  " + pattern.getStartCol());
        System.out.println("StartRow:  " + pattern.getStartRow());
        System.out.println("Pattern:  " + pattern.getCells());
    }
    */
}
