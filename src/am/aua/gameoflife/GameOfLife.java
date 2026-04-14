package am.aua.gameoflife;


import am.aua.gameoflife.core.ArrayWorld;
import am.aua.gameoflife.core.PackedWorld;
import am.aua.gameoflife.core.Pattern;
import am.aua.gameoflife.core.World;
import am.aua.gameoflife.data.PatternStore;
import am.aua.gameoflife.exceptions.PatternFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * The start point of the game.
 */
public class GameOfLife {
    private World world;
    private PatternStore store;
    //constructors

    /**
     * Constructor
     * @param ps -- PatternStore.
     */
    public GameOfLife(PatternStore ps) {
        store = ps;
//        if (w.getClass() == ArrayWorld.class)
//            world = new ArrayWorld((ArrayWorld) w);
//        else
//            world = new PackedWorld((PackedWorld) w);
    }
    //methods
    private void print(){
        System.out.println("- "+world.getGenerationCount());
        System.out.println(world.toString());
    }

    /**
     * To play the game.
     */
    public void play() throws IOException, PatternFormatException {
        String response ="";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please select a pattern to play (l to list):");
        while(!response.equals("q")){
            response = in.readLine();
            System.out.println(response);
            if(response.equals("f")){
                if(world == null)
                    System.out.println("Please select a pattern to play (l to list):");
                else{
                    world.nextGeneration();
                    print();
                }
            }else if(response.equals("l")){
                Pattern[] names = store.getPatterns();
                int i = 0;
                for(Pattern p:names){
                    System.out.println(i+" "+p.getName()+" ("+p.getAuthor()+")" );
                    i++;
                }
            }else if(response.startsWith("p")){
                Pattern[] names = store.getPatterns();
                int patternNum = Integer.parseInt(response.substring(2));
                Pattern assosiatedPattern = names[patternNum];
                if(assosiatedPattern.getWidth() * assosiatedPattern.getHeight() > 64)
                    world = new ArrayWorld(assosiatedPattern);
                else
                    world = new PackedWorld(assosiatedPattern);
                print();
            }
        }
    }
    /**
     * The starting point of the game.
     * @param args The format.
     */
    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("Usage: java am.aua.gameoflife.GameOfLife <path/url to store>");
            return;
        }
        try {
            PatternStore ps = new PatternStore(args[0]);
            GameOfLife gol = new GameOfLife(ps);
            gol.play();
        }
        catch (IOException ioe) {
            System.out.println("Failed to load pattern store");
        }
        catch (PatternFormatException pfe) {
            System.out.println(pfe.getMessage());
        }
    }
}
