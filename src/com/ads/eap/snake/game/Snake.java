package com.ads.eap.snake.game;

public class Snake {
    public int X, Y = 0;
    public int L = 0;
    public int[] tailX, tailY;

    public Snake(int W, int H) {
        X = W/2;
        Y = H/2;
        tailX = new int[W*H];
        tailY = new int[W*H];
    }

    public void eatFood() {
        this.L += 1;
    }
}
