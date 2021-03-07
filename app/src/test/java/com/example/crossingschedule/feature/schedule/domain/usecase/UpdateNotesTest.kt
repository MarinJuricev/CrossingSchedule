package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateNotesTest {

    private val activitiesRepository: ActivitiesRepository = mockk()

    private lateinit var sut: UpdateNotes

    @Before
    fun setUp() {
        sut = UpdateNotes(
            activitiesRepository
        )
    }

    @Test
    fun `updateNotes should trigger repository updateNotes with the provided note`() =
        runBlockingTest {
            val updatedNotes = "updatedNotes"
            val repositoryResult = Either.Right(Unit)

            coEvery {
                activitiesRepository.updateNotes(updatedNotes, "")
            } coAnswers {
                repositoryResult
            }

            val actualResult = sut(updatedNotes, "")

            assert(actualResult == repositoryResult)
            coVerify { activitiesRepository.updateNotes(updatedNotes, "") }
        }
}