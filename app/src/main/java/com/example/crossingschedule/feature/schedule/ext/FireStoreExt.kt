package com.example.crossingschedule.data.ext

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

fun FirebaseFirestore.getIslandActivitiesForSpecifiedDateDocument(
    userId: String,
    islandId: String,
    date: String,
): DocumentReference {
    return this.collection("users")
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
    return this.collection("users")
        .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
        .collection("islands")
        .document("TdWrr3sOWOzylTApiuV6")

}