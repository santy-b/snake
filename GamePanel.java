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
        int delay = 75;
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

            for (int i = 0; i < Panel.BODY_PARTS.value; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillOval(x[i], y[i], Panel.UNIT_SIZE.value, Panel.UNIT_SIZE.value);
            }
            gameText.score(g, applesEaten);
        } else {
            gameText.scoreAndGameOver(g, applesEaten);
        }
    }

    private void newApple() {
        appleX = random.nextInt((Panel.SCREEN_WIDTH.value / Panel.UNIT_SIZE.value)) * Panel.UNIT_SIZE.value;
        appleY = random.nextInt((Panel.SCREEN_HEIGHT.value / Panel.UNIT_SIZE.value)) * Panel.UNIT_SIZE.value;
    }

    private void move() {
        if (!paused) {
            for (int i = Panel.BODY_PARTS.value; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'U':
                    y[0] = y[0] - Panel.UNIT_SIZE.value;
                    break;
                case 'D':
                    y[0] = y[0] + Panel.UNIT_SIZE.value;
                    break;
                case 'L':
                    x[0] = x[0] - Panel.UNIT_SIZE.value;
                    break;
                case 'R':
                    x[0] = x[0] + Panel.UNIT_SIZE.value;
                    break;
                default:
                    // Handle the default case if necessary
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
        return checkBodyCollisions(Panel.BODY_PARTS.value) || checkBorderCollisions(Panel.SCREEN_WIDTH.value, Panel.SCREEN_HEIGHT.value);
    }

    private boolean checkBodyCollisions(int bodyParts) {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }
        return !running;
    }

    private boolean checkBorderCollisions(int width, int height) {
        if (x[0] < 0 || x[0] > width || y[0] < 0 || y[0] > height) {
            running = false;
        }
        return !running;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            if (!paused) {
                move();
                checkApple();
                checkCollisions();
            }
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            var key = e.getKeyCode();

            switch(key) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        direction = 'L';
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        direction = 'R';
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        direction = 'U';
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        direction = 'D';
                    break;

                case KeyEvent.VK_P:
                    paused = true;
                    break;

                case KeyEvent.VK_S:
                    paused = false;
                    break;

                case KeyEvent.VK_R:
                    if (!running) {
                        new GameFrame();
                    }
                    break;
            }
        }
    }

}
