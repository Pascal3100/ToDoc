package fr.plopez.todoc.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.ProjectWithTasks;

/**
 * <p>Project Repository to hold available projects</p>
 *
 * @author Pascal Lopez
 */
public class ProjectsRepository {

    @NonNull
    private final ProjectsDao projectsDao;

    /**
     * Instantiates a new Project.
     *
     */
    public ProjectsRepository(@NonNull ProjectsDao projectsDao){
        this.projectsDao = projectsDao;
    }

    public LiveData<List<Project>> getProjectListLiveData(){
        return projectsDao.getAllProjects();
    }

//    /**
//     * Returns the project with the given unique name, or null if no project with that
//     * identifier can be found.
//     *
//     * @param projectName the unique name of the project to return
//     * @return the project with the given unique identifier, or null if it has not been found
//     */
//    @Nullable
//    public LiveData<Project> getProjectByName(String projectName) {
//        if (projectListLiveData.getValue() == null){
//            return null;
//        }
//        for (Project project : projectListLiveData.getValue()) {
//            if (project.getName().equals(projectName))
//                return project;
//        }
//        return null;
//    }

//    @Nullable
//    public Project getProjectById(long projectId) {
//        if (projectListLiveData.getValue() == null){
//            return null;
//        }
//        for (Project project : projectListLiveData.getValue()) {
//            if (project.getId() == projectId) {
//                return project;
//            }
//        }
//        return null;
//    }

    public LiveData<List<ProjectWithTasks>> getProjectsWithTasksLiveData() {
        return projectsDao.getAllProjectsWithTasks();
    }
}
