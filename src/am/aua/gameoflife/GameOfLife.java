package am.aua.gameoflife;

import am.aua.gameoflife.core.ArrayLife;

public class GameOfLife {
    // testing
    public static void main(String[] args) {
        ArrayLife al = new ArrayLife(args[0]);
        al.play();
    }
}
