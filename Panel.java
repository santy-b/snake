package com.brianfigueroa.games.snake;

public enum Panel {
    SCREEN_WIDTH(600),
    SCREEN_HEIGHT(600),
    UNIT_SIZE(25),
    GAME_UNIT((600 * 600) / 25),
    BODY_PARTS(6);

    public int value;

    private static final int SCREEN_WIDTH_VALUE = 600;
    private static final int SCREEN_HEIGHT_VALUE = 600;
    private static final int UNIT_SIZE_VALUE = 25;
    private static final int GAME_UNIT_VALUE = (SCREEN_WIDTH_VALUE * SCREEN_HEIGHT_VALUE) / UNIT_SIZE_VALUE;
    private static final int BODY_PARTS_VALUE = 6;

    Panel(int value) {
        this.value = value;
    }

    static {
        SCREEN_WIDTH.value = SCREEN_WIDTH_VALUE;
        SCREEN_HEIGHT.value = SCREEN_HEIGHT_VALUE;
        UNIT_SIZE.value = UNIT_SIZE_VALUE;
        GAME_UNIT.value = GAME_UNIT_VALUE;
        BODY_PARTS.value = BODY_PARTS_VALUE;
    }
}
