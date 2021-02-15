package com.company;


/*
 Class for a thread whose only job is updating
 the Viewer window contents for each new generation.
 This is implemented as its own thread so that updates to window
 elements don't happen out of sync. The Controller thread calls join() on
 this thread after this thread is created, to make sure the update
 finishes before the Controller tries to update anything again.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UpdateThread extends Thread {
    private LifeBoard board;
    private GameOfLife window;
    private int gen;

    public UpdateThread(LifeBoard board, GameOfLife window, int gen) {
        this.board = board;
        this.window = window;
        this.gen = gen;
    }

    public void run() {
        window.getGenLabel().setText("Generation #" + gen);
        window.getGenLabel().repaint();

        window.getAliveLabel().setText("Alive: " + board.countLivingNeighbours());
        window.getAliveLabel().repaint();

        window.getLifePanel().updatePopulation(board.getBoard());


    }




}
