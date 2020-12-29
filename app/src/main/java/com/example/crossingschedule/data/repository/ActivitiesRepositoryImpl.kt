package com.example.crossingschedule.data.repository

import android.util.Log
import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class ActivitiesRepositoryImpl(
    private val fireStore: FirebaseFirestore
) : ActivitiesRepository {
    override suspend fun getCurrentActivities(): Flow<Either<Failure, List<CrossingDailyActivities>>> =
        callbackFlow {
            val listener = fireStore
                .collection("islands")
                .addSnapshotListener { value, error ->
                    if (error != null && error.localizedMessage != null) {
                        offer(Either.Error(Failure.RemoteFailure(error.localizedMessage!!)))
                    } else if (value != null) {
                        offer(Either.Success(value.toObjects(CrossingDailyActivities::class.java)))
                    }
                }
            awaitClose {
                listener.remove()
                Log.d("Cleanup", "Removed listener $listener")
            }
        }
}