package com.example.crossingschedule.data.repository

import android.util.Log
import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.model.CrossingTodo
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
    override suspend fun getCurrentActivities(): Flow<Either<Failure, CrossingDailyActivities>> =
        callbackFlow {
            val listener = fireStore
                .collection("/users/IYwmWMpVP3aV4RmWEa8q/islands/")
                .addSnapshotListener { value, error ->
                    if (error != null && error.localizedMessage != null) {
                        offer(Either.Left(Failure.RemoteFailure(error.localizedMessage!!)))
                    } else if (value != null) {
                        offer(
                            Either.Right(
                                value.toObjects(CrossingDailyActivities::class.java).firstOrNull()
                                    ?: CrossingDailyActivities()
                            )
                        )
                    }
                }
            awaitClose {
                listener.remove()
                Log.d("Cleanup", "Removed listener $listener")
            }
        }

    override suspend fun updateCrossingTodoItems(updatedList: List<CrossingTodo>): Either<Failure, Unit> {
        val documentReference =
            fireStore
                .collection("users")
                .document("IYwmWMpVP3aV4RmWEa8q") //TODO Actually get this data from some kind of user object
                .collection("islands")
                .document("TdWrr3sOWOzylTApiuV6") ////TODO Actually get this data from some kind of user object


        fireStore.runTransaction { transaction ->
            transaction.update(documentReference, "crossingTodos", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }
}