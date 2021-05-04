package com.example.crossingschedule.feature.islandSelection.data.repository

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.islandSelection.data.model.IslandResponse
import com.example.crossingschedule.feature.islandSelection.data.service.IslandSelectionApiService
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure.*
import com.example.crossingschedule.feature.islandSelection.domain.repository.IslandSelectionRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ERROR_MESSAGE = "errorMessage"

@ExperimentalCoroutinesApi
class IslandSelectionRepositoryImplTest {

    private val islandSelectionApiService: IslandSelectionApiService = mockk()
    private val islandResponseToEitherMapper: Mapper<
            Either<IslandSelectionFailure, List<IslandInfo>>,
            CrossingResponse<IslandResponse>> = mockk()
    private val stringProvider: StringProvider = mockk()

    private lateinit var sut: IslandSelectionRepository

    @Before
    fun setUp() {
        sut = IslandSelectionRepositoryImpl(
            islandSelectionApiService,
            islandResponseToEitherMapper,
            stringProvider,
        )

        every {
            stringProvider.getString(R.string.generic_error_message)
        } answers { ERROR_MESSAGE }
    }

    @Test
    fun `getIsland should return value from islandResponseToEitherMapper when islandSelectionApiService returns Right`() =
        runBlockingTest {
            val response = CrossingResponse<IslandResponse>(
                status = CrossingStatus.Success
            )
            val mapperResult = listOf<IslandInfo>().buildRight()

            coEvery {
                islandSelectionApiService.getIslands()
            } coAnswers { response }
            coEvery {
                islandResponseToEitherMapper.map(response)
            } coAnswers { mapperResult }

            val actualResult = sut.getIslands()

            assertThat(actualResult).isEqualTo(mapperResult)
        }

    @Test
    fun `getIsland should return RemoteFailure when islandSelectionApiService returns Left`() =
        runBlockingTest {
            coEvery {
                islandSelectionApiService.getIslands()
            } coAnswers { throw Exception(ERROR_MESSAGE) }

            val actualResult = sut.getIslands()
            val expectedResult = RemoteFailure(ERROR_MESSAGE).buildLeft()

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}