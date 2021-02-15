package com.company;

import javax.swing.*;
import java.awt.*;

public class GameCell extends JPanel {

    private boolean alive;

    public GameCell() {
        super();
        setVisible(false);
        setBackground(Color.BLACK);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        setVisible(this.alive);
    }


    public boolean isAlive() {
        return alive;
    }




}
