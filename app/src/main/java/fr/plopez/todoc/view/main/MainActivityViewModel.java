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
    private final LiveData<List<Task>> tasksListLiveData;
    private final LiveData<Integer> numberOfTaskLiveData;
    private final LiveData<PossibleSortMethods> sortMethodLiveData;

    private final MediatorLiveData<List<Task>> tasksListMediatorLiveData = new MediatorLiveData<>();

    public MainActivityViewModel(@NonNull ProjectsRepository projectsRepository,
                                 @NonNull TasksRepository tasksRepository,
                                 @NonNull FilterRepository filterRepository){
        this.projectsRepository = projectsRepository;
        this.tasksRepository = tasksRepository;
        this.filterRepository = filterRepository;
        tasksListLiveData = tasksRepository.getTaskListLiveData();
        numberOfTaskLiveData = tasksRepository.getNumberOfTaskLiveData();
        sortMethodLiveData = filterRepository.getRequiredSortingMethod();

        tasksListMediatorLiveData.addSource(tasksListLiveData,
                taskList -> combine(taskList, sortMethodLiveData.getValue()));
        tasksListMediatorLiveData.addSource(sortMethodLiveData,
                requiredSortMethod -> combine(tasksListLiveData.getValue(), requiredSortMethod));
    }

    private void combine(List<Task> taskList, PossibleSortMethods sortMethod){
        List<Task> tasks;
        tasks = TasksSorterUtil.sortBy(sortMethod, taskList);
        tasksListMediatorLiveData.setValue(tasks);
    }

    public void deleteTask(long taskId){
        tasksRepository.deleteTask(taskId);
    }

    public LiveData<List<TaskViewState>> getTasksListMediatorLiveData(){
        return Transformations.map(tasksListMediatorLiveData, this::mapTasksToTasksViewState);
    }

    private List<TaskViewState> mapTasksToTasksViewState(List<Task> taskList) {
        List<TaskViewState> taskViewStateList = new ArrayList<>();
        for (Task task : taskList) {
            Project project = projectsRepository.getProjectById(task.getProjectId());
            taskViewStateList.add(new TaskViewState(
                    task.getId(),
                    task.getName(),
                    project.getColor(),
                    project.getName()));
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
