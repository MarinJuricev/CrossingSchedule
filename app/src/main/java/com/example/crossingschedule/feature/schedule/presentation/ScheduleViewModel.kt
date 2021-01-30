package com.example.crossingschedule.feature.schedule.presentation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.IDateHandler
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPriceType
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.usecase.*
import com.example.crossingschedule.feature.schedule.presentation.model.ScheduleViewState
import com.example.crossingschedule.feature.schedule.presentation.model.UiShop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScheduleViewModel @ViewModelInject constructor(
    private val getActivitiesForDay: GetActivitiesForDay,
    private val dateHandler: IDateHandler,
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

    private val _crossingDailyActivities = MutableStateFlow(ScheduleViewState())
    val crossingDailyActivities: StateFlow<ScheduleViewState> = _crossingDailyActivities

    fun getActivitiesForDay(
        selectedYear: Int,
        selectedMonth: Int,
        selectedDay: Int
    ) {
        triggerViewStateLoading()
        viewModelScope.launch {
            getActivitiesForDay.invoke(selectedYear, selectedMonth, selectedDay).collect {
                when (it) {
                    is Either.Right -> _crossingDailyActivities.value =
                        activitiesToScheduleViewStateMapper.map(it.value)
                    is Either.Left -> Log.d("FAIL SILENTLY FOR NOW", it.error.toString())
                }
            }
        }
    }

    // Only gets called on the app launch, isLoading should already be set from the viewStateMapper,
    // there is no need set it here again
    fun getActivitiesForToday() {
        val currentCrossingDay = dateHandler.provideCurrentCrossingDay()

        getActivitiesForDay(
            currentCrossingDay.year,
            currentCrossingDay.month,
            currentCrossingDay.day
        )
    }


    fun onTodoItemChanged(updatedItem: CrossingTodo) {
        viewModelScope.launch {
            todoItemDoneClicked(
                _crossingDailyActivities.value.crossingTodos,
                _crossingDailyActivities.value.dateOptions.formattedDate,
                updatedItem,
            )
        }
    }

    fun onTodoCreated(newTodoMessage: String) {
        viewModelScope.launch {
            when (val result = createNewTodoItem(
                _crossingDailyActivities.value.crossingTodos,
                _crossingDailyActivities.value.dateOptions.formattedDate,
                newTodoMessage
            )
            ) {
                is Either.Right -> _crossingDailyActivities.value =
                    _crossingDailyActivities.value.copy(errorMessage = "")
                is Either.Left -> _crossingDailyActivities.value =
                    _crossingDailyActivities.value.copy(errorMessage = result.error.errorMessage)
            }
        }
    }

    fun onTodoItemDeleted(
        itemToBeDeleted: CrossingTodo
    ) {
        viewModelScope.launch {
            deleteTodoItem(
                _crossingDailyActivities.value.crossingTodos,
                _crossingDailyActivities.value.dateOptions.formattedDate,
                itemToBeDeleted
            )
        }
    }

    fun onNotesEdited(updatedNotes: String) {
        viewModelScope.launch {
            updateNotes(
                updatedNotes = updatedNotes,
                currentDate = _crossingDailyActivities.value.dateOptions.formattedDate
            )
        }
    }

    fun onShopChanged(updatedShop: UiShop) {
        viewModelScope.launch {
            shopItemDoneClicked(
                _crossingDailyActivities.value.shops,
                _crossingDailyActivities.value.dateOptions.formattedDate,
                updatedShop
            )
        }
    }

    fun onNewVillagerClicked(
        newVillagerName: String
    ) {
        viewModelScope.launch {
            createVillager(
                _crossingDailyActivities.value.villagersInteraction,
                _crossingDailyActivities.value.dateOptions.formattedDate,
                newVillagerName
            )
        }
    }

    fun onVillagerInteractionsDeleted(
        villagerInteractionToBeDeleted: VillagerInteraction
    ) {
        viewModelScope.launch {
            deleteVillagerInteraction(
                _crossingDailyActivities.value.villagersInteraction,
                _crossingDailyActivities.value.dateOptions.formattedDate,
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
                _crossingDailyActivities.value.turnipPrices,
                _crossingDailyActivities.value.dateOptions.formattedDate,
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
                _crossingDailyActivities.value.dateOptions.formattedDate,
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
                _crossingDailyActivities.value.dateOptions.formattedDate,
                villagerInteractionToChange,
                currentVillagerInteraction
            )
        }
    }

    private fun triggerViewStateLoading() {
        _crossingDailyActivities.value = _crossingDailyActivities.value.copy(isLoading = true)
    }
}