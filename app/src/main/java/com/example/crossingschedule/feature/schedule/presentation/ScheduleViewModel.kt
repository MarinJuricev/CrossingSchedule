package com.example.crossingschedule.feature.schedule.presentation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPriceType
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.usecase.*
import com.example.crossingschedule.feature.schedule.presentation.model.ScheduleViewState
import com.example.crossingschedule.feature.schedule.presentation.model.UiShop
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
    private val updateTurnipPrices: UpdateTurnipPrices,
    private val villagerInteractionGiftClicked: VillagerInteractionGiftClicked,
    private val villagerInteractionTalkedToClicked: VillagerInteractionTalkedToClicked,
    private val activitiesToScheduleViewStateMapper: Mapper<ScheduleViewState, CrossingDailyActivities>
) : ViewModel() {

    private val _crossingDailyActivities = MutableLiveData(ScheduleViewState())
    val crossingDailyActivities: LiveData<ScheduleViewState> = _crossingDailyActivities

    fun getActivitiesForDay(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        triggerViewStateLoading()
        viewModelScope.launch {
            getActivitiesForDay.invoke(selectedYear, selectedMonth, selectedDay).collect {
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

    fun onTurnipPriceChange(
        turnipPriceType: TurnipPriceType,
        updatedPrice: String
    ) {
        viewModelScope.launch {
            updateTurnipPrices(
                _crossingDailyActivities.value?.turnipPrices,
                turnipPriceType,
                updatedPrice
            )
        }
    }

    fun onVillagerInteractionGiftClicked(
        villagerInteractionToChange: VillagerInteraction,
        currentVillagerInteraction: List<VillagerInteraction>
    ) {
        viewModelScope.launch {
            villagerInteractionGiftClicked(
                villagerInteractionToChange,
                currentVillagerInteraction
            )
        }
    }

    fun onVillagerInteractionTalkedToClicked(
        villagerInteractionToChange: VillagerInteraction,
        currentVillagerInteraction: List<VillagerInteraction>
    ) {
        viewModelScope.launch {
            villagerInteractionTalkedToClicked(
                villagerInteractionToChange,
                currentVillagerInteraction
            )
        }
    }

    private fun triggerViewStateLoading() {
        _crossingDailyActivities.value = _crossingDailyActivities.value?.copy(isLoading = true)
    }
}