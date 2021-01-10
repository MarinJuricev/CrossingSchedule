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
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.model.VillagerInteraction
import com.example.crossingschedule.domain.usecase.schedule.*
import com.example.crossingschedule.presentation.schedule.model.ScheduleViewState
import com.example.crossingschedule.presentation.schedule.model.UiShop
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScheduleViewModel @ViewModelInject constructor(
    private val getActivitiesForDay: GetActivitiesForDay,
    private val todoItemDoneClicked: TodoItemDoneClicked,
    private val createNewTodoItem: CreateNewTodoItem,
    private val deleteTodoItem: DeleteTodoItem,
    private val updateNotes: UpdateNotes,
    private val shopItemDoneClicked: ShopItemDoneClicked,
    private val createVillager: CreateVillager,
    private val deleteVillagerInteraction: DeleteVillagerInteraction,
    private val activitiesToScheduleViewStateMapper: Mapper<ScheduleViewState, CrossingDailyActivities>
) : ViewModel() {

    private val _crossingDailyActivities = MutableLiveData<ScheduleViewState>()
    val crossingDailyActivities: LiveData<ScheduleViewState>
        get() = _crossingDailyActivities

    fun getActivitiesForDay(selectedDay: String) {
        viewModelScope.launch {
            triggerViewStateLoading()

            getActivitiesForDay.invoke(selectedDay).collect {
                when (it) {
                    is Either.Right -> _crossingDailyActivities.postValue(
                        activitiesToScheduleViewStateMapper.map(it.value)
                    )
                    is Either.Left -> Log.d("FAIL SILENTLY FOR NOW", it.error.toString())
                }
            }
        }
    }

    fun onTodoItemChanged(updatedItem: CrossingTodo) {
        viewModelScope.launch {
            todoItemDoneClicked(
                _crossingDailyActivities.value?.crossingTodos,
                updatedItem
            )
        }
    }

    fun onTodoCreated(newTodoMessage: String) {
        viewModelScope.launch {
            when (val result = createNewTodoItem(
                _crossingDailyActivities.value?.crossingTodos,
                newTodoMessage
            )
            ) {
                is Either.Right -> _crossingDailyActivities.postValue(
                    _crossingDailyActivities.value?.copy(errorMessage = "")
                )
                is Either.Left -> _crossingDailyActivities.postValue(
                    _crossingDailyActivities.value?.copy(errorMessage = result.error.errorMessage)
                )
            }
        }
    }

    fun onTodoItemDeleted(
        itemToBeDeleted: CrossingTodo
    ) {
        viewModelScope.launch {
            deleteTodoItem(
                _crossingDailyActivities.value?.crossingTodos,
                itemToBeDeleted
            )
        }
    }

    fun onNotesEdited(updatedNotes: String) {
        viewModelScope.launch {
            updateNotes(updatedNotes)
        }
    }

    fun onShopChanged(updatedShop: UiShop) {
        viewModelScope.launch {
            shopItemDoneClicked(
                _crossingDailyActivities.value?.shops,
                updatedShop
            )
        }
    }

    fun onNewVillagerClicked(
        newVillagerName: String
    ) {
        viewModelScope.launch {
            createVillager(
                _crossingDailyActivities.value?.villagersInteraction,
                newVillagerName
            )
        }
    }

    fun onVillagerInteractionsDeleted(
        villagerInteractionToBeDeleted: VillagerInteraction
    ) {
        viewModelScope.launch {
            deleteVillagerInteraction(
                _crossingDailyActivities.value?.villagersInteraction,
                villagerInteractionToBeDeleted
            )
        }
    }


    private fun triggerViewStateLoading() {
        _crossingDailyActivities.value?.copy(isLoading = true)
    }
}