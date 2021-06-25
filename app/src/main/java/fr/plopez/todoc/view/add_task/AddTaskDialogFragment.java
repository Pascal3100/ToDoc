package fr.plopez.todoc.view.add_task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import fr.plopez.todoc.R;
import fr.plopez.todoc.ViewModelFactory;
import fr.plopez.todoc.databinding.DialogFragmentAddTaskBinding;

public class AddTaskDialogFragment extends DialogFragment {

    public static AddTaskDialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }
    private DialogFragmentAddTaskBinding dialogFragmentAddTaskBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Getting instance of viewModel
        AddTaskViewModel addTaskViewModel = new ViewModelProvider(
                this,
                ViewModelFactory.getInstance())
                .get(AddTaskViewModel.class);

        // Inflate Fragment and get viewBinding object
        dialogFragmentAddTaskBinding = DialogFragmentAddTaskBinding.inflate(getLayoutInflater());

        // Setting up the spinner
        spinnerSetup(addTaskViewModel);

        // Setting up the button to add the task
        dialogFragmentAddTaskBinding.addTaskButton.setOnClickListener(view1 -> {
            addTaskViewModel.setTaskSubject(dialogFragmentAddTaskBinding.txtTaskName.getText().toString());
            addTaskViewModel.addTask();
        });

        // Manage the events to notify the user on errors
        addTaskViewModel.getAddTaskSingleLiveEvent().observe(this, viewAction -> {
            if (viewAction != AddTaskViewAction.TASK_OK){
                showSnackBarWarning(viewAction.getMessage());
            } else {
                dismiss();
            }
        });

        addTaskViewModel.getTaskGenMediatorLiveData().observe(this, aBoolean -> {
            // Nothing to do, just observe to activate it
            // but could disable the add button for example
        });

        // returning view
        return dialogFragmentAddTaskBinding.getRoot();
    }

    // Setting up the spinner
    private void spinnerSetup(AddTaskViewModel addTaskViewModel) {
        // Setting up the spinner
        // -- setting up the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item){
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogFragmentAddTaskBinding.projectSpinner.setAdapter(adapter);

        // -- Notify viewModel each time a project is selected
        dialogFragmentAddTaskBinding.projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addTaskViewModel.setSelectedProject(
                        dialogFragmentAddTaskBinding.projectSpinner.getSelectedItem().toString()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // -- Filling the spinner by observing project list
        addTaskViewModel.getProjectListLiveData().observe(this, projectsNamesList -> {
            adapter.clear();
            adapter.addAll(projectsNamesList);
            adapter.notifyDataSetChanged();
        });
    }

    private void showSnackBarWarning(String message) {
        Snackbar snackbar = Snackbar.make(
                dialogFragmentAddTaskBinding.getRoot(),
                message,
                Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(getResources().getColor(R.color.redPinkish));
        snackbar.setTextColor(getResources().getColor(R.color.whiteGrey));
        snackbar.show();
    }
}
