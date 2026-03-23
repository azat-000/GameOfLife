import java.util.Scanner;

public class ArrayLife {
    //instance vars and constants;
    private String name;
    private String author;
    private int width;
    private int height;
    private boolean[][] world;
    private int[] activeCols;
    private int[] activeRows;
    private int activeCounter;

    private int[] nextActiveCols;
    private int[] nextActiveRows;
    private boolean[][] inActiveList;


    private final char liveCell = '\u2588';
    private final char deadCell = '\u0020';
    //constructors;
    public ArrayLife(String format){
        String[] formatArr = format.split(":");
        name = formatArr[0];
        author = formatArr[1];
        width = Integer.parseInt(formatArr[2]);
        height = Integer.parseInt(formatArr[3]);

        world = new boolean[height][width];
        activeCols = new int[width*height];
        activeRows = new int[width*height];
        activeCounter = 0;

        nextActiveCols = new int[width*height];
        nextActiveRows = new int[width*height];
        inActiveList = new boolean[height][width];

        int startUpperCol = Integer.parseInt(formatArr[4]);
        int startUpperRow = Integer.parseInt(formatArr[5]);
        String[] cellsOnRow =  formatArr[6].split(" ");

        boolean isAlive;
        for(int i = 0; i<cellsOnRow.length; i++){
            for(int j = 0; j<cellsOnRow[i].length(); j++){
                isAlive = cellsOnRow[i].charAt(j) == '1';
                if(isAlive){
                    int r =startUpperRow+i;
                    int c =startUpperCol+j;
                    world[r][c] = true;
                    //adding the cell and its neighbors to the active list;
                    for(int nr=r-1;nr<=r+1;nr++){
                        for(int nc=c-1;nc<=c+1;nc++){
                            if(nr>0 && nr<height && nc>0 && nc<width){
                                if(!inActiveList[nr][nc]){
                                    inActiveList[nr][nc] = true;
                                    activeRows[activeCounter] = nr;
                                    activeCols[activeCounter] = nc;
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
    public boolean getCell(int col, int row) {
        if(col < 0 || col >= width || row < 0 || row >= height){
            System.out.println("Fatal error.");
            System.exit(0);
        }
        return world[row][col];
    }
    public void setCell(int col, int row, boolean value) {
        if(col < 0 || col >= width || row < 0 || row >= height){
            System.out.println("Fatal error.");
            System.exit(0);
        }
        world[row][col] = value;


    }

    public void print(){
        System.out.println(toString());
    }
    public int countNeighbors(int col, int row){
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
    public boolean computeCell(int col, int row){
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

    public static void main(String[] args){
        ArrayLife al = new ArrayLife(args[0]);
        al.play();
    }

}
