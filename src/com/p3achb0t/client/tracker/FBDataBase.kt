package com.p3achb0t.client.tracker

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.tracker.string_stuff.str
import org.jsoup.Jsoup
import java.io.File
import java.io.FileInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class FBDataBase {
    val firestore: Firestore
    val userRef: CollectionReference
    private val userSession: MutableMap<String, DocumentReference>
    private val userSkillOrItemCount: MutableMap<String, Int> = HashMap()
    private val fileHashRef: CollectionReference
    var validationKey: String = ""
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

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
        userSession = HashMap()
        fileHashRef = firestore.collection("file_hashes")
    }

    data class StatInfo(val time: String, val xp: Int)
    data class ItemInfo(val name: String,  val count: String, val countPerHour: String)

    private fun getDocID(accountID: String, base: String): String {
        val id = accountID + "_" + base
        if (id !in userSkillOrItemCount) {
            userSkillOrItemCount[id] = 1000
        } else {
            userSkillOrItemCount[id] = userSkillOrItemCount[id]!!.plus(1)
        }
        return userSkillOrItemCount[id].toString()
    }

    fun initalScriptLoad(account: Account){
        val accountID = account.username
        val sessionID = account.sessionToken
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }

        val dateStr = LocalDateTime.now().format(formatter)
        val userFields = mutableMapOf<String,String>()
        userFields["latestStartTime"] = dateStr
        userFields["script"] = account.actionScript
        try {
            userFields["ip"] = Jsoup.connect("https://api.ipify.org?format=text").get().toString()
        }catch (e: Exception){

        }
        userFields["proxy"] = account.proxy
        userRef.document(accountID).set(userFields as Map<String,Any>)


        val lastUpdated = mutableMapOf<String,String>()
        lastUpdated["startTime"] = dateStr
        lastUpdated["latestStartTime"] = dateStr
        lastUpdated["script"] = account.actionScript
        lastUpdated["user"] = accountID
        try {
            lastUpdated["ip"] = Jsoup.connect("https://api.ipify.org?format=text").get().toString()
        }catch (e: Exception){

        }
        lastUpdated["proxy"] = account.proxy
        userSession[accountID]?.set(lastUpdated  as Map<String, Any>)



    }
    fun setBanned(accountID: String, sessionID: String){
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val lastUpdated = mutableMapOf<String,String>()
        val dateStr = LocalDateTime.now().format(formatter)
        lastUpdated["Banned:"] = "True: $dateStr"
        println("setting banned")
        userSession[accountID]?.update(lastUpdated  as Map<String, Any>)

        val userFields = mutableMapOf<String,String>()
        userFields["bannedTime"] = dateStr
        userRef.document(accountID).update(userFields as Map<String,Any>)
    }

    fun setLastUpdated(accountID: String, sessionID: String){
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val lastUpdated = mutableMapOf<String,String>()
        lastUpdated["lastUpdateTime"] = LocalDateTime.now().format(formatter)
        userSession[accountID]?.update(lastUpdated  as Map<String, Any>)

        val userFields = mutableMapOf<String,String>()
        userFields["lastUpdateTime"] = LocalDateTime.now().format(formatter)
        userRef.document(accountID).update(userFields as Map<String,Any>)
    }
    fun updateStat(accountID: String, sessionID: String, skill: Stats.Skill, xp: String, xpPerHour: String) {
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val lastUpdated = mutableMapOf<String,String>()
        lastUpdated["lastUpdated"] = LocalDateTime.now().format(formatter)
        userSession[accountID]?.update(lastUpdated as Map<String, Any>)

        val fields = mutableMapOf<String,String>()
        fields["xp"] = xp
        fields["xp_PerHour"] = xpPerHour
        userSession[accountID]?.collection("Skills")?.document(skill.name)?.update(fields as Map<String,Any>)

    }



    fun updateItemCount(accountID: String, sessionID: String, itemInfo: ArrayList<ItemInfo>) {
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }

        val fields = mutableMapOf<String,String>()
        fields["lastUpdated"] = LocalDateTime.now().format(formatter)
        userSession[accountID]?.update(fields as Map<String, Any>)
        fields.clear()
        for (item in itemInfo){
            fields["${item.name}-count"] = item.count
            userSession[accountID]?.update(fields as Map<String, Any>)
            fields.clear()
            fields["${item.name}-count_PerHour"] = item.countPerHour
            userSession[accountID]?.update(fields as Map<String, Any>)
            fields.clear()
        }
    }

    fun updateSessionField(accountID: String, sessionID: String, fieldName: String, value: String) {
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }

        val fields = mutableMapOf<String,String>()
        fields[fieldName] = value
        userSession[accountID]?.update(fields as Map<String, Any>)

        fields.clear()
    }

    fun updateSessionFields(accountID: String, sessionID: String,data: Map<String,String>){
        if (accountID !in userSession) {
            userSession[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }

        val fields = mutableMapOf<String,String>()
        data.forEach { fieldName, value ->
            fields[fieldName] = value
        }

        userSession[accountID]?.update(fields as Map<String, Any>)

        fields.clear()
    }

    fun updateUserField(accountID: String, fieldName: String, fieldValue: String){

        val fields = mutableMapOf<String,String>()
        fields[fieldName] = fieldValue
        userRef.document(accountID).update(fields as Map<String,Any>)
        fields.clear()
    }
    fun updateUserFields(accountID: String, data: Map<String,String>){

        val fields = mutableMapOf<String,String>()
        data.forEach { (t, u) ->
            fields[t] = u
        }
        userRef.document(accountID).update(fields as Map<String,Any>)
        fields.clear()
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
        val fields = mutableMapOf<String,String>()
        fields["hash"] = sha256
        fields["file"] = fileContent
        fields["date"] = LocalDateTime.now().format(formatter)

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