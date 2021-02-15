package com.company;

/*
 Controller class for the MVC-Pattern
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LifeController extends Thread {

    private LifeBoard board;
    private GameOfLife window;
    private int size;

    public LifeController(LifeBoard board, GameOfLife window, int size) {
        this.board = board;
        this.window = window;
        this.size = size;
    }

    public void run() {
        int generation = 0;

        window.getLifePanel().initialize(size);

        while (true) {
            // new Thread for each update
            var t = new UpdateThread(board, window, generation);
            t.start();


            if (window.isReset()) {
                board = new LifeBoard(size);
                window.reset();
                generation = 0;
            }

            if (window.isPaused()) {
                try {
                    Thread.sleep(1);
                    continue;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                GenerationAlgorithm.updateBoard(board);
                generation++;
            }


            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }

    }

}
