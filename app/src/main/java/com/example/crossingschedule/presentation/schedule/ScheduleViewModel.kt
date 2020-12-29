package com.example.crossingschedule.presentation.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crossingschedule.domain.model.CrossingDailyActivities

class ScheduleViewModel : ViewModel() {

    val crossingDailyActivities = MutableLiveData<CrossingDailyActivities>()
}