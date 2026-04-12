package am.aua.gameoflife.core;

/**
 * The <code>Pattern</code> class represents a Game of Life pattern that
 * supports a <code>String</code> specification format like:
 * NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS
 * For example, "Glider:Richard Guy:20:20:1:1:01 001 111"
 */
public class Pattern {
    // instance variables
    private String cells;
    private int startRow;
    private int startCol;
    private int height;
    private int width;
    private String author;
    private String name;
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
     * Generates the initial state of the world.
     * @param world The board.
     */
    public void initialise(World world) {
        String[] rows = cells.split(" ");
        for (int i = 0; i < rows.length; i++)
            for (int j = 0; j < rows[i].length(); j++)
                if (rows[i].charAt(j) == '1')
                    world.setCell(startCol+j, startRow+i, true);
    }
    /**
     * Returns the description of the pattern.
     * @return the description of the pattern.
     */
    @Override
    public String toString() {
        return name + ":" + author + ":" + width + ":" + height + ":"
                + startCol + ":" + startRow + ":" + cells;
    }
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pattern op = (Pattern) o;
        return name.equals(op.name) && author.equals(op.author)
                && width == op.width && height == op.height
                && startCol == op.startCol && startRow == op.startRow
                && cells.equals(op.cells);
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
