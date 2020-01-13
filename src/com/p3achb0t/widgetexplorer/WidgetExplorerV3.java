package com.p3achb0t.widgetexplorer;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.p3achb0t._runestar_interfaces.Client;
import com.p3achb0t._runestar_interfaces.Component;
import com.p3achb0t.api.Context;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WidgetExplorerV3 {
    private JTextField textField1;
    private JButton searchButton;
    private JTree tree1;
    private JEditorPane textArea1;
    JPanel widgetExplorerPanel;
    private JButton refreshButton;
    private Client client;

    private DefaultMutableTreeNode node = new DefaultMutableTreeNode("Widgets");
    private DefaultTreeModel treeModel = new DefaultTreeModel(node);

    public WidgetExplorerV3(Context ctx) {
        this.client = ctx.getClient();
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Refresh!");
                node.removeAllChildren();
                textField1.setText("");
                Component[][] components = ctx.getClient().getInterfaceComponents();
                DefaultMutableTreeNode currentParentNode = null;
                for (Integer parentID = 0; parentID < components.length; parentID++) {
                    if (components[parentID] != null) {
                        for (Integer childID = 0; childID < components[parentID].length; childID++) {
                            if (components[parentID][childID] != null) {
                                if (currentParentNode == null) {
                                    currentParentNode = new DefaultMutableTreeNode(parentID);
                                }

                                DefaultMutableTreeNode child = new DefaultMutableTreeNode(childID);
                                //Add Children, of Children ids
                                Component[] childChildren = components[parentID][childID].getChildren();
                                if (childChildren != null) {
//                                    System.out.println("Children children len: " + childChildren.length + "(" + parentID + "," + childID + ")");
                                    for (int i = 0; i < childChildren.length; i++) {
                                        child.add(new DefaultMutableTreeNode(i));
                                    }
                                }


                                currentParentNode.add(child);
                            }
                        }
                        if (currentParentNode != null) {
                            node.add(currentParentNode);
                            currentParentNode = null;
                        }
                    }
                }
                treeModel.reload();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Search!");
                doSearch(ctx);
            }
        });
        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                try {
                    System.out.println(e.getNewLeadSelectionPath().toString());
                    String index = e.getNewLeadSelectionPath().toString();
                    index = index.replace("[", "");
                    index = index.replace("]", "");
                    index = index.replace(" ", "");
                    System.out.println(index);
                    if (index.split(",").length > 2) {
                        Integer parentID = Integer.parseInt(index.split(",")[1]);
                        Integer childID = Integer.parseInt(index.split(",")[2]);
                        Component[][] components = ctx.getClient().getInterfaceComponents();
                        Component widget = components[parentID][childID];
                        if (index.split(",").length == 3) {
                            ctx.setSelectedWidget(widget);
                            String result = ctx.getWidgets().getWidgetDetails(widget, 0);
                            textArea1.removeAll();
                            textArea1.setText(result);
                            textArea1.setCaretPosition(0);
                        } else if (index.split(",").length == 4) {
                            // Children of children case
                            Integer childChildID = Integer.parseInt(index.split(",")[3]);
                            Component[] children = widget.getChildren();
                            if (children != null) {
                                Component childChild = children[childChildID];
                                ctx.setSelectedWidget(childChild);
                                String result = ctx.getWidgets().getWidgetDetails(childChild, childChildID);
                                textArea1.removeAll();
                                textArea1.setText(result);
                                textArea1.setCaretPosition(0);

                            }

                        }

                    }
                } catch (Exception ignored) {

                }

            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSearch(ctx);
            }
        });
    }

    private void doSearch(Context ctx) {
        node.removeAllChildren();
        String searchText = textField1.getText();
        // get all text, from widgets
        // loop over all widgets, if a child matches then add that to the node list
        Component[][] components = ctx.getClient().getInterfaceComponents();
        DefaultMutableTreeNode currentParentNode = null;
        for (Integer parentID = 0; parentID < components.length; parentID++) {
            if (components[parentID] != null) {
                for (Integer childID = 0; childID < components[parentID].length; childID++) {
                    if (components[parentID][childID] != null) {
                        String result = ctx.getWidgets().getWidgetDetails(components[parentID][childID], 0);
                        if (result.toLowerCase().contains(searchText.toLowerCase())) {
                            if (currentParentNode == null) {
                                currentParentNode = new DefaultMutableTreeNode(parentID);
                            }
                            DefaultMutableTreeNode child = new DefaultMutableTreeNode(childID);
                            //Add Children, of Children ids
                            Component[] childChildren = components[parentID][childID].getChildren();
                            if (childChildren != null) {
//                                System.out.println("Children children len: " + childChildren.length + "(" + parentID + "," + childID + ")");
                                for (int i = 0; i < childChildren.length; i++) {
                                    String childResult = ctx.getWidgets().getWidgetDetails(components[parentID][childID], i);
                                    if (childResult.toLowerCase().contains(searchText.toLowerCase())) {
                                        child.add(new DefaultMutableTreeNode(i));
                                    }
                                }
                            }

                            currentParentNode.add(child);
                        }
                    }
                }
                if (currentParentNode != null) {
                    node.add(currentParentNode);
                    currentParentNode = null;
                }
            }
        }

        treeModel.reload();
    }

    static public void createWidgetExplorer(Context ctx) {
        JFrame frame = new JFrame("WidgetExplorerV3");
        WidgetExplorerV3 widgetExplorerV3 = new WidgetExplorerV3(ctx);
        widgetExplorerV3.tree1.setModel(widgetExplorerV3.treeModel);
        frame.setContentPane(widgetExplorerV3.widgetExplorerPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        widgetExplorerPanel = new JPanel();
        widgetExplorerPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        searchButton = new JButton();
        searchButton.setText("Search");
        widgetExplorerPanel.add(searchButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(100, -1), 0, false));
        textField1 = new JTextField();
        textField1.setColumns(0);
        widgetExplorerPanel.add(textField1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        widgetExplorerPanel.add(scrollPane1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, new Dimension(120, -1), 0, false));
        tree1 = new JTree();
        tree1.setMaximumSize(new Dimension(100, 74));
        scrollPane1.setViewportView(tree1);
        final JScrollPane scrollPane2 = new JScrollPane();
        widgetExplorerPanel.add(scrollPane2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(350, -1), null, 0, false));
        textArea1 = new JEditorPane();
        scrollPane2.setViewportView(textArea1);
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        widgetExplorerPanel.add(refreshButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return widgetExplorerPanel;
    }

}


