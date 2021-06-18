package fr.plopez.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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

    // class variables
    private ProjectsRepository projectsRepository;

    @Before
    public void SetUp(){
        // Mock the project generator
        projectsRepository = new ProjectsRepository();
    }


    // Verify that the correct project is return when query by name
    @Test
    public void require_good_project_by_name_test() {

        // Given
        String PROJECT_TO_ASK = "Awesome Project";

        // When
        Project result = projectsRepository.getProjectByName(PROJECT_TO_ASK);

        // Then
        assertNotNull(result.getName());
        assertEquals(PROJECT_TO_ASK, result.getName());
    }

    // Verify that null is return when query by name
    @Test
    public void require_bad_project_by_name_test() {

        // Given
        String BAD_PROJECT_TO_ASK = "BAD_PROJECT_TO_ASK";

        // When
        Project result = projectsRepository.getProjectByName(BAD_PROJECT_TO_ASK);

        // Then
        assertNull(result);
    }


}
