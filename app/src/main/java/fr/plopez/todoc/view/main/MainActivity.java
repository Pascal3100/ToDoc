package fr.plopez.todoc.view.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import fr.plopez.todoc.R;
import fr.plopez.todoc.ViewModelFactory;
import fr.plopez.todoc.view.add_task.AddTaskDialogFragment;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY modified by Pascal Lopez
 */
public class MainActivity extends AppCompatActivity implements ListenerShowAddTaskMenu {

    private static final String ADD_TASK_DIALOG = "Add task dialog";


    // Action when a filtering item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        PossibleSortMethods requiredSortingMethod;
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            requiredSortingMethod = PossibleSortMethods.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            requiredSortingMethod = PossibleSortMethods.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            requiredSortingMethod = PossibleSortMethods.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            requiredSortingMethod = PossibleSortMethods.RECENT_FIRST;
        } else if (id == R.id.filter_by_project) {
            requiredSortingMethod = PossibleSortMethods.BY_PROJECT;
        } else {
            requiredSortingMethod = PossibleSortMethods.RECENT_FIRST;
        }

        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(
                this,
                ViewModelFactory.getInstance())
                .get(MainActivityViewModel.class);

        mainActivityViewModel.setSortingMethod(requiredSortingMethod);

        return super.onOptionsItemSelected(item);
    }

    // Generates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_activity_fragment_container, MainActivityFragment.newInstance(), null)
                    .commit();
        }
    }

    // Show the fragment
    @Override
    public void showAddTaskMenu() {
        AddTaskDialogFragment.newInstance().show(getSupportFragmentManager(), ADD_TASK_DIALOG);
    }
}
