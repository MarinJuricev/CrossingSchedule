package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
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
class PerformLoginTest {

    private val authRepository: AuthRepository = mockk()
    private val loginValidator: LoginValidator = mockk()

    lateinit var sut: PerformLogin

    @Before
    fun setUp() {
        sut = PerformLogin(
            authRepository,
            loginValidator
        )
    }

    @Test
    fun `loginClicked should early return when loginValidator returns a failure`() =
        runBlockingTest {
            val email = "email"
            val password = "password"
            val failure = Either.Left(AuthFailure.EmailValidationAuthFailure(""))

            every {
                loginValidator.validate(email, password)
            } answers { failure }
            coEvery {
                authRepository.login(email, password)
            } coAnswers { failure }

            val actualResult = sut(email, password)

            assert(actualResult == failure)
            coVerify(exactly = 0) { authRepository.login(email, password) }
        }

    @Test
    fun `loginClicked should trigger repository login when login validator returns a success`() =
        runBlockingTest {
            val email = "email"
            val password = "password"
            val success = Either.Right(Unit)

            every {
                loginValidator.validate(email, password)
            } answers { success }
            coEvery {
                authRepository.login(email, password)
            } coAnswers { success }

            val actualResult = sut(email, password)

            assert(actualResult == success)

            coVerify(exactly = 1) { authRepository.login(email, password) }
        }
}