package com.example.crossingschedule.feature.schedule.ext

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun FirebaseFirestore.getIslandActivitiesForSpecifiedDateDocument(
    userId: String,
    islandId: String,
    date: String,
): DocumentReference {
    return collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6")
        .collection("date")
        .document("12.01.2021")//TODO Actually get this data from some kind of user object
}

fun FirebaseFirestore.getIslandActivitiesDocument(
    userId: String,
    islandId: String,
): DocumentReference {
    return collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6")

}

@ExperimentalCoroutinesApi
suspend fun FirebaseFirestore.documentExist(islandId: String, selectedDate: String): Boolean {
    val docIdRef: DocumentReference = collection("TdWrr3sOWOzylTApiuV6")
        .document("12.01.2021")

    return suspendCancellableCoroutine { continuation ->
        docIdRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                continuation.resume(document?.exists() == true)
            } else {
                continuation.resume(false)
            }
        }
    }
}

fun FirebaseFirestore.createEmptyDocument(islandId: String, selectedDate: String) {
    val collectionReference: CollectionReference = collection("TdWrr3sOWOzylTApiuV6")
    val dateDocument = hashMapOf("date" to "12.01.2021")

    collectionReference.add(dateDocument)
}
