package com.example.crossingschedule.feature.login.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthApiImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val loginApiService: LoginApiService
) : AuthApi {

    override suspend fun login(
        email: String,
        password: String
    ) = suspendCancellableCoroutine<Either<Failure, Unit>> { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.getIdToken(true)?.addOnSuccessListener { tokenTask ->
                        // Ping our BE this token
                        loginApiService.authenticateUser(
                            tokenTask.token ?: "HANDLE ME"
                        )//TODO: HANDLE ME
                        continuation.resume(Either.Right(Unit))
                    }
                } else {
                    val errorMessage = Failure.RemoteFailure(
                        task.exception?.localizedMessage ?: "Error while performing login"
                    )
                    continuation.resume(Either.Left(errorMessage))
                }
            }
    }
}

//private suspend fun FirebaseAuth.getIdToken(): String? =
//    suspendCancellableCoroutine<String?> { continuation ->
//        currentUser?.getIdToken(true)?.addOnSuccessListener { task ->
//            continuation.resume(task.token)
//        }
//    }