package com.mvvm.endtoend.testing

import android.app.Activity
import android.view.Gravity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mvvm.endtoend.testing.util.EspressoIdlingResource
import com.mvvm.endtoend.testing.data.Task
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
class AppNavigationTest {

    private lateinit var tasksRepository: TaskRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        tasksRepository = ServiceLocator.provideTasksRepository(getApplicationContext())
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
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
    fun tasksScreen_clickOnAndroidHomeIcon_OpensNavigation() {
        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Check that left drawer is closed at startup
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))) // Left Drawer should be closed.

        // Open drawer by clicking drawer icon
        onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(click())

        // Check if drawer is open
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.START))) // Left drawer is open open.
        // When using ActivityScenario.launch, always call close()
        activityScenario.close()

    }

    @Test
    fun taskDetailScreen_doubleUpButton() = runBlocking {

        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        Thread.sleep(1000)
        tasksRepository.saveTask(Task(title = "Up <- button", description = "Description"))

        Thread.sleep(1000)

        // Click on the task on the list
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(endsWith("Up <- button")))))
        onView(withText(endsWith("Up <- button"))).perform(click())

        // Confirm that if we click up button a second time, we end up back at the home screen
        onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(click())
        onView(withId(R.id.add_task_fab)).check(matches(isDisplayed()))

        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }

    @Test
    fun taskDetailScreen_doubleBackButton() = runBlocking {

        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        Thread.sleep(1000)
        tasksRepository.saveTask(Task(title = "Back button", description = "Description"))

        // Click on the task on the list
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(endsWith("Back button")))))
        onView(withText(endsWith("Back button"))).perform(click())

        // Confirm that if we click back once, we end up back at the task details page
        pressBack()
        onView(withId(R.id.add_task_fab)).check(matches(isDisplayed()))

        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }
}

fun <T : Activity> ActivityScenario<T>.getToolbarNavigationContentDescription()
        : String {
    var description = ""
    onActivity {
        description =
            it.findViewById<Toolbar>(R.id.toolbar).navigationContentDescription as String
    }
    return description
}