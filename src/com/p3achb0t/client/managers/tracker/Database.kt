package com.p3achb0t.client.managers.tracker

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.managers.accounts.StatInfo
import java.io.File
import java.io.FileInputStream
import java.util.*

class FBDataBase {
    private val db: Firestore
    private val userRef: CollectionReference
    private val userDocs: MutableMap<String, DocumentReference>
    private val userSkillOrItemCount: MutableMap<String, Int> = HashMap()

    init {
        val str: String = "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"
        val encryptor = AESEncryptor()
        val decryptedValue: String? =encryptor.decryptWithAES("662ede8988e58fb6d057dfs9d85605e0", str)
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


    private fun getDocID(accountID: String, base: String): String {
        val id = accountID + "_" + base
        if (id !in userSkillOrItemCount) {
            userSkillOrItemCount[id] = 1
        } else {
            userSkillOrItemCount[id] = userSkillOrItemCount[id]!!.plus(1)
        }
        return userSkillOrItemCount[id].toString()
    }

    fun updateStat(accountID: String, sessionID: String, skill: Stats.Skill, xp: Int) {
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }
        val messageID = getDocID(accountID, skill.name)
        userDocs[accountID]?.collection("Skills")?.document(skill.name)?.collection("xp")
                ?.document(messageID)?.set(StatInfo(System.currentTimeMillis(),xp))

    }

    fun updateItemCount(accountID: String, sessionID: String, itemName: String, count: Int) {
        if (accountID !in userDocs) {
            userDocs[accountID] = userRef.document(accountID).collection("Sessions").document(sessionID)
        }

        val messageID = getDocID(accountID, itemName)
        userDocs[accountID]?.collection("Items")?.document(itemName)?.collection("entry")
                ?.document(messageID)?.set(StatInfo(System.currentTimeMillis(),count))
    }
}