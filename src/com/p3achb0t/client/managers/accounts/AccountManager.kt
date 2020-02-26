package com.p3achb0t.client.managers.accounts

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.util.Util
import java.util.*

class AccountManager {

    var accounts: List<Account>

    init {
        accounts = loadAccounts()
        for (r in accounts) {
            println(r)
        }
    }

    fun loadAccounts() : MutableList<Account> {
        val content = Util.readConfig("./" + Constants.APPLICATION_CACHE_DIR + "/" + Constants.ACCOUNTS_DIR + "/" + Constants.ACCOUNTS_FILE )
        if(content.isEmpty()){
            return MutableList(0,{Account()})
        }
        val gson = Gson()
        return gson.fromJson(content, object : TypeToken<List<Account>>() {}.type)
    }

    fun createAccount() {
        val account = Account()
        account.id = UUID.randomUUID().toString()
    }
}