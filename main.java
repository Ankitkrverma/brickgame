package brickgame;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        Gameplay gameplay = new Gameplay();
        JFrame frame = new JFrame();
        frame.setBounds(10,10,710,600);
        frame.setTitle("BREAK BALL");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.add(gameplay);
    }
}
