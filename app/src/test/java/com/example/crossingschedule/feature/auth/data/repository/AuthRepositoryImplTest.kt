package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.model.*
import com.example.crossingschedule.core.model.AuthFailure.RemoteAuthFailure
import com.example.crossingschedule.core.util.EncryptedPrefsService
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.CreateAccountBody
import com.example.crossingschedule.feature.auth.data.model.LoginUserResponse
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val USERNAME = "USERNAME"
private const val EMAIL = "EMAIL"
private const val PASSWORD = "PASSWORD"
private const val ID_TOKEN = "ID_TOKEN"

private val AUTH_USER_RESPONSE = LoginUserResponse(CrossingStatus.Success, null, null)

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

    private val authProvider: AuthProvider = mockk()
    private val authApiService: AuthApiService = mockk()
    private val encryptedPrefsService: EncryptedPrefsService = mockk()
    private val loginResponseToEitherMapper: Mapper<Either<AuthFailure, Unit>, LoginUserResponse> =
        mockk()

    lateinit var sut: AuthRepository

    @Before
    fun setUp() {
        sut = AuthRepositoryImpl(
            authProvider,
            authApiService,
            encryptedPrefsService,
            loginResponseToEitherMapper
        )

        prepareMockResponses()
    }

    private fun prepareMockResponses() {
        coEvery {
            encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, ID_TOKEN)
        } just runs

        coEvery {
            authProvider.getUserIdToken()
        } coAnswers { ID_TOKEN }

        coEvery {
            authApiService.loginUser()
        } coAnswers { AUTH_USER_RESPONSE } //TODO This really shouldn't be a string, it should be some kind of POKO

        coEvery {
            loginResponseToEitherMapper.map(AUTH_USER_RESPONSE)
        } coAnswers { Unit.buildRight() }
    }

    @Test
    fun `login should return EitherLeft when the authProvider calls returns a failure`() =
        runBlockingTest {
            val failure = RemoteAuthFailure("").buildLeft()

            coEvery {
                authProvider.login(EMAIL, PASSWORD)
            } coAnswers {
                failure
            }

            val actualResult = sut.login(EMAIL, PASSWORD)

            coVerify(exactly = 0) { encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, ID_TOKEN) }
            coVerify(exactly = 0) { loginResponseToEitherMapper.map(AUTH_USER_RESPONSE) }

            assert(actualResult == failure)
        }

    @Test
    fun `login should should trigger encryptedPrefsService and loginMapper when authProvider calls returns a success`() =
        runBlockingTest {
            val success = Unit.buildRight()

            coEvery {
                authProvider.login(EMAIL, PASSWORD)
            } coAnswers {
                success
            }

            val actualResult = sut.login(EMAIL, PASSWORD)

            coVerify(exactly = 1) { encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, ID_TOKEN) }
            coVerify(exactly = 1) { loginResponseToEitherMapper.map(AUTH_USER_RESPONSE) }

            assert(actualResult == success)
        }

    @Test
    fun `createAccount should return EitherLeft when the authProvider calls returns a failure`() =
        runBlockingTest {
            val failure = RemoteAuthFailure("").buildLeft()

            coEvery {
                authProvider.createAccount(EMAIL, PASSWORD)
            } coAnswers {
                failure
            }

            val actualResult = sut.createAccount(USERNAME, EMAIL, PASSWORD)

            coVerify(exactly = 0) { encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, ID_TOKEN) }
            coVerify(exactly = 0) { loginResponseToEitherMapper.map(AUTH_USER_RESPONSE) }

            assert(actualResult == failure)
        }

    @Test
    fun `createAccount should should trigger encryptedPrefsService and loginMapper when authProvider calls returns a success`() =
        runBlockingTest {
            val success = Unit.buildRight()
            val createAccountBody = CreateAccountBody(USERNAME)

            coEvery { authProvider.createAccount(EMAIL, PASSWORD) } coAnswers { success }
            coEvery { authApiService.createAccount(createAccountBody) } coAnswers { AUTH_USER_RESPONSE }

            val actualResult = sut.createAccount(USERNAME, EMAIL, PASSWORD)

            coVerify(exactly = 1) { encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, ID_TOKEN) }
            coVerify(exactly = 1) { loginResponseToEitherMapper.map(AUTH_USER_RESPONSE) }

            assert(actualResult == success)
        }
}