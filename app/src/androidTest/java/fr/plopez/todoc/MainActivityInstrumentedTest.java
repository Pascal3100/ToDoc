package fr.plopez.todoc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import fr.plopez.todoc.utils.DeleteViewAction;
import fr.plopez.todoc.utils.RecyclerViewItemCountAssertion;
import fr.plopez.todoc.view.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static fr.plopez.todoc.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY modified by Pascal Lopez
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private static final String SELECTED_PROJECT = "Awesome Project";
    private static final String TASK_NAME_1 = "AA_Awesome new task";
    private static final String TASK_NAME_2 = "ZZ_Awesome new task";
    private static final String TASK_NAME_3 = "KK_Awesome new task";

    @Rule
    public final ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        ActivityScenario<MainActivity> activityScenario = activityScenarioRule.getScenario();
        assertThat(activityScenario, notNullValue());
    }

    // Verify initial conditions
    @Test
    public void initial_conditions_test(){
        // Verify that no tasks are available and recyclerView is GONE
        onView(withId(R.id.list_tasks))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // Verify that no tasks image is VISIBLE
        onView(withId(R.id.lbl_no_task))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    // Test that by pressing add floating action button the add task view appears
    @Test
    public void press_add_fab_go_to_add_task_menu_test() {
        // click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // verify that the add activity is correctly loaded
        onView(withId(R.id.add_task_fragment_dialog_container))
                .check(matches(isDisplayed()));
    }

    // Test that a new task is correctly added
    @Test
    public void add_task_test(){

        // Click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // Set a new task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_NAME_1));

        // Select a project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(is(SELECTED_PROJECT))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Click on add button
        onView(withId(R.id.add_task_button)).perform(click());

        // Verify that we are back to main screen
        onView(withId(R.id.main_activity_fragment_container))
                .check(matches(isDisplayed()));

        // Verify that no tasks are available and recyclerView is VISIBLE
        onView(withId(R.id.list_tasks))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // Verify that no tasks image is GONE
        onView(withId(R.id.lbl_no_task))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // Verify that a new task is added
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(1));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_1)));

    }

    // Test that a task is correctly deleted
    @Test
    public void delete_task_test(){

        // Add First Task
        // Click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // Set a new task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_NAME_1));

        // Select a project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(is(SELECTED_PROJECT))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Click on add button
        onView(withId(R.id.add_task_button)).perform(click());

        // Verify that we are back to main screen
        onView(withId(R.id.main_activity_fragment_container))
                .check(matches(isDisplayed()));

        // Add Second Task
        // Click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // Set a new task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_NAME_2));

        // Select a project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(is(SELECTED_PROJECT))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Click on add button
        onView(withId(R.id.add_task_button)).perform(click());

        // Verify that we are back to main screen
        onView(withId(R.id.main_activity_fragment_container))
                .check(matches(isDisplayed()));

        // Verify that a new tasks are added
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(2));

        // Delete the first created task
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(
                        0, new DeleteViewAction()));

        // Verify that task is deleted
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(1));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
        .check(matches(withText(TASK_NAME_1)));

        // Delete the first created task
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(
                        0, new DeleteViewAction()));

        // Verify that no tasks are available and recyclerView is GONE
        onView(withId(R.id.list_tasks))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // Verify that no tasks image is VISIBLE
        onView(withId(R.id.lbl_no_task))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void sortTasks() {

        // Add First Task
        // Click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // Set a new task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_NAME_1));

        // Select a project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(is(SELECTED_PROJECT))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Click on add button
        onView(withId(R.id.add_task_button)).perform(click());

        // Add Second Task
        // Click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // Set a new task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_NAME_2));

        // Select a project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(is(SELECTED_PROJECT))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Click on add button
        onView(withId(R.id.add_task_button)).perform(click());

        // Verify that a new tasks are added
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(2));

        // Add Third Task
        // Click on the floating action button to load the add activity
        onView(withId(R.id.fab_add_task)).perform(click());

        // Set a new task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_NAME_3));

        // Select a project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(is(SELECTED_PROJECT))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Click on add button
        onView(withId(R.id.add_task_button)).perform(click());

        // Verify that a new tasks are added
        onView(allOf(withId(R.id.list_tasks), isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(3));

        // Verify that the tasks are in the default order : most recent first
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_3)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_1)));

        // Sort alphabetical
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_1)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_3)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_2)));

        // Sort alphabetical inverted
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_3)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_1)));

        // Sort old first
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_1)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText(TASK_NAME_3)));
    }
}
