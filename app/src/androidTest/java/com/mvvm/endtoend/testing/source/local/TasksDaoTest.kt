package com.mvvm.endtoend.testing.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mvvm.endtoend.testing.data.Task
import com.mvvm.endtoend.testing.data.source.local.ToDoDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    private lateinit var database: ToDoDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            ToDoDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertTaskAndGetById() = runBlockingTest {
        // GIVEN - insert a task
        val task = Task("title", "description")
        database.taskDao().insertTask(task)

        // WHEN - Get the task by id from the database
        val loaded = database.taskDao().getTaskById(task.id)

        // THEN - The loaded data contains the expected values
        assertThat<Task>(loaded as Task, notNullValue())
        assertThat(loaded.id, `is`(task.id))
        assertThat(loaded.title, `is`(task.title))
        assertThat(loaded.description, `is`(task.description))
        assertThat(loaded.complete, `is`(task.complete))
    }

    @Test
    fun updateTaskAndGetById() = runBlockingTest {
        // When inserting a task
        val originalTask = Task("title", "description")
        database.taskDao().insertTask(originalTask)

        // When the task is updated
        val updatedTask = Task(title = "new title", description = "new description", complete = true, id = originalTask.id)
        database.taskDao().updateTask(updatedTask)

        // THEN - The loaded data contains the expected values
        val loaded = database.taskDao().getTaskById(originalTask.id)
        assertThat(loaded?.id, `is`(originalTask.id))
        assertThat(loaded?.title, `is`("new title"))
        assertThat(loaded?.description, `is`("new description"))
        assertThat(loaded?.complete, `is`(true))
    }
}
