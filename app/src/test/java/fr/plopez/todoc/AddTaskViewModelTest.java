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
import java.util.Arrays;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.LiveDataTestUtils;
import fr.plopez.todoc.view.add_task.AddTaskViewAction;
import fr.plopez.todoc.view.add_task.AddTaskViewModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AddTaskViewModelTest {

    // Constants
    private final String AA_TASK_NAME = "AA_TASK";
    private final String HH_TASK_NAME = "HH_TASK";
    private final String ZZ_TASK_NAME = "ZZ_TASK";
    private final String NEW_TASK_NAME = "NEW_TASK_NAME";
    private final String GOOD_PROJECT_NAME = "GOOD_PROJECT_NAME";
    private final String NULL_PROJECT_NAME = null;
    private final String NULL_TASK_NAME = null;

    private final Project PROJECT = new Project(GOOD_PROJECT_NAME, 0xFFA3CED2);
    private final long PROJECT_ID = 1;
    private final Task AA_TASK = new Task(PROJECT_ID, AA_TASK_NAME, 100001);
    private final Task HH_TASK = new Task(PROJECT_ID, HH_TASK_NAME, 100002);
    private final Task ZZ_TASK = new Task(PROJECT_ID, ZZ_TASK_NAME, 100003);
    private final Task NEW_TASK = new Task(PROJECT_ID, NEW_TASK_NAME, 100004);
    private final int NOMINAL_NUMBER_OF_TASKS = 3;

    // Rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Mocks
    @Mock
    private ProjectsRepository projectsRepositoryMock;

    @Mock
    private TasksRepository tasksRepositoryMock;

    // Class variables
    private AddTaskViewModel addTaskViewModel;
    private final MutableLiveData<List<Task>> taskListMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> projectListMutableLiveData = new MutableLiveData<>();


    @Before
    public void setUp() {
        wireUpProjectsRepositoryMock();
        wireUpTasksRepositoryMock();
        addTaskViewModel = new AddTaskViewModel(projectsRepositoryMock, tasksRepositoryMock);
    }

    // Verify tasks are correctly added
    @Test
    public void add_task_nominal_test() throws InterruptedException {
        // Given

        // Nothing to do, just observe to activate it
        Boolean resultBoolean = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getTaskGenMediatorLiveData());

        addTaskViewModel.setSelectedProject(GOOD_PROJECT_NAME);
        addTaskViewModel.setTaskSubject(NEW_TASK_NAME);
        addTaskViewModel.addTask();

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.TASK_OK, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(1)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_null_both_input_test() throws InterruptedException {
        // Given
        addTaskViewModel.setSelectedProject(NULL_PROJECT_NAME);
        addTaskViewModel.setTaskSubject(NULL_TASK_NAME);
        addTaskViewModel.addTask();

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_null_task_input_test() throws InterruptedException {
        // Given
        addTaskViewModel.setSelectedProject(GOOD_PROJECT_NAME);
        addTaskViewModel.setTaskSubject(NULL_TASK_NAME);
        addTaskViewModel.addTask();

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_empty_task_input_test() throws InterruptedException {
        // Given
        addTaskViewModel.setSelectedProject(GOOD_PROJECT_NAME);
        String EMPTY_TASK_NAME = "";
        addTaskViewModel.setTaskSubject(EMPTY_TASK_NAME);
        addTaskViewModel.addTask();

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_TASK_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // Verify tasks are not added when errors in inputs
    @Test
    public void add_task_errors_null_project_input_test() throws InterruptedException {
        // Given
        addTaskViewModel.setSelectedProject(NULL_PROJECT_NAME);
        String TASK_NAME = "TASK_NAME";
        addTaskViewModel.setTaskSubject(TASK_NAME);
        addTaskViewModel.addTask();

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
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
        Mockito.doReturn(taskListMutableLiveData)
                .when(tasksRepositoryMock)
                .getTaskListLiveData();
    }

    private void wireUpProjectsRepositoryMock() {
        projectListMutableLiveData.setValue(Arrays.asList(PROJECT));

        Mockito.doReturn(projectListMutableLiveData)
                .when(projectsRepositoryMock)
                .getProjectListLiveData();
    }
    // endregion




}
