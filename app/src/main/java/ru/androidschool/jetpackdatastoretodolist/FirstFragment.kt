package ru.androidschool.jetpackdatastoretodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_first.*
import ru.androidschool.jetpackdatastoretodolist.data.SortOrder
import ru.androidschool.jetpackdatastoretodolist.data.MockTasksRepository
import ru.androidschool.jetpackdatastoretodolist.data.UserPreferencesRepository
import ru.androidschool.jetpackdatastoretodolist.ui.TaskAdapter
import ru.androidschool.jetpackdatastoretodolist.ui.TasksViewModel
import ru.androidschool.jetpackdatastoretodolist.ui.TasksViewModelFactory

class FirstFragment : Fragment() {

    private lateinit var viewModel: TasksViewModel
    private val adapter = TaskAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasks_recycler_view.adapter = adapter

        sort_priority.setOnCheckedChangeListener { view, b ->
            viewModel.enableSortByPriority(b)
        }

        sort_deadline.setOnCheckedChangeListener { view, b ->
            viewModel.enableSortByDeadline(b)
        }

        viewModel = ViewModelProvider(
            this,
            TasksViewModelFactory(MockTasksRepository, UserPreferencesRepository(requireContext()))
        ).get(TasksViewModel::class.java)


        viewModel.tasksUiModel.observe(requireActivity()) { tasksUiModel ->
            adapter.refresh(tasksUiModel.tasks)
            updateSort(tasksUiModel.sortOrder)
        }
    }

    private fun updateSort(sortOrder: SortOrder) {
        sort_deadline.isChecked =
            sortOrder == SortOrder.BY_DEADLINE || sortOrder == SortOrder.BY_DEADLINE_AND_PRIORITY
        sort_priority.isChecked =
            sortOrder == SortOrder.BY_PRIORITY || sortOrder == SortOrder.BY_DEADLINE_AND_PRIORITY
    }
}