package com.example.crossingschedule.presentation.schedule

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.usecase.GetActivitiesForDay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScheduleViewModel @ViewModelInject constructor(
    private val getActivitiesForDay: GetActivitiesForDay,
    private val activitiesToScheduleViewStateMapper: Mapper<ScheduleViewState, CrossingDailyActivities>
) : ViewModel() {

    private val _crossingDailyActivities = MutableLiveData<ScheduleViewState>()
    val crossingDailyActivities: LiveData<ScheduleViewState>
        get() = _crossingDailyActivities

    fun getActivitiesForDay(selectedDay: String) {
        viewModelScope.launch {
            getActivitiesForDay.invoke(selectedDay).collect {
                when (it) {
                    is Either.Success -> _crossingDailyActivities.postValue(
                        activitiesToScheduleViewStateMapper.map(it.value)
                    )
                    is Either.Error -> Log.d("FAIL SILENTLY FOR NOW", it.error.toString())
                }
            }
        }
    }
}