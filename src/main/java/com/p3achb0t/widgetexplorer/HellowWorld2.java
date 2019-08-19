package com.p3achb0t.widgetexplorer;

import javax.swing.*;

public class HellowWorld2 {
    private JButton helloButton;
    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("HellowWorld2");
        frame.setContentPane(new HellowWorld2().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
