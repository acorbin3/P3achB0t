/*
 * Created by JFormDesigner on Tue Mar 31 19:05:36 NZDT 2020
 */

package com.p3achb0t.scripts.scripts_loader;

import com.p3achb0t.client.configs.GlobalStructs;
import com.p3achb0t.client.scripts.loading.LoadScripts;
import com.p3achb0t.client.scripts.loading.ScriptInformation;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class ScriptLoaderUI extends JFrame {
    JButton startScriptButton;
    public ScriptLoaderUI(JButton button) {
        initComponents();
        this.startScriptButton = button;
    }

    private void searchTextFieldKeyTyped(KeyEvent e) {
        if(searchTextField.getText().length() == 0){
            sorter.setRowFilter(null);
            return;
        }

        //doesn't work :(
        sorter.setRowFilter(RowFilter.regexFilter(searchTextLabel.getText()));
    }

    private void startButtonActionPerformed(ActionEvent e) {
        if(scriptsTableView.getSelectedRow() == -1) return;
        //Could hash this
        String scriptName = (String)tableModel.getValueAt(scriptsTableView.getSelectedRow(),0);
        String author = (String)tableModel.getValueAt(scriptsTableView.getSelectedRow(),1);
        String version = (String)tableModel.getValueAt(scriptsTableView.getSelectedRow(),3);
        String scriptKey = scriptsIdentifierMap.get(scriptName+author+version);
        GlobalStructs.Companion.getBotTabBar().getCurrentIndex().getInstanceManager().addAbstractScript(scriptKey);
        GlobalStructs.Companion.getBotTabBar().getCurrentIndex().getInstanceManager().startScript();
        scriptsIdentifierMap.clear();
        if(GlobalStructs.Companion.getBotTabBar().getCurrentIndex().getInstanceManager().isScriptRunning()){
            startScriptButton.setEnabled(false);
        }
        DisposeAndRemoveReferences();
    }

    public void DisposeAndRemoveReferences(){
        startScriptButton.removeActionListener(this::startButtonActionPerformed);
        searchTextField.removeKeyListener(keyTypedListener);
        this.dispose();
    }

    TableRowSorter<TableModel> sorter;
    HashMap<String,String> scriptsIdentifierMap = new HashMap<>();
    KeyListener keyTypedListener;
    private void initComponents() {
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return  false;
            }
        };

        this.setResizable(false);
        addColumns();
        LoadScripts();


        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        scrollPane1 = new JScrollPane();
        scriptsTableView = new JTable(tableModel);
        sorter = new TableRowSorter<TableModel>(tableModel);
        scriptsTableView.setRowSorter(sorter);

        searchTextLabel = new JLabel();
        searchTextField = new JTextField();
        startButton = new JButton();
        localScriptsCheckBox = new JCheckBox();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {

            //---- scriptsTableView ----
            scriptsTableView.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            scrollPane1.setViewportView(scriptsTableView);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 820, 315);

        //---- searchTextLabel ----
        searchTextLabel.setText("Search: ");
        contentPane.add(searchTextLabel);
        searchTextLabel.setBounds(5, 320, 60, 35);

        //---- searchTextField ----
        keyTypedListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                searchTextFieldKeyTyped(e);
            }
        };

        searchTextField.addKeyListener(keyTypedListener);
        contentPane.add(searchTextField);
        searchTextField.setBounds(53, 328, 225, searchTextField.getPreferredSize().height);

        //---- startButton ----
        startButton.setText("Start");
        startButton.addActionListener(e -> startButtonActionPerformed(e));
        contentPane.add(startButton);
        startButton.setBounds(0, 361, 820, startButton.getPreferredSize().height);

        //---- localScriptsCheckBox ----
        localScriptsCheckBox.setText("Local scripts only");
        contentPane.add(localScriptsCheckBox);
        localScriptsCheckBox.setBounds(296, 329, 190, localScriptsCheckBox.getPreferredSize().height);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void LoadScripts() {
       LoadScripts scripts = GlobalStructs.Companion.getScripts();
       if(scripts != null){
           for (String key : scripts.getScripts().keySet()) {
               ScriptInformation script = scripts.getScripts().get(key);
               if(script != null && script.getCategory().equals("Abstract")){
                   addRow(script.getName(),script.getAuthor(),script.getCategory(),script.getVersion());
                   scriptsIdentifierMap.put(script.getName() + script.getAuthor() + script.getVersion(),script.getFileName());
               }
           }
       }
    }

    private void customTableViewRenderer(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scriptsTableView.setDefaultRenderer(Object.class,centerRenderer);
    }

    private void addTestRows(){
        addRow("Goblin killer", "Rando", "COMBAT",1);
        addRow("Tut Island", "Rando", "QUESTING",2.2f);
    }

    private void addRow(String scriptName, String author, String category, float version){
        addRow(scriptName,author,category,version + "");
    }

    private void addRow(String scriptName, String author, String category, String version){
        tableModel.addRow(new Object[]{scriptName,author,category,version});
    }

    private void addColumns(){
        String[] columns = {
                "Name",
                "Author",
                "Category",
                "Version"
        };
        for(int i=0;i<columns.length;++i){
            addColumn(columns[i]);
        }
    }

    private void addColumn(String text){
        tableModel.addColumn(text);
    }

    private DefaultTableModel tableModel;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JScrollPane scrollPane1;
    private JTable scriptsTableView;
    private JLabel searchTextLabel;
    private JTextField searchTextField;
    private JButton startButton;
    private JCheckBox localScriptsCheckBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
