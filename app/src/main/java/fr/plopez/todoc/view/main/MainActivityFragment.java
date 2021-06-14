package fr.plopez.todoc.view.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.ViewModelFactory;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.databinding.ActivityMainFragmentBinding;

public class MainActivityFragment extends Fragment implements DeleteTaskListener{

    private final DeleteTaskListener deleteTaskListener = (DeleteTaskListener) this;
    private ActivityMainFragmentBinding mainActivityFragmentBinding;
    private List<String> projectListForDialog = new ArrayList<>();
    private TasksViewModel tasksViewModel;
    private ListenerShowAddTaskMenu listenerShowAddTaskMenu;


    public MainActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MainActivityFragment.
     */
    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerShowAddTaskMenu) {
            listenerShowAddTaskMenu = (ListenerShowAddTaskMenu) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ListenerShowAddTaskMenu");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasksViewModel = new ViewModelProvider(
                this,
                ViewModelFactory.getInstance())
                .get(TasksViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainActivityFragmentBinding = ActivityMainFragmentBinding.inflate(inflater, container, false);
        View view = mainActivityFragmentBinding.getRoot();

        TasksAdapter tasksAdapter = new TasksAdapter(deleteTaskListener);
        mainActivityFragmentBinding.listTasks.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mainActivityFragmentBinding.listTasks.setAdapter(tasksAdapter);

        tasksViewModel.getTasksListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                tasksAdapter.updateTasks(tasks);
            }
        });

        tasksViewModel.getNumberOfTaskLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer numberOfTasks) {
                        if (numberOfTasks == 0) {
                            mainActivityFragmentBinding.listTasks.setVisibility(View.GONE);
                            mainActivityFragmentBinding.lblNoTask.setVisibility(View.VISIBLE);
                        } else {
                            mainActivityFragmentBinding.listTasks.setVisibility(View.VISIBLE);
                            mainActivityFragmentBinding.lblNoTask.setVisibility(View.GONE);
                        }
                    }
                });

                mainActivityFragmentBinding.fabAddTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenerShowAddTaskMenu.showAddTaskMenu();
                    }
                });

        tasksViewModel.getProjectListLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                projectListForDialog.clear();
                projectListForDialog.addAll(strings);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDeleteTask(long taskIdToDelete) {
        tasksViewModel.deleteTask(taskIdToDelete);
    }
}
