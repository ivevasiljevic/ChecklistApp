package personal.ive.checklistapp.ui.deleteallcompleted

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ivasil on 4/11/2021
 */

@AndroidEntryPoint
class DeleteAllCompletedDialogFragment : DialogFragment() {

    private val deleteAllCompletedViewModel: DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation dialog")
            .setMessage("Do you want to delete all completed task?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes")  { _, _->
                deleteAllCompletedViewModel.onConfirmClick()
            }
            .create()
}