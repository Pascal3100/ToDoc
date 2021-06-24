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
import fr.plopez.todoc.view.add_task.AddTaskViewAction;
import fr.plopez.todoc.view.main.PossibleSortMethods;
import fr.plopez.todoc.view.main.MainActivityViewModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

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
    private final Task NEW_TASK = new Task(4, PROJECT, NEW_TASK_NAME, 100004);
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
    private MainActivityViewModel mainActivityViewModel;

    @Before
    public void setUp() {
        wireUpProjectsRepositoryMock();
        wireUpTasksRepositoryMock();
        wireUpFilterRepositoryMock();
        mainActivityViewModel = new MainActivityViewModel(projectsRepositoryMock, tasksRepositoryMock, filterRepositoryMock);
    }

    // Verify tasks are correctly added
    @Test
    public void add_task_nominal_test() throws InterruptedException {
        // Given
        mainActivityViewModel.addTask(NEW_TASK_NAME, GOOD_PROJECT_NAME);

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.TASK_OK, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(1)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_null_both_input_test() throws InterruptedException {
        // Given
        mainActivityViewModel.addTask(NULL_TASK_NAME, NULL_PROJECT_NAME);

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_null_task_input_test() throws InterruptedException {
        // Given
        mainActivityViewModel.addTask(NULL_TASK_NAME, GOOD_PROJECT_NAME);

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_empty_task_input_test() throws InterruptedException {
        // Given
        mainActivityViewModel.addTask(EMPTY_TASK_NAME, GOOD_PROJECT_NAME);

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_null_project_input_test() throws InterruptedException {
        // Given
        mainActivityViewModel.addTask(TASK_NAME, NULL_PROJECT_NAME);

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    @Test
    public void delete_task_test(){
        // Given
        long SUPER_LONG_ID = 1000000101010L;

        // When
        mainActivityViewModel.deleteTask(SUPER_LONG_ID);

        // Then
        Mockito.verify(tasksRepositoryMock, times(1)).deleteTask(SUPER_LONG_ID);
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_default_most_recent_test() throws InterruptedException {
        // Given
        // Default sorting order is most recent first

        // When
        List<Task> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getTasksListMediatorLiveData());

        // Then
        assertEquals(ZZ_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(AA_TASK, result.get(2));
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_most_old_test() throws InterruptedException {
        // Given
        sortMethodMutableLiveData.setValue(PossibleSortMethods.OLD_FIRST);

        // When
        List<Task> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getTasksListMediatorLiveData());

        // Then
        assertEquals(AA_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(ZZ_TASK, result.get(2));
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_alphabetical_test() throws InterruptedException {
        // Given
        sortMethodMutableLiveData.setValue(PossibleSortMethods.ALPHABETICAL);

        // When
        List<Task> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getTasksListMediatorLiveData());

        // Then
        assertEquals(AA_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(ZZ_TASK, result.get(2));
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_invert_alphabetical_test() throws InterruptedException {
        // Given
        sortMethodMutableLiveData.setValue(PossibleSortMethods.ALPHABETICAL_INVERTED);

        // When
        List<Task> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getTasksListMediatorLiveData());

        // Then
        assertEquals(ZZ_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(AA_TASK, result.get(2));
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
        MutableLiveData<Integer> numberOfTasksLiveData = new MutableLiveData<Integer>(taskListMutableLiveData.getValue().size());

        Mockito.doReturn(taskListMutableLiveData)
                .when(tasksRepositoryMock)
                .getTaskListLiveData();
        Mockito.doReturn(numberOfTasksLiveData)
                .when(tasksRepositoryMock)
                .getNumberOfTaskLiveData();
        Mockito.doReturn(4L)
                .when(tasksRepositoryMock)
                .generateTaskId();
    }

    private void wireUpProjectsRepositoryMock() {
        MutableLiveData<List<Project>> projectListMutableLiveData = new MutableLiveData<>();
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(PROJECT);
        projectListMutableLiveData.setValue(projectsList);

        Mockito.doReturn(PROJECT)
                .when(projectsRepositoryMock)
                .getProjectByName(GOOD_PROJECT_NAME);
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
