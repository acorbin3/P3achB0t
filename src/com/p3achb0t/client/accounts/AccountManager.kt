package com.p3achb0t.client.accounts

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.util.Util
import java.io.File
import java.util.*

object AccountManager {

    lateinit var accounts: List<Account>

    var accountsJsonFileName = "./" + Constants.APPLICATION_CACHE_DIR + "/" + Constants.ACCOUNTS_DIR + "/" + Constants.ACCOUNTS_FILE

    init {

    }

    fun loadAccounts()  {
        val content = Util.readConfig(accountsJsonFileName)
        if(content.isEmpty()){
            println("ERROR: couldnt find accounts file: $accountsJsonFileName")
            return
        }
        val gson = Gson()
        val accounts: MutableList<Account> = gson.fromJson(content, object : TypeToken<List<Account>>() {}.type)

        //Check to see if we need to add a UUID for the account ID
        var updatedID = false
        accounts.forEach {
            if(it.uuid == ""){
                it.uuid = UUID.randomUUID().toString()
                updatedID = true
            }
        }
        if(updatedID){
            val file = File(accountsJsonFileName)
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            val jsonAccountPretty = gsonPretty.toJson(accounts)
            file.writeText(jsonAccountPretty)
        }

        AccountManager.accounts = gson.fromJson(content, object : TypeToken<List<Account>>() {}.type)
        for (r in AccountManager.accounts) {
            println(r)
        }
    }

    fun createAccount() {
        val account = Account()
        account.uuid = UUID.randomUUID().toString()
    }
}