package com.company;

import java.awt.EventQueue;
import javax.swing.JFrame;

    public class Rozpristukesz extends JFrame {

        public Rozpristukesz() {

            initUI();

        }

        private void initUI() {

            add(new Board());

            setResizable(false);

            pack();

            setTitle("Rozpristukesz");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public static void main(String[] args) {

            EventQueue.invokeLater(() -> {
                Rozpristukesz ex = new Rozpristukesz();
                ex.setVisible(true);
            });
        }
    }