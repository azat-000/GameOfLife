package am.aua.gameoflife.core;

import java.util.Scanner;
/**
 * This class is a faster game engine for the game "Game of Life" that works if the width * height of the board is less than or equal to 64. It uses the format "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS" as argument. For example, "Glider:Richard Guy:20:20:1:1:010 001 111". This class uses the Pattern class.
 */
public class PackedLife {

    //instance variables/constants;
    private int width;
    private int height;
    private long world;
    private Pattern pattern;
    private final char liveCell = '\u2588';
    private final char deadCell = '\u0020';
    //constructors;
    /**
     *This constructor automatically interacts with the Pattern class
     * @param format "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS"
     */
    public PackedLife(String format){
        pattern = new Pattern(format);
        width = pattern.getWidth();
        height = pattern.getHeight();
        if(width*height>64){
            System.out.println("Width * height should be <= to 64");
            System.out.println("Exiting the program.");
            System.exit(0);
        }
        world = pattern.initialize();
    }
    //accessors;
    /**
     * This method return the value of the cell
     * @param col The column of the cell
     * @param row The row of the cell
     * @return the value of the cell
     */
    public boolean getCell(int col, int row){
        if (row < 0 || row >= height)
            return false;
        if (col < 0 || col >= width)
            return false;
        return ((world >>> (row * width + col)) & 1L) == 1L;
    }
    //mutators
    /**
     * Assigns the specific cell to the given boolean value
     * @param col The column of the cell
     * @param row The row of the cell
     * @param value The value to be assigned
     */
    public void setCell(int col, int row, boolean value){
        if (row < 0 || row >= height)
            return;
        if (col < 0 || col >= width)
            return;
        if(value)
            world |= (1L << (row * width + col));
        else
            world &= ~(1L << (row * width + col));
    }
    private long setCell(long newWorld,int col, int row, boolean value){
        if(value)
            newWorld |= (1L << (row * width + col));
        else
            newWorld &= ~(1L << (row * width + col));
        return newWorld;
    }
    //other
    /**
     * Prints the gameBoard;
     */
    public void print(){
        System.out.print(toString());
    }

    /**
     * returns the string representation of the board
     * @return the string representation of the board
     */
    public String toString(){
        StringBuilder result = new StringBuilder(128);
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                result.append(getCell(col, row)? liveCell: deadCell);
            }
            result.append("\n");
        }
        return result.toString();
    }
    private int countNeighbours(int col, int row){
        if (row < 0 || row >= height)
            return 0;
        if (col < 0 || col >= width)
            return 0;
        int count = 0;
        for(int r = row-1; r <= row+1; r++){
            for(int c = col-1; c <= col+1; c++){
                if(r>=0 && r<height && c>=0 && c<width){
                    if(getCell(c,r))
                        count++;
                }
            }
        }
        if(getCell(col, row)) count--;
        return count;
    }
    private boolean computeCell(int col, int row){
        if(col < 0 || col >= width)
            return false;
        if(row < 0 || row >= height)
            return false;
        int neighbours = countNeighbours(col, row);
        if(getCell(col,row))
            return neighbours == 2 || neighbours == 3;
        else
            return neighbours == 3;
    }
    /**
     * Updates the game board to the next generation
     */
    public void nextGeneration(){
        long newWorld = 0;
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                newWorld = setCell(newWorld, col, row, computeCell(col, row));
            }
        }
        world = newWorld;
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
//    public static void main(String[] args){
//        if(args.length==0){
//            System.out.println("You didn't the format as an argument.\nExiting the program.");
//            System.exit(0);
//        }
//        PackedLife game  = new PackedLife(args[0]);
//        game.play();
//    }
}
