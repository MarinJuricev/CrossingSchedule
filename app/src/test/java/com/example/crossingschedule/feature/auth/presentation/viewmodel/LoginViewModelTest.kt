package com.example.crossingschedule.feature.auth.presentation.viewmodel

import app.cash.turbine.test
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.RemoteAuthFailure
import com.example.crossingschedule.feature.auth.domain.usecase.PerformLogin
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import com.example.crossingschedule.feature.auth.presentation.model.LoginError.GeneralError
import com.example.crossingschedule.feature.auth.presentation.model.LoginEvent.*
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
private const val NEW_PASSWORD = "newPassword"
private const val ERROR_MESSAGE = "errorMessage"

@ExperimentalCoroutinesApi
@ExperimentalTime
class LoginViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val performLogin: PerformLogin = mockk()
    private val authFailureToLoginErrorMapper: Mapper<LoginError, AuthFailure> = mockk()

    private lateinit var sut: LoginViewModel

    @Before
    fun setUp() {
        sut = LoginViewModel(
            performLogin,
            authFailureToLoginErrorMapper,
        )
    }

    @Test
    fun `onEvent should update email when OnEmailChange is provided`() = runBlockingTest {
        sut.onEvent(OnEmailChange(NEW_EMAIL))

        sut.loginViewState.test {
            assertThat(expectItem().email).isEqualTo(NEW_EMAIL)
        }
    }

    @Test
    fun `onEvent should email username when OnEmailChange is provided`() = runBlockingTest {
        sut.onEvent(OnPasswordChange(NEW_PASSWORD))

        sut.loginViewState.test {
            assertThat(expectItem().password).isEqualTo(NEW_PASSWORD)
        }
    }

    @Test
    fun `onEvent should update navigateToSchedule to true when OnLoginClick is provided and PerformLogin returns Right`() =
        runBlockingTest {
            coEvery {
                performLogin(any(), any())
            } coAnswers {
                Unit.buildRight()
            }

            sut.onEvent(OnLoginClick)

            sut.loginViewState.test {
                assertThat(expectItem().navigateToSchedule).isTrue()
            }
        }

    @Test
    fun `onEvent should update signUpError when OnLoginClick is provided and PerformLogin returns Left`() =
        runBlockingTest {
            val useCaseError = RemoteAuthFailure(ERROR_MESSAGE).buildLeft()
            val mapperResult = GeneralError(ERROR_MESSAGE)

            coEvery {
                performLogin(any(), any())
            } coAnswers {
                useCaseError
            }
            coEvery {
                authFailureToLoginErrorMapper.map(useCaseError.error)
            } coAnswers {
                mapperResult
            }

            sut.onEvent(OnLoginClick)

            sut.loginViewState.test {
                assertThat(expectItem().loginError).isEqualTo(mapperResult)
            }
        }
}