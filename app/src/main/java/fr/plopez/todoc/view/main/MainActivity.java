package fr.plopez.todoc.view.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import fr.plopez.todoc.R;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY modified by Pascal Lopez
 */
public class MainActivity extends AppCompatActivity implements ListenerShowAddTaskMenu {

    private static final String ADD_TASK_DIALOG = "Add task dialog";

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

    @Override
    public void showAddTaskMenu() {
        AddTaskDialogFragment.newInstance().show(getSupportFragmentManager(), ADD_TASK_DIALOG);
    }
}
