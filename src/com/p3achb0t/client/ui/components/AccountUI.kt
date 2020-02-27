package com.p3achb0t.client.ui.components

import com.p3achb0t.client.managers.accounts.AccountManager
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTable


class AccountUI(val accountManager: AccountManager): JFrame() {

    init {
        setup()
    }

    private fun setup() {
        val columns = arrayOf("ID", "Username" ,"Password", "Pin", "Script", "minRuntime", "maxRuntime","useBreaks",
                "minBreaktime", "maxBreakTime", "banned")

        val list = arrayOfNulls<Array<Any>>(accountManager.accounts.size)
        accountManager.accounts.forEachIndexed { index, account ->
            list[index]=(arrayOf(account.id,account.username,account.password, account.pin, account.script, account.minRuntime, account.maxRuntime, account.userBreaks,
                    account.minBreakTime, account.maxBreakTime, account.banned))
        }

        //actual data for the table in a 2d array
        val data = arrayOf(arrayOf("John", "placeholder", 4567, "Good", 324), arrayOf("sdfsdf", "placeholder", 4567, "Good", 324),arrayOf("Jdsfdsf", "placeholder", 4567, "Good", 324))
        //create table with data
        val table = JTable(list, columns)

        //add the table to the frame
        this.add(JScrollPane(table))

        this.title = "Account Manager"
        //this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.pack()
        this.isVisible = true
    }

    private fun addContent() {
        val button = JButton("new")
        button.setBounds(30, 30, 70, 40)
        add(button)
    }
}
