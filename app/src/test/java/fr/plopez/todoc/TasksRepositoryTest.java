package fr.plopez.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import fr.plopez.todoc.data.Dao.TasksDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.App;
import fr.plopez.todoc.utils.LiveDataTestUtils;

import static org.junit.Assert.assertEquals;

public class TasksRepositoryTest {

    // Constants
    private final String NEW_TASK_NAME = "NEW_TASK_NAME";
    private final String GOOD_PROJECT_NAME = "GOOD_PROJECT_NAME";

    private final Project PROJECT = new Project(GOOD_PROJECT_NAME, 0xFFA3CED2);
//    private final Task NEW_TASK = new Task(PROJECT, NEW_TASK_NAME, 100004);

    // Rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Mock
    @Mock
    private TasksDao tasksDaoMock;

    // Class variables
    private TasksRepository tasksRepository;


    @Before
    public void setUp(){
        tasksRepository = new TasksRepository(tasksDaoMock);
    }

//    // verify that tasks are correctly added
//    @Test
//    public void add_Task_test() throws InterruptedException {
//        // Given
//
//        // When
//        tasksRepository.addTask(NEW_TASK);
//        List<Task> resultListTask = LiveDataTestUtils
//                .getOrAwaitValue(tasksRepository.getTaskListLiveData());
//        int resultNumberOfTasks = LiveDataTestUtils
//                .getOrAwaitValue(tasksRepository.getNumberOfTaskLiveData());
//
//        // Then
//        assertEquals(NEW_TASK, resultListTask.get(0));
//        assertEquals(1, resultNumberOfTasks);
//    }
//
//    // verify that tasks are correctly added
//    @Test
//    public void delete_Task_test() throws InterruptedException {
//        // Given
//        int resultNumberOfTasks = LiveDataTestUtils
//                .getOrAwaitValue(tasksRepository.getNumberOfTaskLiveData());
//        assertEquals(0, resultNumberOfTasks);
//        tasksRepository.addTask(NEW_TASK);
//        resultNumberOfTasks = LiveDataTestUtils
//                .getOrAwaitValue(tasksRepository.getNumberOfTaskLiveData());
//        assertEquals(1, resultNumberOfTasks);
//
//        // When
//        tasksRepository.deleteTask(NEW_TASK.getId());
//        resultNumberOfTasks = LiveDataTestUtils
//                .getOrAwaitValue(tasksRepository.getNumberOfTaskLiveData());
//
//        // Then
//        assertEquals(0, resultNumberOfTasks);
//
//    }
}
