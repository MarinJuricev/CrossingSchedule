package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import org.junit.Before

import org.junit.Test

class SignUpValidatorTest {

    lateinit var sut: SignUpValidator

    @Before
    fun setUp() {
        sut = SignUpValidator()
    }

    @Test
    fun `validate should return EmailValidationFailure when the provided email does not match the regex`() {
        val invalidEmail = "notValidEmail@"
        val emptyPassword = ""
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(invalidEmail, emptyPassword, emptyConfirmPassword)
        val expectedResult = Either.Left(Failure.EmailValidationFailure("Invalid e-mail provided"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return PasswordValidationFailure when the provided password is blank`() {
        val invalidEmail = "validEmail@test.com"
        val blankPassword = "                "
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(invalidEmail, blankPassword, emptyConfirmPassword)
        val expectedResult =
            Either.Left(Failure.PasswordValidationFailure("Password must have at least 8 chars"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return PasswordValidationFailure when the provided password is not at least MINIMAL_PASSWORD_LENGTH long`() {
        val invalidEmail = "validEmail@test.com"
        val shortPassword = "shortPw"
        val emptyConfirmPassword = ""

        val actualResult = sut.validate(invalidEmail, shortPassword, emptyConfirmPassword)
        val expectedResult =
            Either.Left(Failure.PasswordValidationFailure("Password must have at least 8 chars"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return ConfirmPasswordValidationFailure when the provided password is blank`() {
        val invalidEmail = "validEmail@test.com"
        val blankPassword = "strongPassword"
        val blankConfirmPassword = "            "

        val actualResult = sut.validate(invalidEmail, blankPassword, blankConfirmPassword)
        val expectedResult =
            Either.Left(Failure.ConfirmPasswordValidationFailure("The passwords do not match"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return ConfirmPasswordValidationFailure when the provided password is not at least MINIMAL_PASSWORD_LENGTH long`() {
        val invalidEmail = "validEmail@test.com"
        val shortPassword = "strongPassword"
        val shortConfirmPassword = "shortPw"

        val actualResult = sut.validate(invalidEmail, shortPassword, shortConfirmPassword)
        val expectedResult =
            Either.Left(Failure.ConfirmPasswordValidationFailure("The passwords do not match"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return ConfirmPasswordValidationFailure when the provided password is does not match the password`() {
        val invalidEmail = "validEmail@test.com"
        val strongPassword = "strongPassword"
        val notMatchingConfirmPassword = "strongPassword123"

        val actualResult = sut.validate(invalidEmail, strongPassword, notMatchingConfirmPassword)
        val expectedResult =
            Either.Left(Failure.ConfirmPasswordValidationFailure("The passwords do not match"))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `validate should return Unit when the provided email,password passes the requirements`() {
        val invalidEmail = "validEmail@test.com"
        val strongPassword = "strongPassword"
        val strongConfirmPassword = "strongPassword"

        val actualResult = sut.validate(invalidEmail, strongPassword, strongConfirmPassword)
        val expectedResult = Either.Right(Unit)

        assert(actualResult == expectedResult)
    }
}