package com.mvvm.endtoend.testing.task

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.mvvm.endtoend.testing.R
import com.mvvm.endtoend.testing.ServiceLocator
import com.mvvm.endtoend.testing.data.Task
import com.mvvm.endtoend.testing.source.FakeAndroidTestRepository
import com.mvvm.endtoend.testing.data.source.TaskRepository
import com.mvvm.endtoend.testing.tasks.TasksFragment
import com.mvvm.endtoend.testing.tasks.TasksFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.endsWith
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class TaskFragmentTest {

    private lateinit var repository: TaskRepository
    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() {
        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the "+" button
        onView(withId(R.id.add_task_fab)).perform(ViewActions.click())

        // THEN - Verify that we navigate to the add screen
        verify(navController).navigate(
            TasksFragmentDirections.actionTasksFragmentDestToTaskDetailFragmentDest(
                null
            )
        //ApplicationProvider.getApplicationContext<Context>().getString(R.string.add_task)
        )
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        repository.saveTask(Task(title = "TITLE1", description = "DESCRIPTION1", complete = false, id = "1"))
        repository.saveTask(Task(title = "TITLE2", description = "DESCRIPTION2", complete = true, id = "2"))
        repository.saveTask(Task(title = "TITLE3", description = "DESCRIPTION3", complete = true, id = "3"))
        repository.saveTask(Task(title = "TITLE4", description = "DESCRIPTION4", complete = true, id = "4"))
        repository.saveTask(Task(title = "TITLE5", description = "DESCRIPTION5", complete = true, id = "5"))
        repository.saveTask(Task(title = "TITLE6", description = "DESCRIPTION6", complete = true, id = "6"))
        repository.saveTask(Task(title = "TITLE7", description = "DESCRIPTION7", complete = true, id = "7"))
        repository.saveTask(Task(title = "TITLE8", description = "DESCRIPTION8", complete = true, id = "8"))
        repository.saveTask(Task(title = "TITLE9", description = "DESCRIPTION9", complete = true, id = "9"))
        repository.saveTask(Task(title = "TITLE10", description = "DESCRIPTION10", complete = true, id = "10"))
        repository.saveTask(Task(title = "TITLE11", description = "DESCRIPTION11", complete = true, id = "11"))
        repository.saveTask(Task(title = "TITLE12", description = "DESCRIPTION12", complete = true, id = "12"))

        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the first list item
        onView(withId(R.id.tasks_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(endsWith("TITLE12"))), click()))


        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(
            TasksFragmentDirections.actionTasksFragmentDestToTaskDetailFragmentDest( "12")
        )
    }
}