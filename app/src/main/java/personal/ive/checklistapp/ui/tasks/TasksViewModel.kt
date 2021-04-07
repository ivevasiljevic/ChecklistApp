package personal.ive.checklistapp.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import personal.ive.checklistapp.data.TaskDao

/**
 * Created by ivasil on 4/5/2021
 */

class TasksViewModel @ViewModelInject constructor(private val taskDao: TaskDao): ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortTasks.BY_NAME)
    val isHideCompletedChecked = MutableStateFlow(false)

    private val tasksFlow = combine(searchQuery, sortOrder, isHideCompletedChecked) { searchQuery, sortOrder, isHideCompletedChecked ->
        Triple(searchQuery, sortOrder, isHideCompletedChecked)
    }.flatMapLatest { (searchQuery, sortOrder, isHideCompletedChecked) ->
        taskDao.getTasks(searchQuery, sortOrder, isHideCompletedChecked)
    }
    val tasks = tasksFlow.asLiveData()
}

enum class SortTasks{ BY_NAME, BY_DATE }