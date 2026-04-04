package am.aua.gameoflife;


import am.aua.gameoflife.core.ArrayWorld;
import am.aua.gameoflife.core.PackedWorld;
import am.aua.gameoflife.core.World;

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
        //CAN YOU PLEASE TELL ME IN THE FEEDBACK WHICH VERSION AM I SUPPOSED TO USE HERE? THE FIRST ONE MIGHT CAUSE PRIVACY LEAKS IF NOT USED PROPERLY IN THE MAIN METHOD. HOWEVER, THE COMMENTED ONE DISCOURAGES POLYMORPHISM AND I AM NOT ALLOWED TO USE OTHER METHODS BECAUSE IN THE HOMEWORK PDF, THE UML DIAGRAM SAYS THE CLASSES SHOULD LOOK LIKE IT IN THE END.
        this.world = w;
//        if (w instanceof PackedWorld) {
//            world = new PackedWorld((PackedWorld) w);
//        } else if (w instanceof ArrayWorld) {
//            world = new ArrayWorld((ArrayWorld) w);
//        }
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
        if(args.length == 1) w = new ArrayWorld(args[0]);
        else if(args.length == 2){
            if(args[0].equals("--array")) w = new ArrayWorld(args[1]);
            else if(args[0].equals("--packed")) w = new PackedWorld(args[1]);
        }
        GameOfLife gol = new GameOfLife(w);
        gol.play();
    }
}
