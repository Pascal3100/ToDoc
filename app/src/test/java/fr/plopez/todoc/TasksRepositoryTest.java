package fr.plopez.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import fr.plopez.todoc.data.Dao.TasksDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.App;
import fr.plopez.todoc.utils.LiveDataTestUtils;
import fr.plopez.todoc.utils.TestExecutor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TasksRepositoryTest {

    // Constants
    private final String NEW_TASK_NAME = "NEW_TASK_NAME";
    private final Task NEW_TASK = new Task(666, NEW_TASK_NAME, 100004);

    // Rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Mock
    @Mock
    private TasksDao tasksDaoMock;

    @Spy
    private TestExecutor testExecutor = new TestExecutor();

    // Class variables
    private TasksRepository tasksRepository;


    @Before
    public void setUp(){
        tasksRepository = new TasksRepository(tasksDaoMock, testExecutor);
    }

    // verify that tasks are correctly added
    @Test
    public void add_Task_test() {

        // When
        tasksRepository.addTask(NEW_TASK);

        // Then
        Mockito.verify(testExecutor).execute(any());
        Mockito.verify(tasksDaoMock).insertTask(NEW_TASK);
    }

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
