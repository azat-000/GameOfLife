package am.aua.gameoflife;

import am.aua.gameoflife.core.ArrayLife;
import am.aua.gameoflife.core.PackedLife;

/**
 * This class is the main entry point of the game
 */
public class gameOfLife {
    /**
     * For starting the game with given args in this format: "NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS". E.g. "Glider:Richard Guy:20:20:1:1:010 001 111"
     * @param args
     */
    public static void main(String[] args){
        if(args.length==0){
            System.out.println("You didn't the format as an argument.\nExiting the program.");
            System.exit(0);
        }
        ArrayLife game  = new ArrayLife(args[0]);
        game.play();
    }
}
