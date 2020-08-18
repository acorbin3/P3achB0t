package com.p3achb0t.client.tracker

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.tracker.string_stuff.str
import java.io.File
import java.io.FileInputStream
import java.net.DatagramSocket
import java.net.InetAddress
import java.sql.Timestamp
import java.util.*


class FBDataBase {
    val firestore: Firestore
    private val userRef: CollectionReference
    private val userDocs: MutableMap<String, DocumentReference>
    private val userSkillOrItemCount: MutableMap<String, Int> = HashMap()
    private val fileHashRef: CollectionReference
    var validationKey: String = ""

    init {

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

        firestore = FirestoreClient.getFirestore()
        userRef = firestore.collection("users")
        userDocs = HashMap()
        fileHashRef = firestore.collection("file_hashes")
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
        DatagramSocket().use { socket ->
            socket.connect(InetAddress.getByName("google.com"), 80)
            var ip = socket.localAddress.hostAddress
            lastUpdated["ip"] = ip
        }

        lastUpdated["startTime"] = date.toString()
        lastUpdated["script"] = script
        lastUpdated["user"] = accountID
        userDocs[accountID]?.set(lastUpdated  as Map<String, Any>)

        val userFields = mutableMapOf<String,String>()
        userFields["latestStartTime"] = date.toString()
        userRef.document(accountID).set(userFields as Map<String,Any>)

    }
    fun setBanned(accountID: String, sessionID: String){
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val lastUpdated = mutableMapOf<String,String>()
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        lastUpdated["Banned:"] = "True: " + date.toString()
        println("setting banned")
        userDocs[accountID]?.set(lastUpdated  as Map<String, Any>)

        val userFields = mutableMapOf<String,String>()
        userFields["bannedTime"] = date.toString()
        userRef.document(accountID).set(userFields as Map<String,Any>)
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

        val userFields = mutableMapOf<String,String>()
        userFields["lastUpdateTime"] = date.toString()
        userRef.document(accountID).set(userFields as Map<String,Any>)
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

            val validationDoc = firestore.collection("validation").document(scriptName).get().get()

            val keys = validationDoc.data?.get("keys") as List<String>

            return keys.contains(key)
        }catch (e: Exception){
            println("ERROR: validating script failed for $scriptName key: $key")

        }
        return false
    }

    fun getFileHash(filename: String):String{
        var hash = ""
        try {
            val fileDoc = fileHashRef.document(filename).get().get()
            hash = fileDoc.get("hash").toString()
        }catch (e: Exception){
            return hash
        }
        return hash
    }

    fun pushFile(filePath: File,sha256: String){

        var fileContent = ""
        filePath.bufferedReader().lines().forEach { fileContent += "$it$$" }
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        val fields = mutableMapOf<String,String>()
        fields["hash"] = sha256
        fields["file"] = fileContent
        fields["date"] = date.toString()

        val name = filePath.toString().substringAfter("src\\")
        val fileDoc = fileHashRef.document(name)
        fileDoc.set(fields as Map<String,Any>)
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