package personal.ive.checklistapp.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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

/**
 * Created by ivasil on 4/5/2021
 */

class TasksViewModel @ViewModelInject constructor(private val taskDao: TaskDao, private val preferencesManager: PreferencesManager): ViewModel() {

    val searchQuery = MutableStateFlow("")
    val preferencesFlow = preferencesManager.preferencesFlow
    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEventChannelFlowMessage = tasksEventChannel.receiveAsFlow()

    private val tasksFlow = combine(searchQuery, preferencesFlow) { searchQuery, preferencesFlow ->
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

    fun onTaskSelected(task: Task) {}

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

    sealed class TasksEvent {
        data class ShowUndoDeleteTaskMessage(val task: Task) : TasksEvent()
    }
}

