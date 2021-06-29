package fr.plopez.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import fr.plopez.todoc.data.Dao.ProjectsDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.repositories.ProjectsRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ProjectsRepositoryTest {

    // constants

    // rules
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Mocks
    @Mock
    private ProjectsDao projectsDaoMock;

    // class variables
    private ProjectsRepository projectsRepository;

    @Before
    public void SetUp(){
        projectsRepository = new ProjectsRepository(projectsDaoMock);
    }

    // Verify that the correct method of the DAO is called
    @Test
    public void verify_getAllProjects_DAO_method_is_called_test() {

        // Given

        // When
        projectsRepository.getProjectListLiveData();

        // Then
        Mockito.verify(projectsDaoMock, Mockito.times(1))
                .getAllProjects();
    }

    // Verify that the correct method of the DAO is called
    @Test
    public void verify_getAllProjectsWithTasks_DAO_method_is_called_test() {

        // Given

        // When
        projectsRepository.getProjectWithTasksLiveData();

        // Then
        Mockito.verify(projectsDaoMock, Mockito.times(1))
                .getAllProjectsWithTasks();
    }
}
