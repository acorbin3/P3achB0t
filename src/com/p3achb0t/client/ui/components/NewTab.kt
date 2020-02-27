package com.p3achb0t.client.ui.components

import com.p3achb0t.client.util.JarLoader
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder
import javax.swing.border.EtchedBorder


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
        if(JarLoader.proxy.length > 9) {
            val label = JLabel("${JarLoader.proxy.substring(7)}")
            label.border = EmptyBorder(0, 0, 0, 10)
            add(label)
        }
        if(JarLoader.proxy.length <= 9) {
            val label = JLabel("None")
            label.border = EmptyBorder(0, 0, 0, 10)
            add(label)
        }
        /** add more space between the label and the button  */

    }


}

class CustomButton(val tabs: TabManager, text: String) : JButton(), MouseListener {
    init {
        val size = 15
        focusTraversalKeysEnabled = true
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
        //TODO this can close the wrong tab if the tab is not selected
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