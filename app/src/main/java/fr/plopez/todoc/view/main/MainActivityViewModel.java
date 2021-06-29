package fr.plopez.todoc.view.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.relations.ProjectWithTasks;
import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.data.utils.TasksSorterUtil;
import fr.plopez.todoc.view.model.TaskViewState;

public class MainActivityViewModel extends ViewModel {

    private final TasksRepository tasksRepository;
    private final FilterRepository filterRepository;
    private final MutableLiveData<Boolean> isThereSomeTasks = new MutableLiveData<>(false);
    private final LiveData<PossibleSortMethods> sortMethodLiveData;

    private final MediatorLiveData<List<TaskViewState>> projectsWithTasksMediatorLiveData =
            new MediatorLiveData<>();


    public MainActivityViewModel(@NonNull ProjectsRepository projectsRepository,
                                 @NonNull TasksRepository tasksRepository,
                                 @NonNull FilterRepository filterRepository){
        this.tasksRepository = tasksRepository;
        this.filterRepository = filterRepository;

        LiveData<List<ProjectWithTasks>> projectsWithTasksLiveData =
                projectsRepository.getProjectWithTasksLiveData();

        sortMethodLiveData = filterRepository.getRequiredSortingMethod();

        // Setting up MediatorLiveData
        projectsWithTasksMediatorLiveData.addSource(projectsWithTasksLiveData, projectsWithTasks ->
                combine(projectsWithTasks, sortMethodLiveData.getValue())
        );
        projectsWithTasksMediatorLiveData.addSource(sortMethodLiveData, requiredSortMethod ->
                combine(projectsWithTasksLiveData.getValue(), requiredSortMethod)
        );
    }

    private void combine(List<ProjectWithTasks> projectWithTasksList, PossibleSortMethods sortMethod){

        List<TaskViewState> taskViewStateList = mapTasksToTasksViewState(projectWithTasksList);

        projectsWithTasksMediatorLiveData.setValue(
                TasksSorterUtil.sortBy(sortMethod, taskViewStateList)
        );

        int nbTasks = 0;
        if (projectWithTasksList != null) {
            for (ProjectWithTasks projectWithTasks : projectWithTasksList) {
                nbTasks += projectWithTasks.getTasks().size();
            }
        }
        if (nbTasks == 0){
            isThereSomeTasks.setValue(false);
        } else {
            isThereSomeTasks.setValue(true);
        }
    }

    public void deleteTask(long taskId){
        tasksRepository.deleteTask(taskId);
    }

    public LiveData<List<TaskViewState>> getProjectsWithTasksMediatorLiveData(){
        return projectsWithTasksMediatorLiveData;
    }

    // Transforms ProjectWithTasks objects into TaskViewState objects
    private List<TaskViewState> mapTasksToTasksViewState(List<ProjectWithTasks> projectWithTasksList) {

        List<TaskViewState> taskViewStateList = new ArrayList<>();
        if (projectWithTasksList != null) {
            Project project;

            for (ProjectWithTasks projectWithTasks : projectWithTasksList) {
                project = projectWithTasks.getProject();
                for (Task task : projectWithTasks.getTasks()) {
                    taskViewStateList.add(new TaskViewState(
                            task.getId(),
                            task.getName(),
                            project.getColor(),
                            project.getName()));
                }
            }
        }

        return taskViewStateList;
    }

    // Sets the current sorting method
    public void setSortingMethod(PossibleSortMethods requiredSortMethod) {
        filterRepository.setRequiredSortingMethod(requiredSortMethod);
    }

    // false is there is no tasks, else true
    public LiveData<Boolean> isThereSomeTaskLiveData(){
        return isThereSomeTasks;
    }
}
