package com.p3achb0t.client.managers.tracker

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.p3achb0t.api.wrappers.Stats
import java.io.File
import java.io.FileInputStream
import java.sql.Timestamp
import java.util.*

class FBDataBase {
    private val db: Firestore
    private val userRef: CollectionReference
    private val userDocs: MutableMap<String, DocumentReference>
    private val userSkillOrItemCount: MutableMap<String, Int> = HashMap()

    init {
        val str: String = "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"
        val encryptor = AESEncryptor()
        val decryptedValue: String? =encryptor.decryptWithAES("662edef988y58fb6d077dfs9d85605e0", str)
        val temp = File("temp.json")
        temp.createNewFile()
        temp.writeText(decryptedValue!!)
        val serviceAccount = FileInputStream("temp.json")
        val credentials = GoogleCredentials.fromStream(serviceAccount)

        val options = FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build()
        FirebaseApp.initializeApp(options)
        temp.delete()

        db = FirestoreClient.getFirestore()
        userRef = db.collection("users")
        userDocs = HashMap()
    }

    data class StatInfo(val time: String, val xp: Int)
    data class ItemInfo(val time: String, val count: Int)

    private fun getDocID(accountID: String, base: String): String {
        val id = accountID + "_" + base
        if (id !in userSkillOrItemCount) {
            userSkillOrItemCount[id] = 1000
        } else {
            userSkillOrItemCount[id] = userSkillOrItemCount[id]!!.plus(1)
        }
        return userSkillOrItemCount[id].toString()
    }

    fun initalScriptLoad(accountID: String, sessionID: String, script: String){
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val lastUpdated = mutableMapOf<String,String>()
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        lastUpdated["startTime"] = date.toString()
        lastUpdated["script"] = script
        userDocs[accountID]?.set(lastUpdated  as Map<String, Any>)
    }
    fun setLastUpdated(accountID: String, sessionID: String){
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val lastUpdated = mutableMapOf<String,String>()
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        lastUpdated["lastUpdateTime"] = date.toString()
        userDocs[accountID]?.set(lastUpdated  as Map<String, Any>)
    }
    fun updateStat(accountID: String, sessionID: String, skill: Stats.Skill, xp: Int) {
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val messageID = getDocID(accountID, skill.name)
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        val lastUpdated = mutableMapOf<String,String>()
        lastUpdated["lastUpdated"] = date.toString()
        userDocs[accountID]?.set(lastUpdated as Map<String, Any>)
        userDocs[accountID]?.collection("Skills")?.document(skill.name)?.collection("xp")
                ?.document(messageID)?.set(StatInfo(date.toString(),xp))

    }

    fun updateItemCount(accountID: String, sessionID: String, itemName: String, count: Int) {
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }

        val messageID = getDocID(accountID, itemName)
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        val lastUpdated = mutableMapOf<String,String>()
        lastUpdated["lastUpdated"] = date.toString()
        userDocs[accountID]?.set(lastUpdated as Map<String, Any>)
        userDocs[accountID]?.collection("Items")?.document(itemName)?.collection("entry")
                ?.document(messageID)?.set(ItemInfo(date.toString(),count))
    }

    fun validateScript(scriptName: String, key: String):Boolean {
        try {

            val validationDoc = db.collection("validation").document(scriptName).get().get()

            val keys = validationDoc.data?.get("keys") as List<String>

            return keys.contains(key)
        }catch (e: Exception){
            println("ERROR: validating script failed for $scriptName key: $key")

        }
        return false
    }
}
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val db = FBDataBase()
        println("key found: " + db.validateScript("Zulrah","test"))
        println("key found: " + db.validateScript("Zulrah","df83jf76dh"))
    }
}