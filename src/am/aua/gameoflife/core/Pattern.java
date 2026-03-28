package am.aua.gameoflife.core;

/**
 * This class is for giving a pattern in the form of "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS, which extracts it and sets the components of the board of ArrayLife.
 */
public class Pattern {
    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;
    //constructors

    /**
     * This constructor is automatically implemented by the class ArrayLife.
     * @param format "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS"
     */
    public Pattern(String format) {
        String[] formatArr = format.split(":");
        name = formatArr[0];
        author = formatArr[1];
        width = Integer.parseInt(formatArr[2]);
        height = Integer.parseInt(formatArr[3]);
        startCol = Integer.parseInt(formatArr[4]);
        startRow = Integer.parseInt(formatArr[5]);
        cells = formatArr[6];
    }
    //accessor methods

    /**
     * Return the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the author
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * return the width of the array of the board.
     * @return the width of the array of the board.
     */
    public int getWidth() {
        return width;
    }

    /**
     * returns the height of the array of the board.
     * @return the height of the array of the board.
     */
    public int getHeight() {
        return height;
    }

    /**
     * returns the start column.
     * @return the start column.
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * return the start row.
     * @return the start row.
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * returns the cells part of the format given to the constructor.
     * @return the cells part of the format given to the constructor.
     */
    public String getCells() {
        return cells;
    }

    /**
     * Initialises the given array board to the pattern. Be aware that calling this method more than once can cause bugs. Do it if you know what you are doing.
     * @param world the given array board/world.
     */
    public void initialise(boolean[][] world) {
        String[] cellsOnRow =  cells.split(" ");
        boolean isAlive;
        for(int i = 0; i<cellsOnRow.length; i++){
            for(int j = 0; j<cellsOnRow[i].length(); j++){
                isAlive = cellsOnRow[i].charAt(j) == '1';
                if(isAlive) {
                    int r = startRow + i;
                    int c = startCol + j;
                    world[r][c] = true;
                }
            }
        }
    }
    /**
     * Initialises a board represented as a primitive type of long and returns it. Be aware that calling this method more than once can cause bugs. Do it if you know what you are doing.
     * @return the board of type long
     */
    public long initialize(){
        long world = 0;
        String[] cellsOnRow =  cells.split(" ");
        boolean isAlive;
        for(int i = 0; i<cellsOnRow.length; i++){
            for(int j = 0; j<cellsOnRow[i].length(); j++){
                isAlive = cellsOnRow[i].charAt(j) == '1';
                if(isAlive) {
                    int r = startRow + i;
                    int c = startCol + j;
                    world |= 1L << (r * width + c);

                }
            }
        }
        return world;
    }

//    public static void main(String[] args){
//        Pattern pattern = new Pattern(args[0]);
//        System.out.println("Name:  " + pattern.getName());
//        System.out.println("Author:  " + pattern.getAuthor());
//        System.out.println("Width:  " + pattern.getWidth());
//        System.out.println("Height:  " + pattern.getHeight());
//        System.out.println("StartCol:  " + pattern.getStartCol());
//        System.out.println("StartRow:  " + pattern.getStartRow());
//        System.out.println("Pattern:  " + pattern.getCells());
//    }


}
