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

    private final ProjectsRepository projectsRepository;
    private final TasksRepository tasksRepository;
    private final SingleLiveEvent<AddTaskViewAction> addTaskSingleLiveEvent = new SingleLiveEvent<>();

    public AddTaskViewModel(ProjectsRepository projectsRepository, TasksRepository tasksRepository) {
        this.projectsRepository = projectsRepository;
        this.tasksRepository = tasksRepository;
    }

    public LiveData<List<String>> getProjectListLiveData(){
        return Transformations.map(projectsRepository.getProjectListLiveData(), this::mapProjectsToListOfStrings);
    }

    private List<String> mapProjectsToListOfStrings(List<Project> projectList) {
        List<String> projectListOfStrings = new ArrayList<>();

        // This entry is for Spinner Hint and will not be selectable
        projectListOfStrings.add("Select a project...");

        for (Project project: projectList) {
            projectListOfStrings.add(project.getName());
        }
        return projectListOfStrings;
    }

    public LiveData<AddTaskViewAction> getAddTaskSingleLiveEvent(){
        return addTaskSingleLiveEvent;
    }

    public void addTask(String taskName, String projectName){
        if(taskName == null || taskName.trim().isEmpty()){
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE);
            return;
        }

        if (projectsRepository.getProjectByName(projectName) == null) {
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE);
            return;
        }

        addTaskSingleLiveEvent.setValue(AddTaskViewAction.TASK_OK);

        tasksRepository.addTask(new Task(
                projectsRepository.getProjectByName(projectName).getId(),
                taskName,
                Calendar.getInstance().getTimeInMillis()
        ));
    }


}
