package com.p3achb0t.widgetexplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WidgetExplorerV3 {
    private JTextField textField1;
    private JButton searchButton;
    private JTree tree1;
    private JTextArea textArea1;
    JPanel widgetExplorerPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("WidgetExplorerV3");
        frame.setContentPane(new WidgetExplorerV3().widgetExplorerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public WidgetExplorerV3() {
//        searchButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button pressed");
//            }
//        });
    }

    public static void createWidgetExplorer() {
        JFrame frame = new JFrame("WidgetExplorerV3");
        frame.setContentPane(new WidgetExplorerV3().widgetExplorerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}


