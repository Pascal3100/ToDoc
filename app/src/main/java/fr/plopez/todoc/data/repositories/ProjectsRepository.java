package fr.plopez.todoc.data.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.utils.FakeProjectsGenerator;

/**
 * <p>Project Repository to hold available projects</p>
 *
 * @author Pascal Lopez
 */
public class ProjectsRepository {

    private final MutableLiveData<List<Project>> projectListLiveData = new MutableLiveData<>();
    private final List<Project> projectList = new ArrayList<>();

    /**
     * Instantiates a new Project.
     *
     */
    public ProjectsRepository(){
        FakeProjectsGenerator fakeProjectsGenerator = new FakeProjectsGenerator();
        projectList.addAll(fakeProjectsGenerator.generateFakeProjects());
        updateProjectListLiveData();
    }

    public LiveData<List<Project>> getProjectListLiveData(){
        return projectListLiveData;
    }


    private void updateProjectListLiveData() {
        projectListLiveData.setValue(projectList);
    }

    /**
     * Returns the project with the given unique name, or null if no project with that
     * identifier can be found.
     *
     * @param projectName the unique name of the project to return
     * @return the project with the given unique identifier, or null if it has not been found
     */
    @Nullable
    public Project getProjectByName(String projectName) {
        for (Project project : projectList) {
            if (project.getName().equals(projectName))
                return project;
        }
        return null;
    }
}
