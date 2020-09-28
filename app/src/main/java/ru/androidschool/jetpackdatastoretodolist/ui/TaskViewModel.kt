package ru.androidschool.jetpackdatastoretodolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.androidschool.jetpackdatastoretodolist.data.*

data class TasksUiModel(
    val tasks: List<Task>,
    val showCompleted: Boolean,
    val sortOrder: SortOrder
)

class TasksViewModel(
    repository: MockTasksRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    private val tasksUiModelFlow = combine(
        repository.tasks,
        userPreferencesFlow
    ) { tasks: List<Task>, userPreferences: UserPreferences ->
        return@combine TasksUiModel(
            tasks = filterSortTasks(
                tasks,
                true,
                userPreferences.sortOrder
            ),
            showCompleted = true,
            sortOrder = userPreferences.sortOrder
        )
    }
    val tasksUiModel = tasksUiModelFlow.asLiveData()

    private fun filterSortTasks(
        tasks: List<Task>,
        showCompleted: Boolean,
        sortOrder: SortOrder
    ): List<Task> {
        // filter the tasks
        val filteredTasks = if (showCompleted) {
            tasks
        } else {
            tasks.filter { !it.completed }
        }
        // sort the tasks
        return when (sortOrder) {
            SortOrder.NONE -> filteredTasks
            SortOrder.BY_DEADLINE -> filteredTasks.sortedByDescending { it.deadline }
            SortOrder.BY_PRIORITY -> filteredTasks.sortedBy { it.priority }
            SortOrder.BY_DEADLINE_AND_PRIORITY -> filteredTasks.sortedWith(
                compareByDescending<Task> { it.deadline }.thenBy { it.priority }
            )
        }
    }

    fun enableSortByDeadline(enable: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortByDeadline(enable)
        }
    }

    fun enableSortByPriority(enable: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortByPriority(enable)
        }
    }
}

class TasksViewModelFactory(
    private val repository: MockTasksRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
