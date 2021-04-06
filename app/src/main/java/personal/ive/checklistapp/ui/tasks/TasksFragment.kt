package personal.ive.checklistapp.ui.tasks

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import personal.ive.checklistapp.R

/**
 * Created by ivasil on 4/4/2021
 */

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val tasksViewModel: TasksViewModel by viewModels()
}