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
    private final String GOOD_PROJECT_NAME = "GOOD_PROJECT_NAME";
    private final String NULL_TASK_NAME = null;

    private final Project PROJECT = new Project(GOOD_PROJECT_NAME, 0xFFA3CED2);
    private final long ZERO_PROJECT_ID = 0;
    private final long PROJECT_ID = 1;

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
    private final MutableLiveData<List<Project>> projectListMutableLiveData = new MutableLiveData<>();


    @Before
    public void setUp() {
        wireUpProjectsRepositoryMock();
        addTaskViewModel = new AddTaskViewModel(projectsRepositoryMock, tasksRepositoryMock);
    }

    // Verify tasks are correctly added
    @Test
    public void add_task_nominal_test() throws InterruptedException {
        // Given
        addTaskViewModel.onProjectIdSelected(PROJECT_ID);
        String NEW_TASK_NAME = "NEW_TASK_NAME";
        addTaskViewModel.onTaskTextChanged(NEW_TASK_NAME);
        addTaskViewModel.onAddTaskButtonClicked();

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
        addTaskViewModel.onProjectIdSelected(ZERO_PROJECT_ID);
        addTaskViewModel.onTaskTextChanged(NULL_TASK_NAME);
        addTaskViewModel.onAddTaskButtonClicked();

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
        addTaskViewModel.onProjectIdSelected(PROJECT_ID);
        addTaskViewModel.onTaskTextChanged(NULL_TASK_NAME);
        addTaskViewModel.onAddTaskButtonClicked();

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
        addTaskViewModel.onProjectIdSelected(PROJECT_ID);
        String EMPTY_TASK_NAME = "";
        addTaskViewModel.onTaskTextChanged(EMPTY_TASK_NAME);
        addTaskViewModel.onAddTaskButtonClicked();

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
        addTaskViewModel.onProjectIdSelected(ZERO_PROJECT_ID);
        String TASK_NAME = "TASK_NAME";
        addTaskViewModel.onTaskTextChanged(TASK_NAME);
        addTaskViewModel.onAddTaskButtonClicked();

        // When
        AddTaskViewAction resultErrorMessage = LiveDataTestUtils
                .getOrAwaitValue(addTaskViewModel.getAddTaskSingleLiveEvent());

        // Then
        assertEquals(AddTaskViewAction.DISPLAY_PROJECT_EMPTY_MESSAGE, resultErrorMessage);
        Mockito.verify(tasksRepositoryMock, times(0)).addTask(Mockito.any(Task.class));
    }

    // region IN
    private void wireUpProjectsRepositoryMock() {
        projectListMutableLiveData.setValue(Arrays.asList(PROJECT));

        Mockito.doReturn(projectListMutableLiveData)
                .when(projectsRepositoryMock)
                .getProjectListLiveData();
    }
    // endregion




}
