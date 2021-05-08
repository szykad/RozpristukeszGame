package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private SpaceShip spaceship;
    private List<Zerg> zergs;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 500;
    private final int B_WIDTH = 1400;
    private final int B_HEIGHT = 1050;
    private final int DELAY = 20;
    private final int[][] pos = {

            {1400, 0},  {1200, 400},  {1800, 200}, {1450, 170},
            {1500, 100},  {1700, 140},  {1825, 200}, {1800, 170},
            {800, 800},  {2200, 700},  {1525, 200}, {1000, 150},
            {1950, 500},  {1100, 785},  {1000, 700}, {1200, 170},
            {1700, 400},  {1000, 450},  {1800, 200}, {1350, 180},
    };
    Image img = Toolkit.getDefaultToolkit().getImage("src/background.jpg");


    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());

        setFocusable(true);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initZergs();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initZergs() {

        zergs = new ArrayList<>();

        for (int[] p : pos) {
            zergs.add(new Zerg(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);

        if (ingame) {

            drawObjects(g);


        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(),
                        missile.getY(), this);
            }
        }

        for (Zerg meteor : zergs) {
            if (meteor.isVisible()) {
                g.drawImage(meteor.getImage(), meteor.getX(), meteor.getY(), this);
            }
        }

        g.setColor(Color.RED);
        g.drawString("ZERGS left: " + zergs.size(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "NIECH ŻYJE ZBRODNICZY REŻIM !";
        Font small = new Font("Helvetica", Font.BOLD, 75);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.CYAN);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateShip();

        updateMissiles();

        updateMeteors();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {

        if (spaceship.isVisible()) {

            spaceship.move();
        }
    }

    private void updateMissiles() {

        List<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateMeteors() {

        if (zergs.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < zergs.size(); i++) {

            Zerg a = zergs.get(i);

            if (a.isVisible()) {
                a.move();
            } else {
                zergs.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = spaceship.getBounds();

        for (Zerg meteor : zergs) {

            Rectangle r2 = meteor.getBounds();

            if (r3.intersects(r2)) {

                spaceship.setVisible(false);
                meteor.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Zerg meteor : zergs) {

                Rectangle r2 = meteor.getBounds();

                if (r1.intersects(r2)) {

                    m.setVisible(false);
                    meteor.setVisible(false);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
}