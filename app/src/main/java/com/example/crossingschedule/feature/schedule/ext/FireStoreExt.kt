package com.example.crossingschedule.feature.schedule.ext

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.Serializable
import kotlin.coroutines.resume

fun FirebaseFirestore.getIslandActivitiesForSpecifiedDateDocument(
    userId: String,
    islandId: String,
    date: String,
): DocumentReference =
    collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6")
        .collection("date")
        .document(date)

fun FirebaseFirestore.getIslandActivitiesDocument(
    userId: String,
    islandId: String,
): DocumentReference =
    collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6")

@ExperimentalCoroutinesApi
suspend fun FirebaseFirestore.documentExist(islandId: String, selectedDate: String): Boolean {
    val docIdRef: DocumentReference = collection("TdWrr3sOWOzylTApiuV6")
        .document(selectedDate)

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
//    val collectionReference: CollectionReference = collection("TdWrr3sOWOzylTApiuV6")
    val collectionReference = collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6")
        .collection("date")

    val dateDocument = hashMapOf(selectedDate to {  })
    val test = TestId(selectedDate, {})

    collectionReference.add(test).addOnSuccessListener {
        print(it)
    }. addOnFailureListener {
        print(it)
    }
}

data class TestId(
    @DocumentId
    val date: String,
    val content: Any
)