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
import fr.plopez.todoc.data.relations.ProjectWithTasks;
import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.LiveDataTestUtils;
import fr.plopez.todoc.view.main.PossibleSortMethods;
import fr.plopez.todoc.view.main.MainActivityViewModel;
import fr.plopez.todoc.view.model.TaskViewState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    // Constants
    private final String AA_TASK_NAME = "AA_TASK_NAME";
    private final String HH_TASK_NAME = "HH_TASK_NAME";
    private final String ZZ_TASK_NAME = "ZZ_TASK_NAME";

    private final long AA_TASK_ID = 1;
    private final long HH_TASK_ID = 2;
    private final long ZZ_TASK_ID = 3;

    private final String GOOD_PROJECT_NAME = "GOOD_PROJECT_NAME";

    private final int PROJECT_COLOR = 0xFFA3CED2;

    private final TaskViewState AA_TASK =  new TaskViewState(AA_TASK_ID, AA_TASK_NAME, PROJECT_COLOR, GOOD_PROJECT_NAME);
    private final TaskViewState HH_TASK =  new TaskViewState(HH_TASK_ID, HH_TASK_NAME, PROJECT_COLOR, GOOD_PROJECT_NAME);
    private final TaskViewState ZZ_TASK =  new TaskViewState(ZZ_TASK_ID, ZZ_TASK_NAME, PROJECT_COLOR, GOOD_PROJECT_NAME);

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
    private final MutableLiveData<List<ProjectWithTasks>> projectWithTaskListMutableLiveData =
            new MutableLiveData<>();
    private final MutableLiveData<PossibleSortMethods> sortMethodMutableLiveData =
            new MutableLiveData<>();
    private MainActivityViewModel mainActivityViewModel;

    @Before
    public void setUp() {
        wireUpProjectsRepositoryMock();
        wireUpFilterRepositoryMock();
        mainActivityViewModel = new MainActivityViewModel(
                projectsRepositoryMock,
                tasksRepositoryMock,
                filterRepositoryMock);
    }

    // Verify that deleteTask method is call when deleting a Task
    @Test
    public void delete_task_test(){
        // Given
        long SUPER_LONG_ID = 1000000101010L;

        // When
        mainActivityViewModel.deleteTask(SUPER_LONG_ID);

        // Then
        Mockito.verify(tasksRepositoryMock,
                times(1))
                .deleteTask(SUPER_LONG_ID);
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_default_most_recent_test() throws InterruptedException {
        // Given
        // Default sorting order is most recent first

        // When
        List<TaskViewState> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getProjectsWithTasksMediatorLiveData());

        // Then
        assertEquals(ZZ_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(AA_TASK, result.get(2));
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_most_old_test() throws InterruptedException {
        // Given
        PossibleSortMethods sortMethod = PossibleSortMethods.OLD_FIRST;
        sortMethodMutableLiveData.setValue(sortMethod);
        mainActivityViewModel.setSortingMethod(sortMethod);

        // When
        List<TaskViewState> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getProjectsWithTasksMediatorLiveData());

        // Then
        assertEquals(AA_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(ZZ_TASK, result.get(2));
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_alphabetical_test() throws InterruptedException {
        // Given
        PossibleSortMethods sortMethod = PossibleSortMethods.ALPHABETICAL;
        sortMethodMutableLiveData.setValue(sortMethod);
        mainActivityViewModel.setSortingMethod(sortMethod);

        // When
        List<TaskViewState> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getProjectsWithTasksMediatorLiveData());

        // Then
        assertEquals(AA_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(ZZ_TASK, result.get(2));
    }

    // Verify that tasks are sorted the right way
    @Test
    public void sort_tasks_invert_alphabetical_test() throws InterruptedException {
        // Given
        PossibleSortMethods sortMethod = PossibleSortMethods.ALPHABETICAL_INVERTED;
        sortMethodMutableLiveData.setValue(sortMethod);
        mainActivityViewModel.setSortingMethod(sortMethod);

        // When
        List<TaskViewState> result = LiveDataTestUtils
                .getOrAwaitValue(mainActivityViewModel.getProjectsWithTasksMediatorLiveData());

        // Then
        assertEquals(ZZ_TASK, result.get(0));
        assertEquals(HH_TASK, result.get(1));
        assertEquals(AA_TASK, result.get(2));
    }

    // region IN
    private List<ProjectWithTasks> getProjectWithTaskList() {

        List<ProjectWithTasks> projectWithTaskList = new ArrayList<>();

        Project PROJECT = new Project("GOOD_PROJECT_NAME", 0xFFA3CED2);
        long PROJECT_ID = 1;
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(PROJECT_ID, "AA_TASK_NAME", 100001));
        taskList.add(new Task(PROJECT_ID, "HH_TASK_NAME", 100002));
        taskList.add(new Task(PROJECT_ID, "ZZ_TASK_NAME", 100003));
        // This simulates the autoincrement id of Room when insert in db
        taskList.get(0).setId(1);
        taskList.get(1).setId(2);
        taskList.get(2).setId(3);

        ProjectWithTasks projectWithTasks = new ProjectWithTasks();
        projectWithTasks.setProject(PROJECT);
        projectWithTasks.setTasks(taskList);

        projectWithTaskList.add(projectWithTasks);

        return projectWithTaskList;
    }

    private void wireUpProjectsRepositoryMock() {
        projectWithTaskListMutableLiveData.setValue(getProjectWithTaskList());
        Mockito.doReturn(projectWithTaskListMutableLiveData)
                .when(projectsRepositoryMock)
                .getProjectWithTasksLiveData();
    }

    private void wireUpFilterRepositoryMock() {
        PossibleSortMethods sortMethod = PossibleSortMethods.RECENT_FIRST;
        sortMethodMutableLiveData.setValue(sortMethod);
        Mockito.doReturn(sortMethodMutableLiveData)
                .when(filterRepositoryMock)
                .getRequiredSortingMethod();
    }

    // endregion

}
