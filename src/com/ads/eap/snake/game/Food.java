package com.ads.eap.snake.game;

public class Food {
    public int X, Y = 0;
    public boolean food = false;

    public Food(int W, int H) {
        this.X = (int) (Math.random() * W + 1);
        this.Y = (int) (Math.random() * H);
    }

    public void reset(int W, int H, Snake S) {
        this.X = (int) (Math.random() * W + 1);
        this.Y = (int) (Math.random() * H);

        if (this.X == S.X && this.Y == S.Y) {
            S.eatFood();
            reset(W, H, S);
        }

        if (S.L > 0) {
            for (int i = 0; i < S.L; i++) {
                if (this.X == S.tailX[i] && this.Y == S.tailY[i]) {
                    this.food = true;
                }
            }
            if (this.food) {
                S.eatFood();
                reset(W, H, S);
            }
        }
    }
}
