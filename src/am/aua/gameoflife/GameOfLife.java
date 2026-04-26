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
import java.lang.reflect.Constructor;
import java.util.ArrayList;


/**
 * The start point of the game.
 */
public class GameOfLife {
    private World world;
    private PatternStore store;
    private ArrayList<World> cachedWorlds; //Note that it must be cleared whenever the pattern played changes
    //constructors

    /**
     * Constructor
     * @param ps -- PatternStore.
     */
    public GameOfLife(PatternStore ps) {
        store = ps;
        cachedWorlds = new ArrayList<>();
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

    private World copyWorld(boolean useCloning){
        if(!useCloning){
            try{
                Class<? extends World> clazz = world.getClass();
                Constructor<? extends World> copyConstructor = clazz.getConstructor(clazz);
                return copyConstructor.newInstance(world);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            return world.clone();
        }

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
            if(response.equals("f") || response.equals("b")){
                if(world == null)
                    System.out.println("Please select a pattern to play (l to list):");
                else{
                    if(response.equals("f")){
                        if(world.getGenerationCount() == cachedWorlds.size()-1){
                            World nextSnapshot = copyWorld(true);
                            nextSnapshot.nextGeneration();
                            cachedWorlds.add(nextSnapshot);
                            world = nextSnapshot;
                        }else {
                            world = cachedWorlds.get(world.getGenerationCount() + 1);
                        }
                    }
                    if(response.equals("b")){
                        if(world.getGenerationCount()>0)
                            world=cachedWorlds.get(world.getGenerationCount()-1);
                    }
                    print();
                }
            }
            else if(response.equals("l")){
                ArrayList<Pattern> names = store.getPatternsNameSorted();
                int i = 0;
                for(Pattern p:names){
                    System.out.println(i+" "+p.getName()+" ("+p.getAuthor()+")" );
                    i++;
                }
            }else if(response.startsWith("p")){
                cachedWorlds.clear();
                ArrayList<Pattern> names = store.getPatternsNameSorted();
                int patternNum = Integer.parseInt(response.substring(2));
                Pattern assosiatedPattern = names.get(patternNum);
                if(assosiatedPattern.getWidth() * assosiatedPattern.getHeight() > 64)
                    world = new ArrayWorld(assosiatedPattern);
                else
                    world = new PackedWorld(assosiatedPattern);
                cachedWorlds.add(copyWorld(true));

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
