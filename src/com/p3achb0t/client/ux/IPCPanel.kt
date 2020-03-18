package com.p3achb0t.client.ux

import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import com.p3achb0t.api.interfaces.ChannelInterface
import com.p3achb0t.client.communication.ipc.Broker
import java.awt.Dimension
import java.awt.Insets
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel


class IpcHelper(val broker: Broker) : JFrame() {

    var ipcHelperPanel: JPanel = JPanel()
    val channelsTree = JTree()
    val scrollPane1 = JScrollPane()
    val scrollPane2 = JScrollPane()
    val textInputField = JTextField()
    val sendButton = JButton()
    val refreshButton = JButton()

    private val channelsView = DefaultMutableTreeNode("IPC Channels")
    private val treeModel = DefaultTreeModel(channelsView)
    private lateinit var channel: ChannelInterface
    var l1 = DefaultListModel<String>()
    val channelMessages = JList(l1)

    private val channels = HashMap<String, ChannelInterface>()


    init {



        channelsTree.addTreeSelectionListener {

            if (it.paths.size > 1) {

                println("${it.paths[0].getPathComponent(1)}")
                broker.subscribeChannel(it.paths[0].getPathComponent(1).toString(), ::channelCallback, "IPC Helper", ::receiveCallback)

            }
        }


        refreshButton.addActionListener {
            channelsView.removeAllChildren()
            println("refresh button")
            fill()
            treeModel.reload()
        }

        sendButton.addActionListener {
            channel.notifySubscribers(textInputField.text)

        }



        setup()
        fill()





        channelsTree.model = treeModel
        add(ipcHelperPanel)
        pack()
        isVisible = true
    }

    private fun channelCallback(channelId: String, channel: ChannelInterface) {
        println("subbed $channel")
        this.channel = channel
        channels[channelId] = channel
    }

    private fun receiveCallback(id: String, message: String) {
        println("ROOM: $id, $message")
        //channelMessages.set
        l1.addElement(message)
    }

    private fun fill() {
        for ((key, value) in broker.channels.entries) {
            val entry = DefaultMutableTreeNode(key)
            println("$key ${value.observers.keys.size}")
            for (key1 in value.observers.keys()) {
                println("$key1")
                entry.add(DefaultMutableTreeNode(key1))
            }
            channelsView.add(entry)
        }
    }

    fun setup() {
        ipcHelperPanel = JPanel()
        ipcHelperPanel.layout = GridLayoutManager(2, 3, Insets(0, 0, 0, 0), -1, -1)
        ipcHelperPanel.minimumSize = Dimension(500, 500)
        ipcHelperPanel.preferredSize = Dimension(500, 500)

        ipcHelperPanel.add(scrollPane1, GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false))

        scrollPane1.setViewportView(channelsTree)

        ipcHelperPanel.add(scrollPane2, GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false))

        scrollPane2.setViewportView(channelMessages)

        ipcHelperPanel.add(textInputField, GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, Dimension(150, -1), null, 0, false))

        sendButton.text = "Send"
        ipcHelperPanel.add(sendButton, GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false))

        refreshButton.text = "Refresh"
        ipcHelperPanel.add(refreshButton, GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false))
    }
}