package fr.plopez.todoc.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

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

        TasksViewModel tasksViewModel = new ViewModelProvider(
                this,
                ViewModelFactory.getInstance())
                .get(TasksViewModel.class);

        dialogFragmentAddTaskBinding = DialogFragmentAddTaskBinding.inflate(getLayoutInflater());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogFragmentAddTaskBinding.projectSpinner.setAdapter(adapter);

        tasksViewModel.getProjectListLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> projectsNamesList) {
                adapter.clear();
                adapter.addAll(projectsNamesList);
                adapter.notifyDataSetChanged();
            }
        });

        dialogFragmentAddTaskBinding.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                String spinnerContent = null;
                if (dialogFragmentAddTaskBinding.projectSpinner.getSelectedItem() != null) {
                    spinnerContent = dialogFragmentAddTaskBinding.projectSpinner.getSelectedItem().toString();
                }

                tasksViewModel.addTask(
                        dialogFragmentAddTaskBinding.txtTaskName.getText().toString(),
                        spinnerContent
                );
            }
        });

        tasksViewModel.getAddTaskSingleLiveEvent().observe(this, viewAction -> {
            if (viewAction != AddTaskViewAction.TASK_OK){
                showSnackBarWarning(viewAction.getMessage());
            } else {
                dismiss();
            }
        });



        return dialogFragmentAddTaskBinding.getRoot();
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
