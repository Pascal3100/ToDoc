package fr.plopez.todoc.view.add_task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
    private final LiveData<List<Project>> projectsListLiveData;
    private final TasksRepository tasksRepository;
    private final SingleLiveEvent<AddTaskViewAction> addTaskSingleLiveEvent = new SingleLiveEvent<>();
    private final MutableLiveData<String> taskSubjectMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> currentlySelectedProjectMutableLiveData = new MutableLiveData<>();
    private final MediatorLiveData<Boolean> taskGenMediatorLiveData =
            new MediatorLiveData<>();
    private AddTaskViewAction addTaskViewAction;
    private Project currentlySelectedProject;
    private String taskSubject;

    public AddTaskViewModel(ProjectsRepository projectsRepository, TasksRepository tasksRepository) {
        this.projectsRepository = projectsRepository;
        this.tasksRepository = tasksRepository;
        projectsListLiveData = projectsRepository.getProjectListLiveData();

        // Setting up mediatorLiveData to allow Task creation
        taskGenMediatorLiveData.addSource(
                taskSubjectMutableLiveData, new Observer<String>() {
                    @Override
                    public void onChanged(String taskName) {
                        combineToAddTask(taskName,
                                currentlySelectedProjectMutableLiveData.getValue(),
                                projectsListLiveData.getValue());
                    }
                });

        taskGenMediatorLiveData.addSource(
                currentlySelectedProjectMutableLiveData, new Observer<String>() {
                    @Override
                    public void onChanged(String projectName) {
                        combineToAddTask(taskSubjectMutableLiveData.getValue(),
                                projectName,
                                projectsListLiveData.getValue());

                    }
                });

        taskGenMediatorLiveData.addSource(
                projectsListLiveData, new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projectList) {
                        combineToAddTask(taskSubjectMutableLiveData.getValue(),
                                currentlySelectedProjectMutableLiveData.getValue(),
                                projectList);
                    }
                });
    }

    private void combineToAddTask(String taskSubject, String projectName, List<Project> projectList){

        this.taskSubject = null;
        this.currentlySelectedProject = null;

        if(taskSubject == null || taskSubject.trim().isEmpty()){
            addTaskViewAction = AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE;
            taskGenMediatorLiveData.setValue(false);
            return;
        }

        this.taskSubject = taskSubject;

        if (projectName == null) {
            addTaskViewAction = AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE;
            taskGenMediatorLiveData.setValue(false);
            return;
        }

        for (Project project : projectList) {
            if (project.getName().equals(projectName))
                currentlySelectedProject = project;
        }

        addTaskViewAction = AddTaskViewAction.TASK_OK;
    }

    public LiveData<Boolean> getTaskGenMediatorLiveData(){
        return taskGenMediatorLiveData;
    }

    public LiveData<List<String>> getProjectListLiveData(){
        return Transformations.map(projectsListLiveData, this::mapProjectsToListOfStrings);
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

    public void addTask(){
        addTaskSingleLiveEvent.setValue(addTaskViewAction);

        if (addTaskViewAction == AddTaskViewAction.TASK_OK){
            tasksRepository.addTask(new Task(
                    currentlySelectedProject.getId(),
                    taskSubject,
                    Calendar.getInstance().getTimeInMillis()
            ));
        }
    }

    public void setSelectedProject(String projectName) {
        currentlySelectedProjectMutableLiveData.setValue(projectName);
    }

    public void setTaskSubject(String taskSubject) {
        taskSubjectMutableLiveData.setValue(taskSubject);
    }
}
