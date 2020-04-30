package com.mvvm.endtoend.testing.addedittask

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.mvvm.endtoend.testing.R
import com.mvvm.endtoend.testing.ServiceLocator
import com.mvvm.endtoend.testing.data.Task
import com.mvvm.endtoend.testing.source.FakeAndroidTestRepository
import com.mvvm.endtoend.testing.data.source.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AddEditFragmentTest {

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
    fun taskDetails_displayUi() = runBlockingTest {

        val activeTask = Task(id = "15", title = "Task Title", description = "Happy Coding", complete = false)
        repository.saveTask(activeTask)

        val bundle = AddEditFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<AddEditFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.add_task_title_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.add_task_title_edit_text)).check(matches(withText("Task Title")))
        onView(withId(R.id.add_task_description_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.add_task_description_edit_text)).check(matches(withText("Happy Coding")))
        onView(withId(R.id.save_task_fab)).check(matches(isDisplayed()))
    }
}