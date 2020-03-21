package com.ads.eap.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.io.IOException;
import com.ads.eap.snake.game.Snake;
import com.ads.eap.snake.game.Map;
import com.ads.eap.snake.game.Food;

public class Main extends JFrame {

    static char dir = '0';
    static char Predir = '0';
    static int score = 0;
    static boolean GameOver = false;
    static int Width = 20;
    static int Height = 16;
    static int Speed = 0;

    public static void main(String... arg) throws IOException, InterruptedException {
        Snake S = new Snake(Width, Height);
        Map M = new Map(Width, Height);
        Food F = new Food(Width, Height);

        while (!GameOver) {
            CleanScreen();
            Update(S, F, M, dir, Width, Height);
            Draw(S, M, F, GameOver);
            Speed = UpdateSpeed(score);
        }

    }

    public static int UpdateSpeed(int score) throws InterruptedException {
        if ((270 - score) > 100) {
            Thread.sleep(270 - score);
            return (int) (score / (1.7));
        } else {
            Thread.sleep(100);
            return 100;
        }
    }

    public static void Update(Snake s, Food f, Map m, char dir, int W, int H) {
        if (s.L > 0) {
            for (int i = s.L - 1; i >= 1; i--) {
                s.tailX[i] = s.tailX[i - 1];
                s.tailY[i] = s.tailY[i - 1];
            }
            s.tailX[0] = s.X;
            s.tailY[0] = s.Y;
        }

        if(((dir =='s')&&(Predir == 'w'))||((dir =='w')&&(Predir == 's'))||((dir =='a')&&(Predir == 'd'))||((dir =='d')&&(Predir == 'a'))) {
            dir = Predir;
        }
        switch (dir) {
            case 'w':
                s.Y -= 1;
                break;
            case 's':
                s.Y += 1;
                break;
            case 'a':
                s.X -= 1;
                break;
            case 'd':
                s.X += 1;
                break;
        }
        Predir = dir;

        if ((s.X == 0) || (s.X == m.W + 1) || (s.Y == -1) || (s.Y == m.H)) {

            if (s.X == 0) {
                s.X = m.W;
            } else if (s.X == m.W + 1) {
                s.X = 1;
            } else if (s.Y == -1) {
                s.Y = m.H - 1;
            } else {
                s.Y = 0;
            }
        }

        for (int i = 0; i < s.L; i++) {
            if ((s.X == s.tailX[i]) && (s.Y == s.tailY[i])) {
                GameOver = true;
            }
        }

        if ((s.X == f.X) && (s.Y == f.Y)) {
            f.reset(W, H, s);
            s.eatFood();
            score += 10;
        }

    }

    public static void CleanScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();// 清除螢幕
    }

    public static void Draw(Snake s, Map m, Food f, boolean Gameover) {
        int W = m.W;
        int H = m.H;
        int Snake_x = s.X;
        int Snake_y = s.Y;
        int Food_x = f.X;
        int Food_y = f.Y;
        for (int i = 0; i < W + 2; i++)
            System.out.printf("#");
        System.out.printf("\n");

        if (!Gameover) {
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W + 2; j++) {
                    if (j == 0) {
                        System.out.printf("#");
                    } else if (j == W + 1) {
                        System.out.printf("#");
                    } else if ((i == Food_y) && (j == Food_x)) {
                        System.out.printf("F");
                    } else if ((i == Snake_y) && (j == Snake_x)) {
                        System.out.printf("Q");
                    } else if (s.L > 0) {
                        boolean is_tail = false;
                        for (int k = 0; k < s.L; k++) {
                            if ((s.tailX[k] == j) && (s.tailY[k] == i)) {
                                System.out.printf("O");
                                is_tail = true;
                                break;
                            }
                        }
                        if (!is_tail)
                            System.out.printf(" ");
                    } else {
                        System.out.printf(" ");
                    }
                }
                System.out.printf("\n");
            }
        } else {
            for (int i = 0; i < H; i++) {
                if (i != H / 2) {
                    for (int j = 0; j < W + 2; j++) {
                        if (j == 0) {
                            System.out.printf("#");
                        } else if (j == W + 1) {
                            System.out.printf("#");
                        } else {
                            System.out.printf(" ");
                        }
                    }
                    System.out.printf("\n");
                } else {

                    System.out.printf("#");
                    for (int j = 0; j < (W - 10) / 2; j++) {
                        System.out.printf(" ");
                    }
                    System.out.printf("GAME OVER!");
                    for (int j = 0; j < (W - 10) / 2; j++) {
                        System.out.printf(" ");
                    }
                    if (W % 2 != 0)
                        System.out.printf(" ");
                    System.out.printf("#\n");
                }
            }

        }

        for (int i = 0; i < W + 2; i++)
            System.out.printf("#");
        System.out.printf("\n");
        System.out.println("Score : " + score);
        System.out.println("Speed : " + Speed + "%");
    }

    public Main() {
        this.setSize(250, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("LGPD Snake");
        this.setVisible(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                dir = e.getKeyChar();
            }
        });
    }
}

