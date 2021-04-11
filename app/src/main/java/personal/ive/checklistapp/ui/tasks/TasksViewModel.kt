package personal.ive.checklistapp.ui.tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import personal.ive.checklistapp.data.PreferencesManager
import personal.ive.checklistapp.data.SortTasks
import personal.ive.checklistapp.data.Task
import personal.ive.checklistapp.data.TaskDao
import personal.ive.checklistapp.ui.addedittask.AddEditTaskViewModel
import personal.ive.checklistapp.util.exhaustive

/**
 * Created by ivasil on 4/5/2021
 */

class TasksViewModel @ViewModelInject constructor(private val taskDao: TaskDao, private val preferencesManager: PreferencesManager, @Assisted private val savedState: SavedStateHandle): ViewModel() {

    val searchQuery = savedState.getLiveData("searchQuery", "")
    val preferencesFlow = preferencesManager.preferencesFlow
    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEventChannelFlowMessage = tasksEventChannel.receiveAsFlow()

    private val tasksFlow = combine(searchQuery.asFlow(), preferencesFlow) { searchQuery, preferencesFlow ->
        Pair(searchQuery, preferencesFlow)
    }.flatMapLatest { (searchQuery, preferencesFlow) ->
        taskDao.getTasks(searchQuery, sortOrder = preferencesFlow.first, isHideCompletedChecked = preferencesFlow.second)
    }
    val tasks = tasksFlow.asLiveData()

    fun onSortOrderChanged(sortTasks: SortTasks) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortTasks)
    }

    fun onHideCompletedChanged(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckboxSelected(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.updateTask(task.copy(isCompleted = isChecked))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.deleteTask(task)
        tasksEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(task))
    }

    fun onUndoDeleteTask(task: Task) = viewModelScope.launch {
        taskDao.insertTask(task)
    }

    fun onAddNewTaskClick() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateToAddTaskScreen)
    }

    fun onAddEditResult(result: AddEditTaskViewModel.AddEditTaskEventResult) {
        when(result) {
            AddEditTaskViewModel.AddEditTaskEventResult.TASK_ADDED -> { showTaskAddedConfirmationMessage("Task added") }
            AddEditTaskViewModel.AddEditTaskEventResult.TASK_UPDATED -> { showTaskUpdatedConfirmationMessage("Task updated") }
        }.exhaustive
    }

    private fun showTaskUpdatedConfirmationMessage(message: String) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(message))
    }

    private fun showTaskAddedConfirmationMessage(message: String) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(message))
    }

    fun onDeleteAllCompletedClick() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateToDeleteAllCompletedDialog)
    }

    sealed class TasksEvent {
        object NavigateToAddTaskScreen : TasksEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TasksEvent()
        data class ShowUndoDeleteTaskMessage(val task: Task) : TasksEvent()
        data class ShowTaskSavedConfirmationMessage(val message: String) : TasksEvent()
        object NavigateToDeleteAllCompletedDialog : TasksEvent()
    }
}

