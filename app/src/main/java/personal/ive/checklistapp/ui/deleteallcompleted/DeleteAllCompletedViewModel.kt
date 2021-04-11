package personal.ive.checklistapp.ui.deleteallcompleted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import personal.ive.checklistapp.data.TaskDao
import personal.ive.checklistapp.di.ApplicationScope

/**
 * Created by ivasil on 4/11/2021
 */

class DeleteAllCompletedViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @ApplicationScope private val applicationScope: CoroutineScope
): ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        taskDao.deleteAllCompleted()
    }
}