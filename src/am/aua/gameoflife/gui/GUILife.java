package am.aua.gameoflife.gui;

import am.aua.gameoflife.core.ArrayWorld;
import am.aua.gameoflife.core.PackedWorld;
import am.aua.gameoflife.core.Pattern;
import am.aua.gameoflife.core.World;
import am.aua.gameoflife.data.PatternStore;
import am.aua.gameoflife.exceptions.PatternFormatException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GUILife extends JFrame implements ListSelectionListener {
    private World world;
    private PatternStore store;
    private ArrayList<World> cachedWorlds;
    private GamePanel gamePanel;
    private JButton playButton;
    private Timer timer;
    private boolean playing;
    //constructors
    public GUILife(PatternStore ps){
        super("Game of Life");
        store = ps;
        cachedWorlds = new ArrayList<>();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);

        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(), BorderLayout.CENTER);
        gamePanel.display(world);

    }
    //methods;
    private static void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }
    private JPanel createGamePanel() {
        gamePanel = new GamePanel();
        addBorder(gamePanel, "Game Panel");
        return gamePanel;
    }
    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel();
        addBorder(patt, "Patterns");
        patt.setLayout(new GridLayout(1,1));
        JList<Pattern> list = new JList<>(store.getPatternsNameSorted());
        list.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(list);
        patt.add(scrollPane);
        return patt;
    }
    private JPanel createControlPanel() {
        JPanel ctrl = new JPanel();
        addBorder(ctrl, "Controls");
        ctrl.setLayout(new GridLayout(1,3));

        JButton backButton = new JButton("< Back");
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playing)
                    runOrPause();
                moveBack();
            }
        });
        ctrl.add(backButton);

        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runOrPause();
            }
        });
        ctrl.add(playButton);

        JButton forwardButton = new JButton("Forward >");
        forwardButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playing)
                    runOrPause();
                moveForward();
            }
        });
        ctrl.add(forwardButton);
        return ctrl;
    }
    public void moveBack(){
        if(world == null) {
            System.out.println("Please select a pattern to play (l to list):");
            return;
        }
        if(world.getGenerationCount()>0) {
            world = cachedWorlds.get(world.getGenerationCount() - 1);
            gamePanel.display(world);
        }
    }
    public void moveForward(){
        if(world == null) {
            System.out.println("Please select a pattern to play (l to list):");
            return;
        }
        if(world.getGenerationCount() == cachedWorlds.size()-1){
            World nextSnapshot = copyWorld(true);
            nextSnapshot.nextGeneration();
            cachedWorlds.add(nextSnapshot);
            world = nextSnapshot;
        }else {
            world = cachedWorlds.get(world.getGenerationCount() + 1);
        }
        gamePanel.display(world);

    }
    @Override
    public void valueChanged(ListSelectionEvent e){
        if(playing)
            runOrPause();
        JList<Pattern> list = (JList<Pattern>) e.getSource();
        Pattern p = list.getSelectedValue();
        cachedWorlds.clear();
        try{
            if(p.getHeight() * p.getWidth() <= 64)
                world = new PackedWorld(p);
            else
                world = new ArrayWorld(p);
        }catch(PatternFormatException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        cachedWorlds.add(copyWorld(true));
        gamePanel.display(world);

    }
    private void runOrPause() {
        if (playing) {
            timer.cancel();
            playing = false;
            playButton.setText("Play");
        }
        else {
            playing = true;
            playButton.setText("Stop");
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    moveForward();
                }
            }, 0, 500);
        }
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
    public static void main(String[] args){
        try{
            PatternStore ps = new PatternStore("https://bit.ly/2FJERFh");
            GUILife gui = new GUILife(ps);
            gui.setVisible(true);
        } catch (IOException e) {
            System.out.println("Failed to load the pattern store");
        }


    }
}
