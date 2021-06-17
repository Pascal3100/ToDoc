package fr.plopez.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.view.main.PossibleSortMethods;
import fr.plopez.todoc.view.main.TasksViewModel;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class TasksViewModelTest {

    // Constants
    private final String AA_TASK_NAME = "AA_TASK";
    private final String HH_TASK_NAME = "HH_TASK";
    private final String ZZ_TASK_NAME = "ZZ_TASK";
    private final String NEW_TASK_NAME = "NEW_TASK_NAME";
    private final String GOOD_PROJECT_NAME = "GOOD_PROJECT_NAME";
    private final String BAD_PROJECT_NAME = "BAD_PROJECT_NAME";
    private final String NULL_PROJECT_NAME = null;
    private final String TASK_NAME = "TASK_NAME";
    private final String NULL_TASK_NAME = null;
    private final String EMPTY_TASK_NAME = "";

    private final Project PROJECT = new Project(1, GOOD_PROJECT_NAME, 0xFFA3CED2);
    private final Task AA_TASK = new Task(1, PROJECT, AA_TASK_NAME, 100001);
    private final Task HH_TASK = new Task(2, PROJECT, HH_TASK_NAME, 100002);
    private final Task ZZ_TASK = new Task(3, PROJECT, ZZ_TASK_NAME, 100003);
    private final Task NEW_TASK = new Task(4, PROJECT, NEW_TASK, 100004);
    private final int NOMINAL_NUMBER_OF_TASKS = 3;

    // Rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Mocks
    @Mock
    private ProjectsRepository projectsRepositoryMock;
    @Mock
    private TasksRepository tasksRepositoryMock;
    @Mock
    private FilterRepository filterRepositoryMock;


    // Class variables
    private final MutableLiveData<List<Task>> taskListMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<PossibleSortMethods> sortMethodMutableLiveData = new MutableLiveData<>();
    private TasksViewModel tasksViewModel;

    @Before
    public void setUp() {
        wireUpProjectsRepositoryMock();
        wireUpTasksRepositoryMock();
        wireUpFilterRepositoryMock();
        tasksViewModel = new TasksViewModel(projectsRepositoryMock, tasksRepositoryMock, filterRepositoryMock);
    }

    // Verify tasks are correctly added
    @Test
    public void add_task_nominal_test() throws InterruptedException {
        // Given
        tasksViewModel.addTask(NEW_TASK_NAME, GOOD_PROJECT_NAME);

        // When
        List<Task> resultListTasks = LiveDataTestUtils
                .getOrAwaitValue(tasksViewModel.getTasksListMediatorLiveData());
        int resultNumberOfTasks = LiveDataTestUtils
                .getOrAwaitValue(tasksViewModel.getNumberOfTaskLiveData());

        // Then
        assertEquals(NEW_TASK, resultListTasks.get(0));
        assertEquals(NOMINAL_NUMBER_OF_TASKS+1, resultNumberOfTasks);
    }

    @Test
    public void delete_task_test(){
        // Given

        // When

        // Then

    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_test(){

    }




    // region IN
    private List<Task> getTaskList() {

        List<Task> taskList = new ArrayList<>();
        taskList.add(AA_TASK);
        taskList.add(HH_TASK);
        taskList.add(ZZ_TASK);

        return taskList;
    }

    private void wireUpTasksRepositoryMock() {
        taskListMutableLiveData.setValue(getTaskList());

        Mockito.doReturn(taskListMutableLiveData)
                .when(tasksRepositoryMock)
                .getTaskListLiveData();
        Mockito.doReturn(taskListMutableLiveData.getValue().size())
                .when(tasksRepositoryMock)
                .getNumberOfTaskLiveData();
    }

    private void wireUpProjectsRepositoryMock() {
        MutableLiveData<List<Project>> projectListMutableLiveData = new MutableLiveData<>();
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(PROJECT);
        projectListMutableLiveData.setValue(projectsList);

        Mockito.doReturn(projectListMutableLiveData)
                .when(projectsRepositoryMock)
                .getProjectListLiveData();
        Mockito.doReturn(PROJECT)
                .when(projectsRepositoryMock)
                .getProjectByName(GOOD_PROJECT_NAME);
        Mockito.doReturn(null)
                .when(projectsRepositoryMock)
                .getProjectByName(BAD_PROJECT_NAME);
        Mockito.doReturn(null)
                .when(projectsRepositoryMock)
                .getProjectByName(NULL_PROJECT_NAME);
    }

    private void wireUpFilterRepositoryMock() {
        sortMethodMutableLiveData.setValue(PossibleSortMethods.RECENT_FIRST);
        Mockito.doReturn(sortMethodMutableLiveData)
                .when(filterRepositoryMock)
                .getRequiredSortingMethod();
    }

    // endregion

}
