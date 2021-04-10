package personal.ive.checklistapp.ui.addedittask

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import personal.ive.checklistapp.R
import personal.ive.checklistapp.databinding.FragmentAddEditTaskBinding

/**
 * Created by ivasil on 4/10/2021
 */

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    private val addEditTaskViewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)

        binding.apply {
            editTextTaskName.setText(addEditTaskViewModel.taskName)
            checkboxImportant.isChecked = addEditTaskViewModel.taskImportance
            checkboxImportant.jumpDrawablesToCurrentState()
            textViewDateCreated.isVisible = addEditTaskViewModel.task != null
            textViewDateCreated.text = "Created: ${addEditTaskViewModel.task?.dateCreatedFormatted}"
        }
    }
}