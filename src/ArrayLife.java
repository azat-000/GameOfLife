import java.util.Scanner;

/**
 * This class is a game engine for the game "Game of Life". This class uses the format "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS" as argument. For example, "Glider:Richard Guy:20:20:1:1:010 001 111". This class uses the Pattern class.
 */
public final class ArrayLife {
    //instance vars and constants;
    private final int width;
    private final int height;
    private boolean[][] world;
    private Pattern pattern;
    //other insatnce vars
    //the actives and nextActives just keep track the changed cells and their neighbors of the world to simply avoid going through the whole array and checking every cell of it.
    private int[] activeCols;
    private int[] activeRows;
    private int activeCounter;
    private int[] nextActiveCols;
    private int[] nextActiveRows;
    private boolean[][] inActiveList;
    //characters used
    private final char liveCell = '\u2588';
    private final char deadCell = '\u0020';

    //constructors;

    /**
     *This constructor automatically interacts with the Pattern class
     * @param format "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS"
     */
    public ArrayLife(String format){
        pattern = new Pattern(format);
        width = pattern.getWidth();
        height = pattern.getHeight();
        world = new boolean[height][width];

        activeCols = new int[width*height];
        activeRows = new int[width*height];
        activeCounter = 0;
        nextActiveCols = new int[width*height];
        nextActiveRows = new int[width*height];
        inActiveList = new boolean[height][width];

        pattern.initialise(world);
        /*
         * DESIGN NOTE ON EFFICIENCY VS. OOP "SEPARATION OF CONCERNS":
         * I am aware that sweeping the entire board here is less efficient than
         * simply calling pattern.getCells().split(" ") and building the initial
         * active list by only looking at the explicitly provided starting coordinates.
         * However, parsing that string inside ArrayLife violates the "Separation
         * of Concerns" required in Part 3. The Pattern class should be the ONLY
         * class handling string formats. Therefore, I chose to accept THIS ONE-TIME,
         * full-board sweep during startup. This keeps ArrayLife completely blind to
         * the string format, while keeping my nextGeneration() engine fully optimized.
         */
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                //adding the cell and its neighbors to the active list;
                if(world[i][j]){
                    for(int r=i-1;r<=i+1;r++){
                        for(int c=j-1;c<=j+1;c++){
                            if(r>=0 && r<height && c>=0 && c<width){
                                if(!inActiveList[r][c]){ //checking for duplicates
                                    inActiveList[r][c] = true;
                                    activeRows[activeCounter] = r;
                                    activeCols[activeCounter] = c;
                                    activeCounter++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //..

    /**
     * This method return the value of the cell
     * @param col The column of the cell
     * @param row The row of the cell
     * @return the value of the cell
     */
    public boolean getCell(int col, int row) {
        if(col < 0 || col >= width || row < 0 || row >= height){
            System.out.println("Fatal error.");
            System.exit(0);
        }
        return world[row][col];
    }

    /**
     * Assigns the specific cell to the given boolean value
     * @param col The column of the cell
     * @param row The row of the cell
     * @param value The value to be assigned
     */
    public void setCell(int col, int row, boolean value) {
        if(col < 0 || col >= width || row < 0 || row >= height){
            System.out.println("Fatal error.");
            System.exit(0);
        }
        world[row][col] = value;


    }

    /**
     * Prints the gameBoard
     */
    public void print(){
        System.out.println(toString());
    }
    private int countNeighbors(int col, int row){
        if(col < 0 || col >= width || row < 0 || row >= height){
            System.out.println("Fatal error.");
            System.exit(0);
        }
        int  count = 0;
        boolean leftCellExist = col>0;
        boolean rightCellExist = col<width-1;
        boolean upCellExist = row>0;
        boolean downCellExist = row<height - 1;

        if(leftCellExist && getCell(col-1, row))
            count++;
        if(rightCellExist && getCell(col+1, row))
            count++;
        if(upCellExist && getCell(col, row-1))
            count++;
        if(downCellExist && getCell(col, row+1))
            count++;
        if(leftCellExist && upCellExist && getCell(col-1, row-1))
            count++;
        if(leftCellExist && downCellExist && getCell(col-1, row+1))
            count++;
        if(rightCellExist && upCellExist && getCell(col+1, row-1))
            count++;
        if(rightCellExist && downCellExist && getCell(col+1, row+1))
            count++;
        return count;
    }
    private boolean computeCell(int col, int row){
        if(col < 0 || col >= width || row < 0 || row >= height){
            System.out.println("Fatal error.");
            System.exit(0);
        }
        int neighborCount = countNeighbors(col, row);
        if(getCell(col,row)){ //if the cell is live;
            if(neighborCount < 2){
                return false;
            }else if(neighborCount == 2 || neighborCount == 3){
                return true;
            }else if(neighborCount > 3)
                return false;
        }else{ //if the cell is dead;
            if(neighborCount == 3)
                return true;
            else
                return false;
        }
        return false; //to keep the compiler happy;
    }

    /**
     * Updates the game board to the next generation
     */
    public void nextGeneration(){
        boolean[][] nextWorld = new boolean[height][width];
        int nextActiveCounter = 0;

        for(int i = 0; i<activeCounter; i++){
            int r = activeRows[i];
            int c = activeCols[i];
            nextWorld[r][c] = computeCell(c,r);
        }
        for(int i = 0; i<activeCounter; i++){
            int r = activeRows[i];
            int c = activeCols[i];
            inActiveList[r][c] = false;
        }
        for(int i = 0; i<activeCounter; i++){
            int r = activeRows[i];
            int c = activeCols[i];
            if(nextWorld[r][c]){
                for(int nr=r-1;nr<=r+1;nr++){
                    for(int nc=c-1;nc<=c+1;nc++){
                        if(nr>0 && nr<height && nc>0 && nc<width){
                            if(!inActiveList[nr][nc]){
                                inActiveList[nr][nc] = true;
                                nextActiveRows[nextActiveCounter] = nr;
                                nextActiveCols[nextActiveCounter] = nc;
                                nextActiveCounter++;
                            }
                        }
                    }
                }
            }
        }
        world = nextWorld;
        int[] tempCols = activeCols;
        int[] tempRows = activeRows;
        activeCols = nextActiveCols;
        activeRows = nextActiveRows;
        nextActiveCols = tempCols;
        nextActiveRows = tempRows;
        activeCounter = nextActiveCounter;

    }

    /**
     * Generates and returns the string representation of the board
     * @return the string representation of the board
     */
    public String toString(){
        String result = "";
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                result += getCell(col, row)? liveCell: deadCell;
            }
            result += "\n";
        }
        return result;
    }

    /**
     * This method aks for user input and uses the print() method and the nextGeneration() method repeatedly.
     */
    public void play(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your choice: s or q.");
        char userChoice = scan.next().charAt(0);
        while(userChoice != 'q' && userChoice != 's'){
            System.out.println("Please enter either s for next generation, or q for stopping.");
            userChoice = scan.next().charAt(0);
        }
        while(userChoice == 's'){
            print();
            nextGeneration();
            userChoice = scan.next().charAt(0);
            System.out.println("Enter your choice: s or q.");
            while(userChoice != 'q' && userChoice != 's'){
                System.out.println("Please enter either s for next generation, or q for stopping.");
                userChoice = scan.next().charAt(0);
            }
        }

    }

    /**
     * For testing the class
     * @param args
     */
    public static void main(String[] args){
        ArrayLife al = new ArrayLife(args[0]);
        al.play();
    }

}
