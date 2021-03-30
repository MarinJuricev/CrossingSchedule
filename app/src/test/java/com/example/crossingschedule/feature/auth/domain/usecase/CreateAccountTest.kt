package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.EmailValidationAuthFailure
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val USERNAME = "username"
private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val CONFIRM_PASSWORD = "confirm_password"

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
            val failure = EmailValidationAuthFailure("").buildLeft()

            every {
                signUpValidator.validate(USERNAME, EMAIL, PASSWORD, CONFIRM_PASSWORD)
            } answers { failure }
            coEvery {
                authRepository.createAccount(USERNAME, EMAIL, PASSWORD)
            } coAnswers { failure }

            val actualResult = sut(USERNAME, EMAIL, PASSWORD, CONFIRM_PASSWORD)

            assert(actualResult == failure)
            coVerify(exactly = 0) { authRepository.createAccount(USERNAME, EMAIL, PASSWORD) }
        }

    @Test
    fun `createAccount should trigger repository create account when signUpValidator returns a success`() =
        runBlockingTest {
            val success = Unit.buildRight()

            every {
                signUpValidator.validate(USERNAME, EMAIL, PASSWORD, CONFIRM_PASSWORD)
            } answers { success }
            coEvery {
                authRepository.createAccount(USERNAME, EMAIL, PASSWORD)
            } coAnswers { success }

            val actualResult = sut(USERNAME, EMAIL, PASSWORD, CONFIRM_PASSWORD)

            assert(actualResult == success)
            coVerify(exactly = 1) { authRepository.createAccount(USERNAME, EMAIL, PASSWORD) }
        }
}