package fr.plopez.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import fr.plopez.todoc.data.Dao.ProjectsDao;
import fr.plopez.todoc.data.Dao.TasksDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.relations.ProjectWithTasks;
import fr.plopez.todoc.data.repositories.TasksDatabase;

import static fr.plopez.todoc.utils.LiveDataTestUtils.getOrAwaitValue;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class TasksDaoTest {

    // Rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Class variables
    private TasksDatabase tasksDatabase;
    private TasksDao tasksDao;
    private Task anyTask;

    @Before
    public void setUp() {

        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        // Creation of in-memory DB
        tasksDatabase = Room.inMemoryDatabaseBuilder(
                context,
                TasksDatabase.class)
                .build();

        tasksDao = tasksDatabase.tasksDao();

        // Inserting a project
        ProjectsDao projectsDao = tasksDatabase.projectsDao();

        int ANY_COLOR = 0x00000;
        Project anyProject = new Project(
                "ANY_PROJECT", ANY_COLOR);

        projectsDao.insertProject(anyProject);

        anyTask = new Task(1, "DEVIL_TASK", 6666666);
    }

    @After
    public void closeDb() {
        // Close the db and as it is an in-memory Db, it will be destroyed
        tasksDatabase.close();
    }

    // Verify that inserting a task works
    @Test
    public void insert_task_test() throws InterruptedException {

        // Given

        // When
        tasksDao.insertTask(anyTask);
        List<Task> dbResult = getOrAwaitValue(tasksDao.getAllTasks());

        // Then
        assertEquals(1, dbResult.size());
        assert(anyTask.toString().equals(dbResult.get(0).toString()));
    }

    // Verify that deleting a task works
    @Test
    public void delete_task_test() throws InterruptedException {

        List<Task> dbResult;

        // Given
        tasksDao.insertTask(anyTask);
        dbResult = getOrAwaitValue(tasksDao.getAllTasks());
        assertEquals(1, dbResult.size());

        // When
        tasksDao.deleteTask(1);
        dbResult = getOrAwaitValue(tasksDao.getAllTasks());

        // Then
        assertEquals(0, dbResult.size());
    }

}
