package fr.plopez.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.annotation.UiThreadTest;
import androidx.test.espresso.base.MainThread;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import fr.plopez.todoc.data.Dao.ProjectsDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.relations.ProjectWithTasks;
import fr.plopez.todoc.data.repositories.TasksDatabase;

import static fr.plopez.todoc.utils.LiveDataTestUtils.getOrAwaitValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class ProjectsDaoTest {

    // Rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Class variables
    private TasksDatabase tasksDatabase;
    private ProjectsDao projectsDao;
    private Project anyProject;

    @Before
    public void setUp() {

        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        // Creation of in-memory DB
        tasksDatabase = Room.inMemoryDatabaseBuilder(
                context,
                TasksDatabase.class)
                .build();

        projectsDao = tasksDatabase.projectsDao();

        int ANY_COLOR = 0x00000;
        anyProject = new Project(
                "ANY_PROJECT", ANY_COLOR);

    }

    @After
    public void closeDb() {
        // Close the db and as it is an in-memory Db, it will be destroyed
        tasksDatabase.close();
    }

    // Verify both insert and get projects
    @Test
    public void insert_project_inside_database_test() throws InterruptedException {

        // Given

        // When
        projectsDao.insertProject(anyProject);
        List<Project> dbResult = getOrAwaitValue(projectsDao.getAllProjects());

        // Then
        assertEquals(1, dbResult.size());
        assert(anyProject.toString().equals(dbResult.get(0).toString()));
    }

    // Verify delete all projects from table projects
    @Test
    public void delete_all_project_inside_database_test() throws InterruptedException {

        List<Project> dbResult;

        // Given - inserting 3 projects
        projectsDao.insertProject(anyProject);
        projectsDao.insertProject(anyProject);
        projectsDao.insertProject(anyProject);
        dbResult = getOrAwaitValue(projectsDao.getAllProjects());
        assertEquals(3, dbResult.size());

        // When
        projectsDao.deleteAllProjects();
        dbResult = getOrAwaitValue(projectsDao.getAllProjects());

        // Then
        assertEquals(0, dbResult.size());
    }

    // Verify projectWithTasks objects are correctly given
    @Test
    public void get_project_with_task_test() throws InterruptedException {

        List<ProjectWithTasks> dbResult;

        // Given - inserting 3 projects
        projectsDao.insertProject(anyProject);

        // When
        dbResult = getOrAwaitValue(projectsDao.getAllProjectsWithTasks());

        // Then
        assertEquals(1, dbResult.size());
    }
}
