package fr.plopez.todoc.view.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.relations.ProjectWithTasks;
import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.data.utils.TasksSorterUtil;
import fr.plopez.todoc.utils.SingleLiveEvent;
import fr.plopez.todoc.view.add_task.AddTaskViewAction;
import fr.plopez.todoc.view.model.TaskViewState;

public class MainActivityViewModel extends ViewModel {

    private final ProjectsRepository projectsRepository;
    private final TasksRepository tasksRepository;
    private final FilterRepository filterRepository;
    private final LiveData<Integer> numberOfTaskLiveData;
    private final LiveData<PossibleSortMethods> sortMethodLiveData;

//    private final LiveData<List<Task>> tasksListLiveData;
//    private final MediatorLiveData<List<Task>> tasksListMediatorLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<List<TaskViewState>> projectsWithTasksMediatorLiveData = new MediatorLiveData<>();


    public MainActivityViewModel(@NonNull ProjectsRepository projectsRepository,
                                 @NonNull TasksRepository tasksRepository,
                                 @NonNull FilterRepository filterRepository){
        this.projectsRepository = projectsRepository;
        this.tasksRepository = tasksRepository;
        this.filterRepository = filterRepository;

//        tasksListLiveData = tasksRepository.getTaskListLiveData();
        LiveData<List<ProjectWithTasks>> projectsWithTasksLiveData =
                projectsRepository.getProjectWithTasksLiveData();

        // TODO : A virer!!
        numberOfTaskLiveData = tasksRepository.getNumberOfTaskLiveData();

        sortMethodLiveData = filterRepository.getRequiredSortingMethod();

        // Setting up MediatorLiveData
        projectsWithTasksMediatorLiveData.addSource(projectsWithTasksLiveData, projectsWithTasks ->
                combine(projectsWithTasks, sortMethodLiveData.getValue())
        );
        projectsWithTasksMediatorLiveData.addSource(sortMethodLiveData,
                requiredSortMethod -> combine(projectsWithTasksLiveData.getValue(), requiredSortMethod)
        );
    }

    private void combine(List<ProjectWithTasks> projectWithTasksList, PossibleSortMethods sortMethod){

        List<TaskViewState> taskViewStateList = mapTasksToTasksViewState(projectWithTasksList);

        projectsWithTasksMediatorLiveData.setValue(
                TasksSorterUtil.sortBy(sortMethod, taskViewStateList)
        );
    }

    public void deleteTask(long taskId){
        tasksRepository.deleteTask(taskId);
    }

    public LiveData<List<TaskViewState>> getTasksListMediatorLiveData(){
        return projectsWithTasksMediatorLiveData;
    }

    // Transforms ProjectWithTasks objects into TaskViewState objects
    private List<TaskViewState> mapTasksToTasksViewState(List<ProjectWithTasks> projectWithTaskList) {

        List<TaskViewState> taskViewStateList = new ArrayList<>();
        if (projectWithTaskList != null) {
            Project project;

            for (ProjectWithTasks projectWithTasks : projectWithTaskList) {
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

    public LiveData<Integer> getNumberOfTaskLiveData(){
        return numberOfTaskLiveData;
    }

    public void setSortingMethod(PossibleSortMethods requiredSortMethod) {
        filterRepository.setRequiredSortingMethod(requiredSortMethod);
    }
}
