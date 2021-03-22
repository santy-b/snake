package com.brianfigueroa.games.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int[] x = new int[Panel.GAME_UNIT.value];
    private final int[] y = new int[Panel.GAME_UNIT.value];

    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running;
    private boolean paused;
    private Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(Panel.SCREEN_WIDTH.value, Panel.SCREEN_HEIGHT.value));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    private void startGame() {
            var delay = 75;
            newApple();
            Panel.BODY_PARTS.value = 6;
            running = true;
            paused = false;
            new Timer(delay, this).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        var gameText = new GameText();

        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, Panel.UNIT_SIZE.value, Panel.UNIT_SIZE.value);

            for (var i = 0; i < Panel.BODY_PARTS.value; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillOval(x[i], y[i], Panel.UNIT_SIZE.value, Panel.UNIT_SIZE.value);
            }
            gameText.score(g, applesEaten);
        }
        else {
            gameText.scoreAndGameOver(g, applesEaten);
        }

    }

    private void newApple() {
        appleX = random.nextInt((Panel.SCREEN_WIDTH.value / Panel.UNIT_SIZE.value)) * Panel.UNIT_SIZE.value;
        appleY = random.nextInt((Panel.SCREEN_HEIGHT.value / Panel.UNIT_SIZE.value)) * Panel.UNIT_SIZE.value;
    }

    private void move() {
        if (!paused) {
            for (var i = Panel.BODY_PARTS.value; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'U' -> y[0] = y[0] - Panel.UNIT_SIZE.value;
                case 'D' -> y[0] = y[0] + Panel.UNIT_SIZE.value;
                case 'L' -> x[0] = x[0] - Panel.UNIT_SIZE.value;
                case 'R' -> x[0] = x[0] + Panel.UNIT_SIZE.value;
            }
        }
    }

    private void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            Panel.BODY_PARTS.value++;
            applesEaten++;
            newApple();
        }

    }

    private boolean checkCollisions() {
        return checkBodyCollisions(Panel.BODY_PARTS.value) ||
                checkBorderCollisions(Panel.SCREEN_WIDTH.value, Panel.SCREEN_HEIGHT.value);
    }

    private boolean checkBodyCollisions(int bodyParts) {
        for(int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
            else
                running = true;
        }
        return !running;
    }

    private boolean checkBorderCollisions(int width, int height) {
        if (x[0] < 0 || x[0] > width)
            running = false;

        if (y[0] < 0 || y[0] > height)
            running = false;

        return !running;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            var key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && direction != 'R')
                direction = 'L';

            if (key == KeyEvent.VK_RIGHT && direction != 'L')
                direction = 'R';

            if (key == KeyEvent.VK_UP && direction != 'D')
                direction = 'U';

            if (key == KeyEvent.VK_DOWN && direction != 'U')
                direction = 'D';

            if (key == KeyEvent.VK_P)
                paused = true;

            if (key == KeyEvent.VK_S)
                paused = false;

            if (key == KeyEvent.VK_R && !running)

                new GameFrame();
        }
    }
}
