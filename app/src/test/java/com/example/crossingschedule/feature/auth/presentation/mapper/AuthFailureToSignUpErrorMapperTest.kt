package com.example.crossingschedule.feature.auth.presentation.mapper

import com.example.crossingschedule.core.model.AuthFailure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import org.junit.Before

import org.junit.Test

class AuthFailureToSignUpErrorMapperTest {

    lateinit var sut: Mapper<SignUpError, AuthFailure>

    @Before
    fun setUp() {
        sut = FailureToSignUpErrorMapper()
    }

    @Test
    fun `map should return EmailError when the provided failure is EmailValidationFailure`(){
        val failure = AuthFailure.EmailValidationAuthFailure("")

        val actualResult = sut.map(failure)
        val expectedResult = SignUpError.EmailError("")

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return PasswordError when the provided failure is PasswordValidationFailure`(){
        val failure = AuthFailure.PasswordValidationAuthFailure("")

        val actualResult = sut.map(failure)
        val expectedResult = SignUpError.PasswordError("")

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return ConfirmPasswordError when the provided failure is ConfirmPasswordValidationFailure`(){
        val failure = AuthFailure.ConfirmPasswordValidationAuthFailure("")

        val actualResult = sut.map(failure)
        val expectedResult = SignUpError.ConfirmPasswordError("")

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return GeneralError when the provided failure is not of type EmailValidationFailure or PasswordValidationFailure or ConfirmPasswordValidationFailure`(){
        val failure = AuthFailure.RemoteAuthFailure("")

        val actualResult = sut.map(failure)
        val expectedResult = SignUpError.GeneralError("")

        assert(actualResult == expectedResult)
    }
}