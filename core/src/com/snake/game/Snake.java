package com.snake.game;

import com.badlogic.gdx.Gdx;

public class Snake {

    int x, y, direction;

    Snake (int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case 0:
                y ++;
                break;
            case 1:
                x ++;
                break;
            case 2:
                y --;
                break;
            case 3:
                x --;
                break;
        }
    }
}

class Head extends Snake {
    Head(int x, int y, int direction) {
        super(x, y, direction);
    }

    public void getMove() {
        // input WASD
        if (Gdx.input.isKeyPressed(51)) direction = 0;
        else if (Gdx.input.isKeyPressed(32)) direction = 1;
        else if (Gdx.input.isKeyPressed(47)) direction = 2;
        else if (Gdx.input.isKeyPressed(29)) direction = 3;

        // input arrows
        if (Gdx.input.isKeyPressed(19)) direction = 0;
        else if (Gdx.input.isKeyPressed(22)) direction = 1;
        else if (Gdx.input.isKeyPressed(20)) direction = 2;
        else if (Gdx.input.isKeyPressed(21)) direction = 3;
    }
}

