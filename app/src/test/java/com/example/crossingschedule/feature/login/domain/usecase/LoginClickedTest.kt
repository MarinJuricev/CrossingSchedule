package com.example.crossingschedule.feature.login.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.login.domain.repository.LoginRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginClickedTest {

    private val loginRepository: LoginRepository = mockk()
    private val loginValidator: LoginValidator = mockk()

    lateinit var sut: LoginClicked

    @Before
    fun setUp() {
        sut = LoginClicked(
            loginRepository,
            loginValidator
        )
    }

    @Test
    fun `loginClicked should early return when loginValidator returns a failure`() =
        runBlockingTest {
            val email = "email"
            val password = "password"
            val failure = Either.Left(Failure.EmailValidationFailure(""))

            every {
                loginValidator.validate(email, password)
            } answers { failure }
            coEvery {
                loginRepository.createAccount(email, password)
            } coAnswers { failure }

            val actualResult = sut(email, password)

            assert(actualResult == failure)
            coVerify(exactly = 0) { loginRepository.createAccount(email, password) }
        }

    @Test
    fun `loginClicked should trigger repository create account when login validator returns a success`() =
        runBlockingTest {
            val email = "email"
            val password = "password"
            val success = Either.Right(Unit)

            every {
                loginValidator.validate(email, password)
            } answers { success }
            coEvery {
                loginRepository.createAccount(email, password)
            } coAnswers { success }

            val actualResult = sut(email, password)

            assert(actualResult == success)

            coVerify(exactly = 1) { loginRepository.createAccount(email, password) }
        }
}