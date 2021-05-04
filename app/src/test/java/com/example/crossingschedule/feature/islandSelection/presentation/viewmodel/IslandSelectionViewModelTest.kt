package com.example.crossingschedule.feature.islandSelection.presentation.viewmodel

import app.cash.turbine.test
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.fakeIslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure.*
import com.example.crossingschedule.feature.islandSelection.domain.usecase.FilterIslandsByHemisphere
import com.example.crossingschedule.feature.islandSelection.domain.usecase.GetIslands
import com.example.crossingschedule.feature.islandSelection.presentation.model.IslandSelectionEvent
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

@ExperimentalTime
@ExperimentalCoroutinesApi
class IslandSelectionViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getIslands: GetIslands = mockk()
    private val filterIslandsByHemisphere: FilterIslandsByHemisphere = mockk()

    lateinit var sut: IslandSelectionViewModel

    @Before
    fun setUp() {
        sut = IslandSelectionViewModel(
            getIslands,
            filterIslandsByHemisphere,
        )
    }

    @Test
    fun `should update islandData and unfilteredIslandData data when GetAllIslands is provided and getIslands returns Right`() =
        runBlockingTest {
            val islandInfoList = listOf(fakeIslandInfo)

            coEvery { getIslands() } coAnswers {
                islandInfoList.buildRight()
            }

            sut.onEvent(IslandSelectionEvent.GetAllIslands)

            sut.islandSelectionViewState.test {
                val expectedViewState = expectItem()

                assertThat(expectedViewState.islandData).isEqualTo(islandInfoList)
                assertThat(expectedViewState.unfilteredIslandData).isEqualTo(islandInfoList)
            }
        }

    @Test
    fun `should update errorMessage when GetAllIslands is provided and getIslands returns Left`() =
        runBlockingTest {
            val errorMessage = "errorMessage"

            coEvery { getIslands() } coAnswers {
                RemoteFailure(errorMessage).buildLeft()
            }

            sut.onEvent(IslandSelectionEvent.GetAllIslands)

            sut.islandSelectionViewState.test {
                assertThat(expectItem().errorMessage).isEqualTo(errorMessage)
            }
        }

    @Test
    fun `should update filterIslandExpanded when IslandFilterGroupClicked is provided`() =
        runBlockingTest {
            sut.onEvent(IslandSelectionEvent.IslandFilterGroupClicked)

            sut.islandSelectionViewState.test {
                assertThat(expectItem().filterIslandExpanded).isTrue()
            }
        }

    @Test
    fun `should update islandData and hemisphereToFilter when IslandFilterNewHemisphereSort is provided`() =
        runBlockingTest {
            val newIslandData = listOf(fakeIslandInfo)
            val newHemisphere = Hemisphere.NORTH

            coEvery {
                filterIslandsByHemisphere(listOf(), newHemisphere)
            } coAnswers { newIslandData }

            sut.onEvent(IslandSelectionEvent.IslandFilterNewHemisphereSort(newHemisphere))

            sut.islandSelectionViewState.test {
                val expectedItem = expectItem()

                assertThat(expectedItem.islandData).isEqualTo(newIslandData)
                assertThat(expectedItem.hemisphereToFilter).isEqualTo(newHemisphere)
            }
        }
}