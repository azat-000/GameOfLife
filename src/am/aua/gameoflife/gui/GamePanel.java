package am.aua.gameoflife.gui;

import am.aua.gameoflife.core.World;

import javax.swing.*;
import java.awt.Color;

public class GamePanel extends JPanel {
    private World world = null;

    @Override
    protected void paintComponent(java.awt.Graphics g){
        int width = getWidth();
        int height = getHeight();

        if(world == null){
            return;
        }
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);

        int cellWidth = width/world.getWidth();
        int cellHeight = height/world.getHeight();
        int cellSize = Math.min(cellHeight,cellWidth);
        for(int x = 0; x<world.getWidth();x++){
            for(int y = 0; y<world.getHeight();y++){
                if(world.getCell(x,y) == World.Cell.ALIVE) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
                }
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x*cellSize, y*cellSize, cellSize, cellSize);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString("Generation: " + world.getGenerationCount(), 10, height - 20);}
    public void display(World w){
        world = w;
        repaint();
    }
}
