package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
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
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Either.Right(Unit))
                } else {
                    val errorMessage = Failure.RemoteFailure(
                        task.exception?.localizedMessage ?: "Unknown Error Occurred"
                    )
                    continuation.resume(Either.Left(errorMessage))
                }
            }
    }

    override suspend fun getUserIdToken() = suspendCancellableCoroutine<String> { continuation ->
        firebaseAuth.currentUser?.getIdToken(true)?.addOnSuccessListener { tokenTask ->
            continuation.resume(tokenTask.token ?: "HANDLE ME")//TODO HANDLE NULLABLE CASE
        }
    }
}