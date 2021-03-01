package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateAccountTest {

    private val authRepository: AuthRepository = mockk()
    private val signUpValidator: SignUpValidator = mockk()

    lateinit var sut: CreateAccount

    @Before
    fun setUp() {
        sut = CreateAccount(
            signUpValidator,
            authRepository
        )
    }

    @Test
    fun `createAccount should early return when signUpValidator returns a failure`() =
        runBlockingTest {
            val email = "email"
            val password = "password"
            val confirmPassword = "confirmPassword"
            val failure = Either.Left(Failure.EmailValidationFailure(""))

            every {
                signUpValidator.validate(email, password, confirmPassword)
            } answers { failure }
            coEvery {
                authRepository.createAccount(email, password)
            } coAnswers { failure }

            val actualResult = sut(email, password, confirmPassword)

            assert(actualResult == failure)
            coVerify(exactly = 0) { authRepository.createAccount(email, password) }
        }

    @Test
    fun `createAccount should trigger repository create account when signUpValidator returns a success`() =
        runBlockingTest {
            val email = "email"
            val password = "password"
            val confirmPassword = "confirmPassword"
            val success = Either.Right(Unit)

            every {
                signUpValidator.validate(email, password, confirmPassword)
            } answers { success }
            coEvery {
                authRepository.createAccount(email, password)
            } coAnswers { success }

            val actualResult = sut(email, password, confirmPassword)

            assert(actualResult == success)
            coVerify(exactly = 1) { authRepository.createAccount(email, password) }
        }
}