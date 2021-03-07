package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"

class AuthProviderImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthProvider {

    override suspend fun login(
        email: String,
        password: String
    ) = suspendCancellableCoroutine<Either<Failure, Unit>> { continuation ->
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
        suspendCancellableCoroutine<Either<Failure, Unit>> { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addCrossingOnCompleteListener(
                    onSuccess = { continuation.resume(Either.Right(Unit)) },
                    onFailure = { continuation.resume(Either.Left(it)) }
                )
        }
}

private fun Task<AuthResult>.addCrossingOnCompleteListener(
    onSuccess: () -> Unit,
    onFailure: (Failure) -> Unit,
) {
    if (this.isSuccessful) {
        onSuccess()
    } else {
        val errorMessage = Failure.RemoteFailure(
            this.exception?.localizedMessage ?: "Unknown Error Occurred"
        )
        onFailure(errorMessage)
    }
}