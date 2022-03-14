package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;

    private int dots;
    private Timer timer;

    private final int DOT_SIZE = 10;  // 400 * 400 = 160000 / 100 = 1600 Max no. of dot on frame
    private final int ALL_DOT = 1600;
    private final int x[] = new int[ALL_DOT];  //1600 Max no. of dot on frame
    private final int y[] = new int[ALL_DOT];  //1600 Max no. of dot on frame

    private final int RANDOM_POSITION = 39;   //max pos of apple (390,390)
    private int apple_x;
    private int apple_y;

    Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(400, 400));
        setFocusable(true);
        loadImage();
        initGame();
    }

    public void loadImage() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/head.png"));
        head = i3.getImage();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;  // 1st dot : 50  2nd dot : 50-10 =40  3rd : 50-20;
            y[i] = 50;
        }
        locateApple();
        timer = new Timer(140, this); //delay moving of snake
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = (r * DOT_SIZE);
        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = (r * DOT_SIZE);
    }

    public void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y)   //x[0] y[0] : head
        {
            dots++;  //collision of head and apple increase in len of snake inc in dot
            locateApple();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0)  //at 0 pos is head in red
                {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
           gameOver(g);
        }
    }

    public void gameOver(Graphics g){
        String msg ="GAME OVER";
        Font font =new Font("SAN_SERIF",Font.BOLD,16);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg,(400 - metrices.stringWidth(msg)) / 2,400 / 2);
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        if (y[0] >= 400) {
            inGame = false;
        }
        if (x[0] >= 400) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }

        if (inGame == false) {
            timer.stop();
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];     //x[2] = x[1];
            y[i] = y[i - 1];    //y[2] = y[1];

        }
        if (leftDirection) {               //only change in x direc
            x[0] -= DOT_SIZE;            //suppose 240 - 10 = 230
        }
        if (rightDirection) {             //only change in x direc
            x[0] += DOT_SIZE;          //suppose 240 + 10 = 250
        }
        if (upDirection) {               //only change in y direc
            y[0] -= DOT_SIZE;            //suppose 240 - 10 = 230
        }
        if (downDirection) {             //only change in y direc
            y[0] += DOT_SIZE;          //suppose 240 + 10 = 250
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();  //which ever key is pressed its reference will get store in key
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                leftDirection = false;
                downDirection = true;
                rightDirection = false;
            }
        }
    }
}
