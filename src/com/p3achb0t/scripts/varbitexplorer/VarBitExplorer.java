package com.p3achb0t.scripts.varbitexplorer;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.p3achb0t.api.Context;
import com.p3achb0t.api.wrappers.VarbitData;
import org.runestar.cache.content.config.VarBitType;
import org.runestar.cache.format.Cache;
import org.runestar.cache.format.disk.DiskCache;
import org.runestar.cache.format.net.NetCache;
import org.runestar.cache.tools.MemCache;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VarBitExplorer {
    private JPanel panel1;
    private JEditorPane editorPane1;
    private JTextField varBitEntry;
    private JButton getVarbitButton;
    private JLabel varBitValueLable;
    private Context ctx;
    private VarBitRunnable varBitMonitoringThread;

    public class VarBitRunnable implements Runnable {

        private boolean doStop = false;

        public synchronized void doStop() {
            this.doStop = true;
        }

        private synchronized boolean keepRunning() {
            return !this.doStop;
        }

        @Override
        public void run() {

            // Update Cache
            // get the varbit data
            // look into getting the info from the VarPs
            //TODO - look at the VARPs as well
            try (var net = NetCache.connect(new InetSocketAddress("oldschool7.runescape.com", NetCache.DEFAULT_PORT), 187);
                 var disk = DiskCache.open(Path.of(".cache"))) {
                System.out.println("Updating Cache");
                Cache.update(net, disk).join();
                System.out.println("Complete: Cache updated");
            } catch (IOException e) {
                e.printStackTrace();
            }


            var varBitDataList = new ArrayList<VarbitData>();

            try (var disk = DiskCache.open(Path.of(".cache"))) {
                System.out.println("Getting VarBit info");
                MemCache cache = MemCache.of(disk);
                for (var file : cache.archive(VarBitType.ARCHIVE).group(VarBitType.GROUP).files()) {
                    var varbit = new VarBitType();
                    varbit.decode(file.data());
                    varBitDataList.add(new VarbitData(file.id(), varbit.startBit, varbit.endBit , varbit.baseVar));

                }
                System.out.println("Complete: getting varbit info");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<Integer, Integer> varBitValues = new HashMap<>();
            //Get initial values
            for (int i = 0; i < varBitDataList.size(); i++) {
                varBitValues.put(i, 0);
            }
            int counter = 0;
            while (keepRunning()) {
                try {
                    System.out.println("Counter: " + counter);
                    counter += 1;
                    for (VarbitData varBitDataItem : varBitDataList) {
                        int i = varBitDataItem.getId();
                        int oldVarBitVal = varBitValues.get(i);
                        int varp = ctx.getVars().getVarp(varBitDataItem.getBaseVar());
                        int newVarBitVal = varBitDataItem.getVarbitValue(varp);
                        if (oldVarBitVal != newVarBitVal) {
                            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                            varBitValues.replace(i, newVarBitVal);
                            String newLine = timeStamp + " (" + i + ")\t" + oldVarBitVal + "\t->\t" + newVarBitVal + "\n";
                            SimpleAttributeSet attributes = new SimpleAttributeSet();
                            try {
                                editorPane1.getDocument().insertString(editorPane1.getDocument().getLength(), newLine, attributes);
                                editorPane1.setCaretPosition(editorPane1.getDocument().getLength());
                            } catch (BadLocationException e) {
                                System.out.println(i);
                                e.printStackTrace();
                            }

                        }
                    }

                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public VarBitExplorer(Context ctx) {
        JFrame frame = new JFrame("VarBitExplorer");
        createUIComponents();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                varBitMonitoringThread.doStop();
                super.windowClosing(e);
            }
        });
        this.ctx = ctx;
        $$$setupUI$$$();
        getVarbitButton.addActionListener(e -> {
            int varBit = Integer.parseInt(varBitEntry.getText());
            int varBitResult = ctx.getClient().getVarbit(varBit);
            varBitValueLable.setText(Integer.toString(varBitResult));
        });

        varBitMonitoringThread = new VarBitRunnable();
        new Thread(varBitMonitoringThread).start();
        System.out.println("Continueing ");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VarBitExplorer");
        frame.setContentPane(new VarBitExplorer(new Context(1)).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        panel1 = new JPanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBorder(BorderFactory.createTitledBorder("VarBit Log"));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        editorPane1 = new JEditorPane();
        scrollPane1.setViewportView(editorPane1);
        varBitEntry = new JTextField();
        panel1.add(varBitEntry, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("VarBit ID");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(159, 16), null, 0, false));
        getVarbitButton = new JButton();
        getVarbitButton.setText("Get Varbit");
        panel1.add(getVarbitButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(159, 30), null, 0, false));
        varBitValueLable = new JLabel();
        varBitValueLable.setText("Value");
        panel1.add(varBitValueLable, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
