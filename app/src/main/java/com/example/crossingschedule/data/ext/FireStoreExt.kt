package com.example.crossingschedule.data.ext

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

fun FirebaseFirestore.getIslandDocument(
    userId: String,
    islandId: String
): DocumentReference {
    return this.collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6") //TODO Actually get this data from some kind of user object
}
