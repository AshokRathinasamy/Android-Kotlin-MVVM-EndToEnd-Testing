package com.mvvm.endtoend.testing

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mvvm.endtoend.testing.util.EspressoIdlingResource
import com.mvvm.endtoend.testing.data.source.TaskRepository
import com.mvvm.endtoend.testing.tasks.TasksActivity
import com.mvvm.endtoend.testing.util.DataBindingIdlingResource
import com.mvvm.endtoend.testing.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.endsWith
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TaskActivityTest {

    private lateinit var repository: TaskRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        repository =
            ServiceLocator.provideTasksRepository(
                getApplicationContext()
            )
        runBlocking {
            repository.getTasks(true)
        }
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun editTask() = runBlocking {

        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        Thread.sleep(1000)

        // Click on the task on the list and verify that all the data is correct
        onView(withText(endsWith("second todo"))).perform(click());
        onView(withId(R.id.add_task_title_edit_text)).check(matches(withText("second todo")))

        onView(withId(R.id.add_task_title_edit_text)).perform(ViewActions.replaceText("NEW TITLE TESTING"))
        onView(withId(R.id.add_task_description_edit_text)).perform(ViewActions.replaceText("NEW DESCRIPTION TESTING"))
        onView(withId(R.id.save_task_fab)).perform(click())


        /*onView(withId(R.id.tasks_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(endsWith("TITLE12"))), click()))*/

        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(endsWith("NEW TITLE TESTING")))))
        onView(withText(endsWith("NEW TITLE TESTING"))).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun addTask() = runBlocking {
        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        Thread.sleep(1000)

        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(ViewActions.replaceText("TESTING TITLE"))
        onView(withId(R.id.add_task_description_edit_text)).perform(ViewActions.replaceText("TESTING DESCRIPTION"))
        onView(withId(R.id.save_task_fab)).perform(click())

        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(endsWith("TESTING TITLE")))))
        onView(withText(endsWith("TESTING TITLE"))).check(matches(isDisplayed()))
        onView(withText(endsWith("TESTING DESCRIPTION"))).check(matches(isDisplayed()))
        activityScenario.close()
    }

    @Test
    fun addTaskFromMenu() = runBlocking {
        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        Thread.sleep(1000)

        onView(withId(R.id.menu_filter)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(ViewActions.replaceText("TESTING TITLE"))
        onView(withId(R.id.add_task_description_edit_text)).perform(ViewActions.replaceText("TESTING DESCRIPTION"))
        onView(withId(R.id.save_task_fab)).perform(click())

        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(endsWith("TESTING TITLE")))))
        onView(withText(endsWith("TESTING TITLE"))).check(matches(isDisplayed()))
        onView(withText(endsWith("TESTING DESCRIPTION"))).check(matches(isDisplayed()))
        activityScenario.close()
    }
}