package com.example.crossingschedule.feature.islandSelection.data.mapper

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.islandSelection.data.model.IslandResponse
import com.example.crossingschedule.feature.islandSelection.data.model.RemoteIslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure.RemoteFailure
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class IslandResponseToEitherMapperTest {

    private val remoteIslandInfoToIslandInfoMapper: Mapper<List<IslandInfo>, List<RemoteIslandInfo>> =
        mockk()
    private val stringProvider: StringProvider = mockk()

    private lateinit var sut: Mapper<Either<IslandSelectionFailure, List<IslandInfo>>, CrossingResponse<IslandResponse>>

    @Before
    fun setUp() {
        sut = IslandResponseToEitherMapper(
            remoteIslandInfoToIslandInfoMapper,
            stringProvider
        )
    }

    @Test
    fun `map should return value from remoteIslandInfoToIslandInfoMapper when origin status is CrossingStatusSuccess`() =
        runBlockingTest {
            val islandResponse = IslandResponse(
                availableIslands = listOf()
            )
            val origin = CrossingResponse(
                status = CrossingStatus.Success,
                data = islandResponse,
            )
            val islandInfoList = listOf<IslandInfo>()

            coEvery {
                remoteIslandInfoToIslandInfoMapper.map(origin.data!!.availableIslands)
            } coAnswers { islandInfoList }

            val actualResult = sut.map(origin)
            val expectedResult = islandInfoList.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `map should return RemoteFailure with the origin message when origin status is not CrossingStatusSuccess`() =
        runBlockingTest {
            val errorMessage = "errorMessage"
            val origin = CrossingResponse<IslandResponse>(
                status = CrossingStatus.Fail,
                message = errorMessage,
            )

            val actualResult = sut.map(origin)
            val expectedResult = RemoteFailure(errorMessage).buildLeft()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `map should return RemoteFailure with the a default error message when origin status is not CrossingStatusSuccess and origin message is null`() =
        runBlockingTest {
            val generalErrorMessage = "generalErrorMessage"
            val origin = CrossingResponse<IslandResponse>(
                status = CrossingStatus.Fail,
            )

            every {
                stringProvider.getString(R.string.generic_error_message)
            } answers { generalErrorMessage }


            val actualResult = sut.map(origin)
            val expectedResult = RemoteFailure(generalErrorMessage).buildLeft()

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}