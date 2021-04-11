package personal.ive.checklistapp.ui.addedittask

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import personal.ive.checklistapp.R
import personal.ive.checklistapp.databinding.FragmentAddEditTaskBinding
import personal.ive.checklistapp.util.exhaustive

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

            editTextTaskName.addTextChangedListener {
                addEditTaskViewModel.taskName = it.toString()
            }
            checkboxImportant.setOnCheckedChangeListener { _, isChecked ->
                addEditTaskViewModel.taskImportance = isChecked
            }

            fabSaveTask.setOnClickListener {
                addEditTaskViewModel.onSaveClick()
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                addEditTaskViewModel.addEditTaskChannelFlowMessage.collect { event ->
                    when(event) {
                        is AddEditTaskViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                            binding.editTextTaskName.clearFocus()
                            setFragmentResult("add_edit_request", bundleOf("add_edit_result" to event.result.name))
                            findNavController().popBackStack()
                        }
                        is AddEditTaskViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
                            Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                        }
                    }.exhaustive
                }
            }
        }
    }
}