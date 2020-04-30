package com.mvvm.endtoend.testing.tasks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvvm.endtoend.testing.util.setupSnackbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mvvm.endtoend.testing.util.EventObserver
import com.mvvm.endtoend.testing.R
import com.mvvm.endtoend.testing.TaskApplication
import com.mvvm.endtoend.testing.databinding.FragmentTaskBinding

class TasksFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentTaskBinding
    private val viewModel by viewModels<TasksViewModel> {
        TasksViewModelFactory((requireContext().applicationContext as TaskApplication).taskRepository)
    }

    private val args: TasksFragmentArgs by navArgs()

    companion object {
        var flagRefresh : Boolean = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        viewDataBinding = FragmentTaskBinding.inflate(inflater, container, false).apply {
            viewModels = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()
        setupFab()

        viewModel.loadTasks(flagRefresh)
        flagRefresh = false
    }

    private fun setupListAdapter() {
        val taskListAdapter = TasksAdapter(viewModel)
        viewDataBinding.tasksList.adapter = taskListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_filter -> {
                navigateToAddNewTask(null)
                true
            }
            R.id.menu_refresh -> {
                viewModel.loadTasks(true)
                true
            }
            else -> false
        }

    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.add_task_fab)?.let {
            it.setOnClickListener {
                navigateToAddNewTask(null)
            }
        }
    }

    private fun setupNavigation() {
        viewModel.openTaskEvent.observe(viewLifecycleOwner, EventObserver{
            navigateToAddNewTask(it)
        })
    }

    private fun navigateToAddNewTask(taskId : String?) {
        val action = TasksFragmentDirections
            .actionTasksFragmentDestToTaskDetailFragmentDest(taskId)
        findNavController().navigate(action)
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
        arguments?.let {
            viewModel.showEditResultMessage(args.userMessage)
        }
    }

}