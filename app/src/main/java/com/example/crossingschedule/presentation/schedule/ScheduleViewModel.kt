package com.example.crossingschedule.presentation.schedule

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScheduleViewModel @ViewModelInject constructor(
    private val repository: ActivitiesRepository
) : ViewModel() {

    val crossingDailyActivities = MutableLiveData<CrossingDailyActivities>()

    fun getActivitiesForDay() {
        viewModelScope.launch {
            repository.getCurrentActivities().collect {
                when (it) {
                    is Either.Success -> crossingDailyActivities.postValue(it.value)
                    is Either.Error -> Log.d("FAIL SILENTLY FOR NOW", "FAIL SILENTLY FOR NOW")
                }
            }
        }
    }
}