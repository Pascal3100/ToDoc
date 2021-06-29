package fr.plopez.todoc.view.add_task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.SingleLiveEvent;

public class AddTaskViewModel extends ViewModel {

    private final TasksRepository tasksRepository;

    private final LiveData<List<AddTaskViewState>> viewStateLiveData;

    private final SingleLiveEvent<AddTaskViewAction> addTaskSingleLiveEvent = new SingleLiveEvent<>();

    private Long currentlySelectedProjectId;
    private String taskSubject;

    public AddTaskViewModel(ProjectsRepository projectsRepository, TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        viewStateLiveData = Transformations.map(projectsRepository.getProjectListLiveData(), this::mapProjectsToListOfStrings);
    }

    public LiveData<List<AddTaskViewState>> getProjectListLiveData() {
        return viewStateLiveData;
    }

    private List<AddTaskViewState> mapProjectsToListOfStrings(List<Project> projectList) {
        List<AddTaskViewState> projectListOfStrings = new ArrayList<>();

        // This entry is for Spinner Hint and will not be selectable
        projectListOfStrings.add(new AddTaskViewState(0, "Select a project..."));

        for (Project project : projectList) {
            projectListOfStrings.add(new AddTaskViewState(project.getId(), project.getName()));
        }

        return projectListOfStrings;
    }

    public LiveData<AddTaskViewAction> getAddTaskSingleLiveEvent() {
        return addTaskSingleLiveEvent;
    }

    public void onAddTaskButtonClicked() {

        if (taskSubject == null || taskSubject.trim().isEmpty()) {
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE);
            return;
        }

        if (currentlySelectedProjectId == null || currentlySelectedProjectId == 0) {
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE);
            return;
        }

        tasksRepository.addTask(
                new Task(
                        currentlySelectedProjectId,
                        taskSubject,
                        Calendar.getInstance().getTimeInMillis()
                )
        );

        addTaskSingleLiveEvent.setValue(AddTaskViewAction.TASK_OK);
    }

    public void onProjectIdSelected(long projectId) {
        currentlySelectedProjectId = projectId;
    }

    public void onTaskTextChanged(String taskSubject) {
        this.taskSubject = taskSubject;
    }
}
