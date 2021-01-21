package com.example.crossingschedule.feature.schedule.data.repository

import android.util.Log
import com.example.crossingschedule.data.ext.getIslandActivitiesDocument
import com.example.crossingschedule.data.ext.getIslandActivitiesForSpecifiedDateDocument
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.*
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class ActivitiesRepositoryImpl(
    private val fireStore: FirebaseFirestore,
    private val crossingTodosToDefaultCrossingTodosMapper: Mapper<List<CrossingTodo>, List<CrossingTodo>>
) : ActivitiesRepository {
    override suspend fun getActivitiesFoSpecifiedDay(): Flow<Either<Failure, CrossingDailyActivities>> =
        callbackFlow {
            val defaultActivitiesJob = Job()

            val listener = fireStore
                .collection("/users/IYwmWMpVP3aV4RmWEa8q/islands/TdWrr3sOWOzylTApiuV6/date/")//TODO Actually get the user/island id from somewhere
                .document("12.01.2021") //TODO Actually implement the date change
                .addSnapshotListener { value, error ->
                    if (error != null && error.localizedMessage != null) {
                        offer(Either.Left(Failure.RemoteFailure(error.localizedMessage!!)))
                    } else if (value != null) {
                        val mappedData = value.toObject(CrossingDailyActivities::class.java)

                        if (mappedData == null) {
                            CoroutineScope(defaultActivitiesJob).launch {
                                offer(getDefaultIslandActivities())
                            }
                        } else {
                            offer(Either.Right(mappedData))
                        }
                    }
                }
            awaitClose {
                if (defaultActivitiesJob.isActive) {
                    defaultActivitiesJob.cancel()
                }
                listener.remove()
                Log.d("Cleanup", "Removed listener $listener")
            }
        }

    override suspend fun getDefaultIslandActivities(): Either<Failure, CrossingDailyActivities> {
        val activitiesReference = fireStore
            .collection("/users/IYwmWMpVP3aV4RmWEa8q/islands")
            .document("TdWrr3sOWOzylTApiuV6")

        return suspendCancellableCoroutine { continuation ->
            activitiesReference.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val activities = document.toObject(CrossingDailyActivities::class.java)
                            ?: CrossingDailyActivities()
                        continuation.resume(Either.Right(activities)) {}
                    } else {
                        continuation.resume(Either.Left(Failure.RemoteFailure("TEST"))) {}
                    }
                }.addOnFailureListener { exception ->
                    continuation.resume(Either.Left(Failure.RemoteFailure("TEST"))) {}
                    Log.d("TEST", "get failed with ", exception)
                }
        }
    }

    override suspend fun updateCrossingTodoItems(updatedList: List<CrossingTodo>): Either<Failure, Unit> {
        updateDefaultTodosWithDefaultValues(updatedList)

        val islandReference =
            fireStore.getIslandActivitiesForSpecifiedDateDocument("TEST", "TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "crossingTodos", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    private fun updateDefaultTodosWithDefaultValues(updatedList: List<CrossingTodo>) {
        val defaultValues = crossingTodosToDefaultCrossingTodosMapper.map(updatedList)
        val activitiesReference = fireStore.getIslandActivitiesDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(activitiesReference, "crossingTodos", defaultValues)
        }
    }

    override suspend fun updateShopItems(updatedList: List<Shop>): Either<Failure, Unit> {
        val islandReference =
            fireStore.getIslandActivitiesForSpecifiedDateDocument("TEST", "TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "shops", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateNotes(updatedNotes: String): Either<Failure, Unit> {
        val islandReference =
            fireStore.getIslandActivitiesForSpecifiedDateDocument("TEST", "TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "notes", updatedNotes)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateVillagerInteractions(updatedList: List<VillagerInteraction>): Either<Failure, Unit> {
        val islandReference =
            fireStore.getIslandActivitiesForSpecifiedDateDocument("TEST", "TEST", "TEST")
        val activitiesReference = fireStore.getIslandActivitiesDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(activitiesReference, "villagerInteractions", updatedList)
        }

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "villagerInteractions", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateTurnipPrices(updatedTurnipPrices: TurnipPrices): Either<Failure, Unit> {
        val islandReference =
            fireStore.getIslandActivitiesForSpecifiedDateDocument("TEST", "TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "turnipPrices", updatedTurnipPrices)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }
}