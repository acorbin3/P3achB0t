package com.p3achb0t.client.ui.components

import java.awt.Color
import java.awt.Dimension
import javax.swing.border.EtchedBorder
import java.awt.event.MouseListener
import javax.swing.JButton
import javax.swing.border.EmptyBorder
import javax.swing.JLabel
import java.awt.FlowLayout
import java.awt.event.MouseEvent
import javax.swing.JPanel


class NewTab(val tabs: TabManager) : JPanel() {

    init {
        focusTraversalKeysEnabled = true
        layout = FlowLayout(FlowLayout.LEFT, 0, 0)
        border = EmptyBorder(5, 2, 2, 2)
        isOpaque = false
        addLabel()
        add(CustomButton(tabs,"x"))
    }

    private fun addLabel() {
        val label = JLabel("Game ${tabs.tabCount}")
        /** add more space between the label and the button  */
        label.border = EmptyBorder(0, 0, 0, 10)
        add(label)
    }


}

class CustomButton(val tabs: TabManager, text: String) : JButton(), MouseListener {
    init {
        val size = 15
        setText(text)
        /** set size for button close  */
        preferredSize = Dimension(size, size)

        toolTipText = "close the Tab"

        /** set transparent  */
        isContentAreaFilled = false

        /** set border for button  */
        border = EtchedBorder()
        /** don't show border  */
        isBorderPainted = false

        isFocusable = false

        /** add event with mouse  */
        addMouseListener(this)

    }

    /** when click button, tab will close  */
    override fun mouseClicked(e: MouseEvent) {
        val index = tabs.selectedIndex

        tabs.clients.removeAt(index)

        if (index != -1) {
            tabs.removeTabAt(index)
        }

    }

    override fun mousePressed(e: MouseEvent) {}

    override fun mouseReleased(e: MouseEvent) {}

    /** show border button when mouse hover  */
    override fun mouseEntered(e: MouseEvent) {
        isBorderPainted = true
        foreground = Color.RED
    }

    /** hide border when mouse not hover  */
    override fun mouseExited(e: MouseEvent) {
        isBorderPainted = false
        foreground = Color.BLACK
    }
}