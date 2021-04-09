package personal.ive.checklistapp.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import personal.ive.checklistapp.R
import personal.ive.checklistapp.data.SortTasks
import personal.ive.checklistapp.databinding.FragmentTasksBinding
import personal.ive.checklistapp.util.onQueryTextChanged

/**
 * Created by ivasil on 4/4/2021
 */

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val tasksViewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)
        val tasksAdapter = TasksAdapter()

        binding.recyclerViewTasks.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        tasksViewModel.tasks.observe(viewLifecycleOwner) {
            tasksAdapter.submitList(it)
        }

        setHasOptionsMenu(true)
    }

    private fun setupOptionMenuSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            tasksViewModel.searchQuery.value = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_hide_completed_tasks).isChecked = tasksViewModel.preferencesFlow.first().second
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)
        setupOptionMenuSearchView(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.action_sort_by_name -> {
                tasksViewModel.onSortOrderChanged(SortTasks.BY_NAME)
                true
            }
            R.id.action_sort_by_date -> {
                tasksViewModel.onSortOrderChanged(SortTasks.BY_DATE)
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked = !item.isChecked
                tasksViewModel.onHideCompletedChanged(item.isChecked)
                true
            }
            R.id.action_delete_completed_tasks -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}