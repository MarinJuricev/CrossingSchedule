package com.example.crossingschedule.feature.auth.data.service

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class AuthProviderImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthProvider {

    override suspend fun login(
        email: String,
        password: String
    ) = suspendCancellableCoroutine<Either<AuthFailure, Unit>> { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addCrossingOnCompleteListener(
                onSuccess = { continuation.resume(Either.Right(Unit)) },
                onFailure = { continuation.resume(Either.Left(it)) }
            )
    }

    override suspend fun getUserIdToken() = suspendCancellableCoroutine<String> { continuation ->
        firebaseAuth.currentUser?.getIdToken(true)?.addOnSuccessListener { tokenTask ->
            continuation.resume(tokenTask.token ?: "HANDLE ME")//TODO HANDLE NULLABLE CASE
        }
    }

    override suspend fun createAccount(email: String, password: String) =
        suspendCancellableCoroutine<Either<AuthFailure, Unit>> { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addCrossingOnCompleteListener(
                    onSuccess = { continuation.resume(Either.Right(Unit)) },
                    onFailure = { continuation.resume(Either.Left(it)) }
                )
        }
}

private fun Task<AuthResult>.addCrossingOnCompleteListener(
    onSuccess: () -> Unit,
    onFailure: (AuthFailure) -> Unit,
) {
    this.addOnSuccessListener { onSuccess() }
    this.addOnFailureListener {
        val errorMessage = AuthFailure.RemoteAuthFailure(
            it.localizedMessage ?: "Unknown Error Occurred"
        )
        onFailure(errorMessage)
    }
}