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
import fr.plopez.todoc.data.model.ProjectWithTasks;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.data.utils.TasksSorterUtil;
import fr.plopez.todoc.utils.SingleLiveEvent;

public class TasksViewModel extends ViewModel {

    private final ProjectsRepository projectsRepository;
    private final TasksRepository tasksRepository;
    private final FilterRepository filterRepository;
    private final SingleLiveEvent<AddTaskViewAction> addTaskSingleLiveEvent = new SingleLiveEvent<>();
    private final LiveData<Integer> numberOfTaskLiveData;
    private final LiveData<PossibleSortMethods> sortMethodLiveData;

    private final MediatorLiveData<List<TaskViewState>> projectsWithTasksMediatorLiveData = new MediatorLiveData<>();

    public TasksViewModel(@NonNull ProjectsRepository projectsRepository,
                          @NonNull TasksRepository tasksRepository,
                          @NonNull FilterRepository filterRepository){
        this.projectsRepository = projectsRepository;
        this.tasksRepository = tasksRepository;
        this.filterRepository = filterRepository;
        LiveData<List<ProjectWithTasks>> projectsWithTasksLiveData = projectsRepository.getProjectsWithTasksLiveData();
        numberOfTaskLiveData = tasksRepository.getNumberOfTaskLiveData();
        sortMethodLiveData = filterRepository.getRequiredSortingMethod();

        projectsWithTasksMediatorLiveData.addSource(projectsWithTasksLiveData, projectsWithTasks ->
            combine(projectsWithTasks, sortMethodLiveData.getValue())
        );
        projectsWithTasksMediatorLiveData.addSource(sortMethodLiveData, requiredSortMethod ->
            combine(projectsWithTasksLiveData.getValue(), requiredSortMethod)
        );

        // For a Dev purpose only
//        if (numberOfTaskLiveData.getValue() == 0) {
//            addTask("aller à intermarché", "Awesome Project");
//            addTask("faire la cuisine", "Miraculous Actions");
//            addTask("demander la tondeuse", "Circus Project");
//            addTask("finir le P5", "Awesome Project");
//        }
    }

    private void combine(List<ProjectWithTasks> projectsWithTasks, PossibleSortMethods sortMethod){
        // TODO Sorting à revoir ! ça fonctionne différemment avec ProjectWithTasks
//        List<Task> tasks;
//        tasks = TasksSorterUtil.sortBy(sortMethod, taskList);
        projectsWithTasksMediatorLiveData.setValue(mapTasksToTasksViewState(projectsWithTasks));
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

    public LiveData<List<TaskViewState>> getTasksListMediatorLiveData(){
        return projectsWithTasksMediatorLiveData;
    }

    private List<TaskViewState> mapTasksToTasksViewState(List<ProjectWithTasks> projectsWithTasks) {
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

    public LiveData<AddTaskViewAction> getAddTaskSingleLiveEvent(){
        return addTaskSingleLiveEvent;
    }

    public void setSortingMethod(PossibleSortMethods requiredSortMethod) {
        filterRepository.setRequiredSortingMethod(requiredSortMethod);
    }
}
