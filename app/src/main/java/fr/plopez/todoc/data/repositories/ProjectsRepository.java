package fr.plopez.todoc.data.repositories;

import android.app.Application;

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

    private final LiveData<List<Project>> projectListLiveData;

    /**
     * Instantiates a new Project.
     *
     */
    public ProjectsRepository(Application application){
        TasksDatabase database = TasksDatabase.getDatabase(application);
        ProjectsDao projectsDao = database.projectsDao();
        projectListLiveData = projectsDao.getAllProjects();
    }

    public LiveData<List<Project>> getProjectListLiveData(){
        return projectListLiveData;
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
        if (projectListLiveData.getValue() == null){
            return null;
        }
        for (Project project : projectListLiveData.getValue()) {
            if (project.getName().equals(projectName))
                return project;
        }
        return null;
    }
    @Nullable
    public Project getProjectById(long projectId) {
        if (projectListLiveData.getValue() == null){
            return null;
        }
        for (Project project : projectListLiveData.getValue()) {
            if (project.getId() == projectId) {
                return project;
            }
        }
        return null;
    }
}
