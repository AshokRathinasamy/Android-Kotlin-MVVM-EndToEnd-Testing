package com.mvvm.endtoend.testing

import android.app.Application
import com.mvvm.endtoend.testing.data.source.TaskRepository

class TaskApplication : Application() {

    val taskRepository: TaskRepository
        get() = ServiceLocator.provideTasksRepository(this)

}