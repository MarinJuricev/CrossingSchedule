package com.example.crossingschedule.feature.islandSelection.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.repository.IslandSelectionRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetIslandsTest {

    private val islandSelectionRepository: IslandSelectionRepository = mockk()
    private lateinit var sut: GetIslands

    @Before
    fun setUp() {
        sut = GetIslands(
            islandSelectionRepository
        )
    }

    @Test
    fun `invoke should return value from islandSelectionRepository`() = runBlockingTest {
        val repositoryResult: Either<IslandSelectionFailure, List<IslandInfo>> = listOf<IslandInfo>().buildRight()

        coEvery {
            islandSelectionRepository.getIslands()
        } coAnswers { repositoryResult }

        val actualResult = sut()

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}