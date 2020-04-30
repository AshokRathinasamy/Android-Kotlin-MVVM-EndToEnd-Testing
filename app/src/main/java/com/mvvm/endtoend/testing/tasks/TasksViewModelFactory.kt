package com.mvvm.endtoend.testing.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.endtoend.testing.data.source.TaskRepository

class TasksViewModelFactory(
    private val repository: TaskRepository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(repository) as T
    }
}