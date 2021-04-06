package personal.ive.checklistapp.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import personal.ive.checklistapp.data.TaskDao

/**
 * Created by ivasil on 4/5/2021
 */

class TasksViewModel @ViewModelInject constructor(private val taskDao: TaskDao): ViewModel() {
}