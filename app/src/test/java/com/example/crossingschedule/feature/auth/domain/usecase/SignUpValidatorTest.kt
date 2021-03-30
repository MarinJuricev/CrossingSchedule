package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.*
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import org.junit.Before
import org.junit.Test

class SignUpValidatorTest {

    lateinit var sut: SignUpValidator

    @Before
    fun setUp() {
        sut = SignUpValidator()
    }

    @Test
    fun `validate should return UserNameAuthFailure when the provided username is empty`() {
        val username = ""
        val invalidEmail = ""
        val emptyPassword = ""
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(username, invalidEmail, emptyPassword, emptyConfirmPassword)
        val expectedResult = UserNameAuthFailure("Username can't be empty").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return EmailValidationFailure when the provided email does not match the regex`() {
        val username = "username"
        val invalidEmail = "notValidEmail@"
        val emptyPassword = ""
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(username, invalidEmail, emptyPassword, emptyConfirmPassword)
        val expectedResult = EmailValidationAuthFailure("Invalid e-mail provided").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return PasswordValidationFailure when the provided password is blank`() {
        val username = "username"
        val invalidEmail = "validEmail@test.com"
        val blankPassword = "                "
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(username, invalidEmail, blankPassword, emptyConfirmPassword)
        val expectedResult =
            PasswordValidationAuthFailure("Password must have at least 8 chars").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return PasswordValidationFailure when the provided password is not at least MINIMAL_PASSWORD_LENGTH long`() {
        val username = "username"
        val invalidEmail = "validEmail@test.com"
        val shortPassword = "shortPw"
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(username, invalidEmail, shortPassword, emptyConfirmPassword)
        val expectedResult =
            PasswordValidationAuthFailure("Password must have at least 8 chars").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return ConfirmPasswordValidationFailure when the provided password is blank`() {
        val username = "username"
        val invalidEmail = "validEmail@test.com"
        val blankPassword = "strongPassword"
        val blankConfirmPassword = "            "

        val actualResult = sut.validate(username, invalidEmail, blankPassword, blankConfirmPassword)
        val expectedResult =
            ConfirmPasswordValidationAuthFailure("The passwords do not match").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return ConfirmPasswordValidationFailure when the provided password is not at least MINIMAL_PASSWORD_LENGTH long`() {
        val username = "username"
        val invalidEmail = "validEmail@test.com"
        val shortPassword = "strongPassword"
        val shortConfirmPassword = "shortPw"

        val actualResult = sut.validate(username, invalidEmail, shortPassword, shortConfirmPassword)
        val expectedResult =
            ConfirmPasswordValidationAuthFailure("The passwords do not match").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return ConfirmPasswordValidationFailure when the provided password is does not match the password`() {
        val username = "username"
        val invalidEmail = "validEmail@test.com"
        val strongPassword = "strongPassword"
        val notMatchingConfirmPassword = "strongPassword123"

        val actualResult =
            sut.validate(username, invalidEmail, strongPassword, notMatchingConfirmPassword)
        val expectedResult =
            ConfirmPasswordValidationAuthFailure("The passwords do not match").buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return Unit when the provided email,password passes the requirements`() {
        val username = "username"
        val invalidEmail = "validEmail@test.com"
        val strongPassword = "strongPassword"
        val strongConfirmPassword = "strongPassword"

        val actualResult =
            sut.validate(username, invalidEmail, strongPassword, strongConfirmPassword)
        val expectedResult = Unit.buildRight()

        assert(actualResult == expectedResult)
    }
}