package fr.plopez.todoc.data.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.utils.FakeProjectsGenerator;

public class ProjectsRepository {

    private final MutableLiveData<List<Project>> projectListLiveData = new MutableLiveData<>();
    private final List<Project> projectList = new ArrayList<>();

    public ProjectsRepository(){
        projectList.addAll(FakeProjectsGenerator.generateFakeProjects());
        updateProjectListLiveData();
    }

    public LiveData<List<Project>> getProjectListLiveData(){
        return projectListLiveData;
    }


    private void updateProjectListLiveData() {
        projectListLiveData.setValue(projectList);
    }

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param id the unique identifier of the project to return
     * @return the project with the given unique identifier, or null if it has not been found
     */
    @Nullable
    public Project getProjectById(long id) {
        for (Project project : projectList) {
            if (project.getId() == id)
                return project;
        }
        return null;
    }
    @Nullable
    public Project getProjectByName(String projectName) {
        for (Project project : projectList) {
            if (project.getName().equals(projectName))
                return project;
        }
        return null;
    }
}
