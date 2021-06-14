package fr.plopez.todoc.view.main;

import androidx.annotation.NonNull;
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

public class TasksViewModel extends ViewModel {

    private final ProjectsRepository projectsRepository;
    private final TasksRepository tasksRepository;
    private final SingleLiveEvent<AddTaskViewAction> addTaskSingleLiveEvent = new SingleLiveEvent<>();
    private final LiveData<List<Task>> tasksListLiveData;
    private final LiveData<Integer> numberOfTaskLiveData;
    private long taskId = 1;

    public TasksViewModel(@NonNull ProjectsRepository projectsRepository, @NonNull TasksRepository tasksRepository){
        this.projectsRepository = projectsRepository;
        this.tasksRepository = tasksRepository;
        tasksListLiveData = tasksRepository.getTaskListLiveData();
        numberOfTaskLiveData = tasksRepository.getNumberOfTaskLiveData();
    }

    public void addTask(String taskName, String projectName){
        Boolean taskOk = false;
        Boolean projectOk = false;

        if(taskName.trim().isEmpty()){
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE);
        } else {
            taskOk = true;
        }

        if (projectsRepository.getProjectByName(projectName) == null) {
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE);
        } else {
            projectOk = true;
        }

        if (taskOk && projectOk){
            addTaskSingleLiveEvent.setValue(AddTaskViewAction.TASK_OK);

            tasksRepository.addTask(new Task(
                    generateTaskId(),
                    projectsRepository.getProjectByName(taskName),
                    taskName,
                    Calendar.getInstance().getTimeInMillis()
            ));
        }
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

    public void deleteTask(long taskId){
        tasksRepository.deleteTask(taskId);
    }

    public LiveData<List<Task>> getTasksListLiveData(){
        return tasksListLiveData;
    }

    public LiveData<Integer> getNumberOfTaskLiveData(){
        return numberOfTaskLiveData;
    }

    private long generateTaskId() {
        return taskId++;
    }

    public LiveData<AddTaskViewAction> getAddTaskSingleLiveEvent(){
        return addTaskSingleLiveEvent;
    }
}
