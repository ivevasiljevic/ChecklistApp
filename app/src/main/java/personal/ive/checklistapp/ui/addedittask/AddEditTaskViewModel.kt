package personal.ive.checklistapp.ui.addedittask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import personal.ive.checklistapp.data.Task
import personal.ive.checklistapp.data.TaskDao

/**
 * Created by ivasil on 4/10/2021
 */

class AddEditTaskViewModel @ViewModelInject constructor(@Assisted private val savedState: SavedStateHandle, private val taskDao: TaskDao) : ViewModel() {

    val task = savedState.get<Task>("task")
    private val addEditTaskChannel = Channel<AddEditTaskEvent>()
    val addEditTaskChannelFlowMessage = addEditTaskChannel.receiveAsFlow()

    var taskName = savedState.get<String>("taskName") ?: task?.name ?: ""
        set(value) {
            field = value
            savedState.set("taskName", value)
        }

    var taskImportance = savedState.get<Boolean>("taskImportance") ?: task?.isImportant ?: false
        set(value) {
            field = value
            savedState.set("taskImportance", value)
        }

    fun onSaveClick() {
        if(taskName.isBlank()) {
            showInvalidInputMessage("Name cannot be blank")
            return
        }

        if(task != null) {
            val updatedTask = task.copy(name = taskName, isImportant = taskImportance)
            updateTask(updatedTask)
        }
        else {
            val createdTask = Task(name = taskName, isImportant = taskImportance)
            createTask(createdTask)
        }
    }

    private fun showInvalidInputMessage(message: String) = viewModelScope.launch {
        addEditTaskChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(message))
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.updateTask(task)
        addEditTaskChannel.send(AddEditTaskEvent.NavigateBackWithResult(AddEditTaskEventResult.TASK_UPDATED))
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insertTask(task)
        addEditTaskChannel.send(AddEditTaskEvent.NavigateBackWithResult(AddEditTaskEventResult.TASK_ADDED))
    }

    sealed class AddEditTaskEvent {
        data class ShowInvalidInputMessage(val message: String) : AddEditTaskEvent()
        data class NavigateBackWithResult(val result: AddEditTaskEventResult) : AddEditTaskEvent()
    }

    enum class AddEditTaskEventResult { TASK_ADDED, TASK_UPDATED }
}