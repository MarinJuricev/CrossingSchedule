package com.example.crossingschedule.feature.schedule.data.repository

import android.util.Log
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.data.factory.DefaultShopFactory
import com.example.crossingschedule.feature.schedule.domain.model.*
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.feature.schedule.ext.createEmptyDocument
import com.example.crossingschedule.feature.schedule.ext.documentExist
import com.example.crossingschedule.feature.schedule.ext.getIslandActivitiesDocument
import com.example.crossingschedule.feature.schedule.ext.getIslandActivitiesForSpecifiedDateDocument
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class ActivitiesRepositoryImpl(
    private val fireStore: FirebaseFirestore,
    private val crossingTodosToDefaultCrossingTodosMapper: Mapper<List<CrossingTodo>, List<CrossingTodo>>,
    private val defaultShopFactory: DefaultShopFactory
) : ActivitiesRepository {

    //TODO CLEANUP THIS MESSY IMPLEMENTATION!!!!!

    override suspend fun getActivitiesFoSpecifiedDay(selectedDate: String): Flow<Either<AuthFailure, CrossingDailyActivities>> {
        handleEmptyDocumentCase(selectedDate)

        return callbackFlow {
            val defaultActivitiesJob = Job()

            val listener = fireStore
                .collection("/users/IYwmWMpVP3aV4RmWEa8q/islands/TdWrr3sOWOzylTApiuV6/date/")//TODO Actually get the user/island id from somewhere
                .document(selectedDate)
                .addSnapshotListener { value, error ->
                    if (error != null && error.localizedMessage != null) {
                        offer(Either.Left(AuthFailure.RemoteAuthFailure(error.localizedMessage!!)))
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
    }

    private suspend fun handleEmptyDocumentCase(selectedDate: String) {
        if (!fireStore.documentExist("TEST", selectedDate)) {
            fireStore.createEmptyDocument("TEST", selectedDate)
        }
    }

    override suspend fun getDefaultIslandActivities(): Either<AuthFailure, CrossingDailyActivities> {
        val activitiesReference = fireStore
            .collection("/users/IYwmWMpVP3aV4RmWEa8q/islands")
            .document("TdWrr3sOWOzylTApiuV6")

        return suspendCancellableCoroutine { continuation ->
            activitiesReference.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val activities = document.toObject(CrossingDailyActivities::class.java)
                            ?: CrossingDailyActivities()

                        if (activities.shops.isNullOrEmpty()) {
                            val activitiesWithDefaultShops =
                                activities.copy(shops = defaultShopFactory.generate())

                            continuation.resume(Either.Right(activitiesWithDefaultShops))
                        } else {
                            continuation.resume(Either.Right(activities))
                        }

                    } else {
                        continuation.resume(Either.Left(AuthFailure.RemoteAuthFailure("TEST")))
                    }
                }.addOnFailureListener { exception ->
                    continuation.resume(
                        Either.Left(
                            AuthFailure.RemoteAuthFailure(
                                exception.message
                                    ?: "Unknown error occurred" //TODO provide localized value with StringProvider
                            )
                        )
                    )
                }
        }
    }

    override suspend fun updateCrossingTodoItems(
        updatedList: List<CrossingTodo>,
        currentDate: String
    ): Either<AuthFailure, Unit> {
        updateDefaultTodosWithDefaultValues(updatedList)

        val islandReference = fireStore.getIslandActivitiesForSpecifiedDateDocument(
            "TEST",
            "TEST",
            currentDate
        )

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "crossingTodos", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    private suspend fun updateDefaultTodosWithDefaultValues(updatedList: List<CrossingTodo>) {
        val defaultValues = crossingTodosToDefaultCrossingTodosMapper.map(updatedList)
        val activitiesReference = fireStore.getIslandActivitiesDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(activitiesReference, "crossingTodos", defaultValues)
        }
    }

    override suspend fun updateShopItems(
        updatedList: List<Shop>,
        currentDate: String
    ): Either<AuthFailure, Unit> {
        val islandReference = fireStore.getIslandActivitiesForSpecifiedDateDocument(
            "TEST",
            "TEST",
            currentDate
        )

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "shops", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateNotes(
        updatedNotes: String,
        currentDate: String
    ): Either<AuthFailure, Unit> {
        val islandReference = fireStore.getIslandActivitiesForSpecifiedDateDocument(
            "TEST",
            "TEST",
            currentDate
        )

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "notes", updatedNotes)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateVillagerInteractions(
        updatedList: List<VillagerInteraction>,
        currentDate: String
    ): Either<AuthFailure, Unit> {
        val islandReference = fireStore.getIslandActivitiesForSpecifiedDateDocument(
            "TEST",
            "TEST",
            currentDate
        )
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

    override suspend fun updateTurnipPrices(
        updatedTurnipPrices: TurnipPrices,
        currentDate: String
    ): Either<AuthFailure, Unit> {
        val islandReference = fireStore.getIslandActivitiesForSpecifiedDateDocument(
            "TEST",
            "TEST",
            currentDate
        )

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "turnipPrices", updatedTurnipPrices)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }
}