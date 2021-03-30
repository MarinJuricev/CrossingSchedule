package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.AuthFailure
import org.junit.Before

import org.junit.Test

class LoginValidatorTest {

    lateinit var sut: LoginValidator

    @Before
    fun setUp() {
        sut = LoginValidator()
    }

    @Test
    fun `validate should return EmailValidationFailure when the provided email does not match the regex`() {
        val invalidEmail = "notValidEmail@"
        val emptyPassword = ""

        val actualResult = sut.validate(invalidEmail, emptyPassword)
        val expectedResult = Either.Left(AuthFailure.EmailValidationAuthFailure("Invalid e-mail provided"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return PasswordValidationFailure when the provided password is blank`() {
        val invalidEmail = "validEmail@test.com"
        val blankPassword = "                "

        val actualResult = sut.validate(invalidEmail, blankPassword)
        val expectedResult =
            Either.Left(AuthFailure.PasswordValidationAuthFailure("Password must have at least 8 chars"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return PasswordValidationFailure when the provided password is not at least MINIMAL_PASSWORD_LENGTH long`() {
        val invalidEmail = "validEmail@test.com"
        val shortPassword = "shortPw"

        val actualResult = sut.validate(invalidEmail, shortPassword)
        val expectedResult =
            Either.Left(AuthFailure.PasswordValidationAuthFailure("Password must have at least 8 chars"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return Unit when the provided email,password passes the requirements`() {
        val invalidEmail = "validEmail@test.com"
        val strongPassword = "strongPassword"

        val actualResult = sut.validate(invalidEmail, strongPassword)
        val expectedResult = Either.Right(Unit)

        assert(actualResult == expectedResult)
    }
}