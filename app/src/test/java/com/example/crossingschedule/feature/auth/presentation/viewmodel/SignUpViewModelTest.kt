package com.example.crossingschedule.feature.auth.presentation.viewmodel

import app.cash.turbine.test
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.*
import com.example.crossingschedule.feature.auth.domain.usecase.CreateAccount
import com.example.crossingschedule.feature.auth.presentation.SignUpEvent.*
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import com.example.crossingschedule.testUtilities.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val NEW_EMAIL = "newEmail"
private const val NEW_USERNAME = "newUsername"
private const val NEW_CONFIRM_PASSWORD = "newConfirmPassword"
private const val NEW_PASSWORD = "newPassword"
private const val ERROR_MESSAGE = "errorMessage"

@ExperimentalCoroutinesApi
@ExperimentalTime
class SignUpViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val createAccount: CreateAccount = mockk()
    private val authFailureToSignUpErrorMapper: Mapper<SignUpError, AuthFailure> = mockk()

    private lateinit var sut: SignUpViewModel

    @Before
    fun setUp() {
        sut = SignUpViewModel(
            createAccount,
            authFailureToSignUpErrorMapper,
        )
    }

    @Test
    fun `onEvent should update email when OnEmailChange is provided`() = runBlockingTest {
        sut.onEvent(OnEmailChange(NEW_EMAIL))

        sut.signUpViewState.test {
            assertThat(expectItem().email).isEqualTo(NEW_EMAIL)
        }
    }

    @Test
    fun `onEvent should update username when OnUsernameChange is provided`() = runBlockingTest {
        sut.onEvent(OnUsernameChange(NEW_USERNAME))

        sut.signUpViewState.test {
            assertThat(expectItem().username).isEqualTo(NEW_USERNAME)
        }
    }

    @Test
    fun `onEvent should update confirmPassword when OnConfirmPasswordChange is provided`() =
        runBlockingTest {
            sut.onEvent(OnConfirmPasswordChange(NEW_CONFIRM_PASSWORD))

            sut.signUpViewState.test {
                assertThat(expectItem().confirmPassword).isEqualTo(NEW_CONFIRM_PASSWORD)
            }
        }

    @Test
    fun `onEvent should update password when OnPasswordChange is provided`() = runBlockingTest {
        sut.onEvent(OnPasswordChange(NEW_PASSWORD))

        sut.signUpViewState.test {
            assertThat(expectItem().password).isEqualTo(NEW_PASSWORD)
        }
    }

    @Test
    fun `onEvent should update navigateToSchedule to true when OnCreateAccountClick is provided and CreateAccount returns Right`() =
        runBlockingTest {
            coEvery {
                createAccount(any(), any(), any(), any())
            } coAnswers {
                Unit.buildRight()
            }

            sut.onEvent(OnCreateAccountClick)

            sut.signUpViewState.test {
                assertThat(expectItem().navigateToSchedule).isTrue()
            }
        }


    @Test
    fun `onEvent should update signUpError when OnCreateAccountClick is provided and CreateAccount returns Left`() =
        runBlockingTest {
            val useCaseError = RemoteAuthFailure(ERROR_MESSAGE).buildLeft()
            val mapperResult = SignUpError.GeneralError(ERROR_MESSAGE)

            coEvery {
                createAccount(any(), any(), any(), any())
            } coAnswers {
                useCaseError
            }
            coEvery {
                authFailureToSignUpErrorMapper.map(useCaseError.error)
            } coAnswers {
                mapperResult
            }

            sut.onEvent(OnCreateAccountClick)

            sut.signUpViewState.test {
                assertThat(expectItem().signUpError).isEqualTo(mapperResult)
            }
        }
}