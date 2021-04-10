package personal.ive.checklistapp.ui.addedittask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import personal.ive.checklistapp.data.Task
import personal.ive.checklistapp.data.TaskDao

/**
 * Created by ivasil on 4/10/2021
 */

class AddEditTaskViewModel @ViewModelInject constructor(@Assisted private val savedState: SavedStateHandle, private val taskDao: TaskDao) : ViewModel() {

    val task = savedState.get<Task>("task")

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
}