package com.example.crossingschedule.feature.islandSelection.data.mapper

import com.example.crossingschedule.core.model.CrossingDay
import com.example.crossingschedule.core.util.DateHandler
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandSelection.data.model.RemoteIslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val NUMBER_OF_VILLAGERS = 12
private const val LAST_VISITED = 1212321312L
private val HEMISPHERE = Hemisphere.NORTH

@ExperimentalCoroutinesApi
class RemoteIslandInfoToIslandInfoMapperTest {

    private val dateHandler: DateHandler = mockk()

    lateinit var sut: Mapper<List<IslandInfo>, List<RemoteIslandInfo>>

    @Before
    fun setUp() {
        sut = RemoteIslandInfoToIslandInfoMapper(
            dateHandler,
        )
    }

    @Test
    fun `map should return valid IslandInfo`() = runBlockingTest {
        val crossingDay = mockk<CrossingDay>()
        val origin = listOf(
            RemoteIslandInfo(
                ID,
                NAME,
                HEMISPHERE,
                NUMBER_OF_VILLAGERS,
                LAST_VISITED
            )
        )
        coEvery {
            dateHandler.fromTimeStampToCrossingDay(LAST_VISITED)
        } coAnswers { crossingDay }

        val actualResult = sut.map(origin)

        assertThat(actualResult.first().id).isEqualTo(ID)
        assertThat(actualResult.first().name).isEqualTo(NAME)
        assertThat(actualResult.first().hemisphere).isEqualTo(HEMISPHERE)
        assertThat(actualResult.first().numberOfVillagers).isEqualTo(NUMBER_OF_VILLAGERS)
        assertThat(actualResult.first().lastVisited).isEqualTo(crossingDay)
    }
}