package com.brianfigueroa.games.snake;


import javax.swing.*;

public class GameFrame extends JFrame {
    public static void main(String[] args) {
        new GameFrame();
    }
    public GameFrame() {

        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

