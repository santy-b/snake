package com.brianfigueroa.games.snake;

public enum Panel {
    SCREEN_WIDTH(600),
    SCREEN_HEIGHT(600),
    UNIT_SIZE(25),
    GAME_UNIT((600 * 600) / 25),
    BODY_PARTS(6);

    public int value;
    Panel(int value) {
        this.value = value;
    }

}
