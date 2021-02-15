package com.company;

import javax.swing.*;
import java.awt.*;

/*
 View class for the MVC-Pattern
 */

public class GameOfLife extends JFrame {
    private JPanel statsPanel;
    private LifePanel lifeBoardPanel;
    private JLabel aliveLabel;
    private JLabel generationLabel;
    private JPanel buttons;
    private JButton reset;
    private JToggleButton pause;
    boolean isPaused;
    boolean isReset;



    public GameOfLife() {
        super("Game of Life");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // labels
        statsPanel = new JPanel();
        statsPanel.setName("Stats Panel");
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        add(statsPanel, BorderLayout.PAGE_START);

        // generation label
        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        generationLabel.setText("Generation #0");
        statsPanel.add(generationLabel);


        // alive label
        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: 0");
        statsPanel.add(aliveLabel);

        // grid
        lifeBoardPanel = new LifePanel();
        lifeBoardPanel.setName("Life Board Panel");
        add(lifeBoardPanel, BorderLayout.CENTER);

        // buttons
        buttons = new JPanel();
        buttons.setName("Button Panel");
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        add(buttons, BorderLayout.WEST);


        // reset button
        reset = new JButton();
        reset.setName("Reset Game");
        reset.setText("Reset");
        reset.addActionListener(actionEvent -> isReset = true);
        buttons.add(reset);

        // pause button
        pause = new JToggleButton();
        pause.setName("Pause Game");
        pause.setText("Pause");
        pause.addActionListener(actionEvent -> isPaused = !isPaused);
        buttons.add(pause);



        setVisible(true);
    }


    // methods for the controller
    public LifePanel getLifePanel() {
        return lifeBoardPanel;
    }

    public JLabel getGenLabel() {
        return generationLabel;
    }

    public JLabel getAliveLabel() {
        return aliveLabel;
    }


    public boolean isPaused() {
        return isPaused;
    }

    public boolean isReset() {
        return isReset;
    }

    public void reset(){
        this.isReset = false;
    }



}
