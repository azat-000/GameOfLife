package am.aua.gameoflife;


import am.aua.gameoflife.core.ArrayWorld;
import am.aua.gameoflife.core.PackedWorld;
import am.aua.gameoflife.core.World;
import am.aua.gameoflife.exceptions.PatternFormatException;

import java.util.Scanner;

/**
 * The start point of the game.
 */
public class GameOfLife {
    private World world;
    //constructors

    /**
     * Constructor
     * @param w The world of the game.
     */
    public GameOfLife(World w) {
        if (w.getClass() == ArrayWorld.class)
            world = new ArrayWorld((ArrayWorld) w);
        else
            world = new PackedWorld((PackedWorld) w);
    }
    //methods
    private void print(){
        System.out.println("- "+world.getGenerationCount());
        System.out.println(world.toString());
    }

    /**
     * To play the game.
     */
    public void play() {
        Scanner keyboard = new Scanner(System.in);
        while (keyboard.next().equals("s")) {
            print();
            world.nextGeneration();
        }
    }

    /**
     * The starting point of the game.
     * @param args The format.
     */
    public static void main(String[] args) {
        World w = null;
        try{
            if (args.length == 1)
                w = new ArrayWorld(args[0]);
            else if (args[0].equals("--array"))
                w = new ArrayWorld(args[1]);
            else
                w = new PackedWorld(args[1]);
        }catch (PatternFormatException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        GameOfLife gol = new GameOfLife(w);
        gol.play();
    }
}
