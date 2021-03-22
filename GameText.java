package com.brianfigueroa.games.snake;

import java.awt.*;


public class GameText {

    public void scoreAndGameOver(Graphics g, int applesEaten) {
        score(g, applesEaten);
        gameOver(g);
    }

    public void score(Graphics g, int applesEaten) {
        g.setColor(Color.red);
        g.setFont( new Font("", Font.BOLD, 40));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (Panel.SCREEN_WIDTH.value - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (Panel.SCREEN_WIDTH.value - metrics2.stringWidth("Game Over")) / 2, Panel.SCREEN_HEIGHT.value / 2 );
    }
}
