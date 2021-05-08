package com.company;


public class Zerg extends Sprite {

    private final int INITIAL_X = (int) (Math.floor(Math.random() * (2800 - 1400)) + 1400);

    public Zerg(int x, int y) {
        super(x, y);

        initZergs();
    }

    private void initZergs() {
        int w = (int) (Math.floor(Math.random() * (4 - 1)) + 1);
        if (w == 1) {
            loadImage("src/Meteor.png");
            getImageDimensions();
        }
        else if (w == 2) {
            loadImage("src/Meteor2.png");
            getImageDimensions();
        }
        else if (w == 3) {
            loadImage("src/Meteor3.png");
            getImageDimensions();
        }
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
            int i = (int) (Math.floor(Math.random() * (900 - 0)) + 0);
            if (i % 35 == 0){
                y = i;
            }
        }

        x -= 10;
    }
}