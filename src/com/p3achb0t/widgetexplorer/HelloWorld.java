package com.p3achb0t.widgetexplorer;

import javax.swing.*;

public class HelloWorld {
    private JPanel panel;
    private JButton closeButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWorld");
        frame.setContentPane(new HelloWorld().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
