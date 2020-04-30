package com.mvvm.endtoend.testing.data.source

import androidx.lifecycle.LiveData
import com.mvvm.endtoend.testing.data.Task
import com.mvvm.endtoend.testing.data.Result


interface TaskRepository {

    fun observeTasks(): LiveData<Result<List<Task>>>

    suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>>

    suspend fun refreshTasks()

    suspend fun taskCount() : Int

    fun observeTask(taskId: String): LiveData<Result<Task>>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task>

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}