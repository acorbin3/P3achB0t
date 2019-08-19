package com.p3achb0t.widgetexplorer

import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame

class WidgetExplorerV2(title: String): JFrame() {

    init {
        println("Creating UI")
        createUI(title)
    }

    private fun createUI(title: String){
        setTitle(title)
        val closeBtn = JButton("Close")

        closeBtn.addActionListener { System.exit(0) }

        createLayout(closeBtn)
        defaultCloseOperation = DISPOSE_ON_CLOSE

        setSize(300,200)
        setLocationRelativeTo(null)
    }

    private fun createLayout(vararg arg: JComponent){
        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true

        gl.setHorizontalGroup(gl.createSequentialGroup()
            .addComponent(arg[0])
        )

        gl.setVerticalGroup(gl.createSequentialGroup()
            .addComponent(arg[0])
        )

        pack()
    }
}

fun createAndShowGUI(){
    val frame = WidgetExplorerV2("WidgetExplorer")

    frame.isVisible = true
}