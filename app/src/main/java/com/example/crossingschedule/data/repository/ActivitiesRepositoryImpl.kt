package com.example.crossingschedule.data.repository

import android.util.Log
import com.example.crossingschedule.data.ext.getIslandDocument
import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.*
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
        val islandReference = fireStore.getIslandDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "crossingTodos", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateShopItems(updatedList: List<Shop>): Either<Failure, Unit> {
        val islandReference = fireStore.getIslandDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "shops", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateNotes(updatedNotes: String): Either<Failure, Unit> {
        val islandReference = fireStore.getIslandDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "notes", updatedNotes)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateVillagerInteractions(updatedList: List<VillagerInteraction>): Either<Failure, Unit> {
        val islandReference = fireStore.getIslandDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "villagerInteractions", updatedList)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }

    override suspend fun updateTurnipPrices(updatedTurnipPrices: TurnipPrices): Either<Failure, Unit> {
        val islandReference = fireStore.getIslandDocument("TEST", "TEST")

        fireStore.runTransaction { transaction ->
            transaction.update(islandReference, "turnipPrices", updatedTurnipPrices)
        }
            .addOnSuccessListener { Log.e("BLABLA", "success") } //TODO ADD TIMBER
            .addOnFailureListener { Log.e("BLABLA", "failure", it) }

        return Either.Right(Unit)
    }
}