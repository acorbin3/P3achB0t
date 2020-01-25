package com.p3achb0t.widgetexplorer;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.p3achb0t._runestar_interfaces.Client;
import com.p3achb0t._runestar_interfaces.Component;
import com.p3achb0t.api.Context;
import com.p3achb0t.api.wrappers.widgets.Widget;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

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
                refresh(ctx);
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
                    index = index.replace("(", "");
                    index = index.replace(")", "");
                    System.out.println(index);
                    String[] splitItem = index.split(",");
                    if (splitItem.length > 2) {
                        //Get the last 2 items
                        // test for .child
                        if (!splitItem[splitItem.length - 1].contains(".")) {
                            Integer parentID = Integer.parseInt(splitItem[splitItem.length - 2]);
                            Integer childID = Integer.parseInt(splitItem[splitItem.length - 1]);
                            Component[][] components = ctx.getClient().getInterfaceComponents();
                            Component widget = components[parentID][childID];
                            ctx.setSelectedWidget(widget);
                            String result = ctx.getWidgets().getWidgetDetails(widget, 0);
                            textArea1.removeAll();
                            textArea1.setText(result);
                            textArea1.setCaretPosition(0);
                        } else {
                            // handle children of children
                            if (splitItem[splitItem.length - 1].contains(".")) {
                                System.out.println("Found child: " + splitItem[splitItem.length - 1]);
                            }
                            String getChildIndex = splitItem[splitItem.length - 1];
                            String[] splitChild = getChildIndex.split("\\.");
                            Integer parentID = Integer.parseInt(splitItem[splitItem.length - 2]);
                            Integer childID = Integer.parseInt(splitChild[0]);
                            Integer childChildID = Integer.parseInt(splitChild[1]);
                            System.out.println("child id " + childChildID);
                            Component[][] components = ctx.getClient().getInterfaceComponents();
                            Component widget = components[parentID][childID];
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
                    ignored.printStackTrace();
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

    //TODO update to have correct parent child relationship
    //Key would be parent ID, the obect would be a node where the child would be added
    // Get component, look to see if it has a parent, if so, add it to the parent, otherwise its a parent not and just add it
    private void refresh(Context ctx) {
        node.removeAllChildren();
        textField1.setText("");
        HashMap<Widget.WidgetIndex, DefaultMutableTreeNode> treeNodeHashMap = new HashMap();
        SortedMap<Widget.WidgetIndex, DefaultMutableTreeNode> rootNodes = new TreeMap<>((a1, a2) -> Integer.parseInt(a1.getChildID()) - Integer.parseInt(a2.getChildID()));
        Component[][] components = ctx.getClient().getInterfaceComponents();
        DefaultMutableTreeNode currentParentNode = null;
        for (Integer parentID = 0; parentID < components.length; parentID++) {
            if (components[parentID] != null) {
                for (Integer childID = 0; childID < components[parentID].length; childID++) {
                    if (components[parentID][childID] != null) {
                        Widget.WidgetIndex currentIndex = new Widget.WidgetIndex(parentID.toString(), childID.toString(), 0);
                        Widget.WidgetIndex parentIndex = Widget.Companion.getParentIndex(components[parentID][childID], ctx);

                        //Dive into the tree if we still have a parent
                        while (!parentIndex.getParentID().equals("-1")) {
                            DefaultMutableTreeNode curNode = null;
                            if (!treeNodeHashMap.containsKey(currentIndex)) {
                                curNode = new DefaultMutableTreeNode(currentIndex.toString());
                                treeNodeHashMap.put(currentIndex, curNode);
                            } else {
                                curNode = treeNodeHashMap.get(currentIndex);
                            }
                            System.out.println("(" + currentIndex.getParentID() + "," + currentIndex.getChildID() + " -> " + "(" + parentIndex.getParentID() + "," + parentIndex.getChildID() + ")");
                            //Check to see if parent exits, if not then create a new item and add currnt index to the new parent
                            DefaultMutableTreeNode parentNode = null;
                            if (!treeNodeHashMap.containsKey(parentIndex)) {
                                parentNode = new DefaultMutableTreeNode(parentIndex.toString());
                                treeNodeHashMap.put(parentIndex, parentNode);
                            } else {
                                parentNode = treeNodeHashMap.get(parentIndex);
                            }
                            parentNode.add(curNode);

                            //Get next parent index
                            currentIndex = parentIndex;
                            parentIndex = Widget.Companion.getParentIndex(components[Integer.parseInt(currentIndex.getParentID())][Integer.parseInt(currentIndex.getChildID())], ctx);
                            if (parentIndex.getParentID().equals("-1") && !rootNodes.containsKey(currentIndex)) {
                                rootNodes.put(currentIndex, parentNode);
                                System.out.println("Adding root(" + currentIndex.getParentID() + "," + currentIndex.getChildID() + " -> " + "(" + parentIndex.getParentID() + "," + parentIndex.getChildID() + ")");
                            }
                        }

                        //Add Children, of Children ids
                        Component[] childChildren = components[parentID][childID].getChildren();
                        if (childChildren != null) {
                            System.out.println("Children children len: " + childChildren.length + "(" + parentID + "," + childID + ")");
                            parentIndex = new Widget.WidgetIndex(parentID.toString(), childID.toString(), 0);
                            DefaultMutableTreeNode parentNode = treeNodeHashMap.get(parentIndex);
                            for (int i = 0; i < childChildren.length; i++) {
                                parentNode.add(new DefaultMutableTreeNode(parentIndex.toString() + "." + i));
                            }
                        }
                    }
                }
            }
        }
        // sort the rootNode set
        for (DefaultMutableTreeNode rootNode : rootNodes.values()) {
            if (rootNode != null) {
                node.add(rootNode);
            }
        }
        treeModel.reload();
    }

    private void doSearch(Context ctx) {
        node.removeAllChildren();
        String searchText = textField1.getText();
        if (searchText.isEmpty()) {
            refresh(ctx);
            return;
        }
        // get all text, from widgets
        // loop over all widgets, if a child matches then add that to the node list
        Component[][] components = ctx.getClient().getInterfaceComponents();
        DefaultMutableTreeNode currentParentNode = null;
        for (Integer parentID = 0; parentID < components.length; parentID++) {
            if (components[parentID] != null) {
//                System.out.println("Parent:" + parentID);
                for (Integer childID = 0; childID < components[parentID].length; childID++) {
                    if (components[parentID][childID] != null) {
//                        System.out.println("Child:" + childID);
                        //Check to see if and component has the string, if so at least add the parent
                        String result = ctx.getWidgets().getWidgetDetails(components[parentID][childID], 0);
                        if (result.toLowerCase().contains(searchText.toLowerCase())) {
                            if (currentParentNode == null) {
                                currentParentNode = new DefaultMutableTreeNode(parentID);
                            }

                            DefaultMutableTreeNode child = new DefaultMutableTreeNode(childID);
                            //Add Children, of Children ids if the string matches
                            Component[] childChildren = components[parentID][childID].getChildren();
                            if (childChildren != null) {
//                                    System.out.println("Children children len: " + childChildren.length + "(" + parentID + "," + childID + ")");

                                for (int i = 0; i < childChildren.length; i++) {
                                    String result2 = ctx.getWidgets().getWidgetDetails(childChildren[i], 0);
                                    if (result2.toLowerCase().contains(searchText.toLowerCase())) {
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
        widgetExplorerPanel.add(scrollPane1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tree1 = new JTree();
        tree1.setMaximumSize(new Dimension(150, 74));
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


