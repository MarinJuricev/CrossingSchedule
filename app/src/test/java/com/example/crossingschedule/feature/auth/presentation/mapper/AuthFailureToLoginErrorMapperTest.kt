package com.example.crossingschedule.feature.auth.presentation.mapper

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthFailureToLoginErrorMapperTest {

    lateinit var sut: Mapper<LoginError, AuthFailure>

    @Before
    fun setUp() {
        sut = FailureToLoginErrorMapper()
    }

    @Test
    fun `map should return EmailError when the provided failure is EmailValidationFailure`() =
        runBlockingTest {
            val failure = AuthFailure.EmailValidationAuthFailure("")

            val actualResult = sut.map(failure)
            val expectedResult = LoginError.EmailError("")

            assert(actualResult == expectedResult)
        }

    @Test
    fun `map should return PasswordError when the provided failure is PasswordValidationFailure`() =
        runBlockingTest {
            val failure = AuthFailure.PasswordValidationAuthFailure("")

            val actualResult = sut.map(failure)
            val expectedResult = LoginError.PasswordError("")

            assert(actualResult == expectedResult)
        }

    @Test
    fun `map should return GeneralError when the provided failure is not of type EmailValidationFailure or PasswordValidationFailure`() =
        runBlockingTest {
            val failure = AuthFailure.RemoteAuthFailure("")

            val actualResult = sut.map(failure)
            val expectedResult = LoginError.GeneralError("")

            assert(actualResult == expectedResult)
        }
}